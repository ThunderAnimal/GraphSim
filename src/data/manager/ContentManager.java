package data.manager;

import data.connect.DBagent;
import data.misc.User;
import data.misc.Vorlagen;
import gui.controller.ITableContextMenu;
import system.Converter;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import constant.IPath;
import thread.task.TableWorkerThread;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author GraphSim
 * Klasse bilddet Schnittstelle zwischen Gui- Schicht und Verarbeitungsschicht
 * Gui ruft Klasse dieser Methoden auf um an die notwendigen Daten zum kommen
 * 
 * Klasse ist Singelton
 *
 */
public class ContentManager {
	static private ContentManager ref;
	
	private DBagent db;
	private FrankenStein frankenstein;
	private VorlagenManager vorlagenMang;
	private HashMap<String, ArrayList<String>> dbStructur;
	
	//Eingeloggter User
	private User user;
		
	
	/**
	 * Static
	 * @return ContentManager
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 * 
	 */
	static public ContentManager getInstance() throws FileNotFoundException, ClassNotFoundException, IOException{
		if(ref == null)
			ref = new ContentManager();
		
		return ref;
	}
	
	/**
	 * Private Konstruktor
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	private ContentManager() throws FileNotFoundException, ClassNotFoundException, IOException{
		db = new DBagent();
		frankenstein = new FrankenStein();
		dbStructur = null;
		
		//Vorlagen laden
		vorlagenMang = VorlagenManager.getInstance();
		vorlagenMang.loadAll();
	}
	
	/**
	 * Methode prüft Login --> wenn korrekt wird ein User Objet erstellt
	 * @param email
	 * @param pass
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean checkLogin(String email, String pass) throws NoSuchAlgorithmException, ClassNotFoundException, SQLException{
		String passMd5;
		ResultManager rsManager;
		Converter conv = new Converter();
		
		passMd5 = conv.enycryptToMd5(pass);
		
		db.connnect();
		rsManager = db.abfrage("SELECT * FROM tn_teilnehmer where tn_teilnehmer.email = '" + email + "'");
		db.close();
		
		if(rsManager.noResult())
			return false;
		
		if(rsManager.getResultList().get(0).get("pass").toString().equalsIgnoreCase(passMd5)){
			user = new User((int) rsManager.getResultList().get(0).get("t_id"),
					rsManager.getResultList().get(0).get("nachname").toString(),
					rsManager.getResultList().get(0).get("vorname").toString(),
					rsManager.getResultList().get(0).get("firma").toString(),
					rsManager.getResultList().get(0).get("country").toString(),
					rsManager.getResultList().get(0).get("email").toString());
			return true;
		}
			
		else{
			return false;
		}
	}
	
	
	/**
	 * Methode Erstelle zu einer SQL Abfrage eine TableView und liefert diese zurück
	 * 
	 * @param table, label, sql
	 * @return void
	 */
	public void setTableView(TableView tableFromGUI, Label labelFromGUI, String sqlFromGUI, Boolean contextMenuFlag, ITableContextMenu actionHandler){	
		// Tabelle, Label und SQL-Statement aus GUI; DBagent und Frankenstein vom ContentManager
		TableWorkerThread twt = new TableWorkerThread(tableFromGUI, sqlFromGUI, labelFromGUI, db, frankenstein, contextMenuFlag, actionHandler);
		twt.start();
	}
	
	/**
	 * Hinzufügen einer neuer Vorlage
	 * @param v
	 */
	public void addVorlage(Vorlagen v){
		vorlagenMang.addVorlage(v);
	}
	
	/**
	 * Speichern der Vorlagen
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void saveVorlage() throws FileNotFoundException, IOException{
		vorlagenMang.saveAll();
	}
	
	/**
	 *Holen allen Standard Vorlagen
	 * @return
	 */
	public ArrayList<Vorlagen> getGeneralVorlagen(){
		return vorlagenMang.getListeUser("");
	}
	
	/**
	 * Holen aller Vorlagen für den jeweiligen Nutzer
	 * @return
	 */
	public ArrayList<Vorlagen> getUserVorlagen(){
		return vorlagenMang.getListeUser(user.getEmail());
	}
	
	/**
	 * generierte SQL Abfrage zu einer vorlage zurück liefern 
	 * Liste an Keywords:
	 * %e - UserEmail
	 * &i - UserId
	 * @param v
	 * @return
	 */
	public String getGenerateSql(Vorlagen v){
		String sql = v.getSql();
		if(sql.contains("%e"))
			sql = sql.replace("%e", "'" + user.getEmail()  + "'");
		if(sql.contains("%i"))
			sql = sql.replace("&i", new Integer(user.getT_id()).toString());
		
		return sql;
	}
	
	/**
	 * Methode liefert Namen der Tabellen in der Datenbank zurück
	 * @return ArrayList<String>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<String> getStructurTable() throws ClassNotFoundException, SQLException{
		ArrayList<String> tableName = new ArrayList<String>();
		
		if(dbStructur == null)
			fillDbStructur();
		
		for(String key: dbStructur.keySet()){
			tableName.add(key);
		}
		return tableName;
		
	}
	
	/**
	 * Methode liefert zu einer bestimmten Tabelle alle Felder zuurück
	 * @param tableName
	 * @return ArrayList<String>
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ArrayList<String> getStructurFields(String tableName) throws ClassNotFoundException, SQLException{
		if(dbStructur == null)
			fillDbStructur();
		
		return dbStructur.get(tableName);
	}
	
	/**
	 * Liefert die gesamte Db Structur und speichert diese im Arbeitsspeicher ab
	 * --> Methode dauert etwas länger (ca. 10 sek)
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private void fillDbStructur() throws ClassNotFoundException, SQLException{
		db.connnect();
		dbStructur = db.getStructur();
		db.close();
	}
	
	/**
	 * Aufruf nwenn der User sich ausloggt --> CM wird null gesetzt
	 */
	public void logout(){
		ref = null;
		return;
	}
	
	/**
	 * Öffnen des Benutzerhandbuches
	 * @throws IOException
	 */
	public void openHandbuch() throws IOException{
		Desktop.getDesktop().open(new File(IPath.PATHHANDBUCH));
	}
	
	/**
	 * Öffnen der JavaDoc
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void openJavaDoc() throws IOException, URISyntaxException{
		Desktop.getDesktop().open(new File(IPath.PATHJAVADOC));
	}
	
}

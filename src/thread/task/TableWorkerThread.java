package thread.task;

import gui.controller.ITableContextMenu;
import gui.controller.Main;

import java.awt.Font;
import java.sql.SQLException;
import java.util.HashMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import data.connect.DBagent;
import data.manager.FrankenStein;
import data.manager.ResultManager;

/**
 * 
 * @author GraphSim
 * Klasse ist als Thread zur Erstellung von TableView realisiert
 * 
 */

public class TableWorkerThread extends Thread{

	private TableView table = null;
	private String sql = "";
	private Label infoLabel = null;
	private DBagent db = null;
	private FrankenStein frankenstein = null;
	ObservableList<Object> data = null;
	private Boolean contextMenuFlag = false;
	private ITableContextMenu actionHandler = null;
	
	/**
	 * Konstruktor 
	 * 
	 * @param table
	 * @param sqlQuery
	 * @param infoLabel
	 * @param dbAgent
	 * @param frankenstein
	 */
	public TableWorkerThread(TableView table, String sqlQuery, Label infoLabel, DBagent dbAgent, FrankenStein frankenstein, Boolean contextMenuFlag, ITableContextMenu actionHandler){
		setDaemon(true);
		this.table = table;
		this.sql = sqlQuery;
		this.infoLabel = infoLabel;
		this.db = dbAgent;
		this.frankenstein = frankenstein;
		this.contextMenuFlag = contextMenuFlag;
		this.actionHandler = actionHandler;
		data = FXCollections.observableArrayList();
	}
	
	/**
	 * run Methode 
	 * 1) Tabelle clearen
	 * 2) Datenbank abfragen
	 * 3) Neue Klasse generieren
	 * 4) Gerüst der Tabelle erstellen
	 * 5) Tabelle mit Daten füllen
	 */
	public void run(){
			Platform.runLater(new Runnable(){
				@Override
				public void run(){
						try {
							// Label und Tabelle clearen
							infoLabel.setText("");
							for(Object o : table.getColumns()){
								TableColumn column = (TableColumn) o;
								column.setVisible(false);
							}
							table.getColumns().clear();
							
							// Holen der Daten aus der Datenbank
							db.connnect();
							ResultManager rsManager = db.abfrage(sql);
							db.close();
							
							// Aus der Abfrage heuras die Klasse erstellen
							Class<?> newClass = frankenstein.createClass(("Frankenstein" + System.currentTimeMillis()) , rsManager.getMetaDataMap());
							
							// Erstellen der leeren TableView
							for (final String s : rsManager.getMetaDataMap().keySet()){
								TableColumn column = new TableColumn(s);
								// Hier soll noch das scheiß bild geladen werden
								Image img = new Image(getClass().getResourceAsStream("/resources/images/filter.png"));
								ToggleButton tbt = new ToggleButton("",new ImageView(img));
								column.setGraphic(tbt);
								column.setMinWidth(img.getWidth() + s.length()*15);
								// TODO - richtige cell factory reinklatschen
								column.setCellValueFactory(new PropertyValueFactory(s));
								table.getColumns().addAll(column);
								if(contextMenuFlag){
									ContextMenu menu = new ContextMenu();
									MenuItem addToX = new MenuItem("\"" +  s + "\" auf die X-Achse legen");
									addToX.setOnAction(new EventHandler(){
										@Override
										public void handle(Event event) {
											actionHandler.assignOnX(s);
										}
									});
									MenuItem addToY = new MenuItem("\"" + s + "\" auf die Y-Achse legen");
									addToY.setOnAction(new EventHandler(){
										@Override
										public void handle(Event event) {
											actionHandler.assignOnY(s);
										}
									});
									menu.getItems().addAll(addToX, addToY);
									column.setContextMenu(menu);
								}
							}

							// Füllen der TableView
							HashMap<String, Object> dataSet;
							for(int i = 0; i < rsManager.getResultList().size(); i++){
								Object o = newClass.newInstance();
								dataSet = rsManager.getResultList().get(i);
								frankenstein.fill(o,dataSet);
								data.add(o);
							}
							table.setItems(data);
						} catch (ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException e) {
							// TODO Auto-generated catch block
							infoLabel.setText("Fehler, evtl. im SQL-Statement");
						}
				}
			});
	}
}

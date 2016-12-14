package data.connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import constant.IDbConnection;
import data.manager.ResultManager;

/**
 * 
 * @author GraphSim
 * Klasse zum Aufbau der Datenverbindung, Abfragen und update
 * der Datenbank und zum freigeben der Ressourcen
 *
 */
public class DBagent implements IDbConnection  {
	private Connection con;
	private Statement stmt;
	
	/**
	 * Aufbau der Vebidnung zur Datenbank
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void connnect() throws ClassNotFoundException, SQLException{
		//Register JDBC driver	 
		Class.forName(JDBC_DRIVER);
		
		//Open a connnection
		con = DriverManager.getConnection(URL, USER, PASS);
		stmt = con.createStatement();		
	}
	
	/**
	 * Abfrage zur Datenbank
	 * @param query --> Sql Befeh
	 * @return ResulSset
	 * @throws SQLException
	 */
	public ResultManager abfrage(String query) throws SQLException{
		ResultSet rs;
		ResultSetMetaData md;
		
		HashMap<String, String> metaDataMap = new  HashMap<String, String>();
		ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
		
		rs = stmt.executeQuery(query);
		md = rs.getMetaData();
		
		// ResultList füllen
		while(rs.next()){
			HashMap<String, Object> row = new HashMap<String, Object>(md.getColumnCount());
			for(int i = 1; i <= md.getColumnCount(); i++){
				row.put(md.getColumnName(i), rs.getObject(i));
			}
			resultList.add(row);
		}
		
		//AttributList füllen
		for(int i = 1; i <= md.getColumnCount(); i++){
			metaDataMap.put(md.getColumnName(i), md.getColumnTypeName(i));			
		}
		
		return new ResultManager(resultList, metaDataMap);
	}
	
	/**
	 * Update der Datenbank
	 * @param query --> Sql Befehl 
	 * @return iError 0: Keine Änderung 1: Änderung
	 * @throws SQLException 
	 */
	public int update(String query) throws SQLException{
		int iStatus;
		iStatus = stmt.executeUpdate(query);
		return iStatus;
	}
	
	/**
	 * Emitteln der Datenbankstruktur
	 * @return HashMap --> alle Tabellennamen und die Felder dazu
	 * @throws SQLException
	 */
	public HashMap<String,ArrayList<String>> getStructur() throws SQLException{
		HashMap<String, ArrayList<String>> structur = new HashMap<String, ArrayList<String>>();
		
		String[] types = { "TABLE" };
		
		DatabaseMetaData dbMeta = con.getMetaData();
		
		//holen allerr Tablen vom Typ "TABLE"
		ResultSet resultSetTable = dbMeta.getTables(null, null, "%", types);
		
		//Für jede Tabelle in der Datenbank anschließend die Felder herrausfinden
	    while (resultSetTable.next()) {
	      ArrayList<String> fields = new ArrayList<String>();
	      
	      String tableName = resultSetTable.getString(3);
	      
	      ResultSet resultSetField = stmt.executeQuery("SELECT * FROM " + tableName + " LIMIT 0");
	      ResultSetMetaData rsMeta = resultSetField.getMetaData();
	      
	      for(int i = 1; i <= rsMeta.getColumnCount(); i++){
	    	  fields.add(rsMeta.getColumnName(i));
	      }
	      structur.put(tableName, fields);
	    }
	    return structur;
	}
	
	
	
	/**
	 * Anschließendes freigeben der Ressourcen
	 * @throws SQLException
	 */
	public void close() throws SQLException{
		// Clean-up enviroment
		if(stmt!=null)
			stmt.close();

	    if(con!=null)
	    	con.close();
	}
}

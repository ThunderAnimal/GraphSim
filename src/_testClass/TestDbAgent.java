package _testClass;

import java.sql.SQLException;

import data.connect.DBagent;

public class TestDbAgent {
	private DBagent db;
	
	public TestDbAgent(){
		db = new DBagent();
	}
	public boolean test(){
		System.out.println("Test DB Agent...");
		boolean result = true;
		
		if (testVerbindung()){
			System.out.println("\tTest Verbindung - ERFOLGREICH");
		}
		else{
			System.out.println("\tTest Verbindung - FEHLER");
			result = false;
		}
		
		if(testAbfrageKorrektSyntax()){
			System.out.println("\tTest Abfrage Korrekter Syntax - ERFOLGREICH");
		}
		else{
			System.out.println("\tTest Abfrage Korrekter Syntax - FEHLER");
			result = false;
		}
		
		if(testAbfrageFehlerSyntax()){
			System.out.println("\tTest Abfrage Fehler Syntax - ERFOLGREICH");
			
		}
		else{
			System.out.println("\tTest Abfrage Fehler Syntax - FEHLER");
			result = false;
		}
		
		if(testTrennen()){
			System.out.println("\tTest Trennen - ERFOLGREICH");
		}
		else{
			System.out.println("\tTest Trennen - FEHLER");
		}
		
		return result;
	}
	
	private boolean testVerbindung(){
		try {
			db.connnect();
			return true;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	private boolean testAbfrageKorrektSyntax(){
		try {
			db.abfrage("SELECT * FROM tn_teilnehmer");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	private boolean testAbfrageFehlerSyntax(){
		try {
			db.abfrage("SELECT FROM * tn_teilnehmer");
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return true;
		}
	}
	
	private boolean testTrennen(){
		try {
			db.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}

package _testClass;

import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

import org.objectweb.asm.Attribute;

import data.connect.DBagent;
import data.manager.FrankenStein;
import data.manager.ResultManager;


public class TestFrankenstein {

	private FrankenStein frank;
	private Class<?> geneClass;
	private HashMap<String, String> Metadata;
	private HashMap<String, Class<?>> KontrollMap;
	
	
	public TestFrankenstein(){
		frank = new FrankenStein();
		Metadata = new HashMap<String, String>();
		KontrollMap = new HashMap<String, Class<?>>();
		Metadata.put("Name", "CHAR");
		Metadata.put("Vorname", "CHAR");
		Metadata.put("Alter", "INT");
		Metadata.put("GEB", "DATE");
		for(String key: Metadata.keySet()){
			KontrollMap.put("set" + key, void.class);
			if(Metadata.get(key).equals("CHAR"))
				KontrollMap.put("get" + key, String.class);
			else if(Metadata.get(key).equals("INT"))
				KontrollMap.put("get" + key, Integer.class);
			else
				KontrollMap.put("get" + key, Date.class);
		}
	}
	
	
	public boolean test(){
		System.out.println("Test Frankenstein...");
		boolean result = true;
		
		if(testcreateClass()){
			System.out.println("\tTest Erstelle Klasse - ERFOLGREICH");
		}
		else{
			System.out.println("\tTest Erstelle Klasse - FEHLER");
			result = false;
		}
		
		if(testClassMethoden()){
			System.out.println("\tTest Methoden vorhanden - ERFOLGREICH");
		}
		else{
			System.out.println("\tTest Methoden vorhanden - FEHLER");
			result = false;
		}
		return result;
	}
	
	private boolean  testcreateClass(){
		geneClass = frank.createClass("Frankenstein", Metadata);
		return true;
	}
	

	
	private boolean testClassMethoden(){
		int anzahl = 0;
		for(final Method method: geneClass.getDeclaredMethods()){
			for(String key: KontrollMap.keySet()){
				if(method.getName().equals(key) && method.getReturnType().equals(KontrollMap.get(key))){
					anzahl += 1;
				}
			}
		}
		if(anzahl == KontrollMap.size())
			return true;
		else
			return false;
	}

}

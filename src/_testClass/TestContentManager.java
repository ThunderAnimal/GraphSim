package _testClass;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import data.manager.ContentManager;

public class TestContentManager {
	private ContentManager conManager;
	
	public boolean test(){
		boolean result = true;
		
		System.out.println("Test ContentManager...");
		
		if(testInstance()){
			System.out.println("\tTest Instanziere CM - ERFOLGREICH");
		}else{
			System.out.println("\tTest Instanziere CM - FEHLER");
			result = false;
		}
		
		if(testDbStructur()){
			System.out.println("\tTest Analyse DbStructur - ERFOLGREICH");
		}else{
			System.out.println("\tTest Analyse DbStructur - FEHLER");
			result = false;
		}
		
		if(testLogin("Dummy204@handsim.de", "hallowelt", true)){
			System.out.println("\tTest Login Username und Password korrekt - ERFOLGRTEICH");
		}else{
			System.out.println("\tTest Login Username und Password korrekt - FEHLER");
			result = false;
		}
		
		if(testLogin("1234", "hallowelt", false)){
			System.out.println("\tTest Login Username  inkorrekt - ERFOLGRTEICH");
		}else{
			System.out.println("\tTest Login Username  inkorrekt - FEHLER");
			result = false;
		}
		
		if(testLogin("Dummy204@handsim.de", "1234", false)){
			System.out.println("\tTest Login Password inkorrekt - ERFOLGRTEICH");
		}else{
			System.out.println("\tTest Login Password inkorrekt - FEHLER");
			result = false;
		}
		
		if(testLogin("1234", "1234", false)){
			System.out.println("\tTest Login Username und Password inkorrekt - ERFOLGRTEICH");
		}else{
			System.out.println("\tTest Login Username und Password inkorrekt - FEHLER");
			result = false;
		}
		
		
		return result;
	}
	
	private boolean testInstance(){
		try {
			conManager = ContentManager.getInstance();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}
	
	private boolean testDbStructur(){
		try {
			conManager.getStructurTable();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return true;
		}
		return true;
	}
	
	private boolean testLogin(String email, String pass, boolean resultErwartet){
		try {
			return conManager.checkLogin(email, pass) == resultErwartet;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}

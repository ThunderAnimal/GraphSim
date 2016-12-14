package _testClass;

import gui.controller.WindowController;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class TestWindowController {

	public boolean test(){
		System.out.println("Test WindowController...");
		boolean result = true;
		
		if(loadFXML(WindowController.loginFile)){
			System.out.println("\tTest lade loginFXML - ERFOLGREICH");
		}else{
			System.out.println("\tTest lade loginFXML - FEHLER");
			result = false;
		}
		
		if(loadFXML(WindowController.mainFile)){
			System.out.println("\tTest lade MainFXML - ERFOLGREICH");
		}else{
			System.out.println("\tTest lade MAINFXML - FEHLER");
			result = false;
		}
		
		if(loadFXML(WindowController.advancedFile)){
			System.out.println("\tTest lade AdvancedFXML - ERFOLGREICH");
		}else{
			System.out.println("\tTest lade AdvancedFXML - FEHLER");
			result = false;
		}
		return result;
	}
	
	private boolean loadFXML(String path){
		try {
			
			FXMLLoader.load(getClass().getResource(path));
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (NullPointerException e){
			return false;
		}
	}
}

package _testClass;

import java.io.FileNotFoundException;
import java.io.IOException;

import data.manager.VorlagenManager;

public class TestVorlagenManager {
	private VorlagenManager vorl;
	
	public TestVorlagenManager(){
		vorl = VorlagenManager.getInstance();
	}
	
	public boolean test(){
		System.out.println("Test VorlagenManager...");
		boolean result = true;
		
		if(testEmpty(true)){
			System.out.println("\tTest ob Liste leer bei leerer Liste - ERFOLGREICH");
		}else{
			System.out.println("\tTest ob Liste leer bei leerer Liste - FEHLER");
			result = false;
		}
		
		if(testAddElement()){
			System.out.println("\tTest Element hinzufügen - ERFOLGREICH");
		}else{
			System.out.println("\tTest Element hinzufügen - FEHLER");
			result = false;
		}
		
		if(testEmpty(false)){
			System.out.println("\tTest ob Liste leer bei gefüllter Liste - ERFOLGREICH");
		}else{
			System.out.println("\tTest ob Liste leer bei gefüllter Liste - FEHLER");
			result = false;
		}
		
		if(testload()){
			System.out.println("\tTest Liste laden - ERFOLGREICH");
		}else{
			System.out.println("\tTest Liste laden - FEHLER");
			result = false;
		}
		
		if(testsave()){
			System.out.println("\tTest Liste speichern - ERFOLGREICH");
		}else{
			System.out.println("\tTest Liste speichern - FEHLER");
			result = false;
		}
				
		return result;
	}
	
	private boolean testEmpty(boolean resultErwartet){
		return vorl.isEmpty() == resultErwartet;
	}
	
	private boolean testAddElement(){
		vorl.addVorlage(null);
		if(vorl.getAnzahl() != 0)
			return true;
		else
			return false;
	}
	
	private boolean testsave(){
		try {
			vorl.saveAll();
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	private boolean testload(){
		try {
			vorl.loadAll();
			return true;
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		
	}
}

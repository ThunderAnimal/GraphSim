package _testClass;

import java.io.ObjectInputStream.GetField;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Klasse zum zum starten des unit Test
 * @author GraphSim
 *
 */
public class _UnitTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> fehlerKlassen = new ArrayList<String>();
		SimpleDateFormat formatter = new SimpleDateFormat ("dd.MM.yyyy 'um' HH:mm:ss ");
		Date currentTime = new Date();
		
		boolean result = true;
		
		System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░");
		System.out.println("░░Unit Test - Graphsim░░");
		System.out.println("░░░░░░░░░░░░░░░░░░░░░░░░");

		System.out.println("\nZeit und Datum : " + formatter.format(currentTime));
		
		
		System.out.println("Test start:");
		System.out.println("###########################");
		//Test JavaVersion
		TestJavaVersion javaversion = new TestJavaVersion();
		if(javaversion.test()==false){
			result = false;
			fehlerKlassen.add("Falsche Javaversion");
		}
		System.out.println("----------------------------");
		
		//Test Converter
		TestConverter converter = new TestConverter();
		if(converter.test()==false){
			result = false;
			fehlerKlassen.add("Converter");
		}
		System.out.println("----------------------------");
		
		//Test DbAgent
		TestDbAgent dbagent = new TestDbAgent();
		if(dbagent.test()==false){
			result = false;
			fehlerKlassen.add("DbAgent");
		}
		System.out.println("----------------------------");
		
		//Test Frankenstein
		TestFrankenstein frankenstein = new TestFrankenstein();
		if(frankenstein.test()==false){
			result = false;
			fehlerKlassen.add("FrankenStein");
		}
		System.out.println("----------------------------");
		

		
		//Test VorlagenManager
		TestVorlagenManager vorlagenMang = new TestVorlagenManager();
		if(vorlagenMang.test()==false){
			result = false;
			fehlerKlassen.add("VorlagenManager");
		}
		System.out.println("----------------------------");
		
		//Test ContentManager
		TestContentManager contManager = new TestContentManager();
		if(contManager.test()==false){
			result = false;
			fehlerKlassen.add("ContentManager");
		}
		System.out.println("----------------------------");
		
		
		//Test WindowManager
		TestWindowController windowController = new TestWindowController();
		if(windowController.test()==false){
			result = false;
			fehlerKlassen.add("WindowManager");
		}
		System.out.println("----------------------------");
		
		System.out.println("############################");
		System.out.print("Ende Test:");
		
		//Prüfen ob alles Test erfolgreich
		if(result){
			System.out.println(" TEST ERFOLGREICH ABGESCHLOSSEN");
		}
		else{
			System.out.println(" TEST FEHLERHAFT");
			System.out.println("In folgenden Klassen sind Fehler aufgetreten:");
			for(int i = 0; i<fehlerKlassen.size();i++){
				System.out.print(fehlerKlassen.get(i) + " \t");
			}
		}
	}

}

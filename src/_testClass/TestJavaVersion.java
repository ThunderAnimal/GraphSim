package _testClass;

public class TestJavaVersion {

	public boolean test(){
		boolean result = true;
		System.out.println("Test JavaVersion...");
		System.out.println("\tJava version: " + getJavaVersion());
		if(testVersion()){
			System.out.println("\tErforderliche Java Version vorhanden - ERFOLGREICH");
		}else{
			System.out.println("\tErforderliche Java Version vorhanden - FEHLER");
			result = false;
		}
		
		return result;
	}
	
	private boolean testVersion(){
		boolean result = true;
		
		if(getJavaVersion() < 1.7)
			result = false;
		
		return result;
	}
	
	private double getJavaVersion(){
		String version = System.getProperty("java.version");
		int pos = 0, count = 0;
		for (; pos < version.length() && count < 2; pos++) {
		    if (version.charAt(pos) == '.') {
		        count++;
		    }
		}
		
		--pos; //EVALUATE double
		
		double dversion = Double.parseDouble(version.substring(0, pos));
		return dversion;
	}
}

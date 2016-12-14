package _testClass;

import java.security.NoSuchAlgorithmException;

import system.Converter;

public class TestConverter {
	private Converter conv;
	
	public TestConverter(){
		conv = new Converter();
	}
	
	public boolean test(){
		System.out.println("Test Converter...");
		boolean result = true;
		
		if(testEncryptToMd5("hallo", "598d4c200461b81522a3328565c25f7c")){
			System.out.println("\tTest encrypt 'hallo' to md5 - ERFOLGREICH");
		}else{
			System.out.println("\tTest encrypt 'hallo' to md5 - FEHLER");
			result = false;
		}
		
		if(testEncryptToMd5("uashlUarfu784235l", "3D349609A7110391E7DC4ECCE1DD1C36")){
			System.out.println("\tTest encrypt 'uashlUarfu784235l' to md5 - ERFOLGREICH");
		}else{
			System.out.println("\tTest encrypt 'uashlUarfu784235l' to md5 - FEHLER");
			result = false;
		}
		return result;
	}
	
	private boolean testEncryptToMd5(String wort, String md5erwartet){
		boolean result = false;
		try {
			if(conv.enycryptToMd5(wort).equalsIgnoreCase(md5erwartet))
				return true;
			else
				return false;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
}

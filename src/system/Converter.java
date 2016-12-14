package system;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Klassen zum Enryption eines strings
 * @author GrapSim
 *
 */
public class Converter {
	
	/**
	 *Verschlüseln von Cleartext zu md5
	 * @param text
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String enycryptToMd5(String text) throws NoSuchAlgorithmException{
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    md.update(text.getBytes());

	    byte byteData[] = md.digest();
	    StringBuffer sb = new StringBuffer();
	    
	    for (int i = 0; i < byteData.length; i++){
	        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    return sb.toString();
	}
}

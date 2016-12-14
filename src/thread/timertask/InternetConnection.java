package thread.timertask;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.TimerTask;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Prüfen der Internetverbindung
 * @author GraphSim
 *
 */
public class InternetConnection extends TimerTask {
	private Circle dot;
	
	public InternetConnection (Circle dot){
		this.dot = dot;
	}
	

	public void run(){
		//Prüfen der Netzwerkarten
		if(isNetworkConnect()){
			//ping zum Internet
			if(isInternetConnect()){
				dot.setFill(Color.GREEN);
			}
			else{
				dot.setFill(Color.RED);
			}
		}
		else{
			dot.setFill(Color.RED);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isNetworkConnect(){
		Enumeration<NetworkInterface> interfaces = null;
		
		//prüfen ob eine Netzwerkscchnittstelle Aktiv ist
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(interfaces.hasMoreElements()){
			NetworkInterface nic = interfaces.nextElement();
			try {
				if(nic.isUp() == true && nic.isVirtual() == false && nic.isLoopback() == false){
					return true;
				}
					
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isInternetConnect(){
		String url = "http://www.google.de";
		boolean available;
		
		try{
		    final URLConnection connection = new URL(url).openConnection();
		    connection.connect();
		    available = true;
		} catch(final IOException e){
			available = false;
		    
		}
		return available;
	}


}

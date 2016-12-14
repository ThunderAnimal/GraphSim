package persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Serializer implements IPersistence{
	private ArrayList<Object> liste = new ArrayList<Object>();

	@SuppressWarnings("unchecked")
	public Object loadAll(String Path) throws FileNotFoundException, IOException, ClassNotFoundException  {
		// TODO Auto-generated method stub
		ObjectInputStream strIn = null;
		strIn = new ObjectInputStream(new FileInputStream(Path));

		liste = ((ArrayList<Object>) (strIn.readObject()));
		strIn.close();
		return liste;
	}

	public void saveAll(String Path, Object liste) throws FileNotFoundException, IOException{
		// TODO Auto-generated method stub
		
		//Datei local speichern
		ObjectOutputStream strOut = null;
		strOut = new ObjectOutputStream(new FileOutputStream(Path));
		strOut.writeObject(liste);
		strOut.flush();
		strOut.close();
		
	}
	

}

package data.manager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import data.misc.Vorlagen;

/**
 * 
 * @author GraphSim
 * Klasse zum Verwalten der Vorlagen
 * Klasse ist Singelton
 *
 */
public class VorlagenManager extends Manager {
	static private VorlagenManager ref;
	private ArrayList<Vorlagen> liste = new ArrayList<Vorlagen>();
	
	static public VorlagenManager getInstance(){
		if(ref == null)
			ref = new VorlagenManager();
		
		return ref;
	}
	
	private VorlagenManager() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	/**
	 * Vorlage Hinzufügen
	 * @param v
	 */
	public void addVorlage(Vorlagen v){
		liste.add(v);
	}
	
	@Override
	public void loadAll() throws FileNotFoundException, ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		liste = (ArrayList<Vorlagen>)(persistence.loadAll(PATHVORLAGEN));
	}

	@Override
	public void saveAll() throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		persistence.saveAll(PATHVORLAGEN, liste);
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		if(liste.isEmpty())
			return true;
		else
			return false;
	}

	@Override
	public int getAnzahl() {
		// TODO Auto-generated method stub
		return liste.size();
	}
	
	/**
	 * Rückgabe eines bestimmten Elements
	 * @param i
	 * @return
	 */
	public Vorlagen getElement(int i){
		return liste.get(i);
	}
	
	/**
	 * wenn general Vorlaen dann email = ""
	 * sonst emial vom user der eingelogt ist
	 * @param email 
	 * @return
	 */
	public ArrayList<Vorlagen> getListeUser(String email){
		ArrayList<Vorlagen> userList = new ArrayList<Vorlagen>();
		for(int i = 0; i < getAnzahl(); i++){
			if(getElement(i).getEmail().equals(email))
				userList.add(getElement(i));
		}
		return liste;
	}

}

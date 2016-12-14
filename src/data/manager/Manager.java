package data.manager;

import java.io.FileNotFoundException;
import java.io.IOException;

import persistence.IPersistence;
import persistence.Serializer;
import constant.IPath;


public abstract class Manager implements IPath{
	protected IPersistence persistence;

	public Manager(){
		persistence = new Serializer();
	}
	/**
	 * Laden der Daten 
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public abstract void loadAll() throws FileNotFoundException, ClassNotFoundException, IOException;
	/**
	 * Speichern der Liste
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public abstract void saveAll() throws FileNotFoundException, IOException;
	/**
	 * Prüfen ob Liste leeer ist
	 * @return
	 */
	public abstract boolean isEmpty();
	/**
	 * Anzahl der Elemente in der Liste
	 * @return
	 */
	public abstract int getAnzahl();
}

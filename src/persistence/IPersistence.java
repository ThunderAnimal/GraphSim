package persistence;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface IPersistence {
	/**
	 * 
	 * @param Path
	 * @return Objetce in form einer Liste
	 * @throws ClassNotFoundException 
	 */
	public Object loadAll(String Path) throws FileNotFoundException, IOException, ClassNotFoundException;
	
	/**
	 * 
	 * @param Path
	 * @param liste
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void saveAll(String Path, Object liste) throws FileNotFoundException, IOException;
}

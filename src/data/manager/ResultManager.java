package data.manager;


import java.util.ArrayList;
import java.util.HashMap;


/**
 * Objekt zum Handeln der Datenbank Abfrage
 * @author GraphSim
 *
 */
public class ResultManager {

	private HashMap<String, String> metaDataMap = new HashMap<String, String>();
	private ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
	
	public ResultManager(ArrayList<HashMap<String, Object>> resultList, HashMap<String, String> metaDataMap){
		this.resultList = resultList;
		this.metaDataMap = metaDataMap;
	}
	
	/**
	 * Prüft ob es Ergebissie zu einer Abfrage gibt
	 * @return true/false
	 */
	public boolean noResult(){
		if(resultList.isEmpty())
			return true;
		else
			return false;		
	}

	public HashMap<String, String> getMetaDataMap() {
		return metaDataMap;
	}


	public ArrayList<HashMap<String, Object>> getResultList() {
		return resultList;
	}
	
}

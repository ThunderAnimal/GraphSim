package data.analysis;

import java.util.HashMap;
import java.util.List;

// TODO - eventuell als Singleton implementiern oder mit statischen Methoden
public class DataMiner {
	
	public static HashMap<String,Number> cleanRedundantKeys(List<String> keyList, List<Number> valueList){
		if(keyList.size() != valueList.size()){
			System.out.println("KeyListe und ValueListe müssen die selbe Länge haben!!!");
			return null;
		}
		HashMap<String, Number> newHashMap = new HashMap<>();
		int i = 0;
		for (String s : keyList){
			// look, if entry exists
			if (newHashMap.containsKey(s)){
				newHashMap.put(s, newHashMap.get(s).doubleValue()+valueList.get(i).doubleValue());
			} else {
				newHashMap.put(s, valueList.get(i));
			}
			i++;
		}
//		Double d = new Double(Math.round(1-(new Double(newHashMap.size())/new Double(keyList.size()))))*100;
//		System.out.println("Größe vorher: " + keyList.size());
//		System.out.println("Größe nachher: " + newHashMap.size());
//		System.out.println("Verkleinerung um " + d + "%");
		return newHashMap;
	}
}

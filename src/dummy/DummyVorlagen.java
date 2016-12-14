package dummy;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import data.manager.VorlagenManager;
import data.misc.Vorlagen;


public class DummyVorlagen {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		VorlagenManager vorlagenMan = VorlagenManager.getInstance();
		
		
		HashMap<String, Class<?>> filter =  new HashMap<String, Class<?>>();
		filter.put("Betrag", Double.class);
		filter.put("Buchungstag", Date.class);
		
		vorlagenMan.addVorlage(new Vorlagen("Umsatz", "SELECT Betrag, Buchungstag, Buchungstext FROM ps_konto JOIN tn_teilnehmer ON tn_teilnehmer.t_id = ps_konto.Unternehmen WHERE tn_teilnehmer.email = %e ORDER BY Buchungstag ASC", filter, "", 0));
		vorlagenMan.saveAll();
	}

}

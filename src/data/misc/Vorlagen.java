package data.misc;

import java.io.Serializable;
import java.util.HashMap;

public class Vorlagen implements Serializable {

	/**
	 * Auto-generated
	 */
	private static final long serialVersionUID = 9019496288563678171L;
	
	private String name;
	private String sql;
	private HashMap<String, Class<?>> filter; //<-- filter möglichkeiten <Feld, Datentyp>
	private String email; // <-- zugehörigkeit von wem die vorlage erstellt wurde
	private int diagrammTyp; /*     0 - line chart 
									1 - pie chart
									2 - bar chart
									3 - area chart
									4 - bubble chart
									5 - scatter chart									
								*/
	
	/**
	 * Überladener Konstruktor Vorlagen
	 * @param name
	 * @param sql
	 * @param filter
	 * @param email
	 * @param diagrammTyp:   		0 - line chart 
									1 - pie chart
									2 - bar chart
									3 - area chart
									4 - bubble chart
									5 - scatter chart	
	 */
	public Vorlagen(String name, String sql, HashMap<String, Class<?>> filter,
			String email,int diagrammTyp) {
		super();
		this.name = name;
		this.sql = sql;
		this.filter = filter;
		this.email = email;
		this.diagrammTyp = diagrammTyp;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * 
	 * @param sql
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, Class<?>> getFilter() {
		return filter;
	}
	

	/**
	 * 
	 * @param filter
	 */
	public void setFilter(HashMap<String, Class<?>> filter) {
		this.filter = filter;
	}
	
	/**
	 * 
	 * @return 0 - line chart 
									1 - pie chart
									2 - bar chart
									3 - area chart
									4 - bubble chart
									5 - scatter chart
	 */
	public int getDiagrammtyp(){
		return diagrammTyp;
	}
	
	/**
	 * 
	 * @param diagrammTyp 0 - line chart 
									1 - pie chart
									2 - bar chart
									3 - area chart
									4 - bubble chart
									5 - scatter chart
	 */
	public void setDiagrammtyp(int diagrammTyp){
		this.diagrammTyp = diagrammTyp;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * Override toString Methode
	 * name der Vorlage wird zurückgegeben
	 */
	public String toString(){
		return this.name;
		
	}
}

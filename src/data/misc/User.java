package data.misc;

/**
 * User bei GraphSim
 * @author GraphSim
 *
 */
public class User {
	private int t_id;
	private String nachname;
	private String vorname;
	private String firma;
	private String country;
	private String email;
	
	/**
	 * Überladener Konstruktor
	 * @param t_id
	 * @param nachname
	 * @param vorname
	 * @param firma
	 * @param country
	 * @param email
	 */
	public User(int t_id, String nachname, String vorname, String firma,
			String country, String email) {
		super();
		this.t_id = t_id;
		this.nachname = nachname;
		this.vorname = vorname;
		this.firma = firma;
		this.country = country;
		this.email = email;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getT_id() {
		return t_id;
	}
	/**
	 * 
	 * @param t_id
	 */
	public void setT_id(int t_id) {
		this.t_id = t_id;
	}
	/**
	 * 
	 * @return
	 */
	public String getNachname() {
		return nachname;
	}
	/**
	 * 
	 * @param nachname
	 */
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	/**
	 * 
	 * @return
	 */
	public String getVorname() {
		return vorname;
	}
	/**
	 * 
	 * @param vorname
	 */
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	/**
	 * 
	 * @return
	 */
	public String getFirma() {
		return firma;
	}
	/**
	 * 
	 * @param firma
	 */
	public void setFirma(String firma) {
		this.firma = firma;
	}
	/**
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
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
}

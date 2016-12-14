package data.manager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.core.NamingPolicy;

/**
 * Erstellen von Klasen zur Laufzeit und einbindung in der JVM
 * @author GraphSim
 *
 */
public class FrankenStein{

	/**
	 * Erstellen einer Klasse aus der DB-Abfrage
	 * @param classname --> Klassenname ! muss unique sein !
	 * @param metaData --> aus der DB Abfrage
	 * @return
	 */
	public Class<?> createClass(String classname,HashMap<String, String> metaData){
		HashMap<String, Class<?>> properties = new HashMap<String, Class<?>>();
		for(String key: metaData.keySet()){
			properties.put(key, mapType(metaData.get(key)));
		}
		
		Class<?> frankenstein = createBeanClass(classname, properties);
		return frankenstein;
		
	}
	
	/**
	 * Beanclass erstellen
	 * @param className
	 * @param properties
	 * @return
	 */
	private Class<?> createBeanClass(final String className,Map<String, Class<?>> properties){
		    BeanGenerator beanGenerator = new BeanGenerator();
		    beanGenerator.setNamingPolicy(new NamingPolicy(){
				@Override
				public String getClassName(String arg0, String arg1,
						Object arg2, net.sf.cglib.core.Predicate arg3) {
					// TODO Auto-generated method stub
					return className;
				}});
		    BeanGenerator.addProperties(beanGenerator, properties);
		    return (Class<?>) beanGenerator.createClass();
		}
	
	/**
	 * Mappen der SQl Datentypen auf Java Datentypen
	 * @param type
	 * @return
	 */
	private Class<?> mapType(String type){
		Class<?> classType = null;
		switch(type){
		case "CHAR":
			classType = String.class;
			break;
		case "VARCHAR":
			classType = String.class;
			break;
		case "LONGCHAR":
			classType = String.class;
			break;
		case "NUMERIC":
			classType = BigDecimal.class;
			break;
		case "INTEGER":
			classType = Integer.class;
			break;
		case "INT":
			classType = Integer.class;
			break;
		case "TINYINT":
			classType = Boolean.class;
			break;
		case "DECIMAL":
			classType = BigDecimal.class;
			break;
		case "BIT":
			classType = Boolean.class;
			break;
		case "BOOLEAN":
			classType = Boolean.class;
			break;
		case "SMALLINT":
			classType = Integer.class;
			break;
		case "BIGINT":
			classType = Long.class;
			break;
		case "DOUBLE":
			classType = Double.class;
			break;
		case "FLOAT":
			classType = Double.class;
			break;
		case "DATE":
			classType = Date.class;
			break;
		case "TIME":
			classType = Time.class;
		default:
			classType = String.class;
		}
		return classType;
	}	
	
	/**
	 * Füllen der leeren Klasse mit Daten
	 * @param nakedObject
	 * @param filling
	 */
	public void fill(Object nakedObject, HashMap<String, Object> filling){
		// alle Methoden einlesen
		Object cast;
		Method[] methods = nakedObject.getClass().getDeclaredMethods();
		for (String s : filling.keySet()){

			cast = filling.get(s);
			if(cast != null)
			{
				for(final Method m : methods){
					if(m.getName().startsWith("set") && m.getName().endsWith(s.substring(0,1).toUpperCase()+s.substring(1))){
						try {
								m.invoke(nakedObject, cast);
								
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								System.out.println(m.getName() + " " + cast.getClass());
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			}

			

		}
	}

}

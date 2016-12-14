package gui.controller;

import java.io.IOException;

import constant.IProjectMeta;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Verwalten der FXML Fenster
 * 
 * Singelton
 * 
 * @author GraphSim
 *
 */
public class WindowController implements IProjectMeta {
	
	private static WindowController ref;
	
	private  Stage primaryStage;
	private  Stage loginStage;
	
	private Scene loginScene;
	private Scene mainScene;
	private Scene advancedScene;
	
	//Path der FXML-Dateien
	public static final String loginFile = "/resources/fxml/Login.fxml";
	public static final String mainFile = "/resources/fxml/Main.fxml";
	public static final String advancedFile = "/resources/fxml/AdvancedQuery.fxml";
	
	
	private WindowController() throws IOException{
		logout();
	}
	
	public synchronized static WindowController getInstance() throws IOException{

		if(ref == null)
			ref = new WindowController();
		return ref;
	}
	
	/**
	 * Main Fenster erstellen
	 * @param stage
	 */
	public void setPrimaryStage(Stage stage){
		primaryStage = stage;
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.setTitle(NAME + " - " + VERSION);
		primaryStage.getIcons().add(new Image("/resources/images/graphsim.PNG"));
		
	}
	
	/**
	 * Login Fenster erstellen
	 * @param stage
	 */
	public void setLoginStage(Stage stage){
		loginStage = stage;
		loginStage.initStyle(StageStyle.UNDECORATED);
		loginStage.setTitle(NAME + " - " + VERSION + ": Login");
		loginStage.getIcons().add(new Image("/resources/images/graphsim.PNG"));
		
	}
	
	
	/**
	 * Login Fenster anzeigen
	 */
	public void showLoginFrame(){
		if(loginStage == null)
			setLoginStage(new Stage());
		
		loginStage.setScene(loginScene);
		loginStage.show();
		loginStage.toFront();
	}
	
	/**
	 * Login Fenster schlieﬂen
	 */
	public void closeLoginFrame(){
		loginStage.close();
	}
	
	/**
	 * Main Fenster anzeigen
	 * @throws IOException
	 */
	public void showMainFrame() throws IOException{
		if(primaryStage == null)
			setPrimaryStage(new Stage());
		if(mainScene == null)
			login();
		
		primaryStage.setScene(mainScene);
		primaryStage.show();
		primaryStage.toFront();
		
	}
	
	/**
	 * Advanced Fenster anzeigen
	 */
	public void showAdvancedFrame(){
		primaryStage.setScene(advancedScene);
		primaryStage.show();
		primaryStage.toFront();
	}
	
	
	/**
	 * Mainfenster schlieﬂen
	 */
	public void closeMainFrame(){
		primaryStage.close();
	}
	
	/**
	 * Laden der FXML-Datein beim Login
	 * @throws IOException
	 */
	public void login() throws IOException{
		mainScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(mainFile)));
		advancedScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(advancedFile)));
		return;
	}
	
	/**
	 * Laden der FXML Dateien beim Logout
	 * @throws IOException
	 */
	public void logout() throws IOException{
		loginScene = new Scene((Parent) FXMLLoader.load(getClass().getResource(loginFile)));
		return;
	}
	
	

}

package gui.controller;



import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Timer;


import thread.timertask.InternetConnection;
import data.manager.ContentManager;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

 
public class Login implements Initializable{
	@FXML private GridPane content;
	@FXML private Text actionInfo;
	@FXML private TextField textAreaName;
	@FXML private PasswordField passwordField;
	@FXML private ProgressIndicator progressIndicator;
	@FXML private Button signInButton;
	@FXML private Circle dot;
	
	private Timer timer;
	private final int REFRESH = 500;
	private ContentManager cm;
	private Boolean b = null;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		timer = new Timer();
		timer.schedule(new InternetConnection(dot), 0, REFRESH);
		
	}
	@FXML protected void handleSignInButtonAction(ActionEvent event) throws InterruptedException{
		progressIndicator.setVisible(true);
		if (textAreaName.getText().equals("")){
			actionInfo.setText("Please enter a Usernanme");
			progressIndicator.setVisible(false);
			return;
		}
		if (passwordField.getText().equals("")){
			actionInfo.setText("Please enter a Password");
			progressIndicator.setVisible(false);
			return;
		}
		
		//Abfrage in neuen Thread
		final Task taskCheckLogin = new Task<Boolean>() {
			@Override
			protected Boolean call() throws InterruptedException{
				try {
					if (cm.checkLogin(textAreaName.getText(),passwordField.getText())){
						b = new Boolean(true);
						actionInfo.setText("Login Complete !!!");
					} else {
						actionInfo.setText("Benutzername oder Password inkorrekt");
						b = new Boolean(false);
					}
				} catch (NoSuchAlgorithmException | ClassNotFoundException |SQLException e) {
					if(e.getClass().equals(java.sql.SQLException.class)){
						actionInfo.setText("Keine Anbindung zur Datenbank!");
					}
				}finally{
					progressIndicator.setVisible(false);
				}
				return b;
			}
		};
		final Task enterGui = new Task<Void>(){

			@Override
			protected Void call() throws Exception {
				timer.cancel();
				WindowController.getInstance().login();
				WindowController.getInstance().showMainFrame();
				WindowController.getInstance().closeLoginFrame();
				return null;
			}
			
		};
		
		final Task startGraphSim = new Task<Void>(){
			
			@Override
			protected Void call() throws Exception {
				try {
					cm = ContentManager.getInstance();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// ersten Task als Thread abfeuern und auf das Ende warten
				Thread t = new Thread(taskCheckLogin);
				t.start();
				t.join();

				// Login erfolgreich?
				if(b == true){
					Platform.runLater(enterGui);
				}
				return null;
			}
			
		};
		
		new Thread(startGraphSim).start();
	}
	
	@FXML protected void handleExitButtonAction(ActionEvent event){
		timer.cancel();
		System.exit(0);
	}

}
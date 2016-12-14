package gui.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import data.manager.ContentManager;

public class AdvancedQuery implements Initializable{

	// FXML elements
	@FXML TableView<Object> resultTable;
	@FXML TextArea sqlText;
	@FXML Label actionInfo;
	@FXML ProgressIndicator progressCircle;
	@FXML ListView tableList;
	@FXML ListView attributeList;
	
	// non FXML elements
	ContentManager cm;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// initialize the elements
		try {
			cm = ContentManager.getInstance();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		final ObservableList<String> tables = FXCollections.observableArrayList();
		final ObservableList<String> attributes = FXCollections.observableArrayList();
		
		try {
			// erste TableView mit Werten füllen
			tables.addAll(cm.getStructurTable());
			tableList.setItems(tables);
			// Value Change Listener der ersten Tabelle
			tableList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
				// changeListener der den neuen Wert dem CM übergibt
				@Override
			    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			        try {
			        	attributes.clear();
						attributes.addAll(cm.getStructurFields(newValue));
					} catch (ClassNotFoundException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			});
			// Doppelklick auf der ersten List eingebunden
			tableList.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
			            if(mouseEvent.getClickCount() == 2){
			            	sqlText.setText(sqlText.getText() + " " + tableList.getSelectionModel().getSelectedItem().toString());
			            }
			        }
				}
			});
			// TODO - Doppelklick auf der zweiten Liste eingebunden
			attributeList.setOnMouseClicked(new EventHandler<MouseEvent>(){
				@Override
				public void handle(MouseEvent arg0) {
//					System.out.println("SubString: " + sqlText.getText().substring(6,sqlText.getText().indexOf("FROM", 0)));
				}
			});
			// initial erstes Element auswählen
			tableList.getSelectionModel().select(0);
			attributeList.setItems(attributes);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Methode zur Generierung der Tabelle auf Grundlage des SQL Statements
	 * 
	 * @param event
	 */
	@FXML protected void runSQL(ActionEvent event){
		progressCircle.setVisible(true);
		final Task<Void> tRunSQL = new Task<Void>(){
			@Override
			protected Void call() throws Exception {
				try{
					cm.setTableView(resultTable, actionInfo, sqlText.getText(), new Boolean(false), null);
				} finally {
					progressCircle.setVisible(false);
				}
				return null;
			}
		};
		final Thread trRunSQL = new Thread(tRunSQL);
		trRunSQL.start();
	}

	/**
	 * Akzelerator Methode für das Abschießen von SQL-Statements --> mit F12, doch was macht F1???
	 * @param event
	 * @throws IOException
	 */
	@FXML protected void textboxKeyPressed(KeyEvent event) throws IOException{
		if(event.getCode().toString().equals("F12")){
			runSQL(null);
		} else if (event.getCode().toString().equals("F1")){
			Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/EasterCage.fxml"));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setTitle("Happy Easter, "+System.getProperty("user.name"));
			stage.setScene(scene);
			stage.show();
		}
	}
	@FXML protected void backToMain(ActionEvent event) throws IOException{
		WindowController.getInstance().showMainFrame();
		
	}
	@FXML protected void showErd(ActionEvent event) throws IOException{
		Parent root = FXMLLoader.load(getClass().getResource("/resources/fxml/Erd.fxml"));
		Scene scene = new Scene(root);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.setTitle("Handsim ERD");
		stage.show();
		
	}
	
}

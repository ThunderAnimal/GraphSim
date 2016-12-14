package gui.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;


import data.analysis.DataMiner;
import data.manager.ContentManager;
import data.misc.Vorlagen;


public class Main implements ITableContextMenu, Initializable {
	
	@FXML ListView<Vorlagen> generalVorlagen;
	@FXML TableView<Object> resultTable;
	@FXML XYChart chart;
	
	ObservableList<Object> tableContent;
	ContentManager cm;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			cm = ContentManager.getInstance();
			chart.setVisible(false);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final ObservableList<Vorlagen> generalVorlagenList = FXCollections.observableArrayList(); 
		generalVorlagenList.addAll(cm.getGeneralVorlagen());
		generalVorlagen.setItems(generalVorlagenList);
		// changeListener 
		generalVorlagen.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent){
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
		            if(mouseEvent.getClickCount() == 2){
		            	final Task<Void> tRunSQL = new Task<Void>(){
		        			@Override
		        			protected Void call() throws Exception {
		        				String sql = cm.getGenerateSql(generalVorlagen.getSelectionModel().getSelectedItem());
		        				cm.setTableView(resultTable, new Label(), sql, new Boolean(true), Main.this);
		        				if(!chart.isVisible())
		        					chart.setVisible(true);
		        				chart.getXAxis().setVisible(false);
		        				chart.getYAxis().setVisible(false);
		        				return null;
		        			}
		        		};
		        		final Thread trRunSQL = new Thread(tRunSQL);
		        		trRunSQL.start();
		        		chart.setTitle(generalVorlagen.getSelectionModel().getSelectedItem().getName());
		            }
		        }
			}
		});
	}
	
	@FXML protected void scrollChart(ScrollEvent event){
		final double SCALE_DELTA = 1.1;
		event.consume();
        if (event.getDeltaY() == 0) {
            return;
        }
        double scaleFactor = (event.getDeltaY() > 0) ? SCALE_DELTA : 1 / SCALE_DELTA;
        chart.setScaleX(chart.getScaleX() * scaleFactor);
        chart.setScaleY(chart.getScaleY() * scaleFactor);
	}
	
	@FXML protected void resetChart(ActionEvent event){
		chart.getData().clear();
		chart.getXAxis().setTickLabelsVisible(false);
		chart.getXAxis().setOpacity(0);	
		chart.getYAxis().setTickLabelsVisible(false);
	    chart.getYAxis().setOpacity(0);	
	}
	
	@FXML protected void openAdvancedSQL(ActionEvent event) throws IOException{
		WindowController.getInstance().showAdvancedFrame();
		
	}
	
	@FXML protected void logout(ActionEvent event) throws IOException{
		cm.logout();
		WindowController.getInstance().logout();
		WindowController.getInstance().showLoginFrame();
		WindowController.getInstance().closeMainFrame();
	}

	@Override
	public boolean assignOnX(String attributeX) {
		Axis xAxis = chart.getXAxis();
		xAxis.setTickLabelsVisible(true);
		xAxis.setOpacity(1);
		xAxis.setLabel(attributeX);
		try {
	        chart.getData().add(generateSeries(attributeX, chart.getYAxis().getLabel()));
		} catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException | NoSuchMethodException| SecurityException e) {
			e.printStackTrace();
			e.getCause();
		}
		xAxis.setVisible(true);
		return true;
	}
	
	@Override
	public boolean assignOnY(String attributeY) {
		Axis yAxis = chart.getYAxis();
		yAxis.setLabel(attributeY);
		yAxis.setTickLabelsVisible(true);
		yAxis.setOpacity(1);
		yAxis.setLabel(attributeY);
		try {
			chart.getData().add(generateSeries(chart.getXAxis().getLabel(), attributeY));
		} catch (IllegalAccessException | IllegalArgumentException| InvocationTargetException | NoSuchMethodException| SecurityException e) {
			e.printStackTrace();
		}
		yAxis.setVisible(true);
		return true;
	}
	
	/**
	 * generiert aus der Tabelle die Grafik
	 * 
	 * @param xAttribute
	 * @param yAttribute
	 * @return
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private Series generateSeries(String xAttribute, String yAttribute) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		// variablen initiieren
		Series data = new XYChart.Series();
		List<String> keys = new ArrayList<>();
		List<Number> values = new ArrayList<>();
		// Inhalte der Tabelle abfragen & chart komplett leeren
		tableContent = resultTable.getItems();
		chart.getData().clear();
		// über alle Reihen iterieren
		for (int i=0; i < tableContent.size(); i++){
			// Datensatz abgreifen und 
			Object o = tableContent.get(i);
			keys.add(o.getClass().getDeclaredMethod("get" + xAttribute, null).invoke(o, null).toString());
			values.add(new Double (o.getClass().getDeclaredMethod("get" + yAttribute, null).invoke(o, null).toString()));
		}
		// Datamining über die keys der HashMap
		HashMap<String, Number> map = DataMiner.cleanRedundantKeys(keys, values);
		// Sortierung der HashMap
		SortedSet<String> sortedKeys = new TreeSet<String>(map.keySet());
		for (String key : sortedKeys){
			data.getData().add( new XYChart.Data(key, map.get(key)));
		}
		return data;
	}
	
	@FXML protected void openHandbuch (ActionEvent event) {
		try {
			cm.openHandbuch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@FXML protected void openJavaDoc (ActionEvent event) {
		try {
			cm.openJavaDoc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}

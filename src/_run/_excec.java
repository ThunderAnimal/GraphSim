package _run;


import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;

import gui.controller.WindowController;
/**
 * Klasse zum Starten von GraphSim
 * @author GraphSim
 *
 */
public class _excec extends Application{
	
	
	public static void main(String args[]){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		WindowController.getInstance().setLoginStage(primaryStage);
		WindowController.getInstance().showLoginFrame();
	}

}

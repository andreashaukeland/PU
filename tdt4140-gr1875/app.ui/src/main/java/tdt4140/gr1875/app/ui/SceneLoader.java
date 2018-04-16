package tdt4140.gr1875.app.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * This class is a helping class that opens a new window and closes the current window.
 * 
 */

public class SceneLoader {
    public static void loadWindow(String loc, Node root, Object classname) {
    	try {
			Parent parent = FXMLLoader.load(classname.getClass().getResource(loc));
			Scene newScene = new Scene(parent);
	    	Stage curStage = (Stage) root.getScene().getWindow();
	    	curStage.setScene(newScene);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

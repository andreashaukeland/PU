package tdt4140.gr1875.app.ui;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.framework.junit.ApplicationTest;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import junit.framework.Assert;

public class RunnerToolbarUITest extends ApplicationTest {
	
	@BeforeClass
    public static void headless() {
    		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
    			GitlabCISupport.headless();
    		}
    }

	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RunnerToolbar.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
	
	@Test
	 public void NextTrainingTest() {
		JFXButton nextTraining = (JFXButton) lookup("#nextTraining").query();
	    	Assert.assertEquals("Next Training", nextTraining.getText());
	    	Assert.assertEquals(400.0, nextTraining.getPrefHeight());
	    	Assert.assertEquals(250.0, nextTraining.getPrefWidth());
	 }
	
	@Test
	 public void ViewResultsTest() {
		JFXButton viewResults = (JFXButton) lookup("#ViewResults").query();
	    	Assert.assertEquals("View Trainings", viewResults.getText());
	    	Assert.assertEquals(400.0, viewResults.getPrefHeight());
	    	Assert.assertEquals(250.0, viewResults.getPrefWidth());
	    	
	 }
	
	@Test
	 public void ViewProgressTest() {
		JFXButton viewProgress = (JFXButton) lookup("#ViewProgress").query();
	    	Assert.assertEquals("View Progress", viewProgress.getText());
	    	Assert.assertEquals(400.0, viewProgress.getPrefHeight());
	    	Assert.assertEquals(250.0, viewProgress.getPrefWidth());
    	
	 }
}	

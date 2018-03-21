package tdt4140.gr1875.app.ui;

import java.io.IOException;

import org.junit.Test;
import org.loadui.testfx.GuiTest;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import junit.framework.Assert;

public class RunnerToolbarUITest extends GuiTest {
	
	@Override
	protected Parent getRootNode() {
		try {
			return FXMLLoader.load(this.getClass().getResource("RunnerToolbar.fxml"));
		} catch (IOException e) {
			System.err.println(e);
		}
		return null;
	}
	
	@Test
	 public void NextTrainingTest() {
		JFXButton nextTraining = find("#nextTraining");
	    	Assert.assertEquals("Next Training", nextTraining.getText());
	    	Assert.assertEquals(400.0, nextTraining.getPrefHeight());
	    	Assert.assertEquals(250.0, nextTraining.getPrefWidth());
	 }
	
	@Test
	 public void ViewResultsTest() {
		JFXButton viewResults = find("#ViewResults");
	    	Assert.assertEquals("View Trainings", viewResults.getText());
	    	Assert.assertEquals(400.0, viewResults.getPrefHeight());
	    	Assert.assertEquals(250.0, viewResults.getPrefWidth());
	    	
	 }
	
	@Test
	 public void ViewProgressTest() {
		JFXButton viewProgress = find("#ViewProgress");
	    	Assert.assertEquals("View Progress", viewProgress.getText());
	    	Assert.assertEquals(400.0, viewProgress.getPrefHeight());
	    	Assert.assertEquals(250.0, viewProgress.getPrefWidth());
    	
	 }
}	

package tdt4140.gr1875.app.ui;

import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import org.testfx.framework.junit.ApplicationTest;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import junit.framework.Assert;

public class LoginUITest extends ApplicationTest{
	
	@BeforeClass
    public static void headless() {
    		if (Boolean.valueOf(System.getProperty("gitlab-ci", "false"))) {
    			GitlabCISupport.headless();
    		}
    }

	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
	 
	 @Test
	 public void createUserButtonTest() {
		JFXButton createUserButton = (JFXButton) lookup("#createUserButton").query();
	    	Assert.assertEquals("Create User", createUserButton.getText());
	    	Assert.assertEquals(231.0, createUserButton.getLayoutX());
	    	Assert.assertEquals(301.0, createUserButton.getLayoutY());
	    	Assert.assertEquals(43.0, createUserButton.getPrefHeight());
	    	Assert.assertEquals(97.0, createUserButton.getPrefWidth());
	 }
	 
	 @Test
	 public void loginButtonTest() {
		JFXButton loginButton = (JFXButton) lookup("#loginButton").query();
	    	Assert.assertEquals("Login", loginButton.getText());
	    	Assert.assertEquals(79.0, loginButton.getLayoutX());
	    	Assert.assertEquals(301.0, loginButton.getLayoutY());
	    	Assert.assertEquals(43.0, loginButton.getPrefHeight());
	    	Assert.assertEquals(97.0, loginButton.getPrefWidth());
	 }
	 
	 @Test
	 public void passwordFieldTest() {
		JFXPasswordField passwordField = (JFXPasswordField) lookup("#passwordField").query();
	    	Assert.assertEquals("Password", passwordField.getPromptText());
	    	Assert.assertEquals(144.0, passwordField.getLayoutX());
	    	Assert.assertEquals(211.0, passwordField.getLayoutY());
	 }
	 
	 @Test
	 public void usernameFieldTest() {
		JFXTextField usernameField = (JFXTextField) lookup("#usernameField").query();
	    	Assert.assertEquals("Username", usernameField.getPromptText());
	    	Assert.assertEquals(143.0, usernameField.getLayoutX()); //change later to 144 (same as passwordfield)
	    	Assert.assertEquals(130.0, usernameField.getLayoutY());
	 }
}

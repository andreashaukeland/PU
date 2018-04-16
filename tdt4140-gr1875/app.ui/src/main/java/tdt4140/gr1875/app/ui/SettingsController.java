package tdt4140.gr1875.app.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tdt4140.gr1875.app.core.SessionInformation;
import tdt4140.gr1875.app.core.UseDB;
import tdt4140.gr1875.app.core.CreateNewUser;

/*
 * This class is used to delete the users account, set new username and password and change sound on/off. 
 * 
 */

public class SettingsController {

	@FXML
    public JFXCheckBox checkBox;

    @FXML
    public FontAwesomeIconView soundOnFA;
    
    @FXML
    public JFXButton backButton; 
    
    @FXML
    private AnchorPane anchorPane;
    
    @FXML
    private JFXTextField usernameField;
    
    @FXML
    private JFXTextField passwordField;
    
    @FXML
    private JFXButton passwordButton;
    
    @FXML
    private JFXButton usernameButton;

    @FXML
    private JFXButton deleteButton;
    
    
    public static Clip audioClip;

    @FXML
    public void OnCheck(ActionEvent event) {
    	if (!checkBox.isSelected()) {
    		soundOnFA.setGlyphName("VOLUME_OFF");
    		//stopSond();
    	}else if(checkBox.isSelected()){
    		soundOnFA.setGlyphName("VOLUME_UP");
    		//playSound();
    	}
    	
    }
    
    @FXML
    public void onSetUsername() {
    	
    }
    
    @FXML
    public void onSetPassword() {
    	
    }
    
    @FXML
    public void onDeleteAccount() {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Delete account");
    	alert.setHeaderText("Are you sure you want to delete your account?");
    	
    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK) {
    		int userId = SessionInformation.userId;
        	ArrayList<ArrayList<String>> list = UseDB.getTable("SELECT username"
    				+ " FROM login"
    				+ " WHERE loginid =" + userId +";");
        	String username = list.get(0).get(0);
        	CreateNewUser user = new CreateNewUser();
        	user.deleteUser(username, userId, SessionInformation.userType);
        	SessionInformation.userId = 0;
    		SessionInformation.userType = "";
    		SceneLoader.loadWindow("LoginScreen.fxml", (Node) backButton, this);
    	}
    	else {
    		return;
    	}
    	
    	
    }
    
    
    

    
    //Error when reading Elevator.wav. Worked before, reason not found.
    /*
    void playSound() {
    	File audioFile = new File("Elevator.wav");
    	 
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            audioClip = (Clip) AudioSystem.getLine(info);
           
            audioClip.open(audioStream);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY); 
            audioClip.start();
             

        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
    }
    
    void stopSond() {
    	audioClip.stop();
    }
    */

	@FXML
    public void OnBackArrow(ActionEvent event) {
		if(SessionInformation.userType.equals("runner")) {
			SceneLoader.loadWindow("RunnerMainScreen.fxml", (Node) anchorPane, this);
		}
		else {
			SceneLoader.loadWindow("TrainerMainScreen.fxml", (Node) anchorPane, this);
		}
    }
	
}

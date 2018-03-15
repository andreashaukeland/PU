package tdt4140.gr1875.app.ui;

import java.io.File;
import java.io.IOException;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tdt4140.gr1875.app.core.SessionInformation;

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

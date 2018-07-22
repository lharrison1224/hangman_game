/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmangame;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lkh6y
 */
public class HangmanStatsViewController extends Controller implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Scene gamePageScene;
    private FXMLDocumentController gamePageController;
    private Stage stage;
    
    @FXML
    private Label gameGuess;
    
    @FXML
    private Label gameResult;
    
    @FXML
    private Label sessionWord;
    
    @FXML
    private Label sessionResult;
    
    @FXML
    public void goToGamePage(ActionEvent event){
        stage.setScene(gamePageScene);
    }
    
    @FXML
    @Override
    public void handleAbout(){
        gamePageController.handleAbout();
    }
    
    @FXML 
    @Override
    public void handleNewGame(){
        gamePageController.handleNewGame();
        stage.setScene(gamePageScene);
    }
    
    @FXML
    @Override
    public void handleOpen(){
        gamePageController.handleOpen();
        stage.setScene(gamePageScene);
    }
    
    @FXML
    @Override
    public void handleSave(){
        gamePageController.handleSave();
    }
    
    public void setGameGuesses(String guesses){
        gameGuess.setText(guesses);
    }
    
    public void setGameGuessesResult(String result){
        gameResult.setText(result);
    }
    
    public void setPreviousWords(String previousWords){
        sessionWord.setText(previousWords);
    }
    
    public void setPreviousWordsResults(String results){
        sessionResult.setText(results);
    }
    
    public void setGamePageScene(Scene gamePageScene){
        this.gamePageScene = gamePageScene;
    }
    
    public void setGamePageController(FXMLDocumentController gamePageController){
        this.gamePageController = gamePageController;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void start(Stage stage){
        this.stage = stage;
    }
    
    
    
}

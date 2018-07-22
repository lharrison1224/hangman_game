/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmangame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author lkh6y
 */
public class HangmanGame extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("HangmanView.fxml"));
        Parent root = loader.load();
        FXMLDocumentController controller = loader.getController();
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Hangman");
        stage.show();
        controller.start(stage);*/
        
        //
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
        Parent root = loader.load();
        LoginViewController loginController = loader.getController();
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("Hangman Login");
        stage.show();
        loginController.start(stage);
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

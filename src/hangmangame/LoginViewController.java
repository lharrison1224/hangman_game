/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmangame;


import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;




/**
 * FXML Controller class
 *
 * @author lkh6y
 */
public class LoginViewController implements Initializable {
    private Stage stage;
    private Scene gamePageScene;
    private FXMLDocumentController gamePageController;
    
    Cipher desCipher;
    SecretKey myDesKey;
    
    
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
 
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        myDesKey = new SecretKeySpec("Hangman1".getBytes(), "DES");
        try { 
            desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void start(Stage stage){
        this.stage = stage;
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
    }
    
    public void goToGamePage(){
        try{
            if(gamePageScene == null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HangmanView.fxml"));
                Parent statsPageRoot = loader.load();
                gamePageController = loader.getController();
                gamePageScene = new Scene(statsPageRoot);
            }
        }catch (IOException ex){
        }
        
        gamePageController.setUsername(usernameField.getText());
        gamePageController.handleOpen();
        stage.setScene(gamePageScene);
        gamePageController.start(stage);  
    }
    
    
    
    @FXML
    public void handleLogin() throws SQLException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        Alert alert = new Alert(AlertType.WARNING);
        boolean loggedIn = false;
        
        //check with database here
        String url = "jdbc:mysql://ec2-54-227-232-20.compute-1.amazonaws.com:3306/CS3330Hangman";
        String dbUsername = "ec2-user";
        String dbPassword = "logan";

        System.out.println("Loading driver...");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver loaded!");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
            System.out.println("Database connected!");

            Statement stmt = null;
            String query = "select username, password from CS3330Hangman.userData";

            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while(rs.next()){
                String usernameFromDb = rs.getString("username");
                if(usernameField.getText().equals(usernameFromDb)){
                    System.out.println("Matched username");

                    byte[] passwordFromDb = rs.getBytes("password");

                    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
                    byte[] decryptedPassword = desCipher.doFinal(passwordFromDb);

                    String decryptedString = new String(decryptedPassword);
                    if(decryptedString.equals(passwordField.getText())){
                        loggedIn = true;
                        System.out.println("Matched password");
                    }
                }
            }
            connection.close();
        }
        
        if(loggedIn){
            goToGamePage();
            stage.setTitle("Hangman");
        }
        else{
            alert.setTitle("Error");
            alert.setHeaderText("Invalid username/password");
            alert.setContentText("Please enter your credentials again or create a new account");
            alert.showAndWait();
            usernameField.setText("");
            passwordField.setText("");
            usernameField.setPromptText("Username");
            passwordField.setPromptText("Password");
        }        
    }
    
    @FXML
    public void handleCreateAccount(){
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Create Accout");
        dialog.setHeaderText("Complete the Form Below");
        
        ImageView graphic = new ImageView(this.getClass().getResource("addPerson.png").toString());
        graphic.setFitHeight(75);
        graphic.setFitWidth(75);
        dialog.setGraphic(graphic);
        
        ButtonType createAccountButtonType = new ButtonType("Create Account", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createAccountButtonType, ButtonType.CANCEL);
        
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets (20, 60, 20, 60));
        
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");
        
        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);
        
        Node createAccountButton = dialog.getDialogPane().lookupButton(createAccountButtonType);
        createAccountButton.setDisable(true);
        
        
        
        username.textProperty().addListener((observable, oldValue, newValue) -> {
           createAccountButton.setDisable(newValue.trim().isEmpty());
        });
       
        dialog.getDialogPane().setContent(grid);
        
        Platform.runLater(() -> username.requestFocus());
        
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == createAccountButtonType){
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });
        
        Optional<Pair<String, String>> result = dialog.showAndWait();
        
        result.ifPresent(usernamePassword -> {
            if(usernamePassword.getKey().length() < 4 || usernamePassword.getKey().length() > 20){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Invalid Username");
                alert.setHeaderText("Your username must be between 4 and 20 characters");
                alert.setContentText("Please try re-creating your account with a valid username");
                alert.showAndWait();
                dialog.close();
            }
            else if(usernamePassword.getValue().length() < 4 || usernamePassword.getValue().length() > 20){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Invalid Password");
                alert.setHeaderText("Your password must be between 4 and 20 characters");
                alert.setContentText("Please try re-creating your account with a valid password");
                alert.showAndWait();
                dialog.close();
            }
            else{
                String url = "jdbc:mysql://ec2-54-227-232-20.compute-1.amazonaws.com:3306/CS3330Hangman";
                String dbUsername = "ec2-user";
                String dbPassword = "logan";

                System.out.println("Loading driver...");

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    System.out.println("Driver loaded!");
                } catch (ClassNotFoundException e) {
                    System.out.println(e);
                }

                System.out.println("Connecting database...");

                try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
                    System.out.println("Database connected!");


                    String query = "insert into userData (username, password)" + " values (?, ?)";
                    try (PreparedStatement preparedStmt = connection.prepareStatement(query)) {
                        preparedStmt.setString(1, username.getText());
                        
                        //encrypt password
                        byte[] text = password.getText().getBytes();
                        desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
                        byte[] encryptedPassword = desCipher.doFinal(text);
                        
                        preparedStmt.setBytes(2, encryptedPassword);
                        
                        preparedStmt.execute();
                        preparedStmt.close();
                        
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Account Creation Success");
                        alert.setHeaderText("Your account has been created!");
                        alert.setContentText("Please login with your new credentials to get started!");
                        
                        alert.showAndWait();
                    }
                    connection.close();

                } catch (SQLException e){
                    System.out.println(e);
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Create Account Error");
                    alert.setHeaderText("Something went wrong!");
                    alert.setContentText("Please try creating account again...");
                    alert.showAndWait();
                } catch (InvalidKeyException ex) {
                    Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalBlockSizeException ex) {
                    Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (BadPaddingException ex) {
                    Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
        });
    }
}

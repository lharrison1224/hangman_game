/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmangame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.Files;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.crypto.Cipher;

/**
 *
 * @author lkh6y
 */
public class FXMLDocumentController extends Controller implements Initializable, HangmanInterface {
    public HangmanModel model;
    private Scene statsPageScene;
    private Scene gamePageScene;
    private Stage stage;
    private HangmanStatsViewController statsController;
    private String username = "";
    

    
    @FXML
    Label wordLabel;
    
    @FXML
    public Circle head;
    
    @FXML
    Rectangle body;
    
    @FXML
    Rectangle leftArm;
    
    @FXML
    Rectangle rightArm;
    
    @FXML
    Rectangle leftLeg;
    
    @FXML
    Rectangle rightLeg;
    
    @FXML
    Button aButton; 
        
    @FXML
    Button bButton; 
        
    @FXML
    Button cButton; 
        
    @FXML
    Button dButton; 
        
    @FXML
    Button eButton; 
        
    @FXML
    Button fButton; 
        
    @FXML
    Button gButton; 
        
    @FXML
    Button hButton; 
        
    @FXML
    Button iButton; 
        
    @FXML
    Button jButton; 
        
    @FXML
    Button kButton; 
        
    @FXML
    Button lButton; 
        
    @FXML
    Button mButton; 
        
    @FXML
    Button nButton; 
        
    @FXML
    Button oButton; 
        
    @FXML
    Button pButton; 
        
    @FXML
    Button qButton; 
        
    @FXML
    Button rButton; 
        
    @FXML
    Button sButton; 
        
    @FXML
    Button tButton;     
        
    @FXML
    Button uButton; 
        
    @FXML
    Button vButton; 
        
    @FXML
    Button wButton; 
        
    @FXML
    Button xButton; 
        
    @FXML
    Button yButton; 
        
    @FXML
    Button zButton; 
    
    @FXML
    @Override
    public void handleAbout(){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Hangman Application");
        alert.setContentText("This application was designed as a final project for Professor Wergeles's CS3330 at "
                + "the University of Missouri. This was developed by and is property of Logan Harrison. 2017."); 
        alert.showAndWait();
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    

    @Override
    public void handleSave(){
        HangmanModel modelSave = model;
        
        if(modelSave == null){
            System.out.println("There is nothing to save");
            return;
        }
        
        
        
        //FileChooser fileChooser = new FileChooser();
        //File file = fileChooser.showSaveDialog(stage);
        String filePath = "modelData.txt";
        File file = new File(filePath);
        if (file != null) {
            try {
                FileOutputStream fileOut = new FileOutputStream(file.getPath());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                
                out.writeObject(modelSave);
                out.close();
                fileOut.close();
                /*Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setTitle("Save Successful");
                alert.setContentText("This session was successfully saved to " + file.getPath());
                alert.showAndWait();*/
                FileInputStream inputStream = new FileInputStream(file);
                String sql = "update userData set file = ? where username = '" + username + "'";
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
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setBlob(1, inputStream);
                    statement.executeUpdate();
            
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }   
                
                
            } catch (FileNotFoundException ex) {
                String message = "File not found exception occurred while saving to "+ file.getPath();
                System.out.println(message);
            } catch (IOException ex) {
                String message = "IOException occurred while saving to " + file.getPath();
                System.out.println(message);
            }
        }  
    }
    

    @Override
    public void handleOpen(){
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
            String selectSql = "select file from userData where username = '" + username + "'";
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            File file;
            try (ResultSet rs = pstmt.executeQuery()) {
                file = new File("dataFromDB.txt");
                FileOutputStream output = new FileOutputStream(file);
                while(rs.next()){
                    InputStream input = rs.getBinaryStream("file");
                    if(input != null){
                        byte[] buffer = new byte[1024];
                        while(input.read(buffer) >  0){
                            output.write(buffer);
                        }
                    }
                }
                output.close();
                rs.close();
            }
            pstmt.close();
            try (FileInputStream fileIn = new FileInputStream(file.getPath()); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                if(fileIn != null && in != null){    
                    model = (HangmanModel)in.readObject();
                    in.close();
                    fileIn.close();
                }
            } catch (java.io.EOFException e){}
            file.delete();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (FileNotFoundException ex) {   
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        updateBody();
        wordLabel.setText(model.getLabelText());
        setButtonStyle();
    }
    
    @FXML
    @Override
    public void handleNewGame(){
        resetGame();
    }
    
    @FXML
    public void goToStatsPage(){
        try{
            if(statsPageScene == null){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HangmanStatsView.fxml"));
                Parent statsPageRoot = loader.load();
                statsController = loader.getController();
                statsController.setGamePageScene(gamePageScene);
                statsController.setGamePageController(this);
                statsPageScene = new Scene(statsPageRoot);
            }
        }catch (Exception ex){
        }
        stage.setScene(statsPageScene);
        statsController.start(stage);
        statsController.setGameGuesses(model.getLettersGuessed());
        statsController.setGameGuessesResult(model.getLettersGuessedResults());
        statsController.setPreviousWords(model.getWordsPlayed());
        statsController.setPreviousWordsResults(model.getWordsResults());
    }
    
    public void start(Stage stage){
        this.stage = stage;
        gamePageScene = stage.getScene(); 
        
        stage.setOnCloseRequest(event -> {
            handleSave();
        });
        
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primaryScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primaryScreenBounds.getHeight() - stage.getHeight()) / 2);
    }
    
    
    
    @FXML
    public void aButtonOnAction(ActionEvent event){
        if(!(model.getPressedA())){
            model.checkGuess('A');
            model.appendToLettersGuessed('A');
            updateBody();
            wordLabel.setText(model.getLabelText());
            aButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            aButton.setEffect(shadow);
            model.pressA();
        }
        checkGameStatus();
    }
    @FXML
    public void bButtonOnAction(ActionEvent event){
        if(!(model.getPressedB())){
            model.checkGuess('B');
            model.appendToLettersGuessed('B');
            updateBody();
            wordLabel.setText(model.getLabelText());
            bButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            bButton.setEffect(shadow);
            model.pressB();
        }
        checkGameStatus();
    }
    @FXML
    public void cButtonOnAction(ActionEvent event){
        if(!(model.getPressedC())){
            model.checkGuess('C');
            model.appendToLettersGuessed('C');
            updateBody();
            wordLabel.setText(model.getLabelText());
            cButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            cButton.setEffect(shadow);
            model.pressC();
        }
        checkGameStatus();
    }
    @FXML
    public void dButtonOnAction(ActionEvent event){
        if(!(model.getPressedD())){
            model.checkGuess('D');
            model.appendToLettersGuessed('D');
            updateBody();
            wordLabel.setText(model.getLabelText());
            dButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            dButton.setEffect(shadow);
            model.pressD();
        }
        checkGameStatus();
    }
    @FXML
    public void eButtonOnAction(ActionEvent event){
        if(!(model.getPressedE())){
            model.checkGuess('E');
            model.appendToLettersGuessed('E');
            updateBody();
            wordLabel.setText(model.getLabelText());
            eButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            eButton.setEffect(shadow);
            model.pressE();
        }
        checkGameStatus();
    }
    @FXML
    public void fButtonOnAction(ActionEvent event){
        if(!(model.getPressedF())){
            model.checkGuess('F');
            model.appendToLettersGuessed('F');
            updateBody();
            wordLabel.setText(model.getLabelText());
            fButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            fButton.setEffect(shadow);
            model.pressF();
        }
        checkGameStatus();
    }
    @FXML
    public void gButtonOnAction(ActionEvent event){
        if(!(model.getPressedG())){
            model.checkGuess('G');
            model.appendToLettersGuessed('G');
            updateBody();
            wordLabel.setText(model.getLabelText());
            gButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            gButton.setEffect(shadow);
            model.pressG();
        }
        checkGameStatus();
    }
    @FXML
    public void hButtonOnAction(ActionEvent event){
        if(!(model.getPressedH())){
            model.checkGuess('H');
            model.appendToLettersGuessed('H');
            updateBody();
            wordLabel.setText(model.getLabelText());
            hButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            hButton.setEffect(shadow);
            model.pressH();
        }
        checkGameStatus();
    }
    @FXML
    public void iButtonOnAction(ActionEvent event){
        if(!(model.getPressedI())){
            model.checkGuess('I');
            model.appendToLettersGuessed('I');
            updateBody();
            wordLabel.setText(model.getLabelText());
            iButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            iButton.setEffect(shadow);
            model.pressI();
        }
        checkGameStatus();
    }
    @FXML
    public void jButtonOnAction(ActionEvent event){
        if(!(model.getPressedJ())){
            model.checkGuess('J');
            model.appendToLettersGuessed('J');
            updateBody();
            wordLabel.setText(model.getLabelText());
            jButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            jButton.setEffect(shadow);
            model.pressJ();
        }
        checkGameStatus();
    }
    @FXML
    public void kButtonOnAction(ActionEvent event){
        if(!(model.getPressedK())){
            model.checkGuess('K');
            model.appendToLettersGuessed('K');
            updateBody();
            wordLabel.setText(model.getLabelText());
            kButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            kButton.setEffect(shadow);
            model.pressK();
        }
        checkGameStatus();
    }
    @FXML
    public void lButtonOnAction(ActionEvent event){
        if(!(model.getPressedL())){
            model.checkGuess('L');
            model.appendToLettersGuessed('L');
            updateBody();
            wordLabel.setText(model.getLabelText());
            lButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            lButton.setEffect(shadow);
            model.pressL();
        }
        checkGameStatus();
    }
    @FXML
    public void mButtonOnAction(ActionEvent event){
        if(!(model.getPressedM())){
            model.checkGuess('M');
            model.appendToLettersGuessed('M');
            updateBody();
            wordLabel.setText(model.getLabelText());
            mButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            mButton.setEffect(shadow);
            model.pressM();
        }
        checkGameStatus();
    }
    @FXML
    public void nButtonOnAction(ActionEvent event){
        if(!(model.getPressedN())){
            model.checkGuess('N');
            model.appendToLettersGuessed('N');
            updateBody();
            wordLabel.setText(model.getLabelText());
            nButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            nButton.setEffect(shadow);
            model.pressN();
        }
        checkGameStatus();
    }
    @FXML
    public void oButtonOnAction(ActionEvent event){
        if(!(model.getPressedO())){
            model.checkGuess('O');
            model.appendToLettersGuessed('O');
            updateBody();
            wordLabel.setText(model.getLabelText());
            oButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            oButton.setEffect(shadow);
            model.pressO();
        }
        checkGameStatus();
    }
    @FXML
    public void pButtonOnAction(ActionEvent event){
        if(!(model.getPressedP())){
            model.checkGuess('P');
            model.appendToLettersGuessed('P');
            updateBody();
            wordLabel.setText(model.getLabelText());
            pButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            pButton.setEffect(shadow);
            model.pressP();
        }
        checkGameStatus();
    }
    @FXML
    public void qButtonOnAction(ActionEvent event){
        if(!(model.getPressedQ())){
            model.checkGuess('Q');
            model.appendToLettersGuessed('Q');
            updateBody();
            wordLabel.setText(model.getLabelText());
            qButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            qButton.setEffect(shadow);
            model.pressQ();
        }
        checkGameStatus();
    }
    @FXML
    public void rButtonOnAction(ActionEvent event){
        if(!(model.getPressedR())){
            model.checkGuess('R');
            model.appendToLettersGuessed('R');
            updateBody();
            wordLabel.setText(model.getLabelText());
            rButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            rButton.setEffect(shadow);
            model.pressR();
        }
        checkGameStatus();
    }
    @FXML
    public void sButtonOnAction(ActionEvent event){
        if(!(model.getPressedS())){
            model.checkGuess('S');
            model.appendToLettersGuessed('S');
            updateBody();
            wordLabel.setText(model.getLabelText());
            sButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            sButton.setEffect(shadow);
            model.pressS();
        }
        checkGameStatus();
    }
    @FXML
    public void tButtonOnAction(ActionEvent event){
        if(!(model.getPressedT())){
            model.checkGuess('T');
            model.appendToLettersGuessed('T');
            updateBody();
            wordLabel.setText(model.getLabelText());
            tButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            tButton.setEffect(shadow);
            model.pressT();
        }
        checkGameStatus();
    }
    @FXML
    public void uButtonOnAction(ActionEvent event){
        if(!(model.getPressedU())){
            model.checkGuess('U');
            model.appendToLettersGuessed('U');
            updateBody();
            wordLabel.setText(model.getLabelText());
            uButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            uButton.setEffect(shadow);
            model.pressU();
        }
        checkGameStatus();
    }
    @FXML
    public void vButtonOnAction(ActionEvent event){
        if(!(model.getPressedV())){
            model.checkGuess('V');
            model.appendToLettersGuessed('V');
            updateBody();
            wordLabel.setText(model.getLabelText());
            vButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            vButton.setEffect(shadow);
            model.pressV();
        }
        checkGameStatus();
    }
    @FXML
    public void wButtonOnAction(ActionEvent event){
        if(!(model.getPressedW())){
            model.checkGuess('W');
            model.appendToLettersGuessed('W');
            updateBody();
            wordLabel.setText(model.getLabelText());
            wButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            wButton.setEffect(shadow);
            model.pressW();
        }
        checkGameStatus();
    }
    @FXML
    public void xButtonOnAction(ActionEvent event){
        if(!(model.getPressedX())){
            model.checkGuess('X');
            model.appendToLettersGuessed('X');
            updateBody();
            wordLabel.setText(model.getLabelText());
            xButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            xButton.setEffect(shadow);
            model.pressX();
        }
        checkGameStatus();
    }
    @FXML
    public void yButtonOnAction(ActionEvent event){
        if(!(model.getPressedY())){
            model.checkGuess('Y');
            model.appendToLettersGuessed('Y');
            updateBody();
            wordLabel.setText(model.getLabelText());
            yButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            yButton.setEffect(shadow);
            model.pressY();
        }
        checkGameStatus();
    }
    @FXML
    public void zButtonOnAction(ActionEvent event){
        if(!(model.getPressedZ())){
            model.checkGuess('Z');
            model.appendToLettersGuessed('Z');
            updateBody();
            wordLabel.setText(model.getLabelText());
            zButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            zButton.setEffect(shadow);
            model.pressZ();
        }
        checkGameStatus();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model = new HangmanModel();
        updateBody();
        
        try {
            model.generateWord();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        model.setInitialLabel();
        wordLabel.setText(model.getLabelText());
        setButtonDefaults();
    }
    
    public void setButtonStyle(){
        if(model.getPressedA()){
            aButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            aButton.setEffect(shadow);
        }
        if(model.getPressedB()){
            bButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            bButton.setEffect(shadow);
        }
        if(model.getPressedC()){
            cButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            cButton.setEffect(shadow);
        }
        if(model.getPressedD()){
            dButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            dButton.setEffect(shadow);
        }
        if(model.getPressedE()){
            eButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            eButton.setEffect(shadow);
        }
        if(model.getPressedF()){
            fButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            fButton.setEffect(shadow);
        }
        if(model.getPressedG()){
            gButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            gButton.setEffect(shadow);
        }
        if(model.getPressedH()){
            hButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            hButton.setEffect(shadow);
        }
        if(model.getPressedI()){
            iButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            iButton.setEffect(shadow);
        }
        if(model.getPressedJ()){
            jButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            jButton.setEffect(shadow);
        }
        if(model.getPressedK()){
            kButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            kButton.setEffect(shadow);
        }
        if(model.getPressedL()){
            lButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            lButton.setEffect(shadow);
        }
        if(model.getPressedM()){
            mButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            mButton.setEffect(shadow);
        }
        if(model.getPressedN()){
            nButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            nButton.setEffect(shadow);
        }
        if(model.getPressedO()){
            oButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            oButton.setEffect(shadow);
        }
        if(model.getPressedP()){
            pButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            pButton.setEffect(shadow);
        }
        if(model.getPressedQ()){
            qButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            qButton.setEffect(shadow);
        }
        if(model.getPressedR()){
            rButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            rButton.setEffect(shadow);
        }
        if(model.getPressedS()){
            sButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            sButton.setEffect(shadow);
        }
        if(model.getPressedT()){
            tButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            tButton.setEffect(shadow);
        }
        if(model.getPressedU()){
            uButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            uButton.setEffect(shadow);
        }
        if(model.getPressedV()){
            vButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            vButton.setEffect(shadow);
        }
        if(model.getPressedW()){
            wButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            wButton.setEffect(shadow);
        }
        if(model.getPressedX()){
            xButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            xButton.setEffect(shadow);
        }
        if(model.getPressedY()){
            yButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            yButton.setEffect(shadow);
        }
        if(model.getPressedZ()){
            zButton.setStyle("-fx-background-color: #666666;");
            InnerShadow shadow = new InnerShadow();
            shadow.setOffsetX(3);
            shadow.setOffsetY(3);
            shadow.setColor(Color.BLACK);
            zButton.setEffect(shadow);
        }
    }

    @Override
    public void updateBody(){
        head.setVisible(model.getHeadVisibility());
        body.setVisible(model.getBodyVisibility());
        rightArm.setVisible(model.getRightArmVisibility());
        leftArm.setVisible(model.getLeftArmVisibility());
        rightLeg.setVisible(model.getRightLegVisibility());
        leftLeg.setVisible(model.getLeftLegVisibility());
    }
    
    @Override
    public void checkGameStatus(){
        if(model.checkWin()){
            //win game code
            //System.out.println("win");
            Alert winAlert = new Alert(AlertType.CONFIRMATION);
            winAlert.setTitle("Game Won");
            winAlert.setHeaderText("Congratulations! You have won!");
            winAlert.setContentText("Would you like to play again?");
            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");
            winAlert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            Optional<ButtonType> result = winAlert.showAndWait();
            if(result.get() == buttonTypeOne){
                //play again
                resetGame();
            }
            if(result.get() == buttonTypeTwo){
                //exit app
                System.exit(0);
            }
        }
        
        if(model.checkLose()){
            //lose game code
            //show correct word
            model.setFinalLabel();
            wordLabel.setText(model.getLabelText());
            
            Alert loseAlert = new Alert(AlertType.CONFIRMATION);
            loseAlert.setTitle("Game Lost");
            loseAlert.setHeaderText("You have lost. Better luck next time.");
            loseAlert.setContentText("Would you like to play again?");
            ButtonType buttonTypeOne = new ButtonType("Yes");
            ButtonType buttonTypeTwo = new ButtonType("No");
            loseAlert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            Optional<ButtonType> result = loseAlert.showAndWait();
            if(result.get() == buttonTypeOne){
                //play again
                resetGame();
            }
            if(result.get() == buttonTypeTwo){
                //exit app
                handleSave();
                System.exit(0);
            }
        }    
    }
    
    @Override
    public void resetGame(){
        //reset the game
        setButtonDefaults();
        
        String wordsHold = model.getWordsPlayed();
        String wordsResultsHold = model.getWordsResults();
        model = new HangmanModel();
        updateBody();
        try {
            model.generateWord();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        model.setInitialLabel();
        wordLabel.setText(model.getLabelText());
        model.setWordsPlayed(wordsHold);
        model.setWordsPlayedResults(wordsResultsHold);
    }
    
    public void setButtonDefaults(){
        Light.Distant light = new Light.Distant();
        light.setAzimuth(225);
        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(3.0);
        aButton.setStyle(null);
        bButton.setStyle(null);
        cButton.setStyle(null);
        dButton.setStyle(null);
        eButton.setStyle(null);
        fButton.setStyle(null);
        gButton.setStyle(null);
        hButton.setStyle(null);
        iButton.setStyle(null);
        jButton.setStyle(null);
        kButton.setStyle(null);
        lButton.setStyle(null);
        mButton.setStyle(null);
        nButton.setStyle(null);
        oButton.setStyle(null);
        pButton.setStyle(null);
        qButton.setStyle(null);
        rButton.setStyle(null);
        sButton.setStyle(null);
        tButton.setStyle(null);
        uButton.setStyle(null);
        vButton.setStyle(null);
        wButton.setStyle(null);
        xButton.setStyle(null);
        yButton.setStyle(null);
        zButton.setStyle(null);
        aButton.setEffect(lighting);
        bButton.setEffect(lighting);
        cButton.setEffect(lighting);
        dButton.setEffect(lighting);
        eButton.setEffect(lighting);
        fButton.setEffect(lighting);
        gButton.setEffect(lighting);
        hButton.setEffect(lighting);
        iButton.setEffect(lighting);
        jButton.setEffect(lighting);
        kButton.setEffect(lighting);
        lButton.setEffect(lighting);
        mButton.setEffect(lighting);
        nButton.setEffect(lighting);
        oButton.setEffect(lighting);
        pButton.setEffect(lighting);
        qButton.setEffect(lighting);
        rButton.setEffect(lighting);
        sButton.setEffect(lighting);
        tButton.setEffect(lighting);
        uButton.setEffect(lighting);
        vButton.setEffect(lighting);
        wButton.setEffect(lighting);
        xButton.setEffect(lighting);
        yButton.setEffect(lighting);
        zButton.setEffect(lighting);
    }
}

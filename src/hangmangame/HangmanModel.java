/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hangmangame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author lkh6y
 */
public class HangmanModel implements java.io.Serializable {
    private String word = "";
    private StringBuilder labelText;
    private boolean headVisibility = false;
    private boolean bodyVisibility = false;
    private boolean rightArmVisibility = false;
    private boolean leftArmVisibility = false;
    private boolean rightLegVisibility = false;
    private boolean leftLegVisibility = false;
    private boolean aButtonPressed = false;
    private boolean bButtonPressed = false;
    private boolean cButtonPressed = false;
    private boolean dButtonPressed = false;
    private boolean eButtonPressed = false;
    private boolean fButtonPressed = false;
    private boolean gButtonPressed = false;
    private boolean hButtonPressed = false;
    private boolean iButtonPressed = false;
    private boolean jButtonPressed = false;
    private boolean kButtonPressed = false;
    private boolean lButtonPressed = false;
    private boolean mButtonPressed = false;
    private boolean nButtonPressed = false;
    private boolean oButtonPressed = false;
    private boolean pButtonPressed = false;
    private boolean qButtonPressed = false;
    private boolean rButtonPressed = false;
    private boolean sButtonPressed = false;
    private boolean tButtonPressed = false;
    private boolean uButtonPressed = false;
    private boolean vButtonPressed = false;
    private boolean wButtonPressed = false;
    private boolean xButtonPressed = false;
    private boolean yButtonPressed = false;
    private boolean zButtonPressed = false;
    
    private String lettersGuessed = "";
    private String lettersGuessedResults = "";
    private String wordsPlayed = "";
    private String wordsPlayedResults = "";
    
    
    public String getWord(){
        return word;
    }
    
    public void appendToWordsPlayed(String word){
        wordsPlayed += word + "\n";
    }
    
    public String getWordsResults(){
        return this.wordsPlayedResults;
    }
    
    public void setWordsPlayedResults(String results){
        this.wordsPlayedResults = results;
    }
    
    public String getWordsPlayed(){
        return this.wordsPlayed;
    }
    
    public void setWordsPlayed(String words){
        this.wordsPlayed = words;
    }
     
    public void appendToLettersGuessed(char guess){
        lettersGuessed += guess + "\n";
    }
    
    public String getLettersGuessed(){
        return lettersGuessed;
    }
    
    public void appendToLettersGuessedResults(String result){
        lettersGuessedResults += result + "\n";
    }
    
    public String getLettersGuessedResults(){
        return lettersGuessedResults;
    }
    
    public int getWordLength(){
        return word.length();
    }
    
    public void generateWord() throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader("test/hangmanWordListFinal.txt"));
        String line = null;
        int iterationCount = 0;
        int iterationGoal = (int)(Math.random() * 17536);
        while ((line = br.readLine()) != null && iterationCount < iterationGoal){
            word = line;
            iterationCount++;
        }
        word = word.toUpperCase();
        //appendToWordsPlayed(word);
        br.close(); 
    }
    
    public String getLabelText(){
        return labelText.toString();
    }
    
    public void setInitialLabel(){
        String working = "_";
        for(int i = 1; i < getWordLength(); i++){
            working = working + " _";
        }
        
        labelText = new StringBuilder(working);
    }
    
    public void setFinalLabel(){
        int n = word.length();
        for(int i = 0; i < n; i++){
            labelText.setCharAt(2*i, word.charAt(i));
        }
    }
    

    
    public void checkGuess(char guess){
        boolean charExists = false;

        //check to see if char is in word
        for(int i = 0; i < word.length(); i++){
            if (guess == word.charAt(i)){
                charExists = true;
            }
        }

        if(charExists){
            for(int i = 0; i < word.length(); i++){
                if(word.charAt(i) == guess){
                    labelText.setCharAt(2*i, guess);
                }
            }
            
            appendToLettersGuessedResults("✔");
        }
        
        else{
            appendToLettersGuessedResults("✖");
            
            if(!headVisibility)
                headVisibility = true;
            else if(!bodyVisibility)
                bodyVisibility = true;
            else if(!leftLegVisibility)
                leftLegVisibility = true;
            else if(!rightLegVisibility)
                rightLegVisibility = true;
            else if(!leftArmVisibility)
                leftArmVisibility = true;
            else if(!rightArmVisibility)
                rightArmVisibility = true;
        }
    }
    
    public boolean getHeadVisibility(){
        return headVisibility;
    }
    
    public boolean getBodyVisibility(){
        return bodyVisibility;
    }
    
    public boolean getRightArmVisibility(){
        return rightArmVisibility;
    }
    
    public boolean getLeftArmVisibility(){
        return leftArmVisibility;
    }
    
    public boolean getLeftLegVisibility(){
        return leftLegVisibility;
    }
    
    public boolean getRightLegVisibility(){
        return rightLegVisibility;
    }
    
    public void setHeadVisibility(boolean x){
        headVisibility = x;
    }
    
    public void setBodyVisibility(boolean x){
        bodyVisibility = x;
    }
    
    public void setLeftLegVisibility(boolean x){
        leftLegVisibility = x;
    }
    
    public void setRightLegVisibility(boolean x){
        rightLegVisibility = x;
    }
    
    public void setLeftArmVisibility(boolean x){
        leftArmVisibility = x;
    }
    
    public void setRightArmVisibility(boolean x){
        rightArmVisibility = x;
    }
    
    public boolean checkLose(){
        if(headVisibility && bodyVisibility && leftArmVisibility && rightArmVisibility && leftLegVisibility && rightLegVisibility){
            wordsPlayedResults += "LOSE\n";
            wordsPlayed += word + "\n";
            return true;            
        }

        else
            return false;
    }
    
    public boolean checkWin(){
        if(!(labelText.toString().contains("_"))){
            wordsPlayedResults += "WIN\n";
            wordsPlayed += word + "\n";
            return true;
        }   
        else
            return false;
    }
    
    public void pressA(){
        aButtonPressed = true;
    }
    public void pressB(){
        bButtonPressed = true;
    }
    public void pressC(){
        cButtonPressed = true;
    }
    public void pressD(){
        dButtonPressed = true;
    }
    public void pressE(){
        eButtonPressed = true;
    }
    public void pressF(){
        fButtonPressed = true;
    }
    public void pressG(){
        gButtonPressed = true;
    }
    public void pressH(){
        hButtonPressed = true;
    }
    public void pressI(){
        iButtonPressed = true;
    }
    public void pressJ(){
        jButtonPressed = true;
    }
    public void pressK(){
        kButtonPressed = true;
    }
    public void pressL(){
        lButtonPressed = true;
    }
    public void pressM(){
        mButtonPressed = true;
    }
    public void pressN(){
        nButtonPressed = true;
    }
    public void pressO(){
        oButtonPressed = true;
    }
    public void pressP(){
        pButtonPressed = true;
    }
    public void pressQ(){
        qButtonPressed = true;
    }
    public void pressR(){
        rButtonPressed = true;
    }
    public void pressS(){
        sButtonPressed = true;
    }
    public void pressT(){
        tButtonPressed = true;
    }
    public void pressU(){
        uButtonPressed = true;
    }
    public void pressV(){
        vButtonPressed = true;
    }
    public void pressW(){
        wButtonPressed = true;
    }
    public void pressX(){
        xButtonPressed = true;
    }
    public void pressY(){
        yButtonPressed = true;
    }
    public void pressZ(){
        zButtonPressed = true;
    }
    
    public boolean getPressedA(){
        return aButtonPressed;
    }
    public boolean getPressedB(){
        return bButtonPressed;
    }
    public boolean getPressedC(){
        return cButtonPressed;
    }
    public boolean getPressedD(){
        return dButtonPressed;
    }
    public boolean getPressedE(){
        return eButtonPressed;
    }
    public boolean getPressedF(){
        return fButtonPressed;
    }
    public boolean getPressedG(){
        return gButtonPressed;
    }
    public boolean getPressedH(){
        return hButtonPressed;
    }
    public boolean getPressedI(){
        return iButtonPressed;
    }
    public boolean getPressedJ(){
        return jButtonPressed;
    }
    public boolean getPressedK(){
        return kButtonPressed;
    }
    public boolean getPressedL(){
        return lButtonPressed;
    }
    public boolean getPressedM(){
        return mButtonPressed;
    }
    public boolean getPressedN(){
        return nButtonPressed;
    }
    public boolean getPressedO(){
        return oButtonPressed;
    }
    public boolean getPressedP(){
        return pButtonPressed;
    }
    public boolean getPressedQ(){
        return qButtonPressed;
    }
    public boolean getPressedR(){
        return rButtonPressed;
    }
    public boolean getPressedS(){
        return sButtonPressed;
    }
    public boolean getPressedT(){
        return tButtonPressed;
    }
    public boolean getPressedU(){
        return uButtonPressed;
    }
    public boolean getPressedV(){
        return vButtonPressed;
    }
    public boolean getPressedW(){
        return wButtonPressed;
    }
    public boolean getPressedX(){
        return xButtonPressed;
    }
    public boolean getPressedY(){
        return yButtonPressed;
    }
    public boolean getPressedZ(){
        return zButtonPressed;
    }
}

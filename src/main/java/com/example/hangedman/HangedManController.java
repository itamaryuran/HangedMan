package com.example.hangedman;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

import static com.example.hangedman.GameLogics.getWords;

public class HangedManController {

        @FXML
        private Canvas canv1;       //for drawing the man


        @FXML
        private Label lab;          //presenting the word


        @FXML
        private Label labL;         //presenting the letters


        @FXML
        private GridPane grid;      //contains the buttons


         @FXML
        private VBox vbox;

    private GraphicsContext gc1;
        private int strike =0;

        private ArrayList<String> arr = new ArrayList<>();      //to hold all words
        private String theWord, currWord,chosenL;               //hold chosen word, str with lines and true lettes chosen,all letters chosen
        private int toWin;                                      //num letters to win

    public void initialize() throws IOException {
        currWord=chosenL = "";
        gc1= canv1.getGraphicsContext2D();
        gc1.setFill(Color.LIGHTBLUE);
        gc1.fillRect(0, 0, canv1.getWidth(), canv1.getHeight());
        lab.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));   //setting nice backgrounds
        grid.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));   //setting nice backgrounds
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));   //setting nice backgrounds

        setButtons();                           //sets all buttons
        strike = 0;                              //num of wrong guesses
        arr = getWords();                       //fill arr with words
        theWord = getWord();                    //gets random word from arr
        for (int i = 0; i < theWord.length(); i++) {//sets the string of chosen words to underlines
            currWord +="-";
        }
        toWin = theWord.length();               //sets num guesses to win
        draw(strike++);                         //sets the hanging rope
        lab.setText(currWord);
        labL.setText("");


    }
    public void setButtons(){           //setting up al buttons
        int i = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            i++;
            Button button = new Button(""+c);
            button.setPrefSize(grid.getPrefWidth()/5,grid.getPrefHeight()/5);
            grid.add(button,(i%13),i/13);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {       //setting action evnet to pressed buttons
                    button.setDisable(true);
                    check(button.getText());

                }
            });

        }

    }
    public static ArrayList<String> getWords() throws IOException {//gets all words from file
        ArrayList<String> arr = new ArrayList<>();
        Scanner s = new Scanner(Paths.get("words.txt"));
        while(s.hasNext()){
            arr.add(s.next());
        }
        return arr;
    }

    public String getWord(){        //gets random word and removes it from arr
        Random r = new Random();
        if (arr.size() ==0){            //case out of words in arr
            labL.setText("NO more\nWords in \n for today):");

            for (Node button: grid.getChildren()){// disabeling buttons
                button.setDisable(true);
                return"";
            }
        }
        int i = r.nextInt(arr.size());
        return (arr.remove(i));
    }

    public void check(String c){        //check a chosen letter
        theWord = theWord.toUpperCase();
        chosenL+=c+", ";                //add to chosen letters set
        labL.setText(chosenL);
        if (theWord.indexOf(c) == -1){     //case letter is not in word
            draw(strike++);                 //drawing another organ, increase num of strikes
        }
        else {
        for (int index = theWord.indexOf(c);//switching all ocurences of a letter in curr word
             index >= 0;
             index = theWord.indexOf(c, index + 1))
        {
            currWord = currWord.substring(0,index)+c+currWord.substring(index+1);
            toWin--;
        }
        }
        lab.setText(currWord);      //presenting new cur word
        if (toWin == 0) {           // case all letters where chosen
           win();

        }
    }
    public void draw(int i) {// drawing the hanged man, i stands for num of strikes
        if (i == 0) {
            gc1.strokeLine(canv1.getWidth() / 10, canv1.getWidth() / 10, canv1.getWidth() / 10, 9 * canv1.getWidth() / 10);
            gc1.strokeLine(canv1.getWidth() / 10, canv1.getWidth() / 10, 5 * canv1.getWidth() / 10, canv1.getWidth() / 10);
            gc1.strokeLine(5 * canv1.getWidth() / 10, canv1.getWidth() / 10, 5 * canv1.getWidth() / 10, 2 * canv1.getWidth() / 10);
            gc1.strokeOval(4.5 * canv1.getWidth() / 10, 2 * canv1.getWidth() / 10, 1 * canv1.getWidth() / 10, 1 * canv1.getWidth() / 10);

        }
        if (i == 1) {
            gc1.strokeLine(5 * canv1.getWidth() / 10, 7 * canv1.getWidth() / 10, 3.5 * canv1.getWidth() / 10, 9 * canv1.getWidth() / 10);
        }
        if (i == 2) {
            gc1.strokeLine(5 * canv1.getWidth() / 10, 7 * canv1.getWidth() / 10, 6.5 * canv1.getWidth() / 10, 9 * canv1.getWidth() / 10);

        }
        if (i == 3) {
            gc1.strokeLine(5 * canv1.getWidth() / 10, 7 * canv1.getWidth() / 10, 5 * canv1.getWidth() / 10, 3.2 * canv1.getWidth() / 10);

        }
        if (i == 4) {
            gc1.strokeLine(5 * canv1.getWidth() / 10, 3.2 * canv1.getWidth() / 10, 6.5 * canv1.getWidth() / 10, 5 * canv1.getWidth() / 10);

        }
        if (i == 5) {
            gc1.strokeLine(5 * canv1.getWidth() / 10, 3.2 * canv1.getWidth() / 10, 3.5 * canv1.getWidth() / 10, 5 * canv1.getWidth() / 10);

        }
        if (i==6){
            gc1.strokeLine(5 * canv1.getWidth() / 10, 3.2 * canv1.getWidth() / 10, 5.1 * canv1.getWidth() / 10, 2.7 * canv1.getWidth() / 10);

        }
        if (i==7){      //max num of strikes, the man is hanged, game over
            gc1.setFill(Color.BLACK);
            gc1.fillOval(5 * canv1.getWidth() / 10, 2 * canv1.getWidth() / 10, 1 * canv1.getWidth() / 10, 1 * canv1.getWidth() / 10);
            loose();
        }
    }
    public void loose(){        //game over
        labL.setText("game over ):\nthe word\nis: "+theWord);
        for (Node button: grid.getChildren()){//disabeling all buttons
            button.setDisable(true);

        }

    }
    public void win(){
        lab.setText(currWord + "\nYOU WON!!!");
        for (Node button: grid.getChildren()){
            button.setDisable(true);

        }
    }
    @FXML
    void restart(MouseEvent event) throws IOException {
        vbox.getChildren().removeAll(lab);
        for (Node button: grid.getChildren()){
            button.setDisable(true);
        }
        initialize();


    }


}
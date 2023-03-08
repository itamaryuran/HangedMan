package com.example.hangedman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

public class GameLogics {

public static ArrayList<String> getWords() throws IOException {
    ArrayList<String> arr = new ArrayList<>();
    Scanner s = new Scanner(Paths.get("words.txt"));
    while(s.hasNext()){
        arr.add(s.next());
    }
    return arr;
}
}

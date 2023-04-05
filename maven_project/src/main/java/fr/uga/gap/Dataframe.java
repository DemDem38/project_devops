package fr.uga.gap;

import java.util.ArrayList;

public class Dataframe {
    
    ArrayList<String> nameLines;
    ArrayList<ColumFrame> colums;

    public Dataframe(String[] nl, String[] nc, int[][] t) {
        nameLines = new ArrayList<String>();
        colums = new ArrayList<ColumFrame>();

        for (String elem : nl) {
            nameLines.add((elem));
        }

        for (int i = 0; i < nc.length; i++) {
            colums.add(new ColumFrame(t[i], nc[i]));
        }
    }
}

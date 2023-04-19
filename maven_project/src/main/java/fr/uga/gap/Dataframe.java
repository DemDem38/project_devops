package fr.uga.gap;

import java.util.ArrayList;

public class Dataframe {
    
    ArrayList<String> nameLines;
    ArrayList<Series> columns;

    public Dataframe(String[] nl, String[] nc, int[][] t) {
        nameLines = new ArrayList<String>();
        columns = new ArrayList<Series>();

        for (String elem : nl) {
            nameLines.add((elem));
        }

        // for (int i = 0; i < nc.length; i++) {
        //     columns.add(new Series(t[i], nc[i]));
        // }
    }
}

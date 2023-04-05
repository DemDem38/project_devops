package fr.uga.gap;

import java.util.ArrayList;

public class Dataframe {
    
    ArrayList<String> nameLines;
    ArrayList<ColumnFrame> columns;

    public Dataframe(String[] nl, String[] nc, int[][] t) {
        nameLines = new ArrayList<String>();
        columns = new ArrayList<ColumnFrame>();

        for (String elem : nl) {
            nameLines.add((elem));
        }

        for (int i = 0; i < nc.length; i++) {
            columns.add(new ColumnFrame(t[i], nc[i]));
        }
    }
}

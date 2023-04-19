package fr.uga.gap;

import java.util.ArrayList;
import java.util.HashMap;

public class Dataframe<L> {
    
    private HashMap<L, Series<Object>> mapSeries;
    private ArrayList<Integer> index;

    public Dataframe(L[] ind, Object[][] o) throws IllegalArgumentException {
        mapSeries = new HashMap<>();
        index = new ArrayList<>();

        // Verify if the number of labels and colums is equal
        if (ind.length != o.length) {
            throw new IllegalArgumentException("Number f labels and columns are different");
        }

        int numIndex=0;

        for (int i = 0; i < o.length; i++) {
            /**
             * Get the number of element for the first series and
             * verify if this number is the same for the other series
             */
            if (i == 0) {
                numIndex = o[i].length;
            } else {
                if (numIndex != o[i].length) {
                    throw new IllegalArgumentException("Different number of element in between colums");
                }
            }
            Series<Object> s = new Series<>(o[i]);
            mapSeries.put(ind[i], s);
            index.add(i);
        }

    }

    // Getters and Setters
    public HashMap<L, Series<Object>> getMapSeries() {
        return mapSeries;
    }

    public void setMapSeries(HashMap<L, Series<Object>> mapSeries) {
        this.mapSeries = mapSeries;
    }

    public ArrayList<Integer> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<Integer> index) {
        this.index = index;
    }
}

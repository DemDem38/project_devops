package fr.uga.gap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Dataframe<L> {
    
    private HashMap<L, Series<Object>> mapSeries;
    private ArrayList<Integer> index;

    public Dataframe(L[] ind, Object[][] o) throws IllegalArgumentException {
        // Initialize data structures
        mapSeries = new HashMap<>();
        index = new ArrayList<>();

        initializeDataframe(ind, o);
    }

    public void initializeDataframe(L[] ind, Object[][] o) {
        // Test if the array have the correct size
        sizeLabelColumsEqual(ind, o);
        numberElementColumn(o);

        // Construct the dataframe
        for (int i = 0; i < ind.length; i++) {
            constructSeries(ind[i], o[i], i);
            index.add(i);
        }
    }

    // Verify if the number of labels and columns is equal
    private void sizeLabelColumsEqual(L[] i, Object[][] o) {
        if (i.length != o.length) {
            throw new IllegalArgumentException("Number of columns is " + o.length + " but number of labels is " + i.length);
        }
    }

    // Verify if the number of elements in each column is the same
    private void numberElementColumn(Object[][] o) {
        int size = o.length;
        int sizeColumns;
        if (size > 0) {
            sizeColumns = o[0].length;
            for (int i = 1; i < size; i++) {
                if (sizeColumns != o[i].length) {
                    throw new IllegalArgumentException("Size of column " + i + " is " + o[i].length +
                            " instead of size " + sizeColumns);
                }
            }
        }
    }

    private void constructSeries(L label, Object[] elems, int index) {
        // Get the number of element
        int sizeColumn = elems.length;
        if (sizeColumn > 0) {
            // Get the type of the first element to compare with the rest
            String typeElem = elems[0].getClass().getSimpleName();

            // Verify the type of the rest of elements
            String typeRest;
            for (int i = 1; i < sizeColumn; i++) {
                typeRest = elems[i].getClass().getSimpleName();
                if (!Objects.equals(typeElem, typeRest)) {
                    throw new IllegalArgumentException("Index " + i + " of column " + index + " has type " + typeRest +
                            " instead of type " + typeElem);
                }
            }

            // Construct the Series and add to hashmap
            Series<Object> s = new Series<>(elems, typeElem);
            mapSeries.put(label, s);
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

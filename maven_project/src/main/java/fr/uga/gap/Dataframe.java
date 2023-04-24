package fr.uga.gap;

import java.util.*;

public class Dataframe {

    /* Attributs */
    private HashMap<Object, Series<Object>> mapSeries;
    private ArrayList<Object> labelLines;

    /*  ================= Constructor ================= */
    public Dataframe(Object[] indColumns, Object[][] o) throws IllegalArgumentException {
        // Initialize data structures
        mapSeries = new HashMap<>();
        labelLines = new ArrayList<>();

        initializeDataframe(indColumns, o);
    }

    public Dataframe(Object[] indColumns, Object[][] o, Object[] indLines) throws IllegalArgumentException {
        // Initialize data structures
        mapSeries = new HashMap<>();
        labelLines = new ArrayList<>();

        initializeDataframe(indColumns, o, indLines);
    }

    /* ================= Initialize Dataframe ================= */
    private void initializeDataframe(Object[] indColumns, Object[][] o) {
        // Test if the array have the correct size
        sizeLabelColumsEqual(indColumns, o);
        numberElementColumn(o);

        // Construct the dataframe
        for (int i = 0; i < indColumns.length; i++) {
            constructSeries(indColumns[i], o[i], i);
            labelLines.add(i);
        }
    }

    private void initializeDataframe(Object[] indColumn, Object[][] o, Object[] indLines) {
        // Test if the array have the correct size
        sizeLabelColumsEqual(indColumn, o);
        numberElementColumn(o);
        numberIndexLines(indLines, o[0].length);

        // Construct the dataframe
        for (int i = 0; i < indColumn.length; i++) {
            constructSeries(indColumn[i], o[i], i);
            labelLines.add(indLines[i]);
        }
    }

    // Verify if the number of labels and colums is equal
    private void sizeLabelColumsEqual(Object[] i, Object[][] o) {
        if (i.length != o.length) {
            throw new IllegalArgumentException("Number of columns is " + o.length + " but number of labels is " + i.length);
        }
    }

    // Verify if the number of elements in each column is the same
    private void numberElementColumn(Object[][] o) {
        int size = o.length;
        int sizeColums;
        if (size > 0) {
            sizeColums = o[0].length;
            for (int i = 1; i < size; i++) {
                if (sizeColums != o[i].length) {
                    throw new IllegalArgumentException("Size of column " + i + " is " + o[i].length +
                            " instead of size " + sizeColums);
                }
            }
        }
    }

    // Verify if the number of lines is correct between dataframe and labels for lines
    private void numberIndexLines(Object[] indLines, int nb) {
        if (indLines.length != nb) {
            throw new IllegalArgumentException("Number of lines is " + indLines.length + " but number of lines in dataframe is " + nb);
        }
    }

    private void constructSeries(Object label, Object[] elems, int index) {
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

    // Getters
    public HashMap<Object, Series<Object>> getMapSeries() {
        return mapSeries;
    }

    public ArrayList<Object> getLabelLines() {
        return labelLines;
    }
}

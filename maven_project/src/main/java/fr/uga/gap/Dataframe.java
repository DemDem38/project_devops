package fr.uga.gap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dataframe {

    /* Attributs */
    private HashMap<Object, Series> mapSeries;
    private Object[] labelColumns;
    private Object[] labelLines;

    /*  ================= Constructor ================= */
    public Dataframe(Object[] indColumns, Object[][] o) throws IllegalArgumentException {
        // Initialize data structures
        mapSeries = new HashMap<>();
        labelColumns = indColumns;

        initializeDataframe(indColumns, o);
    }

    public Dataframe(Object[] indColumns, Object[][] o, Object[] indLines) throws IllegalArgumentException {
        // Initialize data structures
        mapSeries = new HashMap<>();
        labelColumns = indColumns;

        initializeDataframe(indColumns, o, indLines);
    }

    /* ================= Initialize Dataframe ================= */
    private void initializeDataframe(Object[] indColumns, Object[][] o) {
        // Test if the array have the correct size
        sizeLabelColumnsEqual(indColumns, o);
        numberElementColumn(o);

        labelLines = new Object[o[0].length];
        // Construct the dataframe
        for (int i = 0; i < indColumns.length; i++) {
            constructSeries(indColumns[i], o[i], i);
            labelLines[i] = i;
        }

    }

    private void initializeDataframe(Object[] indColumn, Object[][] o, Object[] indLines) {
        // Test if the array have the correct size
        sizeLabelColumnsEqual(indColumn, o);
        numberElementColumn(o);
        if (o.length > 0) {
            numberIndexLines(indLines, o[0].length);
        } else {
            numberIndexLines(indLines, 0);
        }

        // Construct the dataframe
        labelLines = indLines;
        for (int i = 0; i < indColumn.length; i++) {
            constructSeries(indColumn[i], o[i], indLines, i);
        }
    }

    // Verify if the number of labels and columns is equal
    private void sizeLabelColumnsEqual(Object[] i, Object[][] o) {
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

    // Verify if the number of lines is correct between dataframe and labels for lines
    private void numberIndexLines(Object[] indLines, int nb) {
        if (indLines.length != nb) {
            throw new IllegalArgumentException("Number of lines is " + indLines.length + " but number of lines in dataframe is " + nb);
        }
    }

    private void constructSeries(Object label, Object[] elems, int index) {
        Object[] indexElems = new Object[elems.length];
        for (int i = 0; i < elems.length; i++) {
            indexElems[i] = i;
        }

        constructSeries(label, elems, indexElems, index);
    }

    private void constructSeries(Object label, Object[] elems, Object[] labelLines, int index) {
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
            Series s = new Series(elems, labelLines, typeElem);
            mapSeries.put(label, s);
        }
    }

    /* Read from CSV file */
    public static Dataframe read_csv(String filename) {
        return read_csv(filename, ",");
    }

    public static Dataframe read_csv(String filename, String sep) {
        String line;
        String[] splitLine;
        String[] labels;
        ArrayList<String[]> value = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));

            // Read 1st line : contains name of labels
            line = br.readLine();
            labels = line.split(sep);

            // Read rest of lines and add into array list
            while ((line = br.readLine()) != null) {
                // Read Line
                splitLine = line.split(sep);
                value.add(splitLine);
            }
        } catch (FileNotFoundException f) {
            throw new RuntimeException("File " + filename + " not found");
        } catch (IOException i) {
            throw new RuntimeException("A problem occur while reading file " + filename);
        }

        // Now we set the correct type of each data
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        ArrayList<Object> obj;
        int i;
        float f;
        char c;
        for (String[] str : value) {
            obj = new ArrayList<>();
            for (String s : str) {
                // If it's an integer, add into array obj
                try {
                    i = Integer.parseInt(s);
                    obj.add(i);
                    continue;
                } catch (NumberFormatException e) {
                    // Nothing because not the good type
                }

                // Same for float
                try {
                    f = Float.parseFloat(s);
                    obj.add(f);
                    continue;
                } catch (NumberFormatException e) {
                    // Nothing
                }

                // If it's not a number, it's a string or a char
                if (s.length() == 1) {
                    c = s.charAt(0);
                    obj.add(c);
                } else {
                    obj.add(s);
                }
            }
            data.add(obj);
        }

        // Transpose the array
        Object[][] objValue = transpose(data);

        return new Dataframe(labels, objValue);
    }

    // Transpose a 2D array list and return a 2D array of object
    private static Object[][] transpose(ArrayList<ArrayList<Object>> obj) {
        ArrayList<ArrayList<Object>> objTranspose = new ArrayList<>();
        int nbObj = 0;

        // Transpose the array list
        for (ArrayList<Object> o : obj) {
            for (int j = 0; j < o.size(); j++) {
                // If there is more columns, create a vector
                if (j >= nbObj) {
                    objTranspose.add(new ArrayList<>());
                    nbObj++;
                }
                // Add element into the correct column
                objTranspose.get(j).add(o.get(j));
            }
        }

        return ArrayListToArray(objTranspose);
    }

    // Transform a 2D array list of object into a 2D array of object
    private static Object[][] ArrayListToArray(ArrayList<ArrayList<Object>> objTranspose) {
        return objTranspose.stream().map(u -> u.toArray(new Object[0])).toArray(Object[][]::new);
    }

    // Getters
    public HashMap<Object, Series> getMapSeries() {
        return mapSeries;
    }

    public Object[] getLabelLines() {
        return labelLines;
    }

    public Object[] getLabelColumns() {
        return labelColumns;
    }
}

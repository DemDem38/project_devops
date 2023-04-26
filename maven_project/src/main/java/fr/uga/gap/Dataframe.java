package fr.uga.gap;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
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
        sizeLabelColumsEqual(indColumns, o);
        numberElementColumn(o);
        if (o.length > 0) {
            labelLines = new Object[o[0].length];
            for (int i = 0; i < o[0].length; i++) {
                labelLines[i] = i;
            }
        } else {
            labelLines = new Object[0];
        }

        // Construct the dataframe
        for (int i = 0; i < indColumns.length; i++) {
            constructSeries(indColumns[i], o[i], i);
        }

    }

    private void initializeDataframe(Object[] indColumn, Object[][] o, Object[] indLines) {
        // Test if the array have the correct size
        sizeLabelColumsEqual(indColumn, o);
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
        constructSeries(label, elems, labelLines, index);
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
            
            br.close();
        } catch (FileNotFoundException f) {
            throw new RuntimeException("File " + filename + " not found");
        } catch (IOException i) {
            throw new RuntimeException("A problem occur while reading file " + filename);
        }

        // Now we set the correct type of each data
        ArrayList<ArrayList<Object>> data = new ArrayList<>();
        ArrayList<Object> obj;
        int i;
        double d;
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

                // Same for double
                try {
                    d = Double.parseDouble(s);
                    if (!s.contains("f")) {
                        obj.add(d);
                        continue;
                    }
                } catch (NumberFormatException e) {
                    // Nothing
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

    /*  ================= Selection ================= */

    // Create a sub-dataframe of this dataframe when giving indexs
    public Dataframe iloc(int[] indCol, int[] indLin) {
        // Verify index
        if (indCol == null || indLin == null) {
            throw new IllegalArgumentException("An array of index is null");
        }
        verifyIndex(indCol, indLin);

        // if it's ok, get the label from index
        // For columns
        int sizeIndCol = indCol.length;
        Object[] labCol;
        labCol = new Object[sizeIndCol];
        for (int i = 0; i < sizeIndCol; i++) {
            labCol[i] = labelColumns[indCol[i]];
        }


        // For lines
        int sizeIndLin = indLin.length;
        Object[] labLin;
        labLin = new Object[sizeIndLin];
        for (int i = 0; i < sizeIndLin; i++) {
            labLin[i] = labelLines[indLin[i]];
        }
        return loc(labCol, labLin);
    }

    // Create a sub-dataframe of this dataframe when giving labels
    public Dataframe loc(Object[] labCol, Object[] labLin) {
        // Verify both arrays
        if (labCol == null || labLin == null) {
            throw new IllegalArgumentException("An array of labels is null");
        }
        verifyLabels(labCol, labLin);

        Object[] newLabCol = labCol;
        Object[] newLabLin = labLin;
        if (newLabCol.length == 0 && newLabLin.length > 0) {
            newLabCol = labelColumns;
        }
        if (newLabLin.length == 0 && newLabCol.length > 0) {
            newLabLin = labelLines;
        }

        // Now, we create the array for the new dataframe
        ArrayList<ArrayList<Object>> newData = new ArrayList<>();
        ArrayList<Object> newDataCol;
        Series actual;
        for (Object lc : newLabCol) {
            newDataCol = new ArrayList<>();
            actual = mapSeries.get(lc);
            for (Object ll : newLabLin) {
                newDataCol.add(actual.getData(ll));
            }
            newData.add(newDataCol);
        }
        return new Dataframe(newLabCol, ArrayListToArray(newData), newLabLin);
    }

    // Verify that all indexes are valide
    private void verifyIndex(int[] indCol, int[] indLines) {
        // For columns
        for (int ic : indCol) {
            if (ic >= labelColumns.length || ic < 0) {
                throw new IllegalArgumentException("Index " + ic + " of colums out of bounds length " + labelColumns.length);
            }
        }
        // For lines
        for (int il : indLines) {
            if (il >= labelLines.length || il < 0) {
                throw new IllegalArgumentException("Index " + il + " of lines out of bounds length " + labelLines.length);
            }
        }
    }

    // Check if the labels in the array are valid for columns and lines
    private void verifyLabels(Object[] labCol, Object[] labLin) {
        // For columns
        for (Object o : labCol) {
            if (mapSeries.get(o) == null) {
                throw new IllegalArgumentException("Label " + o + " of columns don't exist");
            }
        }
        // For lines
        boolean isContain;
        int i = 0;
        for (Object o : labLin) {
            isContain = false;
            while (i < labelLines.length && !isContain) {
                if (labelLines[i].equals(o)) {
                    isContain = true;
                }
                i++;
            }
            if (!isContain) {
                throw new IllegalArgumentException("Label " + o + " of lines don't exist");
            }
        }
    }

    // Add a complex selection : Filtering data to get lines where the comparaison between 
    public Dataframe filterData(Object labCol, String comparator, Object value) {
        if (!checkComparator(comparator)) {
            throw new IllegalArgumentException("Comparator " + comparator + " is not a comparator");
        }

        // Check if labCol is a label for columns
        if (mapSeries.get(labCol) == null) {
            throw new IllegalArgumentException("Label " + labCol + " is not a label fo columns");
        }

        // Check if the value of the Series and value are the same
        Series colSeries = mapSeries.get(labCol);
        if (!checkType(colSeries, value)) {
            throw new IllegalArgumentException("Value " + value + " has type " + value.getClass().getSimpleName() + " instead of " + colSeries.getTypeArray());
        }

        // Now, we compare the values of the column "labCol" with value
        /* Principe : For all labels in labelLines, we compare the value of dataframe[col, lab] with value
         * If it's true, we add the label in lablines, else not.
         * We returned a new Dataframe of all columns with labels labelColumns and lines from labLines
         */
        ArrayList<Object> labLines = new ArrayList<>();
        for (Object ll : labelLines) {
            if (compareValues(colSeries.getData(ll), value, comparator, colSeries.getTypeArray())) {
                labLines.add(ll);
            }
        }
        return this.loc(new Object[]{}, labLines.toArray());
    }

    private boolean checkComparator(String comparator) {
        switch (comparator) {
            case "==":
            case "!=":
            case "<":
            case ">":
            case ">=":
            case "<=":
                return true;
            default:
                return false;
        }
    }

    private boolean checkType(Series s, Object val) {
        String typeS = s.getTypeArray();
        String typeVal = val.getClass().getSimpleName();
        if (typeS.compareTo("Float") == 0 && typeVal.compareTo("Double")==0) {
            return true;
        } else if (typeS.compareTo("Double") == 0 && typeVal.compareTo("Float")==0) {
            return true;
        }
        return typeS.equals(typeVal);
    }

    private boolean compareValues(Object val1, Object val2, String comparator, String type) {
        switch (type) {
            case "Integer":
                int i1 = (int)val1;
                int i2 = (int)val2;
                switch (comparator) {
                    case "==":
                        return i1 == i2;
                    case "!=":
                        return i1 != i2;
                    case "<":
                        return i1 < i2;
                    case ">":
                        return i1 > i2;
                    case ">=":
                        return i1 >= i2;
                    case "<=":
                        return i1 <= i2;
                    default:
                        return false;
                }
            case "Float":
            case "Double":
                BigDecimal d1 = new BigDecimal(""+val1.toString());
                BigDecimal d2 = new BigDecimal(""+val2.toString());
                switch (comparator) {
                    case "==":
                        return d1.compareTo(d2) == 0;
                    case "!=":
                        return d1.compareTo(d2) != 0;
                    case "<":
                        return d1.compareTo(d2) < 0;
                    case ">":
                        return d1.compareTo(d2) > 0;
                    case ">=":
                        return d1.compareTo(d2) >= 0;
                    case "<=":
                        return d1.compareTo(d2) <= 0;
                    default:
                        return false;
                }
            case "Character":
                char c1 = (char)val1;
                char c2 = (char)val2;
                switch (comparator) {
                    case "==":
                        return c1 == c2;
                    case "!=":
                        return c1 != c2;
                    case "<":
                        return c1 < c2;
                    case ">":
                        return c1 > c2;
                    case ">=":
                        return c1 >= c2;
                    case "<=":
                        return c1 <= c2;
                    default:
                        return false;
                }
            case "String":
                String s1 = (String)val1;
                String s2 = (String)val2;
                switch (comparator) {
                    case "==":
                        return s1.compareTo(s2) == 0;
                    case "!=":
                        return s1.compareTo(s2) != 0;
                    case "<":
                        return s1.compareTo(s2) < 0;
                    case ">":
                        return s1.compareTo(s2) > 0;
                    case ">=":
                        return s1.compareTo(s2) >= 0;
                    case "<=":
                        return s1.compareTo(s2) <= 0;
                    default:
                        return false;
                }
            default:
                throw new IllegalArgumentException("The comparison between " + type + " values are not implemented");
        }
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

    public boolean compareDataframe(Dataframe d) {
        return compareLabelColumns(d) && compareLabelLines(d) && compareData(d);
    }

    private boolean compareLabelColumns(Dataframe d) {
        // Compare columns
        if (this.labelColumns.length != d.getLabelColumns().length) {
            return false;
        }
        boolean isInCol;
        for (int i = 0; i < this.labelColumns.length; i++) {
            isInCol = false;
            for (int j = 0; j < d.labelColumns.length; j++) {
                if (this.labelColumns[i].toString().equals(d.getLabelColumns()[i].toString())) {
                    isInCol = true;
                }
            }
            if (!isInCol) {
                return false;
            }
        }
        return true;
    }

    private boolean compareLabelLines(Dataframe d) {
        if (this.labelLines.length != d.getLabelLines().length) {
            return false;
        }
        boolean isInCol;
        for (Object labelLine : this.labelLines) {
            isInCol = false;
            for (int j = 0; j < d.labelLines.length; j++) {
                if (labelLine.toString().equals(d.getLabelLines()[j].toString())) {
                    isInCol = true;
                }
            }
            if (!isInCol) {
                return false;
            }
        }
        return true;
    }

    private boolean compareData(Dataframe d) {
        HashMap<Object, Series> serD = d.getMapSeries();

        for (Object objCol : labelColumns) {
            for (Object objLin : labelLines) {
                Object ourData = mapSeries.get(objCol).getData(objLin);
                Object hisData = serD.get(objCol).getData(objLin);
                if (hisData == null) {
                    return false;
                }
                if (!ourData.toString().equals(hisData.toString())) {
                    return false;
                }
            }
        }
        return true;
    }

    // return -1 if max can't be found because column is not integer or column doesn't exist
    public int findMaxIntegerValueOfColumn(Object labelColumn) {
        Integer maxValue = -1;
        for (Object o: labelColumns) {
            if (o.toString().equals(labelColumn.toString())) {
                if (getMapSeries().get(o).getTypeArray().compareTo("Integer") == 0) {
                    for (Object v: labelLines) {
                        if ((Integer) getMapSeries().get(o).getData(v) > maxValue) {
                            maxValue = (Integer) getMapSeries().get(o).getData(v);
                        }
                    }
                }
            }
        }
        return maxValue;
    }

    // return -1 if min can't be found because column is not integer or column doesn't exist
    public int findMinIntegerValueOfColumn(Object labelColumn) {
        Integer minValue = -1;
        for (Object o: labelColumns) {
            if (o.toString().equals(labelColumn.toString())) {
                if (getMapSeries().get(o).getTypeArray().compareTo("Integer") == 0) {
                    minValue = (Integer) getMapSeries().get(o).getData(labelLines[0]);
                    for (Object v: labelLines) {
                        if ((Integer) getMapSeries().get(o).getData(v) < minValue) {
                            minValue = (Integer) getMapSeries().get(o).getData(v);
                        }
                    }
                }
            }
        }
        return minValue;
    }

    // return -1 if max can't be found because column is not float or column doesn't exist
    public float findMaxFloatValueOfColumn(Object labelColumn) {
        Float maxValue = -1.0f;
        for (Object o: labelColumns) {
            if (o.toString().equals(labelColumn.toString())) {
                if (getMapSeries().get(o).getTypeArray().compareTo("Float") == 0) {
                    for (Object v: labelLines) {
                        if ((Float) getMapSeries().get(o).getData(v) > maxValue) {
                            maxValue = (Float) getMapSeries().get(o).getData(v);
                        }
                    }
                }
            }
        }
        return maxValue;
    }

    // return -1 if min can't be found because column is not float or column doesn't exist
    public float findMinFloatValueOfColumn(Object labelColumn) {
        Float minValue = -1.0f;
        for (Object o: labelColumns) {
            if (o.toString().equals(labelColumn.toString())) {
                if (getMapSeries().get(o).getTypeArray().compareTo("Float") == 0) {
                    minValue = (Float) getMapSeries().get(o).getData(labelLines[0]);
                    for (Object v: labelLines) {
                        if ((Float) getMapSeries().get(o).getData(v) < minValue) {
                            minValue = (Float) getMapSeries().get(o).getData(v);
                        }
                    }
                }
            }
        }
        return minValue;
    }

    // return -1 if mean can't be found because column is not integer or column doesn't exist
    public float findIntegerMeanOfColumn(Object labelColumn) {
        float moyenne = 0.0f;
        float n = 0.0f;
        for (Object o: labelColumns) {
            if (o.toString().equals(labelColumn.toString())) {
                if (getMapSeries().get(o).getTypeArray().compareTo("Integer") == 0) {
                    for (Object v: labelLines) {
                        moyenne += (Integer) getMapSeries().get(o).getData(v);
                        n++;
                    }
                }
            }
        }
        if (n != 0.0f) {
            return moyenne/n;
        } else {
            return moyenne;
        }
    }

    // return -1 if mean can't be found because column is not Float or column doesn't exist
    public float findFloatMeanOfColumn(Object labelColumn) {
        float moyenne = 0.0f;
        float n = 0.0f;
        for (Object o: labelColumns) {
            if (o.toString().equals(labelColumn.toString())) {
                if (getMapSeries().get(o).getTypeArray().compareTo("Float") == 0) {
                    for (Object v: labelLines) {
                        moyenne += (Float) getMapSeries().get(o).getData(v);
                        n++;
                    }
                }
            }
        }
        if (n != 0.0f) {
            return moyenne/n;
        } else {
            return moyenne;
        }
    }

    @Override
    public String toString() {
        String s = "   ";
        int maxSpace = 0;
        String space = "";
        for(Object o : labelLines) {
            if (o.toString().length() > maxSpace) {
                maxSpace = o.toString().length();
            }
        }
        for (int i = 0; i < maxSpace; i++) {
            space += " ";
        }
        s+=space;
        for(Object o : labelColumns) {
            s += o.toString() + " | ";
        }
        s += "\n";
        for(Object l : labelLines) {
            s += l.toString() + " | ";
            for (Object c : labelColumns) {
                s += mapSeries.get(c).getData(l).toString() + " | ";
            }
            s += "\n";
        }
        return s;
    }

    public String printFirstLines(int n) {
        int columns[] = new int[labelColumns.length];
        int lines[] = new int[n];
        for(int i = 0; i < labelColumns.length; i++) {
            columns[i] = i;
        }
        for(int i = 0; i < n; i++) {
            lines[i] = i;
        }
        Dataframe newDataframe = this.iloc(columns, lines);
        return newDataframe.toString();
    }

    public String printLastLines(int n) {
        int columns[] = new int[labelColumns.length];
        int lines[] = new int[n];
        for(int i = 0; i < labelColumns.length; i++) {
            columns[i] = i;
        }
        int j = 0;
        for(int i = labelLines.length - n; i < labelLines.length; i++) {
            lines[j] = i;
            j++;
        }
        Dataframe newDataframe = this.iloc(columns, lines);
        return newDataframe.toString();
    }
}

package fr.uga.gap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataframeTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    Dataframe dataframeStatistics;
    String[] nameStatistics;  
    String[] linesStatistics;
    Object[][] objectStatistics;
  
    Dataframe dataframeReal;
    String[] nameReal;
    Object[][] objectReal;

    public DataframeTest( String testName )
    {
        super( testName );
        nameStatistics = new String[]{"A", "B", "C"};
        objectStatistics = new Object[][]{{0, 1, 5},
                {"a", "b", "c"},
                {0.0f, 1.0f, 2.0f}};
        linesStatistics = new String[]{"a", "b", "c"};
        dataframeStatistics = new Dataframe(nameStatistics, objectStatistics, linesStatistics);        
        nameReal = new String[]{"Age", "Nationalité", "Sexe"};
        objectReal = new Object[][]{{35, 63, 39},{"Français", "Belge", "Suisse"},{'H', 'F', 'H'}};
        dataframeReal = new Dataframe(nameReal, objectReal);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DataframeTest.class );
    }

    /* ============================= Test creation of dataframe ============================= */

    /* -> create manually a dataframe <- */

    // testDataframeCreation() : test initialisation dataframe without label for lines
    public void testDataframeCreation()
    {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        Dataframe dataframe = new Dataframe(name, object);

        for (int i = 0; i < name.length; i++) {
            Series s = dataframe.getMapSeries().get(name[i]);
            assertNotNull(s);
            for (int j = 0; j < object[i].length; j++) {
                Object o = s.getMapData().get(j);
                assertEquals(o, object[i][j]);
            }
        }
    }

    // testDataframeInitLabelLines() : test initialisation dataframe with giving a label for lines
    public void testDataframeInitLabelLines()
    {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        for (int i = 0; i < name.length; i++) {
            Series s = dataframe.getMapSeries().get(name[i]);
            assertNotNull(s);
            for (int j = 0; j < object[i].length; j++) {
                Object o = s.getMapData().get(lines[j]);
                assertEquals(o, object[i][j]);
            }
        }
    }

    // testDataframeNumLabColDifferent() : Test if the number of label and colums is equal => get exception
    public void testDataframeNumLabColDifferent()
    {
        String[] n = new String[]{"A", "B"};
        Object[][] o = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        try {
            Dataframe d = new Dataframe(n, o);
            fail("Missing IllegalArgumentException: Number of columns is 3 but number of labels is 2");
        } catch (IllegalArgumentException e) {
            // catch the exception, so OK
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    // testDataframeNumBetweenColumns() : Test the number of elements between columns => get exception
    public void testDataframeNumBetweenColumns() {
        String[] n = new String[]{"A", "B", "C"};
        Object[][] o = new Object[][]{{0, 1},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        try {
            Dataframe d = new Dataframe(n, o);
            fail("Missing IllegalArgumentException: Size of column 1 is 3 instead of size 2");
        } catch (IllegalArgumentException e) {
            // catch the exception, so OK
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    // testCorrectTypeColumn() : Test if the elements in a column have the same type
    public void testCorrectTypeColumn() {
        String[] n = new String[]{"A"};
        Object[][] o = new Object[][]{ {0, 1, "incorrect"} };
        try {
            Dataframe d = new Dataframe(n, o);
            fail("Missing IllegalArgumentException: Index 2 of column 0 has type String instead of type Integer");
        } catch (IllegalArgumentException e) {
            // Catch exception -> OK
        } catch (Exception e) {
            fail("Incorrect exception");
        }
    }

    // testDataframeNumLabLinDifferent() : Test if the number of label for line is different
    // to number of element in column => get exception
    public void testDataframeNumLabLinDifferent()
    {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b"};

        try {
            Dataframe dataframe = new Dataframe(name, object, lines);
            fail("Dataframe invalid : number of lines in dataframe and label for lines is different");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Must catch exception : IllegalArgumentException");
        }
    }

    /* -> create a dataframe from a csv file <- */

    // testReadCSVcorrect() : file found and dataframe create
    public void testReadCSVcorrect() {
        Dataframe d = Dataframe.read_csv("src/test/resources/correctFile.csv");
        String[] labelCol = new String[]{"String","Float","Character","Integer"};
        Object[][] data = new Object[][]{
                {"booker12", "grey07", "johnson81", "jenkins46", "smith79"},
                {9012.128f, 2070.2994f, 4081.7f, 9346.614f, 5079.01f},
                {'R', 'L', 'C', 'M', 'J'},
                {0, 1, 2, 3, 4}
        };

        for (int i = 0; i < labelCol.length; i++) {
            Series s = d.getMapSeries().get(labelCol[i]);
            assertNotNull(s);

            for (int j = 0; j < data[i].length; j++) {
                Object o = s.getMapData().get(j);
                assertEquals(o, data[i][j]);
            }
        }
    }

    // testReadCSVnotFound() : file not exist -> get Exception
    public void testReadCSVnotFound() {
        try {
            Dataframe d = Dataframe.read_csv("notexist.csv");
            fail("File don't exist and exception not catch");
        } catch (RuntimeException e) {
            // Catch exception -> OK
        } catch (Exception e) {
            fail("Catch another exception than RuntimeException");
        }
    }

    /* ============================= Test selection of dataframe ============================= */

    /* -> Selection with index */

    // testSelectIndexValid() : Check if a selection of all the dataframe give the same dataframe with index
    public void testSelectIndexValidAll() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        // Select a sub par of dataframe
        Dataframe select = dataframe.iloc(new int[]{0, 1, 2}, new int[]{0, 1, 2});

        assertTrue(select.compareDataframe(dataframe));
    }

    // testSelectIndexValid() : Check if a selection of a dataframe give the good "sub" dataframe with index
    public void testSelectIndexValidSub() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        // Correct dataframe
        String[] correctName = new String[]{"A", "B"};
        Object[][] correctObject = new Object[][]{{0}, {"a"}};
        String[] correctLines = new String[]{"a"};
        Dataframe corretDataframe = new Dataframe(correctName, correctObject, correctLines);

        // Select a sub par of dataframe
        Dataframe select = dataframe.iloc(new int[]{0, 1}, new int[]{0});

        assertTrue(select.compareDataframe(corretDataframe));
    }

    // testSelectIndexNullColumns() : Check if a selection of a dataframe with index for columns null get an exception
    public void testSelectIndexNullColumns() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.iloc(null, new int[]{});
            fail("Selection with index null don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index null give another exception");
        }
    }

    // testSelectIndexNullLines() : Check if a selection of a dataframe with index for lines null get an exception
    public void testSelectIndexNullLines() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.iloc(new int[]{}, null);
            fail("Selection with index null don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index null give another exception");
        }
    }

    // testSelectIndexOutBoundsColumns() : Check if a selection of a dataframe with
    // index > number of label (column) =>  get an exception
    public void testSelectIndexOutBoundsColumns() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.iloc(new int[]{3}, new int[]{});
            fail("Selection with index out of bounds don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index out of bounds give another exception");
        }
    }

    // testSelectIndexOutBoundsLines() : Check if a selection of a dataframe with
    // index > number of label (lines) =>  get an exception
    public void testSelectIndexOutBoundsLines() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.iloc(new int[]{}, new int[]{3});
            fail("Selection with index out of bounds don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index out of bounds give another exception");
        }
    }

    // testSelectIndexNegativeColumns() : Check if a selection of a dataframe with index < 0 get an exception
    public void testSelectIndexNegativeColumns() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.iloc(new int[]{-1}, new int[]{});
            fail("Selection with index out of bounds don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index out of bounds give another exception");
        }
    }

    // testSelectIndexNegativeLines() : Check if a selection of a dataframe with index < 0 get an exception
    public void testSelectIndexNegativeLines() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.iloc(new int[]{}, new int[]{-1});
            fail("Selection with index out of bounds don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index out of bounds give another exception");
        }
    }

    /* -> selection with labels <- */

    // testSelectLabelsValidAll() : Check if a selection of all the dataframe give the same dataframe with labels
    public void testSelectLabelsValidAll() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        // Select a sub par of dataframe
        Dataframe select = dataframe.loc(name, lines);

        assertTrue(select.compareDataframe(dataframe));
    }

    // testSelectLabelsValidSub() : Check if a selection of a dataframe give the good "sub" dataframe with labels
    public void testSelectLabelsValidSub() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        // Correct dataframe
        String[] correctName = new String[]{"A", "B"};
        Object[][] correctObject = new Object[][]{{0}, {"a"}};
        String[] correctLines = new String[]{"a"};
        Dataframe corretDataframe = new Dataframe(correctName, correctObject, correctLines);

        // Select a sub par of dataframe
        Dataframe select = dataframe.loc(new Object[]{"A", "B"}, new Object[]{"a"});

        assertTrue(select.compareDataframe(corretDataframe));
    }

    // testSelectLabelsValidColumns() : Check if a selection of a dataframe give
    // the good "sub" dataframe (1 column) with labels
    public void testSelectLabelsValidColumns() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        // Correct dataframe
        String[] correctName = new String[]{"A"};
        Object[][] correctObject = new Object[][]{{0, 1, 3}};
        String[] correctLines = new String[]{"a", "b", "c"};
        Dataframe corretDataframe = new Dataframe(correctName, correctObject, correctLines);

        // Select a sub par of dataframe
        Dataframe select = dataframe.loc(new Object[]{"A"}, new Object[]{});
        assertTrue(select.compareDataframe(corretDataframe));
    }

    // testSelectLabelsNull() : Check if a selection of a dataframe with index null get an exception
    public void testSelectLabelsNull() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.loc(null, null);
            fail("Selection with index null don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index null give another exception");
        }
    }

    // testSelectLabelsNotColumns() : Check if a selection of a dataframe with label
    // who is not a label of a column get an exception
    public void testSelectLabelsNotColumns() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.loc(new Object[]{"H"}, new Object[]{});
            fail("Selection with index out of bounds don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index out of bounds give another exception");
        }
    }

    // testSelectLabelsNotLines() : Check if a selection of a dataframe with label
    // who is not a label of a lines get an exception
    public void testSelectLabelsNotLines() {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        try {
            Dataframe d = dataframe.loc(new Object[]{}, new Object[]{"p"});
            fail("Selection with index out of bounds don't catch the exception");
        } catch (IllegalArgumentException e) {
            // OK
        } catch (Exception e) {
            fail("Selection with index out of bounds give another exception");
        }
    }


    // testFindMaxIntegerValueOfColumn() : test if max value of column is returned (for a Integer)
    // when column selected is Integer
    public void testFindMaxIntegerValueOfColumn() {
        int maxValue = dataframeStatistics.findMaxIntegerValueOfColumn("A");
        assertEquals(5, maxValue);
    }

    // testFindMinIntegerValueOfColumn() : test if min value of column is returned (for a Integer)
    // when column selected is Integer
    public void testFindMinIntegerValueOfColumn() {
        int minValue = dataframeStatistics.findMinIntegerValueOfColumn("A");
        assertEquals(0, minValue);
    }

    // testFindMaxMinIntegerValueOfNotIntegerColumn() : test if max or min value of column is -1 when column is not an Integer column
    public void testFindMaxMinIntegerValueOfNotIntegerColumn() {
        int maxValue = dataframeStatistics.findMaxIntegerValueOfColumn("B");
        assertEquals(-1, maxValue);
        int minValue = dataframeStatistics.findMinIntegerValueOfColumn("B");
        assertEquals(-1, minValue);
    }

    // testFindMaxFloatValueOfColumn() : test if max value of column is returned (for a Float) 
    // when column selected is Float
    public void testFindMaxFloatValueOfColumn() {
        float maxValue = dataframeStatistics.findMaxFloatValueOfColumn("C");
        assertEquals(2.0f, maxValue);
    }

    // testFindMinIntegerValueOfColumn() : test if min value of column is returned (for a Float)
    // when column selected is Float
    public void testFindMinFloatValueOfColumn() {
        float minValue = dataframeStatistics.findMinFloatValueOfColumn("C");
        assertEquals(0.0f, minValue);
    }

    // testFindMaxMinFloatValueOfNotFloatColumn() : test if max or min value of column is -1 when column is not a Float column
    public void testFindMaxMinFloatValueOfNotFloatColumn() {
        float maxValue = dataframeStatistics.findMaxFloatValueOfColumn("B");
        assertEquals(-1.0f, maxValue);
        float minValue = dataframeStatistics.findMinFloatValueOfColumn("B");
        assertEquals(-1.0f, minValue);
    }

    // testIntegerMeanOfColumn() : test if min value of column is returned (for a Integer)
    // when column selected is integer
    public void testIntegerMeanOfColumn() {
        float mean = dataframeStatistics.findIntegerMeanOfColumn("A");
        assertEquals(2.0f, mean);
    }

    // testFloatMeanOfColumn() : test if min value of column is returned (for a Float)
    // when column selected is float
    public void testFloatMeanOfColumn() {
        float mean = dataframeStatistics.findFloatMeanOfColumn("C");
        assertEquals(1.0f, mean);
    }

    // testFindMeanIntegerValueOfNotFloatColumn() : test if mean value of column is 0.0f when column is not a Integer column
    public void testFindMeanIntegerValueOfNotIntegerColumn() {
        float mean = dataframeStatistics.findIntegerMeanOfColumn("B");
        assertEquals(0f, mean);
    }

    // testFindMeanFloatValueOfNotFloatColumn() : test if mean value of column is 0.0f when column is not a Float column
    public void testFindMeanFloatValueOfNotFloatColumn() {

        float mean = dataframeStatistics.findFloatMeanOfColumn("B");
        assertEquals(0f, mean);
    }

    // testAffichageComplet() : vérifie l'affichage complet d'un dataframe
    public void testAffichageComplet() {
        String s = dataframeReal.toString();
        String expected = "    Age | Nationalité | Sexe | \n0 | 35 | Français | H | \n1 | 63 | Belge | F | \n2 | 39 | Suisse | H | \n";
        int equal = s.compareTo(expected);
        assertEquals(0, equal);
    }

    // testAffichagePremieresLignes() : vérifie l'affichage des premières lignes d'un dataframe
    public void testAffichagePremieresLignes() {
        String s = dataframeReal.printFirstLines(2);
        String expected = "    Age | Nationalité | Sexe | \n0 | 35 | Français | H | \n1 | 63 | Belge | F | \n";
        int equal = s.compareTo(expected);
        assertEquals(0, equal);
    }

    // testAffichageDernieresLignes() : vérifie l'affichage des dernières lignes d'un dataframe
    public void testAffichageDernieresLignes() {
        String s = dataframeReal.printLastLines(1);
        String expected = "    Age | Nationalité | Sexe | \n2 | 39 | Suisse | H | \n";
        int equal = s.compareTo(expected);
        assertEquals(0, equal);
    }
}
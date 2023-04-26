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

    public DataframeTest( String testName )
    {
        super( testName );

    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DataframeTest.class );
    }

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

    // testDataframeNumLabLinDifferent() : Test if the number of label for line is equal to number of element in column
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
}
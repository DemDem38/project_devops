package fr.uga.gap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DataframeTest extends TestCase{
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

    // testDataframeCreation() : test initialisation dataframe
    public void testDataframeCreation()
    {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        Dataframe dataframe = new Dataframe(name, object);

        int i = 0;
        int j;

        for (Object n: dataframe.getMapSeries().keySet()) {
            assertEquals(n, name[i]);
            Series<Object> series = dataframe.getMapSeries().get(n);
            j = 0;
            for (Object o: series.getList()) {
                assertEquals(o, object[i][j]);
                j++;
            }
            i++;

        }
    }

    // testDataframeInitLabelLines() : test initialisation dataframe when giving a label for lines
    public void testDataframeInitLabelLines()
    {
        String[] name = new String[]{"A", "B", "C"};
        Object[][] object = new Object[][]{{0, 1, 3},
                {"a", "b", "c"},
                {0.0, 1.0, 2.0}};
        String[] lines = new String[]{"a", "b", "c"};
        Dataframe dataframe = new Dataframe(name, object, lines);

        int i = 0;
        int j;

        for (Object n: dataframe.getMapSeries().keySet()) {
            assertEquals(n, name[i]);
            Series<Object> series = dataframe.getMapSeries().get(n);
            j = 0;
            for (Object o: series.getList()) {
                assertEquals(o, object[i][j]);
                j++;
            }
            i++;
        }

        // compare label lines
        int k = 0;
        for (Object o : dataframe.getLabelLines()) {
            assertEquals(o, lines[k]);
            k++;
        }
    }

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
}
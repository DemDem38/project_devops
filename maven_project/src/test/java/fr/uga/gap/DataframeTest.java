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


    Dataframe<String> dataframe;
    String[] name;
    Object[][] object;

    public DataframeTest( String testName )
    {
        super( testName );
        name = new String[]{"A", "B", "C"};
        object = new Object[][]{{0, 1, 3},
                                {"a", "b", "c"},
                                {0.0, 1.0, 2.0}};
        dataframe = new Dataframe<>(name, object);
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
        int i = 0;
        int j;
        
        for (String n: dataframe.getMapSeries().keySet()) {
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

    // testDataframeNumLabColDifferent() : Test if the number of label and colums is equal => get exception
    public void testDataframeNumLabColDifferent()
    {
        String[] n = new String[]{"A", "B"};
        Object[][] o = new Object[][]{{0, 1, 3},
                                      {"a", "b", "c"},
                                      {0.0, 1.0, 2.0}};
        try {
            Dataframe<String> d = new Dataframe<>(n, o);
            fail("Missing IllegalArgumentException : Number f labels and columns are different");
        } catch (IllegalArgumentException e) {
            // catch the exception, so OK
        }
    }
    
    // testDataframeNumBetweenColumns() : Test the number of elements between columns => get exception
    public void testDataframeNumBetweenColumns() {
        String[] n = new String[]{"A", "B", "C"};
        Object[][] o = new Object[][]{{0, 1},
                                      {"a", "b", "c"},
                                      {0.0, 1.0, 2.0}};
        try {
            Dataframe<String> d = new Dataframe<>(n, o);
            fail("Missing IllegalArgumentException : Different number of element in between colums");
        } catch (IllegalArgumentException e) {
            // catch the exception, so OK
        }
    }
}
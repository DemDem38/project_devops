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

    /**
     * Rigourous Test :-)
     */
    public void testDataframeCreation()
    {   
        int i = 0;
        int j;
        for (String n: dataframe.mapSeries.keySet()) {
            assertEquals(n, name[i]);
            Series<Object> series = dataframe.mapSeries.get(n);
            j = 0;
            for (Object o: series.list) {
                assertEquals(o, object[i][j]);
                j++;
            }
            i++;

        }
    }
}
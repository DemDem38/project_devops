package fr.uga.gap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class SeriesTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SeriesTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SeriesTest.class );
    }


    /**
     * Test the initialisation of Series with different types
     */

    // testIntegerSerieCreation() : test initialization of Series (without index and type)
    public void testSerieCreation()
    {
        Object[] data = new Object[]{0, 1, 2, 3};
        Series s = new Series(data);

        for (int i = 0; i < data.length; i++) {
            Object o = s.getMapData().get(i);
            assertEquals(data[i], o);
        }
    }

    // TestSerieCreationIndex() : test initialization of Series (with index and without type)
    public void testSerieCreationIndex() {
        Object[] index = new String[]{"A", "B", "C"};
        Object[] data = new String[]{"Hello", "Bonjour", "Hola"};
        Series s = new Series(data, index);
        for (int i = 0; i < index.length; i++) {
            Object objData = s.getMapData().get(index[i]);
            assertEquals(data[i], objData);
        }
    }

    // testSerieCreationType() : test initialization of Series (without index and with type)
    public void testSerieCreationType() {
        Object[] data = new String[]{"Hello", "Bonjour", "Hola"};
        String type = "String";
        Series s = new Series(data, type);
        for (int i = 0; i < data.length; i++) {
            Object objData = s.getMapData().get(i);
            assertEquals(data[i], objData);
        }
        assertEquals(type, s.getTypeArray());
    }

    // testSerieCreationIndexType() : test initialization of Series (with index and type)
    public void testSerieCreationIndexType() {
        Object[] index = new String[]{"A", "B", "C"};
        Object[] data = new String[]{"Hello", "Bonjour", "Hola"};
        String type = "String";
        Series s = new Series(data, index, type);
        for (int i = 0; i < index.length; i++) {
            Object objData = s.getMapData().get(index[i]);
            assertEquals(data[i], objData);
        }
        assertEquals(type, s.getTypeArray());
    }
}

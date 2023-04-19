package fr.uga.gap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static junit.framework.Assert.*;


public class SeriesTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    Integer[] integerArray;
    Series<Integer> integerColumn;

    public SeriesTest( String testName )
    {   
        super( testName );
        integerArray = new Integer[]{1, 2, 3 ,4 ,5 ,6 ,7 ,8 ,9 ,10};
        integerColumn = new Series<>(integerArray);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SeriesTest.class );
    }

    /**
     * testColumnFrameCreation
     */
    public void testColumnFrameCreation()
    {
        for(int i = 0; i < integerColumn.list.size();i++){
            assertEquals((Integer)integerColumn.list.get(i), integerArray[i]);
        }
    }
}

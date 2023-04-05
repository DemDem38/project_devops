package fr.uga.gap;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import static junit.framework.Assert.*;


public class ColumnFrameTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    int[] integerArray;
    String integerName;
    ColumnFrame integerColumn;

    public ColumnFrameTest( String testName )
    {   
        super( testName );
        integerArray = new int[]{1, 2, 3 ,4 ,5 ,6 ,7 ,8 ,9 ,10};
        integerName = new String("Colonne d'entiers");
        integerColumn = new ColumnFrame(integerArray, integerName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ColumnFrameTest.class );
    }

    /**
     * testColumnFrameCreation
     */
    public void testColumnFrameCreation()
    {
        assertEquals(integerColumn.name, integerName);
        for(int i = 0; i < integerColumn.list.size();i++){
            assertEquals((int)integerColumn.list.get(i), integerArray[i]);
        }
    }
}

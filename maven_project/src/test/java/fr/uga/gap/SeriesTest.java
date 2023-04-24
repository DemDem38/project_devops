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
    Series<Integer> integerSerie;

    String[] stringArray;
    Series<String> stringSerie;

    Boolean[] boolArray;
    Series<Boolean> boolSerie;

    Character[] charArray;
    Series<Character> charSerie;

    Double[] doubleArray;
    Series<Double> doubleSerie;

    public SeriesTest( String testName )
    {
        super( testName );
        integerArray = new Integer[]{1, 2, 3 ,4 ,5 ,6 ,7 ,8 ,9 ,10};
        integerSerie = new Series<>(integerArray);

        stringArray = new String[]{"bonjour", "tout", "le", "monde", "\n"};
        stringSerie = new Series<>(stringArray);

        boolArray = new Boolean[]{true, true, false, false, true};
        boolSerie = new Series<>(boolArray);

        charArray = new Character[]{'a', 'b', 'c', 'd', 'e'};
        charSerie = new Series<>(charArray, "Character");

        doubleArray = new Double[]{2.5, 3.9, 15.2, 15.6};
        doubleSerie = new Series<>(doubleArray);
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

    // testIntegerSerieCreation() : test with integers
    public void testIntegerSerieCreation()
    {
        for(int i = 0; i < integerSerie.getList().size();i++){
            assertEquals((Integer)integerSerie.getList().get(i), integerArray[i]);
        }
    }

    // testStringSerieCreation() : test with string
    public void testStringSerieCreation()
    {
        for(int i = 0; i < stringSerie.getList().size();i++){
            assertEquals((String)stringSerie.getList().get(i), stringArray[i]);
        }
    }

    // testBooleanSerieCreation() : test with boolean
    public void testBooleanSerieCreation()
    {
        for(int i = 0; i < boolSerie.getList().size();i++){
            assertEquals((Boolean)boolSerie.getList().get(i), boolArray[i]);
        }
    }

    // testDoubleSerieCreation() : test with double
    public void testDoubleSerieCreation()
    {
        for(int i = 0; i < doubleSerie.getList().size();i++){
            assertEquals((Double)doubleSerie.getList().get(i), doubleArray[i]);
        }
    }

    // testDoubleSerieCreation() : test with double
    public void testCharSerieCreation()
    {
        for(int i = 0; i < charSerie.getList().size();i++){
            assertEquals((Character) charSerie.getList().get(i), charArray[i]);
        }
        assertEquals(charSerie.getTypeArray(), "Character");
    }
}

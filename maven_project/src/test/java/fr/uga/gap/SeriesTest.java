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
        charSerie = new Series<>(charArray);

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
     * testColumnFrameCreation
     */
    public void testIntegerSerieCreation()
    {
        for(int i = 0; i < integerSerie.list.size();i++){
            assertEquals((Integer)integerSerie.list.get(i), integerArray[i]);
        }
    }

    public void testStringSerieCreation()
    {
        for(int i = 0; i < stringSerie.list.size();i++){
            assertEquals((String)stringSerie.list.get(i), stringArray[i]);
        }
    }

    public void testBooleanSerieCreation()
    {
        for(int i = 0; i < boolSerie.list.size();i++){
            assertEquals((Boolean)boolSerie.list.get(i), boolArray[i]);
        }
    }

    public void testDoubleSerieCreation()
    {
        for(int i = 0; i < doubleSerie.list.size();i++){
            assertEquals((Double)doubleSerie.list.get(i), doubleArray[i]);
        }
    }
}

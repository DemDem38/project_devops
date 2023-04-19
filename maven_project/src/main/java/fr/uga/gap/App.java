package fr.uga.gap;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        Dataframe<String> dataframe;
        String[] name;
        Object[][] object;
        name = new String[]{"Age", "Nationalité", "Sexe"};
        object = new Object[][]{{35, 63, 39},{"Français", "Belge", "Suisse"},{'H', 'F', 'H'}};
        dataframe = new Dataframe<>(name, object);
        System.out.println(dataframe);
        System.out.println(dataframe.printFirstLines(2));
        System.out.println(dataframe.printLastLines(2));
    }
}

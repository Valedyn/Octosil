package Testing;

/**
 *  This class is meant to make it easier to test any code in this project.
 * @author Valedyn
 * @version 1.0
 */

public class Validator{
    /**
     * This method prints "Validator: ", in addition to a supplied String after by using Utility.Message.print.
     * @author Valedyn
     * @since 1.0
     * @param to_be_printed String to be printed
     */
    public static void print(String to_be_printed){
       Utility.Message.print("Validator", to_be_printed);
    }

    /**
     * This method prints a new line with "Validator: ", in addition to the supplied String after by using Utility.Message.println.
     * @author Valedyn
     * @since 1.0
     * @param to_be_printed String to be printed after a new line
     *
     */
    public static void println(String to_be_printed){
        Utility.Message.println("Validator", to_be_printed);
    }


}

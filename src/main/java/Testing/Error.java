package Testing;

/**
 *  This class is meant to make it easier to make the team aware of any errors.
 * @author Valedyn
 * @version 1.0
 */

public class Error{
    /**
     * This method prints "ERR: ", in addition to a supplied String after by using Utility.Message.print.
     * @author Valedyn
     * @since 1.0
     * @param to_be_printed String to be printed
     */
    public static void print(String to_be_printed){
        Utility.Message.print("ERR", to_be_printed);
    }

    /**
     * This method prints a new line with "ERR: ", in addition to the supplied String after by using Utility.Message.println.
     * @author Valedyn
     * @since 1.0
     * @param to_be_printed String to be printed after a new line
     *
     */
    public static void println(String to_be_printed){
        Utility.Message.println("ERR", to_be_printed);
    }


}

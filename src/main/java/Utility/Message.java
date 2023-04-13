package Utility;

/**
 * This class is meant to make it easier to print out error messages and such.
 * @author Valedyn
 * @version 1.0
 */
public abstract class Message {
    /**
     * This method prints type_of_message, in addition to a parameter of any type after.
     * @author Valedyn
     * @since 1.0
     * @param type_of_message String that gets printed first
     * @param to_be_printed object to be printed by System.out.println
     *
     */
    public static <T> void print(String type_of_message, T to_be_printed){
        System.out.print(type_of_message + ": " + to_be_printed);
    }

    /**
     * This method prints a new line with type_of_message, in addition to a parameter of any type after.
     * @author Valedyn
     * @since 1.0
     * @param type_of_message String that gets printed first
     * @param to_be_printed object to be printed by System.out.println
     *
     */
    public static <T> void println(String type_of_message, T to_be_printed){
        System.out.println(type_of_message + ": " + to_be_printed);
    }

}

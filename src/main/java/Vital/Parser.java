package Vital;

import Utility.ParseErrorException;
import Utility.WrongAmountOfArgumentsException;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;

public class Parser {
    public Parser(){

    }

    public Task parseTask(String string_to_parse) throws ParseErrorException, WrongAmountOfArgumentsException {
        try {
            String[] split_string_no_format = string_to_parse.split("␜");

            if (split_string_no_format.length != 6) {
                throw new WrongAmountOfArgumentsException("ERR: A wrong amount of arguments were given when parsing the String to create a Task object!");
            }
            String[] split_string = new String[split_string_no_format.length];
            for (int format_pos = 0; format_pos < split_string.length; format_pos++) {
                if (split_string_no_format[format_pos].charAt(0) == ' ') {
                    split_string[format_pos] = split_string_no_format[format_pos].substring(1);
                } else {
                    split_string[format_pos] = split_string_no_format[format_pos];
                }
            }
            String name = split_string[0];
            String definition = split_string[1];

            try {
                LocalDate occurrence_date = LocalDate.parse(split_string[2]);
                try {
                    LocalTime occurrence_time = LocalTime.parse(split_string[3]);
                    try {
                        int identifier = Integer.parseInt(split_string[4]);
                        boolean finished = Boolean.parseBoolean(split_string[5]);
                        return new Task(name, definition, occurrence_date, occurrence_time, identifier, finished);
                    } catch (NumberFormatException numberFormatException) {
                        throw new ParseErrorException("ERR: The identifier of the given String to be parsed to create a Task object wasn't a number!");
                    }
                } catch (Exception exception) {
                    throw new ParseErrorException("ERR: The occurrence_time of the given String to be parsed to create a Task object wasn't valid!");
                }


            } catch (Exception exception) {
                throw new ParseErrorException("ERR: The occurrence_date of the given String to be parsed to create a Task object wasn't valid!");
            }
        }catch(PatternSyntaxException patternSyntaxException){
            throw new ParseErrorException(String.format("ERR: Parsing the following string: %s | failed! The delimiter ';' doesn't seem to have been used, when creating a Task object.", string_to_parse));
        }
    }
}

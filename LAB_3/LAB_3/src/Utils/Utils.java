package Utils;

import java.time.Year;
import java.util.Scanner;

public class Utils {

    /**
     * A function to prompt the user for a non-null and non-empty string input.
     *
     * @param welcome The message displayed to the user as a prompt.
     * @param msg The message displayed if the input is empty or null.
     * @return The non-null and non-empty string entered by the user.
     */
    public static String getString(String welcome, String msg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print(welcome);
            result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    /**
     * A function to prompt the user for a non-null and non-empty string input
     * that matches a specified pattern.
     *
     * @param welcome The message displayed to the user as a prompt.
     * @param pattern The regular expression pattern to validate the input.
     * @param msg The message displayed if the input is empty or does not match
     * the pattern.
     * @param msgreg The message displayed if the input format does not match
     * the specified pattern.
     * @return The non-null, non-empty string that matches the provided pattern.
     */
    public static String getStringreg(String welcome, String pattern, String msg, String msgreg) {
        String result;
        do {
            result = getString(welcome, msg);
            if (!result.matches(pattern)) {
                System.out.println(msgreg);
            }
        } while (!result.matches(pattern));
        return result;
    }

    /**
     * A function to prompt the user for a positive double number input.
     *
     * @param welcome The message displayed to the user as a prompt.
     * @param min The minimum allowed value for the input.
     * @return The positive floating-point number entered by the user.
     */
    public static double getDouble(String welcome, double min) {
        double number = 0;
        do {
            String input = getString(welcome, "Input must be a number.");
            try {
                number = Double.parseDouble(input);
                if (number <= min) {
                    System.out.println("Number must be greater than " + min);
                }
            } catch (NumberFormatException e) {
                System.out.println("Input must be a number.");
            }
        } while (number <= min);
        return number;
    }

    /**
     * A function to prompt the user for a valid Year input in the format yyyy.
     *
     * @param prompt The message displayed to the user as a prompt.
     * @return A valid Year object representing the year entered by the user.
     */
    public static Year getYear(String prompt) {
        Scanner sc = new Scanner(System.in);
        Year year = null;

        while (true) {
            try {
                System.out.print(prompt);
                int input = sc.nextInt();
                year = Year.of(input);
                break; // Exit the loop if the year is valid
            } catch (java.time.DateTimeException e) {
                System.out.println("Invalid year. Please re-enter.");
                sc.nextLine(); // Clear the invalid input from the buffer
            } catch (java.util.InputMismatchException ex) {
                System.out.println("Invalid input. Please enter a valid year.");
                sc.nextLine(); // Clear the invalid input from the buffer
            }
        }

        return year;
    }

    /**
     * A function to prompt the user for a string input.
     *
     * @param welcome The message displayed to the user as a prompt.
     * @return The string entered by the user.
     */
    public static String getUpdateString(String welcome) {
        Scanner sc = new Scanner(System.in);
        System.out.print(welcome);
        String result = sc.nextLine().trim();
        return result;
    }

    /**
     * A function to validate and ensure that the user input is a double greater
     * than or equal to a specified minimum value.
     *
     * @param string The input string to be converted to a double and validated.
     * @param min The minimum allowed value for the input.
     * @return True if the input is a valid double greater than or equal to the
     * minimum value, otherwise, false.
     */
    public static boolean getUpdateDouble(String string, double min) {
        try {
            double n = Double.parseDouble(string);
            if (n < min) {
                System.out.println("Number must be larger than " + min);
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Input number!!!");
            return false;
        }
    }

    /**
     * A function to validate and ensure that the user input is a valid year in
     * integer format.
     *
     * @param string The input string to be converted to an integer and
     * validated as a year.
     * @return True if the input is a valid year, otherwise, false with
     * appropriate error messages.
     */
    public static boolean getUpdateYear(String string) {
        try {
            int input = Integer.parseInt(string);
            Year year = Year.of(input);
            return true;
        } catch (java.time.DateTimeException e) {
            System.out.println("Invalid year. Please re-enter.");
            return false;
        } catch (java.util.InputMismatchException ex) {
            System.out.println("Invalid input. Please enter a valid year.");
            return false;
        }
    }

    /**
     * Generates a string consisting of the specified number of space
     * characters.
     *
     * @param count The number of space characters to generate.
     * @return A string containing the specified number of space characters.
     */
    private static String repeatSpaces(int count) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < count; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    /**
     * Prints a message with a decorative border.
     *
     * @param mess The message to be displayed.
     */
    public static void printMess(String mess) {
        int totalWidth = 45;  // Total width of the border string
        int messageWidth = mess.length();
        int padding = (totalWidth - messageWidth) / 2;

        // Create a string for centering the message
        String paddingStr = repeatSpaces(padding);

        // Print the message with a decorative border
        System.out.println("+---------------------------------------------+");
        System.out.println(String.format("|%s%s%s|", paddingStr, mess, paddingStr));
        System.out.println("+---------------------------------------------+");
    }

    public static void printTable() {
        System.out.println("+----------------------------------------------------------------------------------------------------------+");
        System.out.println("| ID_Vehicle | Name of Vehicle |     Color     |     Price     |     Brand     |    Type    | Product Year |");
        System.out.println("+----------------------------------------------------------------------------------------------------------+");
    }

    public static void printLine() {
        System.out.println("+----------------------------------------------------------------------------------------------------------+");
    }

    /**
     * Prints a centered message within a border.
     *
     * @param mess The message to be displayed in the center.
     */
    public static void printCenteredMessage(String mess) {
        int totalWidth = 106; // Total width of the line
        int messageWidth = mess.length();
        int padding = (totalWidth - messageWidth) / 2;

        // Create a string for centering the message
        String paddingStr = repeatSpaces(padding);

        printLine();
        System.out.println(String.format("|%s%s%s|", paddingStr, mess, paddingStr));
    }
}

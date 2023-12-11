package Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

public class Utils {

    /**
     * hàm kiểm tra dữ liệu đưa vào phải là String và không null
     * @param welcome
     * @param msg
     * @return result
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
     * hàm kiểm tra dữ liệu nhập vào phải có định dạng theo biểu thức và không rỗng
     * @param welcome
     * @param pattern
     * @param msg
     * @param msgreg
     * @return result
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
     * hàm kiểm tra giá trị nhập vào phải là số có kiểu dữ liệu int và lớn hơn min và nhỏ hơn max
     * @param welcome
     * @param min
     * @param max
     * @return number
     */
    public static int getInt(String welcome, int min, int max) {
        int number = 0;
        do {
            String input = getString(welcome, "Input must be a number.");
            try {
                number = Integer.parseInt(input);
                if (number <= min) {
                    System.out.println("Number must be greater than " + min);
                } else if (number > max) {
                    System.out.println("Number must be less than " + max);
                } else if (number % 6 != 0) {
                    System.out.println("Number must be divisible by 6!!!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input must be a number.");
            }
        } while (number <= min || number > max || number % 6 != 0);
        return number;
    }

    /**
     * hàm kiểm tra kiểu dữ liệu nhập vào phải có kiểu String bắt đầu là A-F và sau đó là số 
     * @param welcome
     * @param min
     * @param max
     * @return seatExample
     */
    public static String getSeat(String welcome, int min, int max) {
        String seatExample;
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print(welcome);
            seatExample = sc.nextLine().trim().toUpperCase();

            if (seatExample.length() >= 2 && seatExample.length() <= 3) {
                char rowChar = seatExample.charAt(0);
                if (Character.isLetter(rowChar)) {
                    String seatNumberPart = seatExample.substring(1);
                    if (seatNumberPart.matches("\\d+")) {
                        int seatNumber = Integer.parseInt(seatNumberPart);
                        if (rowChar >= 'A' && rowChar <= 'F' && seatNumber > min && seatNumber <= max) {
                            return seatExample;
                        }
                    }
                }
            }
            System.out.println("Invalid seat format or out of range.");
        } while (true);
    }

    /**
     * hàm kiểm tra dữ liệu nhập vào phải theo format dd/mm/yyyy hh:mm
     * @param welcome
     * @return LocalDateTime.parse(input, formatter)
     */
    public static LocalDateTime getDateTime(String welcome) {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        while (true) {
            try {
                System.out.print(welcome);
                String input = sc.nextLine();
                return LocalDateTime.parse(input, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please re-enter.");
            }
        }
    }

    /**
     * hàm tạo ID Random theo format 3 chữ cái ở đầu - và 6 kí tự vừa chữ vừa số
     * @return ticketCode.toString()
     */ 
    public static String getRadomId() {
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String NUMBERS = "0123456789";
        Random random = new Random();
        StringBuilder ticketCode = new StringBuilder();

        // Tạo 3 chữ cái đầu
        for (int i = 0; i < 3; i++) {
            char randomChar = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
            ticketCode.append(randomChar);
        }

        // Thêm dấu "-"
        ticketCode.append("-");

        // Tạo 6 ký tự chữ và số cuối
        for (int i = 0; i < 6; i++) {
            char randomChar = (random.nextBoolean()) ? ALPHABET.charAt(random.nextInt(ALPHABET.length())) : NUMBERS.charAt(random.nextInt(NUMBERS.length()));
            ticketCode.append(randomChar);
        }

        return ticketCode.toString();
    }

    /**
     * hàm tạo khoảng trắng theo count
     * @param count
     * @return spaces.toString()
     */
    public static String repeatSpaces(int count) {
        StringBuilder spaces = new StringBuilder();
        for (int i = 0; i < count; i++) {
            spaces.append(" ");
        }
        return spaces.toString();
    }

    /**
     * hàm in ra mess theo thống báo trong khung
     * @param mess 
     */
    public static void printMess(String mess) {
        int totalWidth = 45;  // Tổng chiều rộng của chuỗi đường viền
        int messageWidth = mess.length();
        int padding = (totalWidth - messageWidth) / 2;

        // Tạo chuỗi để căn lề
        String paddingStr = repeatSpaces(padding);

        // In ra thông báo
        System.out.println("+---------------------------------------------+");
        System.out.println(String.format("|%s%s%s|", paddingStr, mess, paddingStr));
        System.out.println("+---------------------------------------------+");
    }
}

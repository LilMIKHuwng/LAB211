package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Utils {

    /**
     * hàm kiểm tra giá trị người dùng nhập vào phải là String không được rỗng
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
     * hàm kiểm tra giá trị người dùng nhập vào phải là String và theo định dạng (pattern)
     * @param welcome
     * @param pattern
     * @param msg
     * @param msgreg
     * @return result;
     */
    public static String getStringreg(String welcome, String pattern, String msg, String msgreg) {
        boolean check = true;
        String result = "";
        Scanner sc = new Scanner(System.in);
        do {

            System.out.print(welcome);
            result = sc.nextLine().trim();
            if (result.isEmpty()) {
                System.out.println(msg);
            } else if (!result.matches(pattern)) {
                System.out.println(msgreg);
            } else {
                check = false;
            }
        } while (check);
        return result;
    }

    /**
     * hàm kiểm tra giá trị người dùng nhập vào phải là int và > 0
     * @param welcome
     * @param min
     * @return number
     */
    public static int getInt(String welcome, int min) {
        boolean check = true;
        int number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Integer.parseInt(sc.nextLine());
                if (number <= min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number <= min);
        return number;
    }

    /**
     * hàm kiểm tra giá trị người dùng nhập vào phải là float và > 0
     * @param welcome
     * @param min
     * @return number
     */
    public static float getloat(String welcome, float min) {
        boolean check = true;
        float number = 0;
        Scanner sc = new Scanner(System.in);
        do {
            try {

                System.out.print(welcome);
                number = Float.parseFloat(sc.nextLine());
                if (number < min) {
                    System.out.println("Number must be large than " + min);
                } else {
                    check = false;
                }

            } catch (Exception e) {
                System.out.println("Input number!!!");
            }
        } while (check || number < min);
        return number;
    }

    /**
     * hàm kiểm tra giá trị người dùng nhập vào phải là Date và theo format dd/mm/yyyy
     * @param welcome
     * @return date
     */
    public static Date getDate(String welcome) {
        Scanner sc = new Scanner(System.in);
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        /*
        Bằng cách đặt lenient thành false, bạn đảm bảo rằng ngày tháng phải tuân
        theo định dạng chính xác mà bạn đã xác định trong SimpleDateFormat và sẽ
        không chấp nhận các giá trị không hợp lệ. Điều này giúp đảm bảo tính nhất quán
        và chính xác trong việc kiểm tra định dạng ngày tháng.
         */

        while (true) {
            System.out.print(welcome);
            String input = sc.nextLine();

            try {
                date = dateFormat.parse(input);
                //System.out.println("Ngày đã nhập hợp lệ: " + dateFormat.format(date));
                break; // Thoát khỏi vòng lặp nếu ngày hợp lệ
            } catch (ParseException e) {
                System.out.println("Invalid date. Please re-enter.");
            }
        }

        return date;
    }

    /**
     * hàm kiểm tra giá trị người dùng nhập vào phải là String
     * @param welcome
     * @return 
     */
    public static String getUpdateString(String welcome) {
        Scanner sc = new Scanner(System.in);
        System.out.print(welcome);
        String result = sc.nextLine().trim();
        return result;
    }

    /**
     * hàm kiểm tra nếu người dùng đưa vào giá trị string có kiểu int thì trả về true
     * @param string
     * @param min
     * @return true/false
     */
    public static boolean getUpdateInt(String string, int min) {
        try {
            int n = Integer.parseInt(string);
            //check user enter the correct range of number, number must be > 0 
            if (n < min) {
                System.out.println("Number must be large than " + min);
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
     * hàm kiểm tra nếu người dùng đưa vào giá trị string có kiểu Float thì trả về true
     * @param string
     * @param min
     * @return true/false
     */
    public static boolean getUpdateFloat(String string, float min) {
        try {
            float n = Float.parseFloat(string);
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
     * hàm kiểm tra nếu người dùng đưa vào giá trị string có kiểu Date và theo format dd/mm/yyyy thì trả về true
     * @param string
     * @return true/false
     */
    public static boolean getUpdateDate(String string) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            Date date = dateFormat.parse(string);
            return true;
        } catch (ParseException e) {
            System.out.println("Invalid date. Please re-enter.");
            return false;
        }
    }
}

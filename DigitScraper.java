import java.util.Scanner;
public class DigitScraper {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter an integer: ");
        int number = s.nextInt();
// Add prevention of non int inputs
        while(number > 0) {
            int digit = number % 10;
            number = number / 10;
            System.out.println(digit);
        }
    }
}

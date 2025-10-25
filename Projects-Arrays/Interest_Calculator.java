import java.util.Scanner;

public class Interest_Calculator {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to the Interest Calculator!");
        boolean active = true;

        do {
            System.out.println("Would you like to calculate your interest?");
            System.out.println("1. Calculate Interest");
            System.out.println("2. Exit");
            int choice = s.nextInt();
            switch (choice) {
                case 1:
                    double principal;
                    double rate;
                    int years;
                    int timeComp;
                    double amount;

                    System.out.println("Enter your principal: ");
                    principal = s.nextDouble();

                    System.out.println("Enter your interest rate (%): ");
                    rate = s.nextDouble() / 100;

                    System.out.println("Enter # times compounded per year: ");
                    timeComp = s.nextInt();

                    System.out.println("Enter the # of years: ");
                    years = s.nextInt();

                    amount = principal * Math.pow(1 + rate / timeComp , timeComp * years);

                    if(years == 1) {
                        System.out.printf("Your amount after %d year is $%.2f\n", years, amount);
                    } else {
                        System.out.printf("Your amount after %d years is $%.2f\n", years, amount);
                        active = true;
                    }
                    break;
                case 2:
                    System.out.println("Thank you, goodbye!");
                    System.out.println("Exiting...");
                    active = false;
                    break;
                    default: System.out.println("Invalid choice, please pick 1 or 2!");
            }
        } while (active);
    }
}

import java.util.Scanner;
public class Matrix {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int rows;
        int columns;
        char symbol;

        boolean active = true;

        do {
            System.out.println("Would you like to make a matrix?");
            System.out.println("1. Create a new matrix");
            System.out.println("2. Quit");

            int choice = s.nextInt();
            switch (choice) {
                case 1: System.out.println("Enter the number of rows: ");
                    rows = s.nextInt();
                    System.out.println("Enter the number of columns: ");
                    columns = s.nextInt();

                    System.out.println("Enter the symbol: ");
                    symbol = s.next().charAt(0);

                    for(int i = 0; i < rows; i++) { //bp for later fix
                        for(int j = 0; j < columns; j++) {
                            System.out.print(symbol);
                        }
                        System.out.println();
                        break;
                } case 2: System.out.println("Thank you for using matrix!");
                active = false;
                break;
                default: System.out.println("Invalid choice!");
            }
        } while (active);
    }
}

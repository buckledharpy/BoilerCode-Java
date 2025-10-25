import java.util.Random;
import java.util.Scanner;

public class SlotMachine {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int balance = 100; //dollars
        int bet;
        int payout;
        String[] rows;

        System.out.println("Welcome to the Slot Machine !");
        System.out.println("Symbols : 🐩🐖🐍🦈🐬 ");

        while (balance > 0) {
            System.out.println("Current balance: $" + balance);
            System.out.print("Bet : ");
            bet = s.nextInt();
            if (balance < bet) {
                System.out.println("Insufficient balance!");
                continue;
            } else if (bet <= 0) {
                System.out.println("Bet must be greater than zero!");
                continue;
            } else {
                balance -= bet;
            }
            System.out.println("Spinning . . .");
            spinRow();
        }
    }

    static String[] spinRow() {

        String[] symbols = {"🐩", "🐖", "🐍", "🦈", "🐬"};
        String[] row = new String[3];
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            row[i] = symbols[random.nextInt(symbols.length)];
        }
        return row;
    }
}
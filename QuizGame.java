import java.util.Scanner;

public class QuizGame {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);

        String [] questions = {"Who created Java?",
                "What company is repsonsible for Java?",
                "What does CPU stand for?",
                "What is Java based on?",
                "Java used what type of Syntax?"};

        String [][] options = {{"1. Linus Torvalds" , "2. James Gosling" , "3. Linus Sebastian"},
                {"1. IBM" , "2. Sun Microsystems" , "3. Bell Labs"},
                {"1. Computer Processing Unit" , "2. Central programming Unit" , "3. Central Processing Unit"},
                {"1. C" , "2. Fortran" , "3. GoLang"},
                {"1. Curly brace" , "2. Block based" , "3. Struct based"}};

        int [] answers = {2 , 2 , 3 , 1 , 1 };
        int score = 0;
        int guess;
        System.out.println("-------------------------------------");
        System.out.println("***** Welcome to the Quiz Game! *****");
        System.out.println("-------------------------------------");

        for (int i = 0 ; i < questions.length ; i++) {
            System.out.println(questions[i]);
        for (String option : options[i]) {
            System.out.println(option);
        }
        System.out.println("Enter your guess: ");
        guess = s.nextInt();

        if (guess == answers[i]) {
        System.out.println("Correct! +1!");
        score++;
        }
        else {
            System.out.println("Wrong guess!");
        }
        }
        System.out.println("Your score is " + score + "/" + questions.length);
    }
}

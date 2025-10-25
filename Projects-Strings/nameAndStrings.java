public class nameAndStrings { //This works with print format, more to come!
    public static void main(String[] args) { //main method
        String name = "First Last"; // your name!
        double number = 23.4578; // Any double or "float" number with decimal values
        int age = 18; // Your age!

        String first = name.substring(0,5);
        String last = name.substring(5);

        System.out.printf("Your first name is %s\nYour last name is %s\nYour number is %.1f!\nYour age is %d!", first, last, number, age);
        }
}

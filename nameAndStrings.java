public class nameAndStrings {
    public static void main(String[] args) {
        String name = "Kruz Schurz";
        double number = 23.4578;
        int age = 18;

        String first = name.substring(0,5);
        String last = name.substring(5);

        System.out.printf("Your first name is %s\nYour last name is %s\nYour number is %.1f!\nYour age is %d!", first, last, number, age);
        }
}
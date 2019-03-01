import java.io.IOException;
import java.util.Scanner;

public class Messanger {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Hello user. Please sign up or log in.");
            Login.usersFromTextToList();
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("Sign up")) {
                Login.signUp();
            } else if (choice.equalsIgnoreCase("Log in")) Login.logIn();
            {
                while (true) {
                    System.out.println("Welcome " + Login.getUserLoggedIn());
                    System.out.println("Press: 1 - Send Message/ 2 - Read Messages/ 3 - Log out.");

                    int number = scanner.nextInt();
                    if (number == 1) {
                        Messages.sendingMessage();
                    } else if (number == 2) {
                        Messages.checkingMessages();
                    } else if (number == 3) {
                        Login.logOut();
                        break;
                    }else {
                        System.out.println("incorrect input");
                    }

                }
            }


        }

    }
}

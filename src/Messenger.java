import java.io.IOException;
import java.util.Scanner;

public class Messenger {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Login.usersFromTextToList();
            System.out.println("WELCOME USER.\nPRESS [L] TO LOG IN   |   [S] SIGN UP");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("S")) {
                Login.signUp();
            } else if (choice.equalsIgnoreCase("L")) {
                Login.logIn();
                if (UserLists.getUsers().containsKey(Login.getUserLoggedIn())) {
                    System.out.println("WELCOME " + Login.getUserLoggedIn());

                    while (true) {
                        Thread instantMessageCheck = new Thread(new InstantMessageCheck());
                        instantMessageCheck.start();
                        Thread InstantGroupMessageCheck = new Thread(new InstantGroupMessageCheck());
                        InstantGroupMessageCheck.start();
                        System.out.println("PRESS: [1] SEND MESSAGE | [2] MY CHATS | " +
                                "[3] CREATE CHATS | [4] JOIN PUBLIC CHANNELS | [5] READ MESSAGES | [6] LOG OUT");
                        int number = scanner.nextInt();
                        if (number == 1) {
                           MessengerMain.option1();

                        } else if (number == 2) {
                            MessengerMain.option2();

                        } else if (number == 3) {
                           MessengerMain.option3();

                        } else if (number == 4) {
                            GroupChats.joinPublicChats(Login.getUserLoggedIn());


                        } else if (number == 5) {
                           MessengerMain.option5();

                        } else if (number == 6) {
                            Login.logOut();
                            break;

                        } else {
                            System.out.println("INCORRECT INPUT");
                        }
                    }
                }
            }
        }
    }
}

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;


public class Login {
    private static String userLoggedIn;
    private static Scanner scanner = new Scanner(System.in);
    private static Path userFile = Paths.get("userInfo.txt");


    public static void usersFromTextToList() throws IOException {
        if (!Files.exists(userFile)) {
            Files.createFile((userFile));
        }
        List<String> list = Files.readAllLines(userFile);
        for (String line : list) {
            String[] split = line.split(":");
//            System.out.println("Name: " + split[0]);
//            System.out.println("Password: " + split[1]);

            UserList.addUser(split[0], new User(split[0], split[1]));

        }
    }

    public static void signUp() throws IOException {

        while (true) {
            System.out.println("Please enter your nickname.");
            String nickName = scanner.nextLine();
            if (UserList.getUsers().containsKey(nickName)) {
                System.out.println("This nickname is taken.");
            } else {
                System.out.println("Please enter your password.");
                String password = scanner.nextLine();

                String userInfo = nickName + ":" + password + "\r\n";

                String messageFile = nickName + ".txt";
                if (!Files.exists(Paths.get(messageFile))) {
                    Files.createFile(Paths.get(messageFile));
                    Files.write(Paths.get(messageFile), "1".getBytes());
                }
                System.out.println("Now, please try to log in with the nick name and the password you chose.");

                usersFromTextToList();
                Files.write(userFile, userInfo.getBytes(), StandardOpenOption.APPEND);
                break;

            }

        }
    }

    public static String getUserLoggedIn() {
        return userLoggedIn;
    }


    public static void logIn() {

        while (true) {
            System.out.println("Please enter your name.");
            String userName = scanner.nextLine();
            if (UserList.getUsers().containsKey(userName)) {
                System.out.println("Please enter your password.");
                String userPassword = scanner.nextLine();
                if (userPassword.equals(UserList.getUsers().get(userName).getPassword())) {
                    System.out.println("You logged in.");
                    userLoggedIn = userName;
                    break;
                }

            } else {
                System.out.println("Either your nickname or your password is wrong.");
                break;
            }

        }
    }

    public static void logOut() {
        System.out.println("You logged out.");
    }

}


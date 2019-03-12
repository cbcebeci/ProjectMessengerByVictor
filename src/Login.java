import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

class Login {
    private static String userLoggedIn;
    private static Scanner scanner = new Scanner(System.in);
    private static Path userFile = Paths.get("userInfo.txt");
    private static Path offlineUserFile = Paths.get("offlineUsers.txt");
    private static Path onlineUserFile = Paths.get("onlineUsers.txt");
    private static Set<String> offlineUserList = new HashSet<>();
    private static Set<String> onlineUserList = new HashSet<>();


    static void usersFromTextToList() throws IOException {
        if (!Files.exists(userFile)) {
            Files.createFile((userFile));
        }
        List<String> list = Files.readAllLines(userFile);
        for (String line : list) {
            String[] split = line.split(":");
            UserLists.addUser(split[0], split[1]);

        }
    }

    static void signUp() throws IOException {

        while (true) {
            System.out.println("PLEASE ENTER YOUR NAME.");
            String nickName = scanner.nextLine();
            if (UserLists.getUsers().containsKey(nickName)) {
                System.out.println("THIS NAME IS TAKEN.");
            } else {
                System.out.println("PLEASE ENTER YOUR PASSWORD.");
                String password = scanner.nextLine();

                String userInfo = nickName + ":" + password;

                loading();
                System.out.println("\nNow, please try to log in with the nick name and the password you chose.");
                Files.write(userFile, (userInfo + "\n").getBytes(), StandardOpenOption.APPEND);
                UserLists.addNameToFile(nickName, offlineUserFile);
                break;

            }

        }
    }

    static String getUserLoggedIn() {
        return userLoggedIn;
    }

    static void loading() {
        System.out.print("IT MAY TAKE A FEW SECOND");
        for (int i = 0; i < 4; i++) {
            System.out.print(".");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    static void logIn() throws IOException {

        System.out.println("PLEASE ENTER YOUR NAME.");
        String userName = scanner.nextLine();
        if (UserLists.getUsers().containsKey(userName)) {
            System.out.println("PLEASE ENTER YOUR PASSWORD.");
            String userPassword = scanner.nextLine();
            if (UserLists.getUsers().get(userName).equals(userPassword)) {

                loading();
                System.out.println("\nYOU LOGGED IN.");
                userLoggedIn = userName;
                UserLists.addFromFileToFile(offlineUserFile, onlineUserFile, userLoggedIn);
            }else{
                System.out.println("Either your nickname or your password is wrong.");
            }

        } else {
            System.out.println("Either your nickname or your password is wrong.");
        }
    }


    static void logOut() throws IOException {
        loading();
        UserLists.addFromFileToFile(onlineUserFile, offlineUserFile, userLoggedIn);
        System.out.println("\nYOU LOGGED OUT.");

    }

    static void printAllUsers() throws IOException {
        UserLists.addFromFileToList(onlineUserList, onlineUserFile);
        UserLists.addFromFileToList(offlineUserList, offlineUserFile);
        System.out.println("-----------------\n Online users:");
        System.out.println(onlineUserList + "\n");
        System.out.println("Offline users:");
        System.out.println(offlineUserList);
        System.out.println("-----------------");
    }

}


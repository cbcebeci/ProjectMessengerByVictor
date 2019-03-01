import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;

public class Messages {
    static Scanner scanner = new Scanner(System.in);

    public static void sendingMessage() throws IOException {

        System.out.println("Please §he user you would like to send message.");
        String name = scanner.nextLine();
        if (UserList.getUserByName(name) == null) {
            System.out.println("User name does not exist.");
        } else {
            System.out.println("Please enter your message.");
            String message = scanner.nextLine();

            String messageFile = name + ".txt";
            if (!Files.exists(Paths.get(messageFile))) {
                Files.createFile(Paths.get(messageFile));
            }
            Files.write(Paths.get(messageFile), (message + "\r\n").getBytes(), StandardOpenOption.APPEND);
        }

    }

    public static void checkingMessages() throws IOException {


        System.out.println("Would you like to check new messages (N) or read-messages (R)? Press N or R.");
        String userInput = scanner.nextLine();


        if (userInput.equalsIgnoreCase("N")) {
            checkingNewMessages(Login.getUserLoggedIn());

        } else if (userInput.equalsIgnoreCase("R")) {
            printAllMessages(Login.getUserLoggedIn());
        }else{
            System.out.println("Incorrect input.");
        }

    }


    public static void checkingNewMessages(String userName) throws IOException {
        String fileLocation = userName + ".txt";
        List<String> lines = Files.readAllLines(Paths.get(fileLocation), Charset.forName("UTF-8"));

        int number = Integer.parseInt(lines.get(0));
        if (number == lines.size()) {
            System.out.println("There is no new message now.");
        } else {

            for (int counter = number; counter < lines.size(); counter++) {
                System.out.println(lines.get(counter));
            }
            lines.set(0, String.valueOf(lines.size()));
            Files.write(Paths.get(fileLocation), lines);
        }
    }


    public static void printAllMessages(String userName) throws IOException {
        String fileLocation = userName + ".txt";
        List<String> lines = Files.readAllLines(Paths.get(fileLocation), Charset.forName("UTF-8"));

        int number = Integer.parseInt(lines.get(0));
        if (number == lines.size()) {
            System.out.println("You have not received any message yet.");
        } else {
            for (String line : lines) {
                System.out.println(line);

                lines.set(0, String.valueOf(lines.size()));
                Files.write(Paths.get(fileLocation), lines);
            }
        }
    }




}

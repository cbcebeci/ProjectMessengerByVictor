import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

class Messages {

    private static Scanner scanner = new Scanner(System.in);
    private static Path listOfChatNames = Paths.get("src/chats/listOfChatNames.txt");
    private static Path listOfChatNamesCopy = Paths.get("src/copyOfChats/listOfChatNamesCopy.txt");
     static Map<Integer, String> chatList = new HashMap<>();


    static void createPrivateGroupChat(String user) throws IOException{
        Thread instantMessageCheck = new Thread(new InstantMessageCheck());
        instantMessageCheck.start();

        if (!Files.exists(listOfChatNames)) {
            Files.createFile((listOfChatNames));
        }
        if (!Files.exists(listOfChatNamesCopy)) {
            Files.createFile((listOfChatNamesCopy));
        }

        Set<String> groupChat = new TreeSet<>(); //names are already added sorted.

        System.out.println("PLEASE ENTER USER/USER NAMES.\r\n" +
                "[DONE] WHEN YOU ARE DONE.");
        Login.printAllUsers();
        while (true) {
            System.out.println("PLEASE ENTER NAME\r");
            String name = scanner.nextLine();
            if (name.equalsIgnoreCase("DONE")) {
                break;
            } else if (!UserLists.getUsers().containsKey(name)) {
                System.out.println("USER NAME DOES NOT EXIST.");
            } else {
                groupChat.add(name);
            }
        }
        groupChat.add(user);

        //creating a group-chat-file name as 'chatname'.

        String chatName = String.join("-", groupChat);


        //checking if group name exist or not.

        Path groupChatFile = Paths.get("src/chats/"+ chatName + ".txt");
        if (!Files.exists(groupChatFile)) {
            Files.createFile(groupChatFile);

            for (String names : groupChat) {

                Files.write(groupChatFile, (names + ":" + groupChat.size() + "\r\n").getBytes(), StandardOpenOption.APPEND);

            }
            Files.write(listOfChatNames, (chatName + "\r\n").getBytes(), StandardOpenOption.APPEND);
        }

        Path groupChatFileCopy = Paths.get("src/copyOfChats/"+ chatName + ".txt");
        if (!Files.exists(groupChatFileCopy)) {
            Files.createFile(groupChatFileCopy);
            Files.write(listOfChatNamesCopy, (chatName + "\r\n").getBytes(), StandardOpenOption.APPEND);
        }


    }


    static void sendingMessage(String user, String chatName) throws IOException {

        Path groupChatFile = Paths.get("src/chats/"+ chatName + ".txt");
        Path groupChatFileCopy = Paths.get("src/copyOfChats/"+ chatName + ".txt");

        System.out.println("PLEASE ENTER YOUR MESSAGE.");
        String message = scanner.nextLine();

        Files.write(groupChatFile, (user + " said:\r\n" + message + "\r\n").getBytes(),
                StandardOpenOption.APPEND);

        Files.write(groupChatFileCopy, (user + " said:\r\n" + message + "\r\n").getBytes(),
                StandardOpenOption.APPEND);

        List<String> updateChatFile = Files.readAllLines(groupChatFile);
        for (int lineNumber = 0; lineNumber < updateChatFile.size(); lineNumber++) {
            String[] split = updateChatFile.get(lineNumber).split(":");

            if (split[0].equals(user)){
                int number = Integer.parseInt(split[1]);
                if (number != updateChatFile.size()) {

                    String updatedLine = split[0] + ":" + updateChatFile.size();
                    updateChatFile.set(lineNumber, updatedLine);
                    Files.write(groupChatFile, updateChatFile);
                    return;
                }
            }
        }

    }

    static void findingChats(String someone) throws IOException {

        List<String> privateChatList = Files.readAllLines(listOfChatNames, Charset.forName("UTF-8"));

        int number = 1;
        for (String lines : privateChatList) {
            String[] split = lines.split("-");
            for (String s : split) {
                if (someone.equals(s)) {
                    chatList.put(number, lines);

                    number++;
                }

            }
        }
        if(chatList.size() == 0){
            System.out.println("PRIVATE CHAT LIST: \nThere is no chat on your name.\n----------");
        }else {
            System.out.println("PRIVATE CHAT LIST: \n" + chatList + "\n----------\r" );
        }
    }


    static void readingMessagesFromChats(String user) throws IOException {
        System.out.println("YOUR CHATS. PLEASE CHOOSE THE NUMBER.");
        findingChats(user);
        int someNumber = scanner.nextInt();
        scanner.nextLine();

        System.out.println("[N] NEW MESSAGES   |   [R] READ MESSAGES");

        String userInput = scanner.nextLine();

        if (userInput.equalsIgnoreCase("N")) {
            readNewMessagesFromChats(user, chatList.get(someNumber));

        } else if (userInput.equalsIgnoreCase("R")) {
            readAllMessagesInChat(user, chatList.get(someNumber));
        } else {
            System.out.println("INCORRECT INPUT.");
        }


    }

    private static void readNewMessagesFromChats(String someone, String fileName) throws IOException {
        String fileLocation = "src/chats/" + fileName + ".txt";
        List<String> groupChat = Files.readAllLines(Paths.get(fileLocation), Charset.forName("UTF-8"));

        for (int lineNumber = 0; lineNumber < groupChat.size(); lineNumber++) {
            String[] split = groupChat.get(lineNumber).split(":");

            if (split[0].equals(someone)) {
                int number = Integer.parseInt(split[1]);
                if (number == groupChat.size()) {
                    System.out.println("THERE IS NO NEW MESSAGE");
                } else {
                    for (int counter = number; counter < groupChat.size(); counter++) {
                        System.out.println(groupChat.get(counter));

                    }
                    String updatedLine = someone + ":" + groupChat.size();
                    groupChat.set(lineNumber, updatedLine);
                    Files.write(Paths.get(fileLocation), groupChat);

                }
            }
        }

    }

    private static void readAllMessagesInChat(String someOne, String fileName) throws IOException {
        String fileLocation = "src/chats/" + fileName + ".txt";
        List<String> groupChat = Files.readAllLines(Paths.get(fileLocation), Charset.forName("UTF-8"));


        for (int lineNumber = 0; lineNumber < groupChat.size(); lineNumber++) {
            String[] split = groupChat.get(lineNumber).split(":");
            if (split[0].equals(someOne)) {

                String[] anotherSplit = fileName.split("-");

                for (int counter = anotherSplit.length; counter < groupChat.size(); counter++) {
                    System.out.println(groupChat.get(counter));

                }
                String updatedLine = someOne + ":" + groupChat.size();
                groupChat.set(lineNumber, updatedLine);
                Files.write(Paths.get(fileLocation), groupChat);
            }
        }
    }


}


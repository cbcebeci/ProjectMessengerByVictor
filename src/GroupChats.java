import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

class GroupChats {
    private static Scanner scanner = new Scanner(System.in);
    private static Path groupChatNames = Paths.get("src/chats/groupChatNames.txt");
    private static Path groupChatNamesCopy = Paths.get("src/copyOfChats/GroupChatNamesCopy.txt");
    static Set<String> groupNameList = new HashSet<>();
    static Map<Integer, String> myPublicChats = new HashMap<>();

    private static void groupNamesFromFileToList() throws IOException {
        Thread InstantGroupMessageCheck = new Thread(new InstantGroupMessageCheck());
        InstantGroupMessageCheck.start();

        if (!Files.exists(groupChatNames)) {
            Files.createFile(groupChatNames);
        }
        if (!Files.exists(groupChatNamesCopy)) {
            Files.createFile(groupChatNamesCopy);
        }

        List<String> chatName = Files.readAllLines(groupChatNames, Charset.forName("UTF-8"));
        for (String names :
                chatName) {
            String[] split = names.split("=");
            groupNameList.add(split[0]);

        }
    }

    private static void printGroupChatNames() throws IOException {
        groupNamesFromFileToList();
        System.out.println("PUBLIC GROUP CHATS: \n" + groupNameList + "\n-----------------------");

    }


    static void printMyGroupChats(String name) throws IOException {

        List<String> chatName = Files.readAllLines(groupChatNames, Charset.forName("UTF-8"));
        for (int counter = 0; counter < chatName.size(); counter++) {
            List<String> myList = new ArrayList<>();
            Path groupChannel = Paths.get("src/chats/" + chatName.get(counter) + ".txt");
            List<String> firstLine = Files.readAllLines(groupChannel, Charset.forName("UTF-8"));
            UserLists.takeUserListFromPublicChannels(firstLine, myList);

            if (myList.contains(name)) {
                myPublicChats.put((counter + 1), chatName.get(counter));
            }
        }
        if (myPublicChats.size() == 0) {
            System.out.println("PUBLIC CHAT LIST: \nThere is no public chat you belong.\n----------");
        } else {
            System.out.println("PUBLIC CHAT LIST: \n" + myPublicChats + "\n----------");
        }
    }


    static void creatingPublicGroupChat(String name) throws IOException {

        while (true) {
            System.out.println("CREATE YOUR GROUP NAME.   |   [BACK] IF YOU WANNA GO BACK");
            String userInput = scanner.nextLine();

            groupNamesFromFileToList();
            if (userInput.equalsIgnoreCase("back")) {
                break;
            } else if (groupNameList.contains(userInput)) {
                System.out.println("THIS GROUP NAME ALREADY EXIST.");
            } else {

                Path groupName = Paths.get("src/chats/" + userInput + ".txt");
                Path groupNameCopy = Paths.get("src/copyOfChats/" + userInput + ".txt");

                if (!Files.exists(groupName)) {
                    Files.createFile(groupName);
                }

                if (!Files.exists(groupNameCopy)) {
                    Files.createFile(groupNameCopy);
                }

                Files.write(groupChatNames, (userInput + "\n").getBytes(), StandardOpenOption.APPEND);
                Files.write(groupChatNamesCopy, (userInput + "\n").getBytes(), StandardOpenOption.APPEND);
                Files.write(groupName, (name + "-1").getBytes(), StandardOpenOption.APPEND);

                while (true) {
                    System.out.println("[1] ADD PEOPLE  |  [2] GO BACK");
                    String someNumber = scanner.nextLine();
                    if (someNumber.equals("1")) {
                        addPeopleToGroupChat(userInput);
                    } else if (someNumber.equals("2")) {
                        break;
                    } else {
                        System.out.println("INCORRECT INPUT");
                    }
                }
            }
        }
    }

    private static void addPeopleToGroupChat(String chat) throws IOException {
        Login.printAllUsers();
        while (true) {
            System.out.println("ENTER USER NAMES.  |  [DONE] WHEN YOU ARE DONE.");
            String userInput = scanner.nextLine();
            if (userInput.equalsIgnoreCase("done")) {
                break;
            } else {

                List<String> pplInChat = new ArrayList<>();
                Path groupName = Paths.get("src/chats/" + chat + ".txt");
                List<String> getName = Files.readAllLines(groupName, Charset.forName("UTF-8"));
                for (String names : getName) {
                    String[] split = names.split("-");
                    for (int i = 0; i < split.length; i++) {
                        pplInChat.add(split[i]);
                    }


                    String[] arr = new String[]{userInput};
                    for (String names1 : arr) {
                        String[] split1 = names1.split(" ");
                        for (String s : split1) {
                            if (UserLists.getUsers().containsKey(s) && pplInChat.contains(s)) {
                                System.out.println(s + " HAS BEEN ALREADY ADDED.");
                            } else if (UserLists.getUsers().containsKey(s)) {
                                pplInChat.add(s);
                                pplInChat.add(String.valueOf(getName.size()));
                                System.out.println("YOU SUCCESSFULLY ADDED " + s + " TO THE CHANNEL.");
                            } else {
                                System.out.println("THERE IS NO SUCH A USER: " + s);
                            }
                        }
                    }

                }
                String people = String.join("-", pplInChat);
                getName.set(0, people);
                Files.write(groupName, getName);
            }

        }
    }

    static void sendMessageToPublicChannels(String userName, String channelName) throws IOException {

        List<String> peopleList = new ArrayList<>();
        Path groupChannel = Paths.get("src/chats/" + channelName + ".txt");
        Path groupChannelCopy = Paths.get("src/copyOfChats/" + channelName + ".txt");


        System.out.println("PLEASE ENTER YOUR MESSAGE.");
        String userInput = scanner.nextLine();

        Files.write(groupChannel, (userName + " said:\r\n" + userInput + "\r\n").getBytes(), StandardOpenOption.APPEND);
        Files.write(groupChannelCopy, (userName + " said:\r\n" + userInput + "\r\n").getBytes(), StandardOpenOption.APPEND);

        List<String> firstLine = Files.readAllLines(groupChannel, Charset.forName("UTF-8"));
        UserLists.updatingUserList(peopleList, userName, firstLine);

        String people = String.join("-", peopleList);
        firstLine.set(0, people);
        Files.write(groupChannel, firstLine);

    }


    static void joinPublicChats(String user) throws IOException {
        printGroupChatNames();
        System.out.println("PLEASE ENTER THE PUBLIC CHANNEL NAME YOU WOULD LIKE TO JOIN.");
        String userInput = scanner.nextLine();
        Path groupChannel = Paths.get("src/chats/" + userInput + ".txt");
        Path groupChannelCopy = Paths.get("src/copyOfChats/" + userInput + ".txt");
        List<String> pplList = new ArrayList<>();

        Login.loading();

        Files.write(groupChannel, (user + "JOINED THE CHANNEL").getBytes(), StandardOpenOption.APPEND);
        Files.write(groupChannelCopy, (user + "JOINED THE CHANNEL").getBytes(), StandardOpenOption.APPEND);

        List<String> firstLine = Files.readAllLines(groupChannel, Charset.forName("UTF-8"));
        UserLists.takeUserListFromPublicChannels(firstLine, pplList);
        pplList.add(user);
        pplList.add(String.valueOf(firstLine.size()));

        String people = String.join("-", pplList);
        firstLine.set(0, people);
        Files.write(groupChannel, firstLine);

        System.out.println("\nYOU JOINED THE CHANNEL.");
        while (true) {
            System.out.println("[1] SEND MESSAGE    |    [2] READ PREVIOUS MESSAGES    |    [3] BACK");
            String someNumber = scanner.nextLine();

            if (someNumber.equals("1")) {
                sendMessageToPublicChannels(user, userInput);
            } else if (someNumber.equals("2")) {
                readAllMessagesFromPublicChannels(user, userInput);
            } else if (someNumber.equals("3")) {
                break;
            } else {
                System.out.println("INCORRECT INPUT.");
            }
        }

    }

    static void readMessagesFromPublicChannels(String userName) throws IOException {
        printMyGroupChats(userName);
        if (myPublicChats.size() != 0) {
            System.out.println("PLEASE CHOOSE THE NUMBER.");
            int someNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.println("[N] NEW MESSAGES   |   [R] READ ALL MESSAGES");
            String userInput = scanner.nextLine();

            if (userInput.equalsIgnoreCase("N")) {
                readNewMessagesFromPublicChannels(Login.getUserLoggedIn(), myPublicChats.get(someNumber));

            } else if (userInput.equalsIgnoreCase("R")) {
                readAllMessagesFromPublicChannels(Login.getUserLoggedIn(), myPublicChats.get(someNumber));
            } else {
                System.out.println("INCORRECT INPUT.");
            }

        }


    }

    private static void readNewMessagesFromPublicChannels(String user, String chatName) throws IOException {
        List<String> pplList = new ArrayList<>();
        Path groupChannel = Paths.get("src/chats/" + chatName + ".txt");
        List<String> firstLine = Files.readAllLines(groupChannel, Charset.forName("UTF-8"));

        UserLists.takeUserListFromPublicChannels(firstLine, pplList);

        for (int i = 0; i < pplList.size(); i++) {
            if (user.equals(pplList.get(i))) {
                int number = Integer.parseInt(pplList.get(i + 1));
                if (Integer.parseInt(pplList.get(i + 1)) == firstLine.size()) {
                    System.out.println("THERE IS NO NEW MESSAGE.");
                } else {
                    for (int counter = number; counter < firstLine.size(); counter++) {
                        System.out.println(firstLine.get(counter));

                    }
                }

            }

        }
        UserLists.updatingUserList(pplList, user, firstLine);
        String people = String.join("-", pplList);
        firstLine.set(0, people);
        Files.write(groupChannel, firstLine);

    }

    private static void readAllMessagesFromPublicChannels(String user, String chatName) throws IOException {
        List<String> pplList = new ArrayList<>();

        Path groupChannel = Paths.get("src/chats/" + chatName + ".txt");
        List<String> firstLine = Files.readAllLines(groupChannel, Charset.forName("UTF-8"));

        if (firstLine.size() == 1) {
            System.out.println("THERE IS NO MESSAGE IN THAT CHANNEL YET.");
        } else {
            for (int counter = 1; counter < firstLine.size(); counter++) {
                System.out.println(firstLine.get(counter));

            }
        }

        UserLists.updatingUserList(pplList, user, firstLine);

        String people = String.join("-", pplList);
        firstLine.set(0, people);
        Files.write(groupChannel, firstLine);
    }

}

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
                                "[3] CREATE PUBLIC CHANNEL | [4] JOIN PUBLIC CHANNELS | [5] READ MESSAGES | [6] LOG OUT");
                        int number = scanner.nextInt();
                        if (number == 1) {
                            System.out.println("[1]  PRIVATE CHATS   |   [2] PUBLIC CHANNELS");
                            int someNumber = scanner.nextInt();
                            if (someNumber == 1) {
                                while(true){
                                    System.out.println("PLEASE WRITE THE PRIVATE CHANNEL NUMBER");
                                    Messages.findingChats(Login.getUserLoggedIn());
                                    scanner.nextLine();
                                    if(Messages.chatList.size() == 0){
                                        break;
                                    }
                                    String chatName = scanner.nextLine();
                                    if(Messages.chatList.containsKey(Integer.parseInt(chatName))){
                                        Messages.sendingMessage(Login.getUserLoggedIn(), Messages.chatList.get(Integer.parseInt(chatName)));
                                        break;
                                    }else{
                                        System.out.println("INCORRECT INPUT");
                                    }
                                }

                            } else if (someNumber == 2) {
                                while (true) {
                                    System.out.println("PLEASE WRITE THE PUBLIC CHANNEL NUMBER");
                                    GroupChats.printMyGroupChats(Login.getUserLoggedIn());
                                    scanner.nextLine();
                                    if (GroupChats.myPublicChats.size() == 0) {
                                        break;
                                    }
                                    String channelName = scanner.nextLine();
                                    if (GroupChats.myPublicChats.containsKey(Integer.parseInt(channelName))) {
                                        GroupChats.sendMessageToPublicChannels(Login.getUserLoggedIn(),
                                                GroupChats.myPublicChats.get(Integer.parseInt(channelName)));
                                        break;
                                    } else {
                                        System.out.println("INCORRECT INPUT");
                                    }
                                }
                            } else {
                                System.out.println("INCORRECT INPUT");
                            }

                        } else if (number == 2) {
                            Messages.findingChats(Login.getUserLoggedIn());
                            GroupChats.printMyGroupChats(Login.getUserLoggedIn());

                        } else if (number == 3) {
                            System.out.println("[1]  PRIVATE CHATS   |   [2] PUBLIC CHANNELS");
                            int someNumber = scanner.nextInt();
                            if (someNumber == 1) {
                                Messages.createPrivateGroupChat(Login.getUserLoggedIn());
                            } else if (someNumber == 2) {
                                GroupChats.creatingPublicGroupChat(Login.getUserLoggedIn());
                            } else {
                                System.out.println("INCORRECT INPUT");
                            }

                        } else if (number == 4) {
                            GroupChats.joinPublicChats(Login.getUserLoggedIn());


                        } else if (number == 5) {
                            System.out.println("[1]  PRIVATE CHATS   |   [2] PUBLIC CHANNELS");
                            int someNumber = scanner.nextInt();
                            if (someNumber == 1) {
                                Messages.readingMessagesFromChats(Login.getUserLoggedIn());
                            } else if (someNumber == 2) {
                                GroupChats.readMessagesFromPublicChannels(Login.getUserLoggedIn());
                            } else {
                                System.out.println("INCORRECT INPUT");
                            }
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

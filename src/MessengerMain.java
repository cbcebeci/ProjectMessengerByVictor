import java.io.IOException;
import java.util.Scanner;

public class MessengerMain {
    private static Scanner scanner = new Scanner(System.in);


    static void option1 () throws IOException {
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


    }

    static void option2 () throws IOException {
        Messages.findingChats(Login.getUserLoggedIn());
        GroupChats.printMyGroupChats(Login.getUserLoggedIn());

    }
    static void option3 () throws IOException {
        System.out.println("[1]  PRIVATE CHATS   |   [2] PUBLIC CHANNELS");
        int someNumber = scanner.nextInt();
        if (someNumber == 1) {
            Messages.createPrivateGroupChat(Login.getUserLoggedIn());
        } else if (someNumber == 2) {
            GroupChats.creatingPublicGroupChat(Login.getUserLoggedIn());
        } else {
            System.out.println("INCORRECT INPUT");
        }


    }

    static void option5 () throws IOException {
        System.out.println("[1]  PRIVATE CHATS   |   [2] PUBLIC CHANNELS");
        int someNumber = scanner.nextInt();
        if (someNumber == 1) {
            Messages.readingMessagesFromChats(Login.getUserLoggedIn());
        } else if (someNumber == 2) {
            GroupChats.readMessagesFromPublicChannels(Login.getUserLoggedIn());
        } else {
            System.out.println("INCORRECT INPUT");
        }
    }

}

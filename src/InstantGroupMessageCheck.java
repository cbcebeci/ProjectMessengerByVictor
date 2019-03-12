import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class InstantGroupMessageCheck extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Path listOfPublicChannelsCopy = Paths.get("src/copyOfChats/GroupChatNamesCopy.txt");
                List<String> publicChannelsList = Files.readAllLines(listOfPublicChannelsCopy, Charset.forName("UTF-8"));

                for (String chatNames : publicChannelsList) {
                    String[] split = chatNames.split(" ");
                    for (int i = 0; i < split.length; i++) {
                        Path copyFile = Paths.get("src/copyOfChats/" + split[i] + ".txt");
                        List<String> copyFileLines = Files.readAllLines(copyFile, Charset.forName("UTF-8"));

                        for (String lines : copyFileLines) {
                            String[] userNames = lines.split(" ");
                            for (int j = 0; j < userNames.length; j++) {
                                if (UserLists.getUsers().containsKey(userNames[j]) &&
                                        !userNames[j].equals(Login.getUserLoggedIn())) {
                                    System.out.println("** NEW MESSAGE -  PUBLIC CHANNEL: " + split[i] + " USER: " + userNames[j]);

                                }
                            }
                            List<String> list = Files.lines(copyFile)
                                    .filter(line -> !line.contains(lines))
                                    .collect(Collectors.toList());
                            Files.write(copyFile, list, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

                        }
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

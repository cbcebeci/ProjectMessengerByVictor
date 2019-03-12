import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;

public class InstantMessageCheck extends Thread {

    @Override
    public void run() {
        while (true) {
            try {
                Path listOfChatNamesCopy = Paths.get("src/copyOfChats/listOfChatNamesCopy.txt");
                List<String> groupChatList = Files.readAllLines(listOfChatNamesCopy, Charset.forName("UTF-8"));


                for (String lines : groupChatList) {
                    String[] split = lines.split("-");
                    for (int i = 0; i < split.length; i++) {
                        if (Login.getUserLoggedIn().equals(split[i])) {

                            Path originalFile = Paths.get("src/copyOfChats/" + lines + ".txt");
                            List<String> copyFileLines = Files.readAllLines(originalFile, Charset.forName("UTF-8"));


                            for (String copylines : copyFileLines) {
                                String[] split3 = copylines.split(" ");
                                for (int j = 0; j < split3.length; j++) {
                                    if (UserLists.getUsers().containsKey(split3[j]) &&
                                            !split3[j].equals(Login.getUserLoggedIn())) {
                                        System.out.println("** NEW MESSAGE -  PRIVATE CHAT: " + lines + " USER: " + split3[j]);
                                    }
                                }

                                List<String> list = Files.lines(originalFile)
                                        .filter(line -> !line.contains(copylines))
                                        .collect(Collectors.toList());
                                Files.write(originalFile, list, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
                            }

                        }
                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
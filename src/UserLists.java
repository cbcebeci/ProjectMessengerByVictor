import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class UserLists {
    private static Map<String, Object> users = new HashMap<>();

    static Map<String, Object> getUsers() {
        return users;
    }

//    static Object getUserByName(String name) {
//        return users.get(name);
//    }

    static void addUser(String userName, Object password) {
        users.put(userName, password);
    }

    static void addNameToFile(String name, Path fileName) throws IOException {
        if (!Files.exists(fileName)) {
            Files.createFile((fileName));
        }

        Files.write(fileName, (name + "\n").getBytes(), StandardOpenOption.APPEND);

    }

    static void addFromFileToList(Set<String> listName, Path fileName) throws IOException {
        if (!Files.exists(fileName)) {
            Files.createFile((fileName));
        }

        List<String> list = Files.readAllLines(fileName, Charset.forName("UTF-8"));
        listName.addAll(list);


    }

    public static void addFromListToFile(Set<String> listName, Path fileName) throws IOException {
        if (!Files.exists(fileName)) {
            Files.createFile((fileName));
        }

        for (String line :
                listName) {
            Files.write(fileName, (line + "\n").getBytes(), StandardOpenOption.APPEND);
        }


    }

    static void addFromFileToFile(Path fileName, Path anotherFileName, String userName) throws IOException {

        if (!Files.exists(fileName)) {
            Files.createFile((fileName));
        }

        if (!Files.exists(anotherFileName)) {
            Files.createFile((anotherFileName));
        }

        List<String> list = Files.lines(fileName)
                .filter(line -> !line.contains(userName))
                .collect(Collectors.toList());
        Files.write(fileName, list, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        Files.write(anotherFileName, (userName + "\n").getBytes(), StandardOpenOption.APPEND);

    }

    static void takeUserListFromPublicChannels(List<String> firstLine, List<String> mapList) {

        String[] split = firstLine.get(0).split("-");
        for (int counter = 0; counter < split.length; counter++) {
            mapList.add(split[counter]);
        }

    }

    static void updatingUserList(List<String> pplList, String user, List<String> firstLine) {
        if(pplList.size() != 0){
            pplList.clear();
        }


        String[] split = firstLine.get(0).split("-");
        for (int counter = 0; counter < split.length; counter++) {
            if (!user.equals(split[counter])) {
                pplList.add(split[counter]);
            } else if (user.equals(split[counter])) {
                pplList.add(split[counter]);
                pplList.add(String.valueOf(firstLine.size()));
                counter++;
            }

        }

    }


}

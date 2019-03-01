import java.util.HashMap;
import java.util.Map;

public class UserList {
    private static Map<String, User> users = new HashMap<>();

    public static Map<String, User> getUsers() {
        return users;
    }

    public static User getUserByName(String name) {
        return users.get(name);
    }

    public static void addUser(String userName, User user) {
        users.put(userName, user);
    }

}

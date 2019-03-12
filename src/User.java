public class User {
    private static String nickname;
    private static String password;

    public  User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }


    public static String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

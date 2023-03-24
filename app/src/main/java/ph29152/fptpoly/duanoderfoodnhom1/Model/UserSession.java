package ph29152.fptpoly.duanoderfoodnhom1.Model;

public class UserSession {
    private static Users currentUser;

    public static void setCurrentUser(Users user) {
        currentUser = user;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }
}

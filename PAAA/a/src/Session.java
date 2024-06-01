public class Session {
    private static int currentUserId;
    private static String currentUserRole;

    public static void setCurrentUserId(int userId) {
        currentUserId = userId;
    }

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserRole(String role) {
        currentUserRole = role;
    }

    public static String getCurrentUserRole() {
        return currentUserRole;
    }

    public static void clearSession() {
        currentUserId = 0;
        currentUserRole = null;
    }
}

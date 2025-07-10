package app.utils;

public class CustomExceptions {

    public static class InvalidCredentialsException extends Exception {
        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    public static class InvalidUserRoleException extends Exception {
        public InvalidUserRoleException(String message) {
            super(message);
        }
    }

    public static class DuplicateUsernameException extends Exception {
        public DuplicateUsernameException(String message) {
            super(message);
        }
    }

    public static class wrongTimeInputException extends Exception {
        public wrongTimeInputException(String message) {
            super(message);
        }
    }
}

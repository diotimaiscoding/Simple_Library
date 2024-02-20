public class Exceptions extends Exception {
    // Exceptions

    // Wrong number of arguments exception
    public static class WrongNumberOfArgumentsException extends Exception {
        @Override
        public String getMessage() {
            return "Wrong number of arguments";
        }
    }

    // Wrong type exception for Book and Member
    public static class WrongTypeException extends Exception {
        @Override
        public String getMessage() {
            return "Wrong type";
        }
    }

    //Student cannot read in handwritten book exception
    public static class StudentsCannotReadInException extends Exception {
        @Override
        public String getMessage() {
            return "Students can not read handwritten books!";
        }
    }

    // Limit reached exception. Both for borrowing limit and created instances limit
    public static class LimitReachedException extends Exception {
        private String message;
        public LimitReachedException() {
            this.message = "You have exceeded the borrowing limit!";
        }
        public LimitReachedException(String message) {
            this.message = message;
        }
        @Override
        public String getMessage() {
            return this.message;
        }
    }

    // Borrowing book exceptions
    public static class BorrowBookException extends Exception {
        private String message;
        public BorrowBookException() {
            this.message = "You cannot borrow this book!";
        }
        public BorrowBookException(String message) {
            this.message = message;
        }
        @Override
        public String getMessage() {
            return message;
        }
    }

    // Returning book exceptions
    public static class ReturnBookException extends Exception {
        @Override
        public String getMessage() {
            return "You cannot return this book!";
        }
    }

    // Extending book exceptions
    public static class ExtendBookException extends Exception {
        @Override
        public String getMessage() {
            return "You cannot extend the deadline!";
        }
    }
    
    // File not found exception
    public static class FileNotFoundException extends Exception {
        private String message;
        public FileNotFoundException() {
            message = "File not found";
        }
        public FileNotFoundException(String message) {
            this.message = message;
        }
        @Override
        public String getMessage() {
            return message;
        }
    }
}
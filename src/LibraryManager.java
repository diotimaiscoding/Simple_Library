import java.time.LocalDate;
import java.util.ArrayList;

public class LibraryManager {
    public static ArrayList<Book> books = new ArrayList<Book>();
    public static ArrayList<Member> members = new ArrayList<Member>();

    // Add book command
    public static void addBook(String type) throws Exceptions.LimitReachedException {
        Book newBook;
        if (type.equals("P")) {
            newBook = new PrintedBook();
        }
        else {
            newBook = new HandwrittenBook();
        }
        books.add(newBook);
        WriteLines.writeLineToFile("Created new book: " + newBook.getType() + " " + "[id: " + newBook.getId() + "]");
    }

    // Add member command
    public static void addMember(String type) throws Exceptions.LimitReachedException {
        Member newMember;
        if (type.equals("S")) {
            newMember = new Student();
        }
        else {
            newMember = new Academician();
        }
        members.add(newMember);
        WriteLines.writeLineToFile("Created new member: " + newMember.getType() + " " + "[id: " + newMember.getId() + "]");
    }

    // Get book by id
    public static Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    // Get member by id
    public static Member getMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    // Borrow book command
    public static void borrowBook(int bookId, int memberId, LocalDate borrowTime) throws Exceptions.LimitReachedException, Exceptions.BorrowBookException {
        Book book = getBookById(bookId);
        if (book.getStatus() && !book.isBorrowed()) {
            book.borrowBook(memberId, borrowTime);
            WriteLines.writeLineToFile("The book [" + bookId + "] was borrowed by member [" + memberId + "] at " + borrowTime);
        }
        else {
            WriteLines.writeLineToFile("The book " + bookId + " is not available");
        }
    }

    // Return book command
    public static void returnBook(int bookId, int memberId, LocalDate returnTime) throws Exceptions.ReturnBookException {
        Book book = getBookById(bookId);
        Member member = getMemberById(memberId);
        if (!book.getStatus()) {
            if (book.isBorrowed()) {
                member.updateFee(bookId, returnTime);
            }
            book.returnBook();
            WriteLines.writeLineToFile("The book [" + book.getId() + "] was returned by member [" + member.getId() + "] at " + returnTime + " Fee: " + member.getFee());
        }
        else {
            WriteLines.writeLineToFile("The book [" + bookId + "] was not borrowed");
        }
    }

    // Extend book command
    public static void extendBook(int bookId, int memberId, LocalDate requestTime) throws Exceptions.ExtendBookException {
        Book book = getBookById(bookId);
        if (!book.getStatus()) {
            book.extendBook(memberId);
            WriteLines.writeLineToFile("The deadline of book [" + bookId + "] was extended by member [" + memberId + "] at " + requestTime);
            WriteLines.writeLineToFile("New deadline of book [" + bookId + "] is " + book.getDueDate());
        }
        else {
            WriteLines.writeLineToFile("The book [" + bookId + "] was not borrowed or read in library.");
        }
    }

    // Read in book command
    public static void readInBook(int bookId, int memberId, LocalDate borrowTime) throws Exceptions.StudentsCannotReadInException,
    Exceptions.LimitReachedException, Exceptions.BorrowBookException {
        Book book = getBookById(bookId);
        Member member = getMemberById(memberId);
        if (book.getStatus()) {
            if (book.getType().equals("Handwritten") && member.getType().equals("Student")) {
                throw new Exceptions.StudentsCannotReadInException();
            }
            else {
                book.readIn(member, borrowTime);
                WriteLines.writeLineToFile("The book [" + bookId + "] was read in library by member [" + memberId + "] at " + borrowTime);
            }
        }
        else {
            throw new Exceptions.BorrowBookException("You can not read this book!");
        }
    }

    // Get the history command
    public static void getHistory() {
        WriteLines.writeLineToFile("History of library:\n");
        WriteLines.writeLineToFile(Member.getMembersInfo());
        WriteLines.writeLineToFile(Book.getBooksInfo());
        WriteLines.writeLineToFile(Book.getBooksAvailabilityInfo());
    }
}
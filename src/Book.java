import java.time.LocalDate;
import java.util.*;

public class Book {

    // Initialize instance variables
    private int id;
    private String type;
    private boolean isAvailable = true; // If the book is being read in
    private boolean isBorrowed = false; // It is not available but it is not borrowed either
    private int borrowerId = -1;
    private LocalDate borrowTime = null;
    private LocalDate dueDate = null;
    private static int numBooks = 0;
    private HashMap<Integer, Integer> memberExtentions = new HashMap<>();

    // Constructor
    public Book(String type) throws Exceptions.LimitReachedException {
        this.type = type;
        if (numBooks == 999999) {
            throw new Exceptions.LimitReachedException("You can not add a new book! The limit is 999999!");
        }
        this.id = ++numBooks;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public String getType() {
        if (type.equals("P")) {
            return "Printed";
        }
        else if (type.equals("H")) {
            return "Handwritten";
        }
        else {
            return "Unknown";
        }
    }
    public boolean getStatus() {
        return isAvailable;
    }
    public boolean isBorrowed() {
        return isBorrowed;
    }
    public int getBorrowerId() {
        return borrowerId;
    }
    public LocalDate getBorrowTime() {
        return borrowTime;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public static int getNumBooks() {
        return numBooks;
    }

    // Borrow book method
    public void borrowBook(int borrowerId, LocalDate borrowTime) throws Exceptions.LimitReachedException, Exceptions.BorrowBookException {
        // Checks if book is Handwritten, Handwritten books cannot be borrowed
        if (type.equals("H")) {
            throw new Exceptions.BorrowBookException("Handwritten books cannot be borrowed!");
        }
        // Checking for book status is made in LibraryManager
        else {
            // Get the member who is borrowing the book
            Member member = LibraryManager.getMemberById(borrowerId);
            // Calls the borrowBook method of the member
            member.borrowBook(this.id);
            // Updates the book status
            isAvailable = false;
            isBorrowed = true;
            // Updates the book's borrow time and borrower id
            this.borrowTime = borrowTime;
            this.borrowerId = borrowerId;
            // Updates the book's due date
            if (member.getType().equals("Student")) {
                this.dueDate = borrowTime.plusDays(7);
            }
            else if (member.getType().equals("Academic")) {
                this.dueDate = borrowTime.plusDays(14);
            }
        }
    }

    // Return book method
    public void returnBook() throws Exceptions.ReturnBookException {
        // Checks if book is suitiable for returning
        if (isAvailable == false || isBorrowed == true) {
            // Get the member who is returning the book
            Member member = LibraryManager.getMemberById(borrowerId);
            // Calls the returnBook method of the member
            member.returnBook(this.id);
            // Updates the book status
            isAvailable = true;
            isBorrowed = false;
            borrowerId = -1;
        }
        else {
            throw new Exceptions.ReturnBookException();
        }
    }

    // Extend book method
    public void extendBook(int memberId) throws Exceptions.ExtendBookException {
        // Checks if book is suitiable for extending
        if (memberExtentions.containsKey(memberId) && memberExtentions.get(memberId) > 0) {
            throw new Exceptions.ExtendBookException();
        }
        else {
            // If member is not extended the book before, add to the memberExtentions HashMap
            if (!memberExtentions.containsKey(memberId)) {
                memberExtentions.put(memberId, 0);
            }
            // Get the member who is extending the book
            Member member = LibraryManager.getMemberById(memberId);
            // Calls the extendBook method of the member
            member.extendBook(this); 
            // Update the extentions of the member          
            memberExtentions.put(memberId, memberExtentions.get(memberId) + 1);
        }
    }

    // Read in book method
    public void readIn(Member member, LocalDate borrowTime) throws Exceptions.StudentsCannotReadInException, Exceptions.LimitReachedException, Exceptions.BorrowBookException {
        // Checks if student is trying to read in a handwritten book
        if (member.getType().equals("Student") && this.type.equals("H")) {
            throw new Exceptions.StudentsCannotReadInException();
        }
        // Checks if book is available
        if (!isAvailable) {
            throw new Exceptions.BorrowBookException("You can not read this book!");
        }
        // Calls the borrowBook method of the member
        member.borrowBook(this.id);
        // Update the book status
        isAvailable = false;
        isBorrowed = false;
        // Update the book's borrow time and borrower id
        this.borrowTime = borrowTime;
        this.borrowerId = member.getId();
    }

    // Get info method
    public String getInfo() {
        // Construct the info string
        String info = "The book [" + id + "] was ";
        // Check the book status
        if (isBorrowed) {
            info += "borrowed by member [" + borrowerId + "] at " + borrowTime;
        }
        // If the book is not borrowed, check if it is read in
        else if (!isAvailable) {
            info += "read in library by member [" + borrowerId + "] at " + borrowTime;
        }
        return info;
    }
    // Get books info method
    public static String getBooksInfo() {
        // Construct the books info string
        String booksInfo = "";
        // Get the number of printed books
        booksInfo += "Number of printed books: " + PrintedBook.getNumPrintedBooks() + "\n";
        // Get the printed books info
        booksInfo += PrintedBook.getPrintedBooksInfo() + "\n";
        // Get the number of handwritten books
        booksInfo += "Number of handwritten books: " + HandwrittenBook.getNumHandwrittenBooks() + "\n";
        // Get the handwritten books info
        booksInfo += HandwrittenBook.getHandwrittenBooksInfo();
        return booksInfo;
    }
    // Get books availability info method
    public static String getBooksAvailabilityInfo() {
        // Construct the books availability info string
        String booksAvailabilityInfo = "";
        // Create ArrayList for borrowed and read in books
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        ArrayList<Book> readInBooks = new ArrayList<>();
        // Iterate the books in the library
        for (Book book : LibraryManager.books) {
            // Determine the borrowed books
            if (book.isBorrowed()) {
                borrowedBooks.add(book);
            }
            // Determine the read in books
            else if (!book.getStatus() && !book.isBorrowed()) {
                readInBooks.add(book);
            }
        }
        // Get the number of borrowed books
        booksAvailabilityInfo += "Number of borrowed books: " + borrowedBooks.size() + "\n";
        // Get the borrowed books info
        for (Book book : borrowedBooks) {
            booksAvailabilityInfo += book.getInfo() + "\n";
        }
        booksAvailabilityInfo += "\n";
        // Get the number of read in books
        booksAvailabilityInfo += "Number of books read in library: " + readInBooks.size() + "\n";
        // Get the read in books info
        for (Book book : readInBooks) {
            booksAvailabilityInfo += book.getInfo();
        }
        return booksAvailabilityInfo;
    }
}
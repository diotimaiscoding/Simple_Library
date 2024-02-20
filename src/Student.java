import java.time.LocalDate;
import java.util.*;

public class Student extends Member {

    // Initialize instance variables
    private static ArrayList<Student> students = new ArrayList<Student>();

    // Constructor
    public Student() throws Exceptions.LimitReachedException {
        super("S");
        students.add(this);
    }

    // Getters and setters
    // Override methods
    @Override
    public String getType() {
        return "Student";
    }
    public static int getNumStudents() {
        return students.size();
    }
    
    // Override borrowBook method
    public void borrowBook(int borrowTheBookWithId) throws Exceptions.LimitReachedException {
        // If student has not reached the limit of borrowed books call super method
        if (this.borrowedBooksIdList.size() < 2) {
            super.borrowBook(borrowTheBookWithId);
        }
        // If student has reached the limit of borrowed books throw appropriate exception
        else {
            throw new Exceptions.LimitReachedException();
        }
    }

    // Override extendBook method
    public void extendBook(Book book) {
        // Set due date to 7 days after request time
        book.setDueDate(book.getDueDate().plusDays(7));
    }

    // Override updateFee method
    public void updateFee(int borrowedBookWithId, LocalDate returnedByDate) {
        // Get the book with given id
        Book book = LibraryManager.getBookById(borrowedBookWithId);
        // If book is returned after due date calculate the fee
        if(returnedByDate.isAfter(book.getDueDate())) {
            // Calculate the days over due
            int daysOverDue = returnedByDate.getDayOfYear() - book.getDueDate().getDayOfYear();
            super.fee = daysOverDue;
        }
    }

    // Get students info
    public static String getStudentsInfo() {
        String studentsInfo = "";
        for(Student student : students) {
            studentsInfo += "Student [id: " + student.getId() + "]" + "\n";
        }
        return studentsInfo;
    }
}
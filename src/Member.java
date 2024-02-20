import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Member {

    // Initialize instance variables
    protected int id;
    protected String type;
    protected ArrayList<Integer> borrowedBooksIdList = new ArrayList<Integer>();
    protected static int numberOfMembers = 0;
    protected int fee = 0;

    // Constructor
    public Member(String type) throws Exceptions.LimitReachedException {
        this.type = type;
        if (numberOfMembers == 999999) {
            throw new Exceptions.LimitReachedException("You can not add a new member! The limit is 999999!");
        }
        this.id = ++numberOfMembers;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    
    // Abstract method
    public abstract String getType();
    
    // Getters and setters
    public ArrayList<Integer> getBorrowedBookId() {
        return borrowedBooksIdList;
    }
    public static int getNumMembers() {
        return numberOfMembers;
    }

    // Borrow book
    public void borrowBook(int borrowTheBookWithId) throws Exceptions.LimitReachedException{
        this.borrowedBooksIdList.add(borrowTheBookWithId);
    }

    // Return book
    public void returnBook(int returnTheBookWithId) {
        int indexOfTheBook = this.borrowedBooksIdList.indexOf(returnTheBookWithId);
        this.borrowedBooksIdList.remove(indexOfTheBook);
    }

    // Extend book abstract method
    public abstract void extendBook(Book book);

    // Update fee abstract method
    public abstract void updateFee(int borrowedBookWithId, LocalDate returnedByDate);

    // Get fee
    public int getFee() {
        return this.fee;
    }

    // Get members info
    public static String getMembersInfo() {
        String membersInfo = "";
        membersInfo += "Number of students: " + Student.getNumStudents() + "\n";
        membersInfo += Student.getStudentsInfo() + "\n";
        membersInfo += "Number of academics: " + Academician.getNumAcademicians() + "\n";
        membersInfo += Academician.getAcademicsInfo();
        return membersInfo;
    }
}
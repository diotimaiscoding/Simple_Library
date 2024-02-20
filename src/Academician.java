import java.time.LocalDate;
import java.util.*;

public class Academician extends Member {

    // static variable to keep track of all the academicians
    private static ArrayList<Academician> academicians = new ArrayList<Academician>();

    // constructor
    public Academician() throws Exceptions.LimitReachedException {
        super("A");
        academicians.add(this);
    }
    
    // getters and setters
    @Override
    public String getType() {
        return "Academic";
    }
    public static int getNumAcademicians() {
        return academicians.size();
    }
    
    // borrowBook method
    public void borrowBook(int borrowTheBookWithId) throws Exceptions.LimitReachedException {
        if (this.borrowedBooksIdList.size() < 4) {
            super.borrowBook(borrowTheBookWithId);
        }
        else {
            throw new Exceptions.LimitReachedException();
        }
    }
    
    // extendBook method
    public void extendBook(Book book) {
        book.setDueDate(book.getDueDate().plusDays(14));
    }

    // updateFee method
    public void updateFee(int borrowedBookWithId, LocalDate returnedByDate) {
        Book book = LibraryManager.getBookById(borrowedBookWithId);
        if(returnedByDate.isAfter(book.getDueDate())) {
                int daysOverDue = returnedByDate.getDayOfYear() - book.getDueDate().getDayOfYear();
                super.fee = daysOverDue;
            }
    }

    // getAcademicsInfo method
    public static String getAcademicsInfo() {
        String academicsInfo = "";
        for(Academician academician : academicians) {
            academicsInfo += "Academic [id: " + academician.getId() + "]" + "\n";
        }
        return academicsInfo;
    }
}
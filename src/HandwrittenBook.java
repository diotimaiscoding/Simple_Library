import java.util.*;

public class HandwrittenBook extends Book{
    // Initialize instance variables
    private static ArrayList<HandwrittenBook> HandwrittenBooks = new ArrayList<HandwrittenBook>();
    // Constructor
    public HandwrittenBook() throws Exceptions.LimitReachedException {
        super("H");
        HandwrittenBooks.add(this);
    }
    // Getter for HandwrittenBook number
    public static int getNumHandwrittenBooks() {
        return HandwrittenBooks.size();
    }
    // Returns information about all HandwrittenBooks
    public static String getHandwrittenBooksInfo() {
        String HandwrittenBooksInfo = "";
        for(HandwrittenBook HandwrittenBook : HandwrittenBooks) {
            HandwrittenBooksInfo += "Handwritten [id: " + HandwrittenBook.getId() + "]" + "\n";
        }
        return HandwrittenBooksInfo;
    }
}
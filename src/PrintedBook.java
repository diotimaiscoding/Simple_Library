import java.util.*;

public class PrintedBook extends Book{

    // Initialize instance variables
    private static ArrayList<PrintedBook> printedBooks = new ArrayList<PrintedBook>();

    // Constructor
    public PrintedBook() throws Exceptions.LimitReachedException {
        super("P");
        printedBooks.add(this);
    }

    // Getter for PrintedBook number
    public static int getNumPrintedBooks() {
        return printedBooks.size();
    }
    // Get printed books info
    public static String getPrintedBooksInfo() {
        String printedBooksInfo = "";
        for(PrintedBook printedBook : printedBooks) {
            printedBooksInfo += "Printed [id: " + printedBook.getId() + "]" + "\n";
        }
        return printedBooksInfo;
    }
}
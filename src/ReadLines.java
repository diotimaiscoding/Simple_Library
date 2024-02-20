import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReadLines {
    
    /**
     * Reads lines from the file and returns them as an ArrayList.
     * @param filename the name of the file to read from
     * @return an ArrayList of Strings containing the lines from the file
     * @throws Exceptions.FileNotFoundException
     */
    private static ArrayList<String> readLinesFromFile(String filename) throws Exceptions.FileNotFoundException {
        ArrayList<String> lines = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
        } catch (IOException e) {
            throw new Exceptions.FileNotFoundException("File " + filename + " not found!");
        }
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals("")) {
                lines.remove(i);
                i--;
            }
        }
        return lines;
    }

    // Run lines
    public static void runLines(String inputFileName, String outputFileName) {
        // Initialize the output file
        WriteLines.writeLinesInitialize(outputFileName);
        try {
        // Read lines from the file
        ArrayList<String> lines = readLinesFromFile(inputFileName);
        // Create the time formatter object
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Iterate over the lines
        for (String line : lines) {
            try {
                String[] lineElems = line.split("\t");
                String command = lineElems[0];

                // Determine the command
                if (command.equals("")){
                    // Do nothing
                }
                // If the command is addBook
                else if (command.equals("addBook")) {
                    // If the number of arguments is not 2 throw appropriate exception
                    if (lineElems.length != 2) {
                        throw new Exceptions.WrongNumberOfArgumentsException();
                    }
                    // If the type is not H or P throw appropriate exception
                    else if (!(lineElems[1].equals("H") || lineElems[1].equals("P"))) {
                        throw new Exceptions.WrongTypeException();
                    }
                    // Call addBook method
                    LibraryManager.addBook(lineElems[1]);
                }

                // If the command is addMember
                else if (command.equals("addMember")) {
                    // If the number of arguments is not 2 throw appropriate exception
                    if (lineElems.length != 2) {
                        throw new Exceptions.WrongNumberOfArgumentsException();
                    }
                    // If the type is not A or S throw appropriate exception
                    else if (!(lineElems[1].equals("A") || lineElems[1].equals("S"))) {
                        throw new Exceptions.WrongTypeException();
                    }
                    // Call addMember method
                    LibraryManager.addMember(lineElems[1]);
                }

                // If the command is borrowBook
                else if (command.equals("borrowBook")) {
                    // If the number of arguments is not 4 throw appropriate exception
                    if (lineElems.length != 4) {
                        throw new Exceptions.WrongNumberOfArgumentsException();
                    }
                    // Call borrowBook method
                    LibraryManager.borrowBook(Integer.parseInt(lineElems[1]), Integer.parseInt(lineElems[2]),
                    LocalDate.parse(lineElems[3], formatter));
                }

                // If the command is returnBook
                else if (command.equals("returnBook")) {
                    // If the number of arguments is not 4 throw appropriate exception
                    if (lineElems.length != 4) {
                        throw new Exceptions.WrongNumberOfArgumentsException();
                    }
                    // Call returnBook method
                    LibraryManager.returnBook(Integer.parseInt(lineElems[1]), Integer.parseInt(lineElems[2]),
                    LocalDate.parse(lineElems[3], formatter));
                }

                // If the command is extendBook
                else if (command.equals("extendBook")) {
                    // If the number of arguments is not 4 throw appropriate exception
                    if (lineElems.length != 4) {
                        throw new Exceptions.WrongNumberOfArgumentsException();
                    }
                    // Call extendBook method
                    LibraryManager.extendBook(Integer.parseInt(lineElems[1]), Integer.parseInt(lineElems[2]),
                    LocalDate.parse(lineElems[3], formatter));
                }

                // If the command is readInLibrary
                else if (command.equals("readInLibrary")) {
                    // If the number of arguments is not 4 throw appropriate exception
                    if (lineElems.length != 4) {
                        throw new Exceptions.WrongNumberOfArgumentsException();
                    }
                    // Call readInLibrary method
                    LibraryManager.readInBook(Integer.parseInt(lineElems[1]), Integer.parseInt(lineElems[2]),
                    LocalDate.parse(lineElems[3], formatter));
                }

                // If the command is getTheHistory
                else if (command.equals("getTheHistory")) {
                    // If the number of arguments is not 1 throw appropriate exception
                    if (lineElems.length != 1) {
                        throw new Exceptions.WrongNumberOfArgumentsException();
                    }
                    // Call getTheHistory method
                    LibraryManager.getHistory();
                }
            }
            catch (Exceptions.WrongNumberOfArgumentsException e) {
                WriteLines.writeLineToFile(e.getMessage());
            }
            catch (Exceptions.WrongTypeException e) {
                WriteLines.writeLineToFile(e.getMessage());
            }
            catch (Exceptions.StudentsCannotReadInException e) {
                WriteLines.writeLineToFile(e.getMessage());
            }
            catch (Exceptions.LimitReachedException e) {
                WriteLines.writeLineToFile(e.getMessage());
            }
            catch (Exceptions.BorrowBookException e) {
                WriteLines.writeLineToFile(e.getMessage());
            }
            catch (Exceptions.ReturnBookException e) {
                WriteLines.writeLineToFile(e.getMessage());
            }
            catch (Exceptions.ExtendBookException e) {
                WriteLines.writeLineToFile(e.getMessage());
            }
            catch (Exception e) {
                WriteLines.writeLineToFile("Unknown error occured during the process!");
            }
        }
        }
        catch (Exceptions.FileNotFoundException e) {
            WriteLines.writeLineToFile(e.getMessage());
        }
        catch (Exception e) {
            WriteLines.writeLineToFile("Unknown error occured during the process!");
        }
    }
}
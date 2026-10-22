import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Book {
    int bookId;
    String bookName;
    String author;
    boolean available;
    String issuedTo;
    Queue<String> waitingQueue = new LinkedList<>();

    Book(int bookId, String bookName, String author) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.author = author;
        this.available = true;
        this.issuedTo = "";
    }
}

public class LibrarySystem {
    static Book[] books = new Book[100];
    static int bookCount = 0;
    static Scanner sc = new Scanner(System.in);

    static void addBook() {
        if (bookCount >= books.length) {
            System.out.println("Book storage is full.");
            return;
        }

        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Book Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        books[bookCount] = new Book(id, name, author);
        bookCount++;

        System.out.println("Book added successfully!");
    }

    static void displayBooks() {
        if (bookCount == 0) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\n----- BOOK LIST -----");

        for (int i = 0; i < bookCount; i++) {
            System.out.println("Book ID : " + books[i].bookId);
            System.out.println("Name    : " + books[i].bookName);
            System.out.println("Author  : " + books[i].author);

            if (books[i].available) {
                System.out.println("Status  : Available");
            } else {
                System.out.println("Status  : Issued");
            }

            System.out.println("----------------------");
        }
    }

    static void displayAvailableBooks() {
        System.out.println("\n----- AVAILABLE BOOKS -----");
        boolean found = false;

        for (int i = 0; i < bookCount; i++) {
            if (books[i].available) {
                System.out.println(books[i].bookId + " - "
                        + books[i].bookName + " - "
                        + books[i].author);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books are currently available.");
        }
    }

    static void searchBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();

        for (int i = 0; i < bookCount; i++) {
            if (books[i].bookId == id) {
                System.out.println("\nBook Found");
                System.out.println("Book Name : " + books[i].bookName);
                System.out.println("Author    : " + books[i].author);

                if (books[i].available) {
                    System.out.println("Status    : Available");
                } else {
                    System.out.println("Status    : Issued");
                    System.out.println("Issued To : " + books[i].issuedTo);
                }
                return;
            }
        }

        System.out.println("Book not found.");
    }

    static void issueBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < bookCount; i++) {
            if (books[i].bookId == id) {
                System.out.print("Enter Student Name: ");
                String student = sc.nextLine();

                if (books[i].available) {
                    books[i].available = false;
                    books[i].issuedTo = student;
                    System.out.println("Book issued successfully to " + student);
                } else {
                    System.out.println("Book is already issued.");
                    System.out.print("Do you want to join waiting queue? (yes/no): ");
                    String choice = sc.nextLine();

                    if (choice.equalsIgnoreCase("yes")) {
                        books[i].waitingQueue.add(student);
                        System.out.println(student + " added to waiting queue.");
                        System.out.println("Queue Position: " + books[i].waitingQueue.size());
                    }
                }
                return;
            }
        }

        System.out.println("Book not found.");
    }

    static void returnBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < bookCount; i++) {
            if (books[i].bookId == id) {
                if (books[i].available) {
                    System.out.println("This book is already available.");
                    return;
                }

                System.out.println("Book returned by " + books[i].issuedTo);
                books[i].issuedTo = "";

                if (!books[i].waitingQueue.isEmpty()) {
                    String nextStudent = books[i].waitingQueue.poll();
                    books[i].issuedTo = nextStudent;
                    books[i].available = false;
                    System.out.println("Book automatically issued to waiting student: " + nextStudent);
                } else {
                    books[i].available = true;
                    System.out.println("Book is now available.");
                }
                return;
            }
        }

        System.out.println("Book not found.");
    }

    static void reserveBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < bookCount; i++) {
            if (books[i].bookId == id) {
                if (books[i].available) {
                    System.out.println("Book is available. You can issue it directly.");
                } else {
                    System.out.print("Enter Student Name: ");
                    String student = sc.nextLine();
                    books[i].waitingQueue.add(student);
                    System.out.println("Book reserved successfully.");
                    System.out.println("Your waiting position is: " + books[i].waitingQueue.size());
                }
                return;
            }
        }

        System.out.println("Book not found.");
    }

    static void displayWaitingQueue() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();

        for (int i = 0; i < bookCount; i++) {
            if (books[i].bookId == id) {
                System.out.println("\nWaiting Queue for " + books[i].bookName);

                if (books[i].waitingQueue.isEmpty()) {
                    System.out.println("Waiting queue is empty.");
                } else {
                    int position = 1;
                    for (String student : books[i].waitingQueue) {
                        System.out.println(position + ". " + student);
                        position++;
                    }
                }
                return;
            }
        }

        System.out.println("Book not found.");
    }

    public static void main(String[] args) {
        books[bookCount++] = new Book(101, "Java Programming", "Herbert Schildt");
        books[bookCount++] = new Book(102, "Data Structures", "Seymour Lipschutz");
        books[bookCount++] = new Book(103, "Computer Networks", "Andrew Tanenbaum");

        int choice;

        do {
            System.out.println("\n================================");
            System.out.println("      LIBRARY MANAGEMENT");
            System.out.println("================================");
            System.out.println("1. Add Book");
            System.out.println("2. Display All Books");
            System.out.println("3. Display Available Books");
            System.out.println("4. Search Book");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Reserve Book");
            System.out.println("8. Display Waiting Queue");
            System.out.println("9. Exit");
            System.out.print("\nEnter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1: addBook(); break;
                case 2: displayBooks(); break;
                case 3: displayAvailableBooks(); break;
                case 4: searchBook(); break;
                case 5: issueBook(); break;
                case 6: returnBook(); break;
                case 7: reserveBook(); break;
                case 8: displayWaitingQueue(); break;
                case 9: System.out.println("Thank you!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 9);

        sc.close();
    }
}

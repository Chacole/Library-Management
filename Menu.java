import java.util.*;

public class Menu {
    private Library library;
    private Scanner scanner = new Scanner(System.in);

    public Menu(Library library) {
        this.library = library;
    }

    /**
     * visiable table menu for users.
     */
    public void printMenu() {
        System.out.println("Welcome to My Application!");
        System.out.println("[0] Exit");
        System.out.println("[1] Add Document");
        System.out.println("[2] Remove Document");
        System.out.println("[3] Update Document");
        System.out.println("[4] Find Document");
        System.out.println("[5] Display Document");
        System.out.println("[6] Add User");
        System.out.println("[7] Borrow Document");
        System.out.println("[8] Return Document");
        System.out.println("[9] Display User Info");
    }

    /**
     * constructor.
     */
    public void runMenu() {
        boolean running = true;
        while (running) {
            printMenu();

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 0 -> {
                        System.out.println("Existing...");
                        running = false;
                    }
                    case 1 -> HandleAddDocument();
                    case 2 -> HandleRemoveDocument();
                    case 3 -> HandleUpdateDocument();
                    case 4 -> HandleFindDocument();
                    case 5 -> HandleDisplayDocuments();
                    case 6 -> HandleAddUser();
                    case 7 -> HandleBorrowDocument();
                    case 8 -> HandleReturnDocument();
                    case 9 -> HandleDisplayUserInfo();
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public void HandleAddDocument() {
        System.out.println("Enter document author: ");
        String author = scanner.nextLine();
        System.out.println("Enter document title: ");
        String title = scanner.nextLine();
        System.out.println("Enter document type: ");
        String type = scanner.nextLine();
        System.out.println("Enter document year: ");
        int year = scanner.nextInt();
        System.out.println("Enter document quantity: ");
        int quantity = scanner.nextInt();

        if (author != null && title != null && type != null && year > 0 && quantity > 0) {
            Documents doc = new Documents(author, title, type, year, quantity);
            library.AddDocument(doc);
            System.out.println("Document added!");
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }

    public void HandleRemoveDocument() {
        System.out.print("Enter document ID: ");
        int id = scanner.nextInt();

        List<Documents> document = library.SearchDocument(id, null, null, null, null);
        if (document.isEmpty()) {
            System.out.println("No document found with ID: " + id);
        } else {
            library.RemoveDocument(document.get(0));
            System.out.println("Document removed!");
        }
    }

    public void HandleUpdateDocument() {
        System.out.print("Enter document ID: ");
        int id = scanner.nextInt();
        List<Documents> documents = library.SearchDocument(id, null, null, null, null);

        if (!documents.isEmpty()) {
            Documents doc = documents.get(0);
            System.out.println("Enter new document author: ");
            String author = scanner.nextLine();
            if (author != null) {
                doc.setAuthor(author);
            }
            System.out.println("Enter new document title: ");
            String title = scanner.nextLine();
            if (title != null) {
                doc.setTitle(title);
            }
            System.out.println("Enter new document type: ");
            String type = scanner.nextLine();
            if (type != null) {
                doc.setType(type);
            }
            System.out.println("Enter new document year: ");
            int year = scanner.nextInt();
            if (year != -1) {
                doc.setYear(year);
            }
            System.out.println("Enter new document quantity: ");
            int quantity = scanner.nextInt();
            if (quantity != -1) {
                doc.setQuantity(quantity);
            }

            library.AlterDocuments(doc);
        } else {
            System.out.println("No document found with ID: " + id);
        }
    }

    public void HandleFindDocument() {
        System.out.print("Enter document ID: ");
        int id = scanner.nextInt();
        System.out.println("Enter document author: ");
        String author = scanner.nextLine();
        System.out.println("Enter document title: ");
        String title = scanner.nextLine();
        System.out.println("Enter document type: ");
        String type = scanner.nextLine();
        System.out.println("Enter document year: ");
        int year = scanner.nextInt();
        System.out.println("Enter document quantity: ");
        int quantity = scanner.nextInt();

        List<Documents> document = library.SearchDocument(id, author, title, type, year);
        if (!document.isEmpty()) {
            for (Documents doc : document) {
                doc.getInfo();
            }
        } else {
            System.out.println("No document found with ID: " + id);
        }
    }

    public void HandleDisplayDocuments() {
        System.out.print("Enter document ID: ");
        int id = scanner.nextInt();
        List<Documents> document = library.SearchDocument(id, null, null, null, null);
        if (!document.isEmpty()) {
            document.get(0).getInfo();
        } else {
            System.out.println("No document found with ID: " + id);
        }
    }

    public void HandleAddUser() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        Users user = new Users(name, email, address, phone);
        if (name != null && email != null && address != null && phone != null) {
            library.AddUser(user);
            System.out.println("User added!");
        } else {
            System.out.println("Invalid input. Please try again.");
        }
    }

    public void HandleBorrowDocument() {
        System.out.println("Enter user ID: ");
        int userId = scanner.nextInt();
        System.out.println("Enter document ID: ");
        int docId = scanner.nextInt();

        List<Users> users = library.SearchUser(userId, null, null, null, null, null);
        List<Documents> documents = library.SearchDocument(docId, null, null, null, null);

        if (!users.isEmpty() && !documents.isEmpty()) {
            library.BorrowDocument(users.get(0), documents.get(0));
        } else {
            System.out.println("Borrow failed. User or document not found.");
        }
    }

    public void HandleReturnDocument() {
        System.out.println("Enter user ID: ");
        int userId = scanner.nextInt();
        System.out.println("Enter document ID: ");
        int docId = scanner.nextInt();

        List<Users> users = library.SearchUser(userId, null, null, null, null, null);
        List<Documents> documents = library.SearchDocument(docId, null, null, null, null);

        if (!users.isEmpty() && !documents.isEmpty()) {
            library.returnDocument(users.get(0), documents.get(0));
        } else {
            System.out.println("Return failed. User or document not found.");
        }
    }

    public void HandleDisplayUserInfo() {
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        List<Users> user = library.SearchUser(userId, null, null, null, null, null);
        if (!user.isEmpty()) {
            user.get(0).getInfo();
        } else {
            System.out.println("No user found with ID: " + userId);
        }
    }
}

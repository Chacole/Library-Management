import java.util.*;

public class Library {
    private List<Documents> documents = new ArrayList<>();
    private List<Users> users = new ArrayList<>();
    private List<BorrowRecord> borrowRecords = new ArrayList<>();
    private ManagementID managementID;

    /**
     * Add a document.
     * @param Newdoc
     */
    public void AddDocument(Documents Newdoc) {
        for (Documents doc : documents) {
            if (doc.equals(Newdoc)) {
                doc.setQuantity(doc.getQuantity() + Newdoc.getQuantity()); // inspection if the document is already exist.
                return;
            }
        }

        Newdoc.setID(managementID.generateDocID());
        documents.add(Newdoc);
        System.out.println("Add document succeed: " + Newdoc.getTitle());
    }

    /**
     * Remove a document.
     * @param d
     */
    public void RemoveDocument(Documents d) {
        boolean removed = documents.removeIf(doc -> doc.getID() == d.getID());

        if (removed) {
            managementID.recycleDocID(d.getID());
            System.out.println("Removed document: " + d.getTitle());
        } else {
            System.out.println("Document with ID " + d.getID() + " not found.");
        }
    }

    /**
     * Change a document.
     * @param doc
     */
    public void AlterDocuments(Documents doc) {
        int index = documents.indexOf(doc);

        if (index != -1) {
            documents.set(index, doc);
            System.out.println("Alter document succeeded: " + doc.getTitle());
        } else {
            System.out.println("Document not found: " + doc.getTitle());
        }
    }

    /**
     * Search a document.
     * @param ID
     * @param author
     * @param title
     * @param type
     * @param year
     * @return
     */
    public List<Documents> SearchDocument(Integer ID, String author, String title, String type, Integer year) {
        List<Documents> docs = new ArrayList<>();

        for(Documents doc: documents) {
            boolean match = true;

            if (ID != null && !ID.equals(doc.getID())) {
                match = false;
            }
            if (author != null && !author.toLowerCase().contains(doc.getAuthor().toLowerCase())) {
                match = false;
            }
            if (title != null && !title.toLowerCase().contains(doc.getTitle().toLowerCase())) {
                match = false;
            }
            if (type != null && !type.toLowerCase().equals(doc.getType().toLowerCase())) {
                match = false;
            }
            if (year != null && !year.equals(doc.getYear())) {
                match = false;
            }
            if (match) {
                docs.add(doc);
            }
        }
        return docs;
    }

    /**
     * Add an user.
     * @param newUser
     */
    public void AddUser(Users newUser) {
        for (Users user : users) {
            if (user.equals(newUser)) {
                return;
            }
        }
        newUser.setID(managementID.generateUserID());
        users.add(newUser);
        System.out.println("Add user succeed: " + newUser.getName());
    }

    /**
     * Remove an user.
     * @param u
     */
    public void RemoveUser(Users u) {
        Users userToRemove = null;
        for (Users user : users) {
            if (user.getID() == u.getID()) {
                userToRemove = user;
                break;
            }
        }
        if (userToRemove != null) {
            users.remove(userToRemove);
            managementID.recycleUserID(userToRemove.getID());
            System.out.println("Remove user succeed: " + userToRemove.getName());
        } else {
            System.out.println("User with ID " + u.getID() + " not found.");
        }
    }

    /**
     * Change an user.
     * @param User
     */
    public void AlterUser(Users User) {
        int index = users.indexOf(User);

        if (index != -1) {
            users.set(index, User);
            System.out.println("Alter user succeeded: " + User.getName());
        } else {
            System.out.println("user not found: " + User.getName());
        }
    }

    /**
     * Search an user.
     * @param ID
     * @param name
     * @param email
     * @param address
     * @param phone
     * @param role
     * @return
     */
    public List<Users> SearchUser(Integer ID, String name, String email, String address, String phone, String role) {
        List<Users> users = new ArrayList<>();

        for(Users user: users) {
            boolean match = true;

            if (ID != null && !ID.equals(user.getID())) {
                    match = false;
            }
            if (name != null && !name.toLowerCase().contains(user.getName().toLowerCase())) {
                    match = false;
            }
            if (email != null && !email.equals(user.getEmail())) {
                    match = false;
            }
            if (address != null && !address.toLowerCase().contains(user.getAddress().toLowerCase())) {
                    match = false;
            }
            if (phone != null && !phone.contains(user.getPhone().toLowerCase())) {
                    match = false;
            }
            if (match) {
                    users.add(user);
            }
        }
        return users;
    }

    /**
     * Setup borrow document.
     * @param user
     * @param doc
     */
    public void BorrowDocument(Users user, Documents doc) {
        if (doc.getQuantity() > 0) {
            doc.setQuantity(doc.getQuantity() - 1);
            BorrowRecord record = new BorrowRecord(user, doc);
            borrowRecords.add(record);
            System.out.println(" Borrow succeed: " + doc.getTitle());
        } else {
            System.out.println(" Borrow failed");
        }
    }

    /**
     * Update return document.
     * @param user
     * @param doc
     */
    public void returnDocument(Users user, Documents doc) {
        BorrowRecord recordToRemove = null;
        for (BorrowRecord record : borrowRecords) {
            if (record.getUsers().equals(user) && record.getDocuments().equals(doc)) {
                recordToRemove = record;
                break;
            }
        }
        if (recordToRemove != null) {
            recordToRemove.setReturnDate(new Date()); // Set the payment date to today
            borrowRecords.remove(recordToRemove); // remove record
            doc.setQuantity(doc.getQuantity() + 1); // increase quantity

            System.out.println(user.getName() + " return succeed: " + doc.getTitle());
        } else {
            System.out.println(" Can't found");
        }
    }
}

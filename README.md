# Library Management System

## Overview
The **Library Management System** is a Java-based application designed to simplify the management of library operations. The system allows administrators to manage books and users, handle borrowing activities, and streamline library workflows efficiently.

This project utilizes **JavaFX** for the user interface and integrates with a database for data persistence.

---

## Features

### 1. **Book Management**
   - Add, update, and delete book information.
   - Search for books by title, author, type or year of publication.
   - Display books in a paginated ListView for better navigation.

### 2. **User Management**
   - Add, update, and delete user accounts.
   - Manage user details such as name, email, address and phone number.

### 3. **Borrowing System**
   - Allow users to borrow books by entering their details in a pop-up dialog.
   - Track borrowed books and their return dates.
   - Generate QR codes for borrowed books for easy tracking.

### 4. **GoogleAPI**
   - Using Google API to provide a comprehensive solution for book information management, and other library related activities.
---

## Technologies Used

- **Programming Language**: Java
- **UI Framework**: JavaFX
- **Database**: MySQL (or any relational database system)
- **Libraries**:
  - `ZXing` for QR code generation.
  - `JUnit` for unit testing.

---

## Installation and Setup

### Prerequisites
- Java Development Kit (JDK) 23 or higher.
- MySQL or compatible database.
- An Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse.

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/Chacole/library-management-system.git
   ```
2. Open the project in your IDE.
3. Set up the database:
   - Create a MySQL database.
   - Import the SQL file `databasestructure.sql` and `insertdata.sql` located in the folder.
4. Configure database connection:
   - Update the `DatabaseConnection.java` file with your database username and password.
5. Run the project:
   - Execute the `Main.java` file to start the application.

---

## How to Use

### For Administrators
1. Log in to the system using admin credentials.
2. Navigate through the menu to manage books and users.
3. Use the borrowing system to handle user requests.

### For Users
1. Search for books and view their details.
2. Borrow books by providing the necessary information.

---

## Testing
- Unit tests are located in the `src/test` folder.
- Run tests using your IDE or Maven to ensure the system works as expected.

---

## Screenshots


---



# Author
- Trần Anh Tuấn - 23021710
- Nguyễn Thanh Tùng - 23021714
- Ngô Anh Tú - 23021702

# Additional Comments
The Library Management System offers a comprehensive suite of features found in a traditional library, including book borrowing and returning, maintaining detailed records of books and users, and real-time updates on book availability. It provides seamless functionality for searching, adding, and managing books effortlessly. Integrated with Google Books API, the system allows for easy retrieval of book information. Additionally, it includes a QR code system for quick access to book details. With its intuitive and visually appealing interface, the system ensures smooth and efficient operation, making it an ideal solution for modern library management.

# Reference

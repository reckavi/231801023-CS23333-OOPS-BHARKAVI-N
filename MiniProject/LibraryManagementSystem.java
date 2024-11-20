import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {
    private static final String URL = "jdbc:mysql://localhost:3306/libraryDB"; // URL to connect to MySQL
    private static final String USER = "root"; // MySQL username (default: root)
    private static final String PASSWORD = ""; // MySQL password (leave empty by default)

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Connect to the database
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database!");

            // SQL query to insert a new book
            String insertQuery = "INSERT INTO books (title, author, genre, publish_year, quantity) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
                
                // Ask the user to enter book details
                System.out.print("Enter Book Title: ");
                String title = scanner.nextLine();

                System.out.print("Enter Author Name: ");
                String author = scanner.nextLine();

                System.out.print("Enter Genre: ");
                String genre = scanner.nextLine();

                System.out.print("Enter Publish Year: ");
                int publishYear = scanner.nextInt();

                System.out.print("Enter Quantity: ");
                int quantity = scanner.nextInt();

                // Set the values in the query
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setString(3, genre);
                pstmt.setInt(4, publishYear);
                pstmt.setInt(5, quantity);

                // Execute the query to insert data into the database
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Book added successfully!");
                } else {
                    System.out.println("Failed to add the book.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }

        scanner.close();
    }
}

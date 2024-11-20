import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserRegistrationForm extends JFrame {

    private JTextField nameField, emailField, phoneField;
    private JPasswordField passwordField;
    private JButton submitButton;

    public UserRegistrationForm() {
        // Set up the JFrame
        setTitle("User Registration");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        // Create and add the form components
        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField();  // This field is now optional
        add(phoneField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        submitButton = new JButton("Submit");
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user input
                String name = nameField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String password = new String(passwordField.getPassword());

                // Validate input
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(UserRegistrationForm.this, "Please fill in all required fields.");
                    return;
                }

                // Set phone to 'N/A' if not provided
                if (phone.isEmpty()) {
                    phone = "N/A";  // Default value for phone if not provided
                }

                // Insert user data into the database
                try {
                    // Database connection
                    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/librarydb", "root", ""); // Update with your credentials
                    String query = "INSERT INTO users (name, email, phone, password) VALUES (?, ?, ?, ?)";
                    PreparedStatement pst = conn.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setString(2, email);
                    pst.setString(3, phone);  // Store the phone value
                    pst.setString(4, password);  // Store the password
                    pst.executeUpdate(); // Execute the insert query

                    // Close the connection
                    pst.close();
                    conn.close();

                    // Inform the user
                    JOptionPane.showMessageDialog(UserRegistrationForm.this, "User registered successfully!");

                    // Close the registration form
                    dispose();

                    // Open the Library Management System
                    new LibraryManagementSystemGUI().setVisible(true);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(UserRegistrationForm.this, "Error registering user: " + ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        // Create and display the registration form
        new UserRegistrationForm().setVisible(true);
    }
}

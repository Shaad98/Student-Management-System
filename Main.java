

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends JFrame implements ActionListener {
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;
    private JTextField addUsernameField;
    private JTextField addEmailField;
    private JPasswordField addPasswordField;
    private JTextField addIdField;
    private JTextArea displayArea;
    // private final String filePath = "users.csv";
    private DemoConnection dbConnection;

    public Main() {
        dbConnection = new DemoConnection();

        setTitle("Student Management System");
        setSize(600, 600);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        // Welcome Panel
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        JLabel welcomeLabel = new JLabel("Welcome to the Student Management System!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        welcomePanel.add(welcomeLabel, gbc);

        mainPanel.add(welcomePanel, "Welcome");

        // Login Panel
        JPanel loginPanel = new JPanel(new GridBagLayout());
        TitledBorder loginBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Login Page",
                TitledBorder.CENTER,
                TitledBorder.TOP
        );
        loginPanel.setBorder(loginBorder);
        GridBagConstraints loginGbc = new GridBagConstraints();
        loginGbc.insets = new Insets(5, 5, 5, 5);
        loginGbc.fill = GridBagConstraints.HORIZONTAL;

        loginGbc.gridx = 0;
        loginGbc.gridy = 0;
        loginPanel.add(new JLabel("Email:"), loginGbc);
        loginGbc.gridx = 1;
        loginEmailField = new JTextField(15);
        loginPanel.add(loginEmailField, loginGbc);

        loginGbc.gridy++;
        loginGbc.gridx = 0;
        loginPanel.add(new JLabel("Password:"), loginGbc);
        loginGbc.gridx = 1;
        loginPasswordField = new JPasswordField(15);
        loginPanel.add(loginPasswordField, loginGbc);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginGbc.gridy++;
        loginGbc.gridx = 1;
        loginPanel.add(loginButton, loginGbc);

        mainPanel.add(loginPanel, "Login");

        // Add User Panel
        JPanel addUserPanel = new JPanel(new GridBagLayout());
        TitledBorder addUserBorder = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Add User",
                TitledBorder.CENTER,
                TitledBorder.TOP
        );
        addUserPanel.setBorder(addUserBorder);
        GridBagConstraints addGbc = new GridBagConstraints();
        addGbc.insets = new Insets(5, 5, 5, 5);
        addGbc.fill = GridBagConstraints.HORIZONTAL;

        addGbc.gridx = 0;
        addGbc.gridy = 0;
        addUserPanel.add(new JLabel("Username:"), addGbc);
        addGbc.gridx = 1;
        addUsernameField = new JTextField(15);
        addUserPanel.add(addUsernameField, addGbc);

        addGbc.gridy++;
        addGbc.gridx = 0;
        addUserPanel.add(new JLabel("Email:"), addGbc);
        addGbc.gridx = 1;
        addEmailField = new JTextField(15);
        addUserPanel.add(addEmailField, addGbc);

        addGbc.gridy++;
        addGbc.gridx = 0;
        addUserPanel.add(new JLabel("Password:"), addGbc);
        addGbc.gridx = 1;
        addPasswordField = new JPasswordField(15);
        addUserPanel.add(addPasswordField, addGbc);

        addGbc.gridy++;
        addGbc.gridx = 0;
        addUserPanel.add(new JLabel("ID:"), addGbc);
        addGbc.gridx = 1;
        addIdField = new JTextField(15);
        addUserPanel.add(addIdField, addGbc);

        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(this);
        addGbc.gridy++;
        addGbc.gridx = 1;
        addUserPanel.add(addUserButton, addGbc);

        mainPanel.add(addUserPanel, "AddUser");

        // Display Users Panel
        JPanel displayUsersPanel = new JPanel(new BorderLayout());
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayUsersPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        mainPanel.add(displayUsersPanel, "DisplayUsers");

        // Menu Bar
        JMenuBar header = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menu.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JMenuItem loginMenuItem = new JMenuItem("Login");
        JMenuItem addUserMenuItem = new JMenuItem("Add User");
        JMenuItem displayUsersMenuItem = new JMenuItem("Display Users");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        loginMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addUserMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        displayUsersMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        exitMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        loginMenuItem.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        addUserMenuItem.addActionListener(e -> cardLayout.show(mainPanel, "AddUser"));
        displayUsersMenuItem.addActionListener(e -> {
            displayArea.setText(dbConnection.displayUsers());
            cardLayout.show(mainPanel, "DisplayUsers");
        });
        exitMenuItem.addActionListener(e -> System.exit(0));

        menu.add(loginMenuItem);
        menu.add(addUserMenuItem);
        menu.add(displayUsersMenuItem);
        menu.add(exitMenuItem);

        header.add(menu);

        add(header, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Login")) {
            String email = loginEmailField.getText();
            String password = new String(loginPasswordField.getPassword());
            if (dbConnection.authenticateUser(email, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                loginEmailField.setText("");
                loginPasswordField.setText("");
                cardLayout.show(mainPanel, "Welcome");
            } else {
                loginEmailField.setText("");
                loginPasswordField.setText("");
                JOptionPane.showMessageDialog(this, "Invalid credentials!");
            }
        } else if (e.getActionCommand().equals("Add User")) {
            String username = addUsernameField.getText();
            String email = addEmailField.getText();
            String password = new String(addPasswordField.getPassword());
            String id = addIdField.getText();
            if (isValidEmail(email)) {
                dbConnection.addUser(username, email, password, id);
                JOptionPane.showMessageDialog(this, "User added to Student Management System!");
                addUsernameField.setText("");
                addEmailField.setText("");
                addPasswordField.setText("");
                addIdField.setText("");
                cardLayout.show(mainPanel, "Welcome");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email format!");
            }
        }
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}


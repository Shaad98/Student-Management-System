

import java.sql.*;

class DemoConnection {
    private Connection conn;
    private Statement st;
    private PreparedStatement preInsert;
    private PreparedStatement preSelectLogin;
    private ResultSet rs;
    private final String url = "jdbc:mysql://localhost:3306/student";
    private final String username = "root";
    private final String password = ""; 
    private final String displayQuery = "SELECT username, id FROM student";
    private final String loginAuthentication = "SELECT * FROM student WHERE email = ? AND password = ?";

    public DemoConnection() {
        try {
            conn = DriverManager.getConnection(url, username, password);
            st = conn.createStatement();
            System.out.println("Connection is successful!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addUser(String username, String email, String password, String id) {
        String query = "INSERT INTO student (username, email, password, id) VALUES (?, ?, ?, ?)";
        try {
            preInsert = conn.prepareStatement(query);
            preInsert.setString(1, username);
            preInsert.setString(2, email);
            preInsert.setString(3, password);
            preInsert.setString(4, id);
            preInsert.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean authenticateUser(String email, String password) {
        try {
            preSelectLogin = conn.prepareStatement(loginAuthentication);
            preSelectLogin.setString(1, email);
            preSelectLogin.setString(2, password);
            rs = preSelectLogin.executeQuery();
            return rs.next();
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public String displayUsers() {
        StringBuilder userList = new StringBuilder();
        try {
            rs = st.executeQuery(displayQuery);
            while (rs.next()) {
                userList.append("Username: ").append(rs.getString(1)).append(", ID: ").append(rs.getString(2)).append("\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return userList.toString();
    }
}


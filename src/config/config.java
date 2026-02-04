package config;
import net.proteanit.sql.DbUtils;
import java.sql.*;
import javax.swing.JTable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class config {

    // ================= DATABASE CONNECTION =================
    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:users.db");
            System.out.println("Connection Successful");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
        return con;
    }

    // ================= ADD RECORD METHOD =================
    public void addRecord(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]);
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]);
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]);
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]);
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]);
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime()));
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]);
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]);
                } else {
                    pstmt.setString(i + 1, values[i].toString());
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }

    // ================= LOGIN =================
    public boolean loginUser(String email, String password) {
        String sql = "SELECT * FROM tbl_user WHERE email = ? AND password = ?";
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            return false;
        }
    }

    // ================= PASSWORD HASH =================
    public static String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.out.println("Error hashing password: " + e.getMessage());
            return null;
        }
    }

    public boolean authenticate(String sql, Object... values) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Login Error: " + e.getMessage());
        }
        return false;
    }

    public String getSingleValue(String sql, Object... values) {
        String result = null;
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setObject(i + 1, values[i]);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) result = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching value: " + e.getMessage());
        }
        return result;
    }

    public void executeSQL(String sql) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error executing SQL: " + e.getMessage());
        }
    }

    public void displayData(String sql, JTable table) {
        try (Connection conn = connectDB();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            table.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            System.out.println("Error displaying data: " + e.getMessage());
        }
    }

    // ================= ADD USER =================
    public void executeSQL(String sql, String name, String email, String phone, String password, String status, String role) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, password);
            pstmt.setString(5, status);
            pstmt.setString(6, role);

            pstmt.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            System.out.println("Error executing SQL: " + e.getMessage());
        }
    }

    // ================= UPDATE USER =================
    public void executeSQL(String sql, String name, String email, String phone, String password, String status, String role, Object userId) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, phone);
            pstmt.setString(4, password);
            pstmt.setString(5, status);
            pstmt.setString(6, role);
            pstmt.setObject(7, userId);

            pstmt.executeUpdate();
            System.out.println("User updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error executing SQL: " + e.getMessage());
        }
    }

    // ================= DELETE USER =================
    public void executeSQL(String sql, Object userId) {
        try (Connection conn = connectDB();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, userId);
            pstmt.executeUpdate();
            System.out.println("User deleted successfully!");
        } catch (SQLException e) {
            System.out.println("Error executing SQL: " + e.getMessage());
        }
    }

    // ================= UPDATE STATUS =================
    public void updateStatus(int userId, String newStatus) {
        String sql = "UPDATE tbl_user SET u_status = ? WHERE u_id = ?";

        try (Connection conn = connectDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, newStatus);
            pst.setInt(2, userId);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error updating status: " + e.getMessage());
        }
    }

    // ================= UPDATE ROLE =================
    public void updateRole(int userId, String newRole) {
          String sql = "UPDATE tbl_user SET u_role = ? WHERE u_id = ?";
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, newRole);
        pstmt.setInt(2, userId);

        pstmt.executeUpdate();
        System.out.println("User role updated successfully for user ID: " + userId);
    } catch (SQLException e) {
        System.out.println("Error updating role: " + e.getMessage());
    }
    }
    public ResultSet getUserProfileByEmail(String email) {
    String sql = "SELECT u_id, name, email, u_status, u_role FROM tbl_user WHERE email = ?";
    try {
        Connection conn = connectDB();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, email);
        return pst.executeQuery();
    } catch (SQLException e) {
        System.out.println("Error fetching profile: " + e.getMessage());
        return null;
    }
}

    public ResultSet executeQuery(String sql, String email, String hashedPass) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public ResultSet getData(String sql, String email, String password) {
    try {
        Connection conn = this.connectDB();
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, email);
        pst.setString(2, password);
        return pst.executeQuery();
    } catch (Exception e) {
        System.out.println("getData Error: " + e.getMessage());
        return null;
    }
}

    public ResultSet getData(String checkSql, String email) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

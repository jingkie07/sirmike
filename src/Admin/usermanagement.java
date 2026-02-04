/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import config.config;
import javax.swing.*;
 

/**
 *
 * @author Jingkie
 */
public class usermanagement extends javax.swing.JFrame {
  config cfg = new config();
  
    /**
     * Creates new form usermanagement
     */
public usermanagement() {
        initComponents();
        loadUsers();

        jButton1.addActionListener(e -> addUser());
        jButton2.addActionListener(e -> updateUser());
        jButton3.addActionListener(e -> deleteUser());
    }
   private void loadUsers() {
    String sql = "SELECT name, email, u_id, phone, password, u_status, u_role FROM tbl_user";
    cfg.displayData(sql, jTable1);
}

private void addUser() {
   // Input fields
    JTextField nameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField phoneField = new JTextField();
    JTextField passwordField = new JTextField();

    // Role and Status dropdowns
    String[] roleOptions = {"Customer", "Admin"};
    JComboBox<String> roleBox = new JComboBox<>(roleOptions);

    String[] statusOptions = {"Active", "Inactive"};
    JComboBox<String> statusBox = new JComboBox<>(statusOptions);

    Object[] message = {
        "Name:", nameField,
        "Email:", emailField,
        "Phone:", phoneField,
        "Password:", passwordField,
        "Status:", statusBox,
        "Role:", roleBox
    };

    int option = JOptionPane.showConfirmDialog(this, message, "Add User", JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText();
        String role = (String) roleBox.getSelectedItem();
        String status = (String) statusBox.getSelectedItem();

        // Validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        // Insert into database
        String sql = "INSERT INTO tbl_user (name, email, phone, password, u_status, u_role) VALUES (?, ?, ?, ?, ?, ?)";
        cfg.executeSQL(sql, name, email, phone, password, role, status);
        JOptionPane.showMessageDialog(this, "User added successfully!");
        loadUsers();
    }
}

private void updateUser() {
   int row = jTable1.getSelectedRow();

    // 🔒 Validation: must select a row
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Please select a user to update.");
        return;
    }

    // 🔒 Safely get values from table
    int userId = Integer.parseInt(jTable1.getValueAt(row, 2).toString());

    String name = jTable1.getValueAt(row, 0) != null
            ? jTable1.getValueAt(row, 0).toString() : "";

    String email = jTable1.getValueAt(row, 1) != null
            ? jTable1.getValueAt(row, 1).toString() : "";

    String phone = jTable1.getValueAt(row, 3) != null
            ? jTable1.getValueAt(row, 3).toString() : "";

    String password = jTable1.getValueAt(row, 4) != null
            ? jTable1.getValueAt(row, 4).toString() : "";

    String status = jTable1.getValueAt(row, 5) != null
            ? jTable1.getValueAt(row, 5).toString() : "Active";

    String role = jTable1.getValueAt(row, 6) != null
            ? jTable1.getValueAt(row, 6).toString() : "Customer";

    // 🧾 Input fields
    JTextField nameField = new JTextField(name);
    JTextField emailField = new JTextField(email);
    JTextField phoneField = new JTextField(phone);
    JTextField passwordField = new JTextField(password);

    // 🔽 Status ComboBox
    JComboBox<String> statusBox = new JComboBox<>(new String[]{"Active", "Inactive"});
    statusBox.setSelectedItem(status);

    // 🔽 Role ComboBox
    JComboBox<String> roleBox = new JComboBox<>(new String[]{"Customer", "Admin"});
    roleBox.setSelectedItem(role);

    Object[] message = {
        "Name:", nameField,
        "Email:", emailField,
        "Phone:", phoneField,
        "Password:", passwordField,
        "Status:", statusBox,
        "Role:", roleBox
    };

    int option = JOptionPane.showConfirmDialog(
            this,
            message,
            "Update User",
            JOptionPane.OK_CANCEL_OPTION
    );

    if (option == JOptionPane.OK_OPTION) {

        // ✏️ Get updated values
        String newName = nameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newPhone = phoneField.getText().trim();
        String newPassword = passwordField.getText().trim();
        String newStatus = statusBox.getSelectedItem().toString();
        String newRole = roleBox.getSelectedItem().toString();

        // 🔒 Validation
        if (newName.isEmpty() || newEmail.isEmpty() || newPhone.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        // 💾 Update database
        String sql = "UPDATE tbl_user SET name=?, email=?, phone=?, password=?, u_status=?, u_role=? WHERE u_id=?";

        cfg.executeSQL(
                sql,
                newName,
                newEmail,
                newPhone,
                newPassword,
                newStatus,
                newRole,
                userId
        );

        JOptionPane.showMessageDialog(this, "User updated successfully!");
        loadUsers();
    }

}
private void deleteUser() {
     int row = jTable1.getSelectedRow();

    if (row >= 0) {
        Object userId = jTable1.getValueAt(row, 2); // Correct column for u_id
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete User ID: " + userId + "?",
            "Confirm",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tbl_user WHERE u_id = ?"; // Parameterized query
            cfg.executeSQL(sql, userId); // Call your config method
            JOptionPane.showMessageDialog(this, "User deleted");
            loadUsers(); // Refresh table
        }
    } else {
        JOptionPane.showMessageDialog(this, "Select a user to delete");
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(153, 153, 153));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton4.setText("Back");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 440, 107, 40));

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton2.setText("Update User");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 350, -1, 39));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton1.setText("Add User");
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, 107, 36));

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jButton3.setText("Delete User");
        jPanel2.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 35));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/car.png"))); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 124));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 167, 570));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 570, 370));

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(527, 123, 190, 30));

        jButton5.setFont(new java.awt.Font("Times New Roman", 1, 12)); // NOI18N
        jButton5.setText("Search");
        jPanel1.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 130, -1, -1));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("USER TABLE");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(385, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 890, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 960, 580));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       
    new Admindashboard().setVisible(true); // Open Admin Dashboard
    this.dispose(); // Close User Management window// TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(usermanagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new usermanagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables


    private void User() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.service;

import com.mycompany.absensi_rfid.object.User;
import com.mycompany.absensi_rfid.DAO.GenericDAO;
import com.mycompany.absensi_rfid.util.SecurityUtils;
import com.mongodb.client.model.Filters;
import java.awt.Frame;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

/**
 *
 * @author MyBook Hype AMD
 */
public class AuthService {
    private final GenericDAO<User> userDAO = new GenericDAO<>("users", User.class);
    public void login(String username, String plainPassword, javax.swing.JFrame LoginPage){
        String hashedInput = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);
         User user = userDAO.findOne(Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", hashedInput)
        ));

        if (user != null) {
            // Update lastLogin
            user.setLastLogin(LocalDateTime.now());
            userDAO.update(Filters.eq("username", username), user);

            JOptionPane.showMessageDialog(null, "Selamat Datang, " + user.getFullname());
            
            // Buka JFrame Dashboard Anda (sesuaikan nama class)
            // Dashboard dashboard = new Dashboard();
            // dashboard.setLocationRelativeTo(null);
            // dashboard.setVisible(true);
            // dashboard.setExtendedState(Frame.MAXIMIZED_BOTH);
            LoginPage.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Username atau Password Salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void registerUser(String fullname, String username, String plainPassword) {
        String hashedPassword = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);
        User newUser = new User(fullname, username, hashedPassword, null);
        try {
            userDAO.save(newUser);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal mendaftarkan user: " + e.getMessage());
        }
    }
}

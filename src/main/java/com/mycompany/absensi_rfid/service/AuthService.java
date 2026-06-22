/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.service;

import com.mycompany.absensi_rfid.object.Admin;
import com.mycompany.absensi_rfid.DAO.GenericDAO;
import com.mycompany.absensi_rfid.util.SecurityUtils;
import com.mycompany.absensi_rfid.Dashboard;
import com.mongodb.client.model.Filters;
import java.awt.Frame;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;

public class AuthService {

    private final GenericDAO<Admin> adminDAO = new GenericDAO<>("admin", Admin.class);

    public void login(String username, String plainPassword, javax.swing.JFrame loginPage) {
        // Hash password input
        String hashedInput = SecurityUtils.getHash(plainPassword, SecurityUtils.SHA_256);

        Admin admin = adminDAO.findOne(Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", hashedInput)
        ));
        

        if (admin != null) {

            JOptionPane.showMessageDialog(null, "Selamat Datang, " + admin.getNama());
            Dashboard dashboard = new Dashboard(admin);
            dashboard.setLocationRelativeTo(null);
            dashboard.setVisible(true);
            dashboard.setExtendedState(Frame.MAXIMIZED_BOTH);
            loginPage.setVisible(false);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Username atau Password Salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
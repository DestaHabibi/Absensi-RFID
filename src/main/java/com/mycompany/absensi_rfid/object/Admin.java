/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.object;

import java.time.LocalDateTime;

/**
 *
 * @author MyBook Hype AMD
 */
public class Admin {
    private String nama;
    private String username;
    private String password;
    private String divisi;
    private String status;
    private String id_admin;
    private LocalDateTime lastLogin;
    
    public Admin(){
        
    }
    
    public Admin(String nama,String username, String divisi, String password, String status, String id_admin, LocalDateTime lastLogin) {
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.divisi = divisi;
        this.status = status;
        this.id_admin = id_admin;
        this.lastLogin = lastLogin;
    }
    
    @Override
    public String toString() {
        return "admin{" +
                "nama='" + nama + '\'' +
                ", username='" + username + '\'' +
                ", divisi='" + divisi + '\'' +
                ", status='" + status + '\'' +
                ", id_admin='" + id_admin + '\'' +
                ", lastLogin='" + lastLogin + '\''+
                '}';
    }

    // Getter dan Setter
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
        public String getDivisi() {
        return divisi;
    }

    public void setDivisi(String divisi) {
        this.divisi = divisi;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getId_admin() {
        return id_admin;
    }

    public void setIdAdmin(String id_admin) {
        this.id_admin = id_admin;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(LocalDateTime lasLogin) {
        this.lastLogin = lasLogin;
    }
}

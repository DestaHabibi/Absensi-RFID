/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.object;

/**
 *
 * @author MyBook Hype AMD
 */
public class Karyawan {
    private String nama;
    private String username;
    private String divisi;
    private String status;
    private String id_karyawan;
    
    public Karyawan(){
        
    }
    
    public Karyawan(String nama, String username, String divisi, String status, String id_karyawan) {
    this.nama = nama;
    this.username = username;
    this.divisi = divisi;
    this.status = status;
    this.id_karyawan = id_karyawan;
    }
    
        @Override
    public String toString() {
        return "karyawan{" +
                "nama='" + nama + '\'' +
                ", username='" + username + '\'' +
                ", divisi='" + divisi + '\'' +
                ", status='" + status + '\'' +
                ", id_admin='" + id_karyawan + '\'' +
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getId_karyawan() {
        return id_karyawan;
    }

    public void setId_karyawan(String id_karyawan) {
        this.id_karyawan = id_karyawan;
    }
    
    public String rfid_tag;
    public String getRfidTag() {return rfid_tag; }
    public void setRfidTag(String rfid_tag) {this.rfid_tag = rfid_tag; }
    
}

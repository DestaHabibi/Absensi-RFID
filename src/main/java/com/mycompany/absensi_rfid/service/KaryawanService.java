/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.service;

import com.mycompany.absensi_rfid.object.Karyawan;
import com.mycompany.absensi_rfid.DAO.GenericDAO;
import com.mongodb.client.model.Filters;
import com.mycompany.absensi_rfid.panels.PanelDashboard;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.JOptionPane;
import org.bson.conversions.Bson;

/**
 *
 * @author MyBook Hype AMD
 */
public class KaryawanService {
    private final GenericDAO<Karyawan> DAO;
    
    public KaryawanService(){
        this.DAO = new GenericDAO<>("karyawan", Karyawan.class);
        
    }
    public void tampilKaryawan(DefaultTableModel tableModel, String key){
        List<Karyawan> daftar;
        
        if (key.isEmpty()) {
            daftar = DAO.findAll();
        } else {
            Bson filter = Filters.or(
                Filters.regex("nama", key, "i"),
                Filters.regex("id_karyawan", key, "i")
            );
            daftar = DAO.findMany(filter);
        }
        tableModel.setRowCount(0);
        
        for (Karyawan m : daftar) {
            Object[] row = {
                m.getId_karyawan(),
                m.getNama(),
                m.getDivisi(),
            };
            tableModel.addRow(row);
        }
    }
    
    public void simpanKaryawan(Karyawan k) {
        try {
            System.out.println("Menyimpan: " + k.toString());
            DAO.save(k);
            System.out.println("Berhasil disimpan! ");
            JOptionPane.showMessageDialog(null, "Data karyawan berhasil ditambahkan!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Simpan: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void updateDashboardStats(JLabel lblTotal) {
        try {
            List<Karyawan> daftar = DAO.findAll();
            
            lblTotal.getParent().revalidate();
            lblTotal.getParent().repaint();

            int total = daftar.size();

            lblTotal.setText(String.valueOf(total));
        } catch (Exception e) {
            lblTotal.setText("0");
            e.printStackTrace();
        }
    }
    
    public List<Karyawan> cariKaryawan(String key) {
        List<Bson> filters = new ArrayList<>();
        // Get all fields from the Karyawan class
        for (Field field : Karyawan.class.getDeclaredFields()) {
            if (field.getName().equals("id_karyawan")) {
                continue;
            }
            filters.add(Filters.regex(field.getName(), key, "i"));
        }
        List<Karyawan> results = DAO.findMany(Filters.or(filters));
        return results;
    }
    
    public void updateKaryawan(String idLama, Karyawan newK) {
        try {
            Bson filter = Filters.eq("id_karyawan", idLama);
            Bson update = com.mongodb.client.model.Updates.combine(
                com.mongodb.client.model.Updates.set("id_karyawan", newK.getId_karyawan()),
                com.mongodb.client.model.Updates.set("nama", newK.getNama()),
                com.mongodb.client.model.Updates.set("divisi", newK.getDivisi())
            );
            DAO.updateFields(filter, update);
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal update: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void hapusKaryawan(String idK) {
        Bson filter = Filters.eq("id_karyawan", idK);
        DAO.delete(filter); // Menggunakan deleteOne [6]
        PanelDashboard.showData("");
        JOptionPane.showMessageDialog(null, "Data karyawan berhasil dihapus.");
    }
    
    
}

package com.mycompany.absensi_rfid.service;
 
import com.mycompany.absensi_rfid.object.Karyawan;
import com.mycompany.absensi_rfid.DAO.GenericDAO;
import com.mycompany.absensi_rfid.Dialog.EditKaryawan;
import com.mycompany.absensi_rfid.panels.PanelDashboard;
import com.mongodb.client.model.Filters;
import com.mycompany.absensi_rfid.karyawan.KaryawanCard;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.bson.conversions.Bson;
 
public class KaryawanService {
 
    private final GenericDAO<Karyawan> DAO;
 
    public KaryawanService() {
        this.DAO = new GenericDAO<>("karyawan", Karyawan.class);
    }
 
    // =====================================================================
    // 1. SAVE: Simpan karyawan baru ke MongoDB
    // =====================================================================
    public void simpanKaryawan(Karyawan karyawanBaru) {
        DAO.save(karyawanBaru);
    }
 
    // =====================================================================
    // 2. READ: Tampilkan semua karyawan dalam bentuk grid panel
    // =====================================================================
    public void tampilKaryawan(JPanel panelTarget, String key) {
    List<Karyawan> daftarKaryawan;
    if (key.isEmpty()) {
        daftarKaryawan = DAO.findAll();
    } else {
        daftarKaryawan = cariKaryawan(key);
    }

    panelTarget.removeAll();
    panelTarget.setLayout(new BorderLayout());
    panelTarget.setBackground(new Color(37, 44, 88));

    JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
    gridPanel.setOpaque(false);
    gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    if (daftarKaryawan.isEmpty()) {
        JLabel lblKosong = new JLabel("Tidak ada data karyawan ditemukan.", JLabel.CENTER);
        lblKosong.setForeground(Color.WHITE);
        lblKosong.setFont(new Font("Arial", Font.ITALIC, 14));
        panelTarget.add(lblKosong, BorderLayout.CENTER);
    } else {
        for (Karyawan k : daftarKaryawan) {
            gridPanel.add(KaryawanCard.buildCard(k)); // pakai KaryawanCard
        }
        panelTarget.add(gridPanel, BorderLayout.NORTH);
    }

    panelTarget.revalidate();
    panelTarget.repaint();
}
 
    // =====================================================================
    // 3. UPDATE: Perbarui data karyawan
    // =====================================================================
    public void updateKaryawan(String idLama, Karyawan newK) {
        Bson filter = Filters.eq("id_karyawan", idLama);
        Karyawan k = DAO.findOne(filter);
        if (k != null) {
            DAO.update(filter, newK);
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        } else {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan!");
        }
    }
 
    // =====================================================================
    // 4. DELETE: Hapus data karyawan
    // =====================================================================
    public void hapusKaryawan(String idK) {
        Bson filter = Filters.eq("id_karyawan", idK);
        DAO.delete(filter);
        JOptionPane.showMessageDialog(null, "Data karyawan berhasil dihapus.");
    }
 
    // =====================================================================
    // 5. SEARCH: Cari karyawan berdasarkan keyword
    // =====================================================================
    public List<Karyawan> cariKaryawan(String key) {
        List<Bson> filters = new ArrayList<>();
        for (Field field : Karyawan.class.getDeclaredFields()) {
            if (field.getName().equals("id_karyawan")) {
                continue;
            }
            filters.add(Filters.regex(field.getName(), key, "i"));
        }
        return DAO.findMany(Filters.or(filters));
    }
 
    // =====================================================================
    // 6. Update label statistik total karyawan di dashboard
    // =====================================================================
    public void updateDashboardStats(JLabel lblTotal) {
        try {
            List<Karyawan> daftar = DAO.findAll();
            int total = daftar.size();
            lblTotal.setText(String.valueOf(total));
            lblTotal.getParent().revalidate();
            lblTotal.getParent().repaint();
        } catch (Exception e) {
            lblTotal.setText("0");
            e.printStackTrace();
        }
    }
    public Karyawan findByUid(String hashedUid) {
        Bson filter = Filters.eq("rfidTag", hashedUid);
        return DAO.findOne(filter);
    }
}
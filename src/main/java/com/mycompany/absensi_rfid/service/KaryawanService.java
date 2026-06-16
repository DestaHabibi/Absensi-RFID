package com.mycompany.absensi_rfid.service;
 
import com.mycompany.absensi_rfid.object.Karyawan;
import com.mycompany.absensi_rfid.DAO.GenericDAO;
import com.mycompany.absensi_rfid.Dialog.EditKaryawan;
import com.mycompany.absensi_rfid.panels.PanelDashboard;
import com.mongodb.client.model.Filters;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
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
        // Ambil data dari MongoDB
        List<Karyawan> daftarKaryawan;
        if (key.isEmpty()) {
            daftarKaryawan = DAO.findAll();
        } else {
            daftarKaryawan = cariKaryawan(key);
        }
 
        // Bersihkan panel target sebelum memuat data baru
        panelTarget.removeAll();
        panelTarget.setLayout(new BorderLayout());
        panelTarget.setBackground(new Color(37, 44, 88));
 
        // Buat grid panel: 3 kolom, jarak antar card 10px
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
        try {
            for (Karyawan k : daftarKaryawan) {
                // Card panel: 4 baris (ID, Nama, Divisi, tombol aksi)
                JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 5));
                cardPanel.setBackground(new Color(43, 121, 221));
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(230, 231, 237), 1, true),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
 
                JLabel lblID = new JLabel("ID: " + k.getId_karyawan());
                lblID.setForeground(Color.WHITE);
                lblID.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
 
                JLabel lblNama = new JLabel("Nama: " + k.getNama());
                lblNama.setForeground(Color.WHITE);
                lblNama.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
 
                JLabel lblDivisi = new JLabel("Divisi: " + k.getDivisi());
                lblDivisi.setForeground(new Color(200, 230, 255));
                lblDivisi.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 11));
 
                // Panel tombol Edit & Hapus
                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 8, 0));
                controlPanel.setBackground(new Color(43, 121, 221));
 
                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setBackground(new Color(255, 153, 0));
                tombolEdit.setForeground(Color.WHITE);
                tombolEdit.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 11));
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolEdit.setBorderPainted(false);
                tombolEdit.addActionListener((ActionEvent e) -> {
                    // Passing seluruh object Karyawan ke dialog edit (persis seperti dosen)
                    EditKaryawan dialog = new EditKaryawan(null, true);
                    dialog.setDataEdit(k);
                    dialog.setVisible(true);
                    PanelDashboard.showData("");
                });
 
                JButton tombolHapus = new JButton("Hapus");
                tombolHapus.setBackground(new Color(255, 0, 51));
                tombolHapus.setForeground(Color.WHITE);
                tombolHapus.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 11));
                tombolHapus.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolHapus.setBorderPainted(false);
                tombolHapus.addActionListener((ActionEvent e) -> {
                    Object[] options = {"Ya, Hapus", "Batal"};
                    int choice = JOptionPane.showOptionDialog(
                            null,
                            "Apakah Anda yakin ingin menghapus " + k.getNama() + "?",
                            "Konfirmasi Hapus",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                    switch (choice) {
                        case JOptionPane.YES_OPTION -> {
                            hapusKaryawan(k.getId_karyawan());
                            PanelDashboard.showData("");
                        }
                        case JOptionPane.NO_OPTION ->
                            System.out.println("User memilih: Batal");
                        default -> {
                        }
                    }
                });
 
                controlPanel.add(tombolEdit);
                controlPanel.add(tombolHapus);
 
                cardPanel.add(lblID);
                cardPanel.add(lblNama);
                cardPanel.add(lblDivisi);
                cardPanel.add(controlPanel);
 
                gridPanel.add(cardPanel);
            }
 
            if (daftarKaryawan.isEmpty()) {
                JLabel lblKosong = new JLabel("Tidak ada data karyawan ditemukan.", JLabel.CENTER);
                lblKosong.setForeground(Color.WHITE);
                lblKosong.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 14));
                panelTarget.add(lblKosong, BorderLayout.CENTER);
            } else {
                panelTarget.add(gridPanel, BorderLayout.NORTH);
            }
 
        } catch (Exception e) {
            e.printStackTrace();
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
}
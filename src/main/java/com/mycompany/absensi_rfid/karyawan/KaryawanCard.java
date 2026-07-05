/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.karyawan;

import com.mycompany.absensi_rfid.object.Karyawan;
import com.mycompany.absensi_rfid.Dialog.EditKaryawan;
import com.mycompany.absensi_rfid.panels.PanelDashboard;
import com.mycompany.absensi_rfid.service.KaryawanService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import com.mycompany.absensi_rfid.service.I18nService;
import java.text.MessageFormat;
/**
 *
 * @author MyBook Hype AMD
 */
public class KaryawanCard {
    public static JPanel buildCard(Karyawan k) {

        // Card utama: 4 baris (ID, Nama, Divisi, tombol aksi)
        JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 5));
        cardPanel.setBackground(new Color(43, 121, 221));
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 231, 237), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblID = new JLabel(I18nService.get("ui.card.id") + k.getId_karyawan());
        lblID.setForeground(Color.WHITE);
        lblID.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel lblNama = new JLabel(I18nService.get("ui.card.name") + k.getNama());
        lblNama.setForeground(Color.WHITE);
        lblNama.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel lblDivisi = new JLabel(I18nService.get("ui.card.division") + k.getDivisi());
        lblDivisi.setForeground(new Color(200, 230, 255));
        lblDivisi.setFont(new Font("Arial", Font.ITALIC, 11));

        // Panel tombol Edit & Hapus
        JPanel controlPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        controlPanel.setBackground(new Color(43, 121, 221));

        JButton tombolEdit = new JButton(I18nService.get("ui.card.edit"));
        tombolEdit.setBackground(new Color(255, 153, 0));
        tombolEdit.setForeground(Color.WHITE);
        tombolEdit.setFont(new Font("Arial", Font.BOLD, 11));
        tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tombolEdit.setBorderPainted(false);
        tombolEdit.addActionListener((ActionEvent e) -> {
            EditKaryawan dialog = new EditKaryawan(null, true);
            dialog.setDataEdit(k);
            dialog.setVisible(true);
            PanelDashboard.showData("");
        });

        JButton tombolHapus = new JButton(I18nService.get("ui.card.delete"));
        tombolHapus.setBackground(new Color(255, 0, 51));
        tombolHapus.setForeground(Color.WHITE);
        tombolHapus.setFont(new Font("Arial", Font.BOLD, 11));
        tombolHapus.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tombolHapus.setBorderPainted(false);
        tombolHapus.addActionListener((ActionEvent e) -> {
                Object[] options = {I18nService.get("ui.card.confirmyes"), I18nService.get("ui.card.confirmno")};
                    String message = MessageFormat.format(I18nService.get("ui.card.deletemessage"), k.getNama());
                    int choice = JOptionPane.showOptionDialog(
                            null,
                            message,
                            I18nService.get("ui.card.deletetitle"),
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );
                if (choice == JOptionPane.YES_OPTION) {
                    KaryawanService service = new KaryawanService();
                    service.hapusKaryawan(k.getId_karyawan());
                    PanelDashboard.showData("");
                }
        });

        controlPanel.add(tombolEdit);
        controlPanel.add(tombolHapus);

        cardPanel.add(lblID);
        cardPanel.add(lblNama);
        cardPanel.add(lblDivisi);
        cardPanel.add(controlPanel);

        return cardPanel;
    }
}

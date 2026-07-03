/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.service;

/**
 *
 * @author lenovo
 */

import com.mycompany.absensi_rfid.DAO.GenericDAO;
import com.mycompany.absensi_rfid.object.LogAbsensi;
import java.time.LocalDateTime;
import java.util.UUID;

public class LogAbsensiService {
    private final GenericDAO<LogAbsensi> logDAO = new GenericDAO<>("log_absensi", LogAbsensi.class);
    
    public void simpanLog(String hashedUid, String status) {
        LogAbsensi log = new LogAbsensi(
        UUID.randomUUID().toString(),
        hashedUid,
        LocalDateTime.now(),
        status
        );
        logDAO.save(log);
    }
}

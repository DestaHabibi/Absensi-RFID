/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.koneksi;

import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 *
 * @author MyBook Hype AMD
 */
public class TesKoneksi {
    public static void main(String[] args) {
        try {
            System.out.println("Mohon tunggu, sedang menghubungkan ke database");
            
            MongoDatabase database = MongoManager.getDatabase();
            
            Document ping = new Document("ping", 1);
            database.runCommand(ping);
            
            System.out.println("-------------------------");
            System.out.println("Status: Connection Successfull!");
            System.out.println("Connect to DataBase: " + database.getName());
            System.out.println("-------------------------");
            
            System.out.println("Daftar Collection pada " + database.getName() + ":");
            for(String name : database.listCollectionNames()) {
                System.out.println("-" + name);
            }
            
        } catch (Exception e) {
            System.err.println("----------------------");
            System.err.println("Status: Connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("----------------------");
        }
    };
}

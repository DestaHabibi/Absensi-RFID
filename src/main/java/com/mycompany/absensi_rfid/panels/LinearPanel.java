/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.absensi_rfid.panels;


import java.awt.*;
import javax.swing.JPanel;

public class LinearPanel extends JPanel {

    private int radius = 20;
    private Color color1 = new Color(91, 164, 207);
    private Color color2 = new Color(46, 109, 164);
    private boolean horizontal = true;

    public LinearPanel() {
        setOpaque(false);
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        repaint();
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
        repaint();
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
        repaint();
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gradient;
        if (horizontal) {
            gradient = new GradientPaint(0, 0, color1, getWidth(), 0, color2);
        } else {
            gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        }

        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}

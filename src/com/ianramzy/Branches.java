// Class that opens a window where you can change a branch or 'tree'
// The tree branches are drawn through recursion
// Ian Ramzy
// Version 1.0

package com.ianramzy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Branches extends JFrame implements MouseListener, ItemListener {
    int x = 940;
    int y = 700;
    int num = 1;
    int newAngle = 10;
    int thickness;
    float length = 30;
    float factor;
    JLabel L1, L2, L4, L5;
    JSlider sliderBranches = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);  //ranges and default values for sliders
    JSlider sliderAngle = new JSlider(JSlider.HORIZONTAL, 5, 180, 10);
    JSlider sliderLength = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
    JSlider sliderThickness = new JSlider(JSlider.HORIZONTAL, 0, 10, 2);
    JCheckBox chkbox1, chkbox2, chkbox3;
    Font roboto = new Font("Roboto", Font.BOLD, 14);

    public Branches() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel paneltwo = new JPanel();
        paneltwo.setLayout(new GridLayout(6, 2));
        JPanel panelthree = new JPanel();
        panelthree.setLayout(new GridLayout(3, 1));

        L1 = new JLabel("Number of Recursions");
        L1.setFont(roboto);
        L2 = new JLabel("Branch Angle");
        L2.setFont(roboto);
        L4 = new JLabel("Branch  Length");
        L4.setFont(roboto);
        L5 = new JLabel("Branch  Thickness");
        L5.setFont(roboto);

        chkbox1 = new JCheckBox("Rainbow");
        chkbox1.setFont(roboto);
        chkbox2 = new JCheckBox("Progressively Shorter");
        chkbox2.setFont(roboto);
        chkbox3 = new JCheckBox("Progressively Thinner");
        chkbox3.setFont(roboto);
        chkbox1.addItemListener(this);
        chkbox2.addItemListener(this);
        chkbox3.addItemListener(this);
        System.out.println();

        panel.add(paneltwo, BorderLayout.WEST);
        paneltwo.setBackground(Color.LIGHT_GRAY);
        panelthree.setBackground(Color.LIGHT_GRAY);
        panel.setBackground(Color.GRAY);

        paneltwo.add(sliderBranches);
        paneltwo.add(L1);
        paneltwo.add(sliderAngle);
        paneltwo.add(L2);
        paneltwo.add(sliderLength);
        paneltwo.add(L4);
        paneltwo.add(sliderThickness);
        paneltwo.add(L5);
        panelthree.add(chkbox1);
        panelthree.add(chkbox2);
        panelthree.add(chkbox3);
        paneltwo.add(panelthree);

        sliderBranches.setMinorTickSpacing(1);      //Set up slider for branches
        sliderBranches.setMajorTickSpacing(10);
        sliderBranches.setPaintTicks(true);
        sliderBranches.setPaintLabels(true);
        sliderBranches.setLabelTable(sliderBranches.createStandardLabels(10));
        sliderBranches.setSnapToTicks(true);

        sliderAngle.setMinorTickSpacing(5);         //Set up slider for angle
        sliderAngle.setMajorTickSpacing(30);
        sliderAngle.setPaintTicks(true);
        sliderAngle.setPaintLabels(true);
        sliderAngle.setLabelTable(sliderAngle.createStandardLabels(25));
        sliderAngle.setSnapToTicks(true);

        sliderLength.setMinorTickSpacing(5);        //Set up slider for length
        sliderLength.setMajorTickSpacing(10);
        sliderLength.setPaintTicks(true);
        sliderLength.setPaintLabels(true);
        sliderLength.setLabelTable(sliderLength.createStandardLabels(10));
        sliderLength.setSnapToTicks(true);

        sliderThickness.setMinorTickSpacing(1);     //Set up slider for thickness
        sliderThickness.setMajorTickSpacing(5);
        sliderThickness.setPaintTicks(true);
        sliderThickness.setPaintLabels(true);
        sliderThickness.setLabelTable(sliderLength.createStandardLabels(5));
        sliderThickness.setSnapToTicks(true);

        this.setVisible(true);
        this.setContentPane(panel);

        sliderAngle.addMouseListener(this);
        sliderLength.addMouseListener(this);
        sliderBranches.addMouseListener(this);
        sliderThickness.addMouseListener(this);
    }

    public static void main(String[] args) {
        new Branches();
    }

    public void branch(Graphics g, int num, int x, int y, float length, int angle, int angleChange, float factor) {
        if (num == 0) {
            return;
        }
        thickness = sliderThickness.getValue();

        int x2 = (int) (x - (length * Math.cos(angle * Math.PI / 180)));
        int y2 = (int) (y - (length * Math.sin(angle * Math.PI / 180)));
        g.drawLine(x, y, x2, y2);

        if (chkbox2.isSelected()) {   // change length by recursion
            length = (float) (length * (0.8));
        }

        branch(g, num - 1, x2, y2, length, angle + angleChange, angleChange, factor);

        if (chkbox1.isSelected()) {   // change color by recursion on branch
            float hue = (factor * num); //hue
            float saturation = 1.0f; //saturation
            float brightness = 1.0f; //brightness
            g.setColor(Color.getHSBColor(hue, saturation, brightness));
        }
        thickness = sliderThickness.getValue();

        if (chkbox3.isSelected()) {  //change thickness by recursion
            thickness = (num - 1);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(thickness));
        }

        branch(g, num - 1, x2, y2, length, angle - angleChange, angleChange, factor);
    }

    public void paint(Graphics d) {
        d.setColor(Color.red);        //default color for drawing
        super.paint(d);
        if (chkbox3.isSelected()) {  //change thickness by recursion
            thickness = (num - 1);
        }

        if (chkbox1.isSelected()) {   // change color by recursion
            float hue = (factor * num); //hue
            float saturation = 1.0f; //saturation
            float brightness = 1.0f; //brightness
            d.setColor(Color.getHSBColor(hue, saturation, brightness));
        }

        num = sliderBranches.getValue();  //get slider values
        length = sliderLength.getValue();
        newAngle = sliderAngle.getValue();
        thickness = sliderThickness.getValue();

        Graphics2D d2 = (Graphics2D) d;
        d2.setStroke(new BasicStroke(thickness));

        this.setVisible(true);
        factor = (0.5f / num);
        branch(d, num, x, y, length, 90, newAngle, factor);   //call first branch
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();
        if (source == chkbox1) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                repaint();
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                repaint();
            }
        }
        if (source == chkbox2) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                repaint();
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                repaint();
            }
        }
        if (source == chkbox3) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                repaint();
            }
            if (e.getStateChange() == ItemEvent.DESELECTED) {
                repaint();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
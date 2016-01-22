package PJ1;

/**
 * Created by chohee Kim on 10/2/15.
 */

import java.awt.event.ActionEvent;
import java.util.*;

import PJ1.*;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * 
 */
public class PJ1_Test_GUI extends JFrame implements ActionListener {

    private JTextField xtf1 = new JTextField(8);
    private JTextField ytf1 = new JTextField(8);
    private JTextField xtf2 = new JTextField(8);
    private JTextField ytf2 = new JTextField(8);


    public PJ1_Test_GUI() {

        this.setLayout(new FlowLayout());

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(5, 2, 5, 5));
        p2.add(new JLabel("Numerator 1: "));
        p2.add(xtf1);
        p2.add(new JLabel("Denominator 1 : "));
        p2.add(ytf1);
        p2.add(new JLabel("Numerator 2: "));
        p2.add(xtf2);
        p2.add(new JLabel("Denominator 2 : "));
        p2.add(ytf2);


        this.add(p2);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(5, 3));

        makeButton(p1, "0) exit");
        makeButton(p1, "1) add");
        makeButton(p1, "2) subtract");
        makeButton(p1, "3) multiply");
        makeButton(p1, "4) divide");
        makeButton(p1, "5) compareTo");
        makeButton(p1, "6) equals");
        makeButton(p1, "7) reciprocal");
        makeButton(p1, "8) toDouble");
        makeButton(p1, "9) setFraction");

        this.add(p1);

        p1.setBorder(new TitledBorder("Press operation "));


    }

    /**
     * This method adds a button on the parameter p and Once an action is made it is added on an Action Listener method.
     * @param p
     * @param t
     */
    public void makeButton(JPanel p, String t) {

        JButton b = new JButton(t);
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        p.add(b);
        b.addActionListener(this);
    }


    public static void main(String[] args) {


        PJ1_Test_GUI frame = new PJ1_Test_GUI();
        frame.setTitle("Project #1 GUI");
        frame.setSize(600, 300);
        frame.setVisible(true);
    }

    /**
     * This method finds if text fields for number are empty or not. If it is change data type to int and return the variable.
     * @param jtf
     * @return input number when there is one, otherwise 0.
     */
    public static int textFieldInt(JTextField jtf) {

        if (jtf.getText().length() > 0) {
            return Integer.parseInt(jtf.getText());
        }

        return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton bButton = (JButton) e.getSource();

        /*  try-catch catches exception that is thrown at Fraction class in a situation that denominator is 0.
            As well as the exception for not having any variables as needed. For example, for multiply operation it needs two fraction.
        */
        try {

            int a = textFieldInt(xtf1);
            int b = textFieldInt(ytf1);
            int c = textFieldInt(xtf2);
            int d = textFieldInt(ytf2);

            Fraction f1, f2;
            String resultS = "";


            f1 = new Fraction(a, b);


            if (bButton.getText().equals("0) exit")) {

                System.exit(0);

            } else if (bButton.getText().equals("1) add")) {

                f2 = new Fraction(c, d);
                resultS = f1.add(f2).toString();

            } else if (bButton.getText().equals("2) subtract")) {

                f2 = new Fraction(c, d);
                resultS = f1.subtract(f2).toString();

            } else if (bButton.getText().equals("3) multiply")) {

                f2 = new Fraction(c, d);
                resultS = f1.multiply(f2).toString();

            } else if (bButton.getText().equals("4) divide")) {

                f2 = new Fraction(c, d);
                resultS = f1.divide(f2).toString();

            } else if (bButton.getText().equals("5) compareTo")) {

                f2 = new Fraction(c, d);
                resultS = f1.compareTo(f2) + "";

            } else if (bButton.getText().equals("6) equals")) {

                f2 = new Fraction(c, d);
                resultS = f1.equals(f2) + "";

            } else if (bButton.getText().equals("7) reciprocal")) {
                resultS = f1.getReciprocal().toString();

            } else if (bButton.getText().equals("8) toDouble")) {
                resultS = "" + f1.toDouble();
                System.out.println("hello");
            } else {

                System.out.printf("DEBUG: %s\n", bButton.getText());
            }

            JFrame resultFrame = new JFrame();
            resultFrame.setSize(200, 150);
            resultFrame.setLocationRelativeTo(null);
            resultFrame.setVisible(true);
            resultFrame.add(new JLabel(" Result : " + resultS));

        } catch (FractionException fe) {

            JFrame alertFrame = new JFrame();
            alertFrame.setSize(300, 200);
            alertFrame.setLocationRelativeTo(null);
            alertFrame.setVisible(true);
            alertFrame.add(new JLabel("The denominator can't be 0."));

        } catch (NumberFormatException n) {
            JFrame alertFrame = new JFrame();
            alertFrame.setSize(300, 200);
            alertFrame.setLocationRelativeTo(null);
            alertFrame.setVisible(true);
            alertFrame.add(new JLabel("Invalid Number"));
        }
    }
}

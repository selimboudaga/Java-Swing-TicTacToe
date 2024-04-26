package tictactoeclient;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Victory extends JFrame {
    public Victory() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420,600);
        this.setResizable(false);
        JPanel mainpanel=new JPanel();
        mainpanel.setLayout(null);
        mainpanel.setBackground(new java.awt.Color(33, 36, 65));
        ImageIcon trophy=new ImageIcon(Objects.requireNonNull(getClass().getResource("trophy.png")));

        JLabel image=new JLabel(trophy);
        image.setBounds(50,50,300,500);
        mainpanel.add(image);
        this.add(mainpanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Victory v=new Victory();
    }
}

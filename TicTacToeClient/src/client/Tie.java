package client;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Tie extends JFrame {
    public Tie() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420,380);
        this.setResizable(false);
        JPanel mainpanel=new JPanel();
        mainpanel.setLayout(null);
        mainpanel.setBackground(new java.awt.Color(33, 36, 65));
        JLabel draw=new JLabel("Draw!");
        draw.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,38));
        draw.setForeground(Color.white);
        draw.setBounds(143,40,300,50);
        mainpanel.add(draw);
        ImageIcon trophy=new ImageIcon(Objects.requireNonNull(getClass().getResource("tie.png")));
        JLabel image=new JLabel(trophy);
        image.setBounds(50,-60,300,500);
        mainpanel.add(image);
        JLabel tiemsg=new JLabel("Its a tie! Both players did good");
        tiemsg.setForeground(Color.white);
        tiemsg.setFont(new Font("Arial Rounded MT",Font.PLAIN,16));
        tiemsg.setBounds(100,283,400,25);
        mainpanel.add(tiemsg);
        this.add(mainpanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Tie t=new Tie();
    }
}

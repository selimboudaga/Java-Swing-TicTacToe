package client;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Victory extends JFrame {
    public Victory() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420,420);
        this.setResizable(false);
        JPanel mainpanel=new JPanel();
        mainpanel.setLayout(null);
        mainpanel.setBackground(new java.awt.Color(33, 36, 65));
        JLabel winner=new JLabel("Victory!");
        winner.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,38));
        winner.setForeground(Color.white);
        winner.setBounds(120,40,300,50);
        mainpanel.add(winner);
        ImageIcon trophy=new ImageIcon(Objects.requireNonNull(getClass().getResource("trophy.png")));
        JLabel image=new JLabel(trophy);
        image.setBounds(50,-50,300,500);
        mainpanel.add(image);
        JLabel winnermsg=new JLabel("Congrats champion you won the game");
        winnermsg.setForeground(Color.white);
        winnermsg.setFont(new Font("Arial Rounded MT",Font.PLAIN,16));
        winnermsg.setBounds(60,315,400,25);
        mainpanel.add(winnermsg);
        this.add(mainpanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Victory v=new Victory();
    }
}

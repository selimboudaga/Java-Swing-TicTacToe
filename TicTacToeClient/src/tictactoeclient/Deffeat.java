package tictactoeclient;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Deffeat extends  JFrame{
    public Deffeat() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(420,380);
        this.setResizable(false);
        JPanel mainpanel=new JPanel();
        mainpanel.setLayout(null);
        mainpanel.setBackground(new java.awt.Color(33, 36, 65));
        JLabel loser=new JLabel("Deffeat!");
        loser.setFont(new Font("Arial Rounded MT Bold",Font.BOLD,38));
        loser.setForeground(Color.white);
        loser.setBounds(120,40,300,50);
        mainpanel.add(loser);
        ImageIcon trophy=new ImageIcon(Objects.requireNonNull(getClass().getResource("loser1.png")));
        JLabel image=new JLabel(trophy);
        image.setBounds(50,-60,300,500);
        mainpanel.add(image);
        JLabel losermsg=new JLabel("You lost the game,try again !");
        losermsg.setForeground(Color.white);
        losermsg.setFont(new Font("Arial Rounded MT",Font.PLAIN,16));
        losermsg.setBounds(100,283,400,25);
        mainpanel.add(losermsg);
        this.add(mainpanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Deffeat d=new Deffeat();
    }
}

// Assignment 3, Tic Tac Toe Client/Server
// Name: Cory Siebler
// StudentID: 1000832292
// Lecture Topic: 9 - Networking
// Description: 
package client;

// Test class for Tic-Tac-Toe client.
import javax.swing.JFrame;


public class Test {

    public static void main(String[] args) {
        Client application; // declare client application

        // Check for command line args
        if (args.length == 0) {
            application = new Client("127.0.0.1"); // localhost
        } else {
            application = new Client(args[0]); // use args
        }

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

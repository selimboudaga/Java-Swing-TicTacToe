
package tictactoeclient;

// Client side of client/server Tic-Tac-Toe program.
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.net.InetAddress;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.util.Formatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author caustin
 * @author csiebler
 */
public final class TicTacToeClient extends JFrame implements Runnable {

    private final JTextField idField; // textfield to display player's mark
    private final JTextArea displayArea; // JTextArea to display output
    private final JTextArea chatArea; // JTextArea to display chat
    private final JTextField chatfield;
    private final JPanel boardPanel; // panel for tic-tac-toe board
    private final JPanel panel2; // panel to hold board
    private final Square[][] board; // tic-tac-toe board
    private Square currentSquare; // current square
    private Socket connection; // connection to server
    private Scanner input; // input from server
    private Formatter output; // output to server
    private final String ticTacToeHost; // host name for server
    private String myMark; // this client's mark
    private boolean myTurn; // determines which client's turn it is
    private  String X_MARK = "X"; // mark for first client

    private final String O_MARK = "O"; // mark for second client
    private boolean win;



    // set up user-interface and board
    public TicTacToeClient(String host) {

        ticTacToeHost = host; // set name of server

        JPanel gamepanel=new JPanel();
        gamepanel.setBounds(0,0,800,800);
        gamepanel.setBackground(new java.awt.Color(33, 36, 65));
        gamepanel.setLayout(null);
        add(gamepanel);
        //chat Area styling
        chatArea=new JTextArea(4,30);
        chatArea.setEditable(false);
        Border chatborder=new RoundedBorder(10,new java.awt.Color(21, 22, 40));
        chatArea.setBorder(chatborder);
        chatArea.setBackground(new java.awt.Color(21, 22, 40));
        chatArea.setForeground(Color.white);
        chatArea.setFont(new Font("Arial",Font.PLAIN,14));
        JScrollPane chatscroll = new JScrollPane(chatArea);
        chatscroll.getVerticalScrollBar().setUI(new MyCustomScrollBarUI());
        chatscroll.setBorder(BorderFactory.createLineBorder(new java.awt.Color(33, 36, 65))); // Set custom border color
        chatscroll.setBackground(new java.awt.Color(21, 22, 40)); // Set background color
        chatscroll.setBounds(40,80,420,200);
        gamepanel.add(chatscroll);

        chatfield=new JTextField();
        Border chatfieldborder=new RoundedBorder(1,Color.white);
        chatfield.setBorder(chatfieldborder);
        chatfield.setBackground(new java.awt.Color(21, 22, 40));
        chatfield.setForeground(Color.white);
        chatfield.setFont(new Font("Arial",Font.PLAIN,14));
        chatfield.setBounds(40,280,420,40);
        gamepanel.add(chatfield);


        //display Area Styling
        displayArea = new JTextArea(4, 30); // set up JTextArea
        displayArea.setEditable(false);
        Border mainborder=new RoundedBorder(10,new java.awt.Color(21, 22, 40));
        displayArea.setBorder(mainborder);

        displayArea.setBackground(new java.awt.Color(21, 22, 40));
        displayArea.setForeground(Color.white);
        displayArea.setFont(new Font("Arial",Font.PLAIN,14));

        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.getVerticalScrollBar().setUI(new MyCustomScrollBarUI());
        scrollPane.setBorder(BorderFactory.createLineBorder(new java.awt.Color(33, 36, 65))); // Set custom border color
        scrollPane.setBackground(new java.awt.Color(21, 22, 40)); // Set background color
        scrollPane.setBounds(40,332,420,80);
        gamepanel.add(scrollPane);
        //add(scrollPane, BorderLayout.SOUTH);


        boardPanel = new JPanel(); // set up panel for squares in board
        boardPanel.setLayout(new GridLayout(3, 3, 10, 10));
        boardPanel.setBackground(new java.awt.Color(33, 36, 65));
        board = new Square[3][3]; // create board


        // loop over the rows in the board
        for (int row = 0; row < 3; row++) {
            // loop over the columns in the board
            for (int column = 0; column < 3; column++) {
                // create square
                board[row][column] = new Square(" ", row * 3 + column);
                boardPanel.add(board[row][column]); // add square
            }
        }


        idField = new JTextField(); // set up textfield
        idField.setEditable(false);
        idField.setForeground(Color.white);
        idField.setBackground(new java.awt.Color(33, 36, 65));
        idField.setBorder(new RoundedBorder(0,new java.awt.Color(33, 36, 65)));
        idField.setFont(new Font("Arial",Font.BOLD,19));
        idField.setBounds(380,30,800,20);
        gamepanel.add(idField);
        //add(idField, BorderLayout.NORTH);

        panel2 = new JPanel(); // set up panel to contain boardPanel
        panel2.setBackground(new java.awt.Color(33, 36, 65));
        panel2.add(boardPanel, BorderLayout.CENTER); // add board panel
        panel2.setBounds(420,82,500,500);
        gamepanel.add(panel2);
        //add(panel2, BorderLayout.CENTER); // add container panel

        setSize(890, 480); // set size of window
        setResizable(false);
        setVisible(true); // show window

        startClient();
    }

    // start the client thread
    public void startClient() {
        // connect to server and get streams
        try {
            // make connection to server
            connection = new Socket(InetAddress.getByName(ticTacToeHost), 12345);

            // get streams for input and output
            input = new Scanner(connection.getInputStream());
            output = new Formatter(connection.getOutputStream());
        } catch (IOException ioException) {
            System.out.println(ioException.toString());
        }

        // create and start worker thread for this client
        ExecutorService worker = Executors.newFixedThreadPool(1);
        worker.execute(this); // execute client
    }

    /**
     * The Player’s run method controls the information that is sent to and
     * received from the client.
     */
    @Override
    public void run() {
        myMark = input.nextLine(); // get player's mark (X or O)

        SwingUtilities.invokeLater(() -> {
            // display player's mark
            idField.setText("You are player \"" + myMark + "\"!");
        });

        myTurn = (myMark.equals("X")); // determine if client's turn

        // receive messages sent to client and output them
        while (true) {
            if (input.hasNextLine()) {
                processMessage(input.nextLine());
            }
        }
    }

    // process messages sent to the client
    private void processMessage(String message) {
        // valid move occurred
        switch (message) {
            case "Valid move.":
                displayMessage("Valid move, please wait.\n");
                setMark(currentSquare, myMark); // set mark in square
                break;
            case "Invalid move, try again":
                displayMessage(message + "\n"); // display invalid move
                myTurn = true; // still this client's turn
                break;
            case "Opponent moved":
                int location = input.nextInt(); // get move location
                input.nextLine(); // skip newline after int location
                int row = location / 3; // calculate row
                int column = location % 3; // calculate column
                setMark(board[row][column],
                        (myMark.equals("X") ? O_MARK : X_MARK)); // mark move
                displayMessage("Opponent moved. Your turn.\n");
                myTurn = true; // now this client's turn
                break;
            case "DEFEAT":
                // Display the message for the winner only
                displayMessage(message + "\n"); // display the message
                myTurn = false;
                new Victory(); // Launch the Victory GUI for the winner
                dispose();
                break;
            case "TIE":
                new Tie();
                dispose();
                break;
            case "VICTORY":
                // For other outcomes, just display the message
                displayMessage(message + "\n"); // display the message
                myTurn = false;
                new Deffeat();
                dispose();
                break;
            default:
                displayMessage(message + "\n"); // display the message
                break;
        }
    }

    // manipulate displayArea in event-dispatch thread
    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(() -> {
            displayArea.append(messageToDisplay); // updates output
        });
    }

    // utility method to set mark on board in event-dispatch thread
    private void setMark(final Square squareToMark, final String mark) {
        SwingUtilities.invokeLater(() -> {

            squareToMark.setMark(mark); // set mark in square
        });
    }

    // send message to server indicating clicked square
    public void sendClickedSquare(int location) {
        // if it is my turn
        if (myTurn) {
            output.format("%d\n", location); // send location to server
            output.flush();
            myTurn = false; // not my turn any more
        }
    }

    // set current Square
    public void setCurrentSquare(Square square) {
        currentSquare = square; // set current square to argument
    }




    // private inner class for the squares on the board
    private class Square extends JPanel {

        private String mark; // mark to be drawn in this square
        private final int location; // location of square



        public Square(String squareMark, int squareLocation) {

            mark = squareMark; // set mark for this square
            location = squareLocation; // set location of this square
            this.setBackground(new java.awt.Color(21, 22, 40));
            Border mainborder=new RoundedBorder(10,new java.awt.Color(21,22,40));
            this.setBorder(mainborder);



            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    setCurrentSquare(Square.this); // set current square

                    // send location of this square
                    sendClickedSquare(getSquareLocation());
                    if (mark.equals("X")) {
                        System.out.println("x CLICKE");

                    } else if (mark.equals("O")) {
                        //g.setColor(new Color(222, 91, 77)); // Set color for O mark
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    Border mainborder=new RoundedBorder(10,Color.white);
                    setBorder(mainborder);

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    Border mainborder=new RoundedBorder(10,new java.awt.Color(21,22,40));
                    setBorder(mainborder);

                }
            });
        }

        // return preferred size of Square
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(101, 101); // return preferred size
        }

        // return minimum size of Square
        @Override
        public Dimension getMinimumSize() {
            return getPreferredSize(); // return preferred size
        }

        // set mark for Square
        public void setMark(String newMark) {
            mark = newMark; // set mark of square
            repaint(); // repaint square
        }

        // return Square location
        public int getSquareLocation() {
            return location; // return location of square
        }

        // draw Square
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Set rendering hints for text anti-aliasing
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            g.drawRect(0, 0, 100, 100); // draw square
            if (mark.equals("X")) {
                g.setColor(Color.white); // Set color for X mark
                Border mainborder=new RoundedBorder(10,new java.awt.Color(34, 55, 115));
                setBackground(new java.awt.Color(24, 41, 71));
                setBorder(mainborder);
            } else if (mark.equals("O")) {
                g.setColor(new Color(222, 91, 77)); // Set color for O mark
                Border mainborder=new RoundedBorder(10,new java.awt.Color(222, 91, 77));
                setBackground(new java.awt.Color( 63, 35, 49));
                setBorder(mainborder);
            }
            g.setFont(new Font("Arial", Font.PLAIN, 32)); // Set font for X and O marks
            g.drawString(mark, 40, 60); // draw mark
        }


    }

    public class RoundedBorder implements Border {
        private int radius;
        private Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();

            // Set border color
            g2d.setColor(color);
            // Draw rounded border
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }
    }

    class MyCustomScrollBarUI extends BasicScrollBarUI {
        private final int THUMB_SIZE = 8; // Adjust this value for the desired scrollbar width

        @Override
        protected JButton createDecreaseButton(int orientation) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0)); // Hides the decrease button
            return button;
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0)); // Hides the increase button
            return button;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
            g.setColor(new java.awt.Color(33, 36, 65));
            g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);

            // Remove any border drawing
            ((Graphics2D) g).setStroke(new BasicStroke(0)); // Set a stroke with 0 width
            g.setColor(new java.awt.Color(33, 36, 65)); // Set the track color again to cover any remaining border
            g.drawRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height); // Draw an empty rectangle to cover any remaining border
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();

            // Set rendering hints for smooth border
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);


            g2.setColor(Color.white);
            // Adjust the border radius for a smoother curve
            int arc = THUMB_SIZE / 2;
            g2.fillRoundRect(thumbBounds.x, thumbBounds.y, THUMB_SIZE, thumbBounds.height, 8, 8);

            // Remove the border by not drawing it
            // You can also set a stroke with a 0 width to achieve the same effect
            // g2.setColor(Color.white); // Set the border color
            // g2.setStroke(new BasicStroke(0)); // Set a stroke with 0 width

            g2.dispose();
        }



        @Override
        protected void setThumbBounds(int x, int y, int width, int height) {
            // Adjust thumb bounds for the desired width
            super.setThumbBounds(x, y, THUMB_SIZE, height);
        }
    }



}

# Tic-Tac-Toe Game ❌⭕

This is a simple client-server based Tic-Tac-Toe game implemented in Java. The server hosts the game, and two clients can connect to it to play against each other.
- The project has about 4 files : 
- One for the server code, the server code is based on sockets threads and locks  
- One for the server main method 
- One for the client code, the client code is based on sockets swing GUI 
- And finanlly a file for the client main method 

## Table of Contents
- [Getting Started](#getting-started)
- [Prerequisites](#prerequisites)
- [Running the Server](#running-the-server)
- [Running the Client](#running-the-client)
- [Quick Tutorial](#Quick-Tutorial)
- [Contributing](#contributing)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

You will need to have Java Development Kit (JDK) installed on your machine. you can run it on Vscode , Inttellijidea, eclipse...

## Cloning the project 

Clone the repository to your local machine.
    ```sh
    git clone https://github.com/your-username/tictactoe-game.git
    cd tictactoe-game
    ```
## Running the Server

 Compile the server code in your JDK and run it ,just compile and run the servertest file  

The server will start and wait for clients to connect.

## Running the Client
 Compile the client code in you JDK and run it, just compile and run the clienttest file  

When prompted for the server address, enter the IP address or hostname of the machine running the server. If the server is running on the same machine, you can use `localhost`.

If you are testing the projet on your local machine without putting the ip address of the other player, you have to compile and run 2 client instances so u can start playing/testing 

## Quick Tutorial

Follow these steps to run the Tic-Tac-Toe game:

### Step 1: Run the TicTacToeServerTest file

First, start the server by running the `TicTacToeServerTest` file:

![Server Awaiting Connections](https://github.com/selimboudaga/Java-Swing-TicTacToe/assets/159728726/9eaf6053-e9b6-419e-8a57-38a20e8ea093)

### Step 2: Run the TicTacToeClientTest file twice

Next, run the `TicTacToeClientTest` file twice to start two client instances. Make sure you allow multiple instances for the client while running:

- Client for one of the players ,(Player X's view):

![Player X Connected](https://github.com/selimboudaga/Java-Swing-TicTacToe/assets/159728726/95e6f2f2-a693-4a05-8dae-99c4eba59665)

### Step 3: Start Playing

Once both clients are connected, you can start playing the game. Enjoy!

- Gameplay Screen (Player X's view):

![Gameplay Player X](https://github.com/selimboudaga/Java-Swing-TicTacToe/assets/159728726/755e647e-84a6-497a-a838-bb8738862616)

## Contributing

If you would like to contribute to the project, please fork the repository and use a feature branch. Pull requests are warmly welcome.

1. Fork the repository.
2. Create a feature branch (`git checkout -b feature/your-feature-name`).
3. Commit your changes (`git commit -am 'Add some feature'`).
4. Push to the branch (`git push origin feature/your-feature-name`).
5. Create a new Pull Request.

## THank you for your attention have a good day Dont forget to give us a "STAR" ⭐️

import java.net.*;
import java.io.*;

public class Player {

    int playerNum;
    int numGames;
    String[] submissions;
    int countSub;
    int score = 0;
    String myChoice;
    int syncCount;
    public static void main(String[] args) {
        
        int playerNum = Integer.parseInt(args[0]);
        int numGames = Integer.parseInt(args[1]);
        Player p = new Player(playerNum, numGames);
        p.start();
    }

    public Player(int playerNum, int numGames) {
        this.playerNum = playerNum;
        this.numGames = numGames;
        submissions = new String[2];
        countSub = 0;
        syncCount = 0;
    }

    public synchronized void submit(String message) {
        submissions[countSub] = message;
        countSub++;
        if(countSub == 2) {
            handleGame();
            submissions = new String[2];
            countSub = 0;
        }
    }

    private void handleGame() {
        String win = null;
        int tempScore = 0;
        if(myChoice.equalsIgnoreCase("ROCK")) {
            win = "SCISSORS";
        }
        else if(myChoice.equalsIgnoreCase("PAPER")) {
            win = "ROCK";
        }
        else if(myChoice.equalsIgnoreCase("SCISSORS")) {
            win = "PAPER";
        }
        for (String opp : submissions) {
            if(opp.equalsIgnoreCase(win)) {
                tempScore++;
            }
        }
        System.out.println("Player " + playerNum + " scored " + tempScore + " points");
        score += tempScore;   
    }

    public void start() {
        System.out.println("Starting up player" + playerNum + ". Playing " + numGames + " games!" );

        int port = 5554 + playerNum;
        ServerSocket server = null;
        IncomingConnection[] clients = new IncomingConnection[2];
        OutgoingConnection[] servers = new OutgoingConnection[2];
        int numClients = 0;

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(playerNum == 1) {
            while(numClients < 2) {
                Socket temp;
                try {
                    temp = server.accept();
                    clients[numClients] = new IncomingConnection(temp, this);
                    clients[numClients].start();
                    numClients++;
                } catch (SocketTimeoutException s) {
                    System.out.println("Socket timed out!");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }

            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            servers[0] = new OutgoingConnection(2);
            servers[1] = new OutgoingConnection(3); 

        }

        if(playerNum == 2) {
            Socket temp;
            try {
                temp = server.accept();
                clients[numClients] = new IncomingConnection(temp, this);
                clients[numClients].start();
                numClients++;
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            servers[0] = new OutgoingConnection(1);
            servers[1] = new OutgoingConnection(3);

            try {
                temp = server.accept();
                clients[numClients] = new IncomingConnection(temp, this);
                clients[numClients].start();
                numClients++;
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(playerNum == 3) {
            servers[0] = new OutgoingConnection(1);
            servers[1] = new OutgoingConnection(2);

            while(numClients < 2) {
                Socket temp;
                try {
                    temp = server.accept();
                    clients[numClients] = new IncomingConnection(temp, this);
                    clients[numClients].start();
                    numClients++;
                } catch (SocketTimeoutException s) {
                    System.out.println("Socket timed out!");
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }

        String[] choices = {"ROCK", "PAPER", "SCISSORS"};
        int roundsPlayed = 0;
        int choice = (int)(Math.random()*3);
        myChoice = choices[choice];


        while(roundsPlayed++ < numGames) {
            System.out.println("Round: " + roundsPlayed);

            sendToAll(servers, myChoice);

            try {
                Thread.sleep(150);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            choice = (int)(Math.random()*3);
            myChoice = choices[choice];
        }

        System.out.println("My final score (Player" + playerNum + "): " + score);
        sendToAll(servers, "QUIT");

        /*
        IncomingConnection playerServer;
        OutgoingConnection playerClient;

        if(playerNum == 1) {
            Socket temp;
            try {
                temp = server.accept();
                playerServer = new IncomingConnection(temp);
                playerServer.start();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }

            playerClient = new OutgoingConnection(2);
            playerClient.send("Hello!");
            playerClient.send("Goodbye!");
            playerClient.send("QUIT");

            playerClient.close();

        }

        else if(playerNum == 2) {
            playerClient = new OutgoingConnection(1);
            playerClient.send("Hello!");
            
            Socket temp;
            try {
                temp = server.accept();
                playerServer = new IncomingConnection(temp);
                playerServer.start();
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
            } catch (IOException e) {
                e.printStackTrace();
            }

            playerClient.send("Goodbye!");
            playerClient.send("QUIT");
            playerClient.close();
        }
        */
        
    }

    public void synchronize(OutgoingConnection[] servers) {
        try {
            Thread.sleep(500);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        sendToAll(servers, "SYNCHRONIZE");

        while(syncCount < 2) {
        }
        syncCount -= 2;
        System.out.println("Synchronized");
    }

    public void sendToAll(OutgoingConnection[] servers, String message) {
        for (OutgoingConnection server : servers) {
            server.send(message);
        }
    }

    //class representing a client connected to us
    protected class IncomingConnection extends Thread {
        private Socket server;
        Player p;
        public BufferedReader input;
        public DataOutputStream output;
   
        public IncomingConnection(Socket socket, Player p) throws IOException {
            this.server = socket;
            this.p = p;
        }

        public void run() {
            boolean connected = true;
            System.out.println("Recieved connection from: " + server.getRemoteSocketAddress());
            try {
                input = new BufferedReader(new InputStreamReader(server.getInputStream()));
                output = new DataOutputStream(server.getOutputStream());
            } catch(IOException e) {
                e.printStackTrace();
            }
            while(connected) {
                String message = null;
                message = listenRecieve();
                if(message != null) {
                    if(message.equals("QUIT")) {
                        System.out.println("Closing connection with " + server.getRemoteSocketAddress());
                        connected = false;
                    }
                    else if(message.equalsIgnoreCase("SYNCHRONIZE")) {
                        p.syncCount++;
                    }
                    else {
                        System.out.println("Player " + p.playerNum + ": " + p.myChoice + " vs. " + "Opponent: " + message);
                        p.submit(message);
                    }
                }

            }
            try {
                server.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        
        public String listenRecieve() {
            String message = null;
            try {
                String line = input.readLine();
                message = line;
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return message;
        }
    }

    //represents an outgoing connection to another server
    protected class OutgoingConnection {
        Socket client;
        public PrintWriter output;
        public BufferedReader input;

        public OutgoingConnection(int playerNum) {
            try {
                String serverName = "localhost";
                int port = 5554 + playerNum;
                System.out.println("Connecting to " + serverName + " on port " + port);
                client = new Socket(serverName, port);
                
                System.out.println("Just connected to " + client.getRemoteSocketAddress());
                OutputStream outToServer = client.getOutputStream();
                output = new PrintWriter(outToServer);
                
                InputStream inFromServer = client.getInputStream();
                input = new BufferedReader(new InputStreamReader(inFromServer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String message) {
            output.println(message);
            //output.writeUTF(message + "\n");
            output.flush();
        }

        public void close() {
            try {
                Thread.sleep(1000);
                client.close();
            } catch(IOException|InterruptedException e) {
                e.printStackTrace();
            } 
        }
    }
}
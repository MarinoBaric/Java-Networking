import java.io.*;
import java.net.*;
import java.util.*;

public class GuessGameServer {

    private static final int PORT = 54321;
    private static List<PrintWriter> allClients = new ArrayList<>();
    private static int secretNumber;
    private static boolean gameEnded = false;
    private static Object lock = new Object();
    

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server started on port " + PORT);
        secretNumber = new Random().nextInt(100) + 1;

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ClientHandler(clientSocket).start();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private Scanner in;
        private String clientName;
        

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());

                // TODO: Step 1 - Read client name
                this.clientName = in.nextLine();
                // TODO: Step 2 - Add this client's PrintWriter to allClients (synchronized)
                synchronized(allClients){
                    allClients.add(out);
                }
                // TODO: Step 3 - While game not ended and input available, process GUESS messages
              
                // TODO: Step 4 - Respond with TOO LOW / TOO HIGH / CORRECT
                // TODO: Step 5 - If correct, broadcast END message and set gameEnded = true

                while(in.hasNextLine()){
                    
                    String msg = in.nextLine();

                    if(msg.startsWith("GUESS: ")){

                        int guessedNumber = Integer.parseInt(msg.substring(7).trim());

                        synchronized(lock){
                            if(gameEnded == true){
                                break;
                            } else if(guessedNumber < secretNumber){
                                out.println("TOO LOW!");
                                out.flush();
                            } else if(guessedNumber > secretNumber){
                                out.println("TOO HIGH");
                                out.flush();
                            } else{
                                gameEnded = true;
                                broadcast("CORRECT: " + clientName);
                                broadcast("END: " + clientName + " has guessed the number!");
                            }
                        }

                    }

                    
                }
            } catch (IOException e) {
                System.out.println("Client error: " + e.getMessage());
            } finally {
                // TODO: Close streams and remove client from allClients if needed
                in.close();
                out.close();
            }
        }

        private void broadcast(String message) {
            synchronized (lock) {
                for (PrintWriter pw : allClients) {
                    pw.println(message);
                    pw.flush();
                }
            }
        }
    }
}

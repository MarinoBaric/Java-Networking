import java.io.*;
import java.net.*;
import java.util.*;

public class ChatGameServer {

    private static final int PORT = 54321;
    private static final List<PrintWriter> clients = new ArrayList<>();
    private static final Object lock = new Object();
    private static String secretWord;
    private static boolean gameOver = false;

    public static void main(String[] args) throws IOException {
        String[] words = {"apple", "java", "socket", "thread"};
        secretWord = words[new Random().nextInt(words.length)];
        System.out.println("Secret word selected. Server is running on port " + PORT);

        ServerSocket server = new ServerSocket(PORT);
        while (true) {
            Socket socket = server.accept();
            new ClientHandler(socket).start();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private Scanner in;
        private String name;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream());
                in = new Scanner(socket.getInputStream());

                // TODO: Read name from client
                // TODO: Add out to client list (synchronized)
                // TODO: While input exists and game not over:
                //       - If input matches secretWord, broadcast WINNER and END
                //       - Else broadcast "<name>: <msg>"

                this.name = in.nextLine();

                synchronized(clients){
                    clients.add(out);
                }

                while(in.hasNextLine()){
                    String input = in.nextLine();
                    
                    synchronized(lock){
                        if(gameOver == true) break;

                        if(input.equals(secretWord)){
                            gameOver = true;
                            broadcast("WINNER: " + this.name);
                            broadcast("END: The secret word was: " + secretWord);
                            break;
                        } else{
                            broadcast(this.name + ": " + input);
                        }
                    }

                }

            } catch (IOException e) {
                System.out.println("Client error: " + e.getMessage());
            } finally {
                // TODO: Close socket and remove from clients list
                
                try {
                    in.close();
                    out.close();
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                synchronized(clients){
                    clients.remove(out);
                }
                
            }
        }

        private void broadcast(String message) {
            synchronized (lock) {
                for (PrintWriter pw : clients) {
                    pw.println(message);
                    pw.flush();
                }
            }
        }
    }
}

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GuessGameClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 54321);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream());
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = keyboard.nextLine();
        out.println(name);
        out.flush();

        // TODO: Start thread to listen for server messages
        // TODO: In main thread, let user enter guesses (numbers) and send GUESS:<number>

        // TODO: Close everything when "END:" message is received or user types "exit"
        
        Thread threadReader = new Thread(() -> {
            while (in.hasNextLine()) {
                String serverMsg = in.nextLine();
                System.out.println("SERVER: " + serverMsg);

                if(serverMsg.startsWith("END:")){
                    String resulst = serverMsg.substring(5).trim();
                    System.out.println(resulst + " Game is over!");
                    System.exit(0);
                }
            }

        });
        threadReader.start();

        while(true){
            System.out.println("Your guess (type 'exit' to quit): ");
            String input = keyboard.nextLine();

            if(input.equals("exit")){
                break;
            }

            out.println("GUESS: " + input);
            out.flush();
        }
    
    }
}

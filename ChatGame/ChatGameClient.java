import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatGameClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 54321);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream());
        Scanner keyboard = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = keyboard.nextLine();
        out.println(name);
        out.flush();

        // TODO: Start listener thread for server messages
        // TODO: In main thread, let user input text and send to server
        //       - If message from server starts with END: → exit program

      

        Thread threadReader = new Thread(() -> {
            while (in.hasNextLine()) {
                String serverMsg = in.nextLine();
                System.out.println("SERVER: " + serverMsg);

                if (serverMsg.startsWith("END:")) {
                    System.out.println(serverMsg);
                    System.exit(0);
                }
            }
        });
        threadReader.start();

        while (true) {
            System.out.println("Enter your guess (if you want to exit type 'exit'): ");
            String input = keyboard.nextLine();

            if(input.equals("exit")){
                break;
            }

            out.println(input);
            out.flush();
        }

        in.close();
        out.close();
        keyboard.close();
        socket.close();

    }
}

/*
@ASSESSME.USERID: mb8561
@ASSESSME.AUTHOR: Marino Barić
@ASSESSME.DESCRIPTION: Week13_Day_01
@ASSESSME.ANALYZE: YES
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EcoServer {
    
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(54321);
        System.out.println("Waiting clent to connect..");
        Socket client = server.accept();

        //streams
        Scanner in = new Scanner(client.getInputStream());
        PrintWriter out = new PrintWriter(client.getOutputStream());

        while(in.hasNextLine()){ // Untill there is a connection
            String message = in.nextLine();
            System.out.println("Message recieved: " + message);
            out.println(message.toUpperCase());
            out.flush();
        }

        server.close();
        client.close();
    }
}

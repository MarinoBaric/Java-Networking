import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class EcoClient {

    public static void main(String[] args) throws UnknownHostException, IOException {
        Socket client = new Socket("127.0.0.1", 12345);

        //streams
        PrintWriter out = new PrintWriter(client.getOutputStream());
        Scanner in = new Scanner(client.getInputStream());

        Scanner keyboard = new Scanner(System.in);

        while(true){
            System.out.println("Write your name: ");
            String name = keyboard.nextLine();
            if(name.equals("")) break;
            System.out.println("Sending: " + name);
            out.println(name);
            out.flush();
            System.out.println("Received: " + in.nextLine());
        }

        client.close();
    }
}
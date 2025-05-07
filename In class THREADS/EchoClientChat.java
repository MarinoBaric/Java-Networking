import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class EchoClientChat{

    private Scanner in = null;
    private PrintWriter out = null;

    public EchoClientChat() throws UnknownHostException, IOException{
        Socket client = new Socket("127.0.0.1",12345);
        this.in = new Scanner(client.getInputStream());
        this.out = new PrintWriter(client.getOutputStream());

        ReceivingThread rt = new ReceivingThread();
        rt.start();

        Scanner keyboard = new Scanner(System.in);

        //Sending message
        while(true){
            System.out.print("Write a meesage:");
            String message = keyboard.nextLine();
            if(message.equals("")) break;
            out.println(message);
            out.flush();
        }
        client.close();
    }

    class ReceivingThread extends Thread{
        @Override
        public void run(){
            while(true){
                System.out.println("Received:" + in.nextLine());
            }
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
           new EchoClientChat();
    }
}
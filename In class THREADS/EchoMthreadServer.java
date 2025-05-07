import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class EchoMthreadServer {
    //socket stuff
    private ServerSocket sSocket = null;
    private static final int SERVER_PORT = 12345;
    private int clientCount = 1;
    private ArrayList<PrintWriter> pwtList = new ArrayList<>();

    public EchoMthreadServer(){
        System.out.println("MThread started!");
        ServerThread serverThread = new ServerThread();
        serverThread.start();
    }

    public static void main(String[] args) {
        new EchoMthreadServer();
    }

    class ServerThread extends Thread{
        public void run(){
            try {
                sSocket = new ServerSocket(SERVER_PORT);
            } catch (IOException ieo) {
                // TODO Auto-generated catch block
                System.out.println("IO exception:" + ieo.toString());
                return;
            }

            while(true){
                Socket client=null;
                System.out.println("Waiting client to connect...");
                try {
                    client = sSocket.accept();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return;
                }//blocking point

                //start a client thread
                ClientThread ct = new ClientThread(client,"Client"+clientCount);
                clientCount++;
                ct.start();
            }
            

            
        }
    }

    class ClientThread extends Thread{
        private Socket cSocket =null;
        private String cName = "";
        public ClientThread(Socket cSocket, String cName){
            this.cSocket = cSocket;
            this.cName = cName;
        }
        public void run(){
            System.out.println(this.cName+" connected!");
            Scanner in=null;
            PrintWriter out = null;

            //streams
            try {
                in = new Scanner(this.cSocket.getInputStream());
                out = new PrintWriter(this.cSocket.getOutputStream());

                pwtList.add(out);

                while(in.hasNextLine()){//untill there is a connection
                    String message = in.nextLine();
                    System.out.println("Messaege received:"+message);
                    //out.println(message.toUpperCase());
                    //out.flush();

                    for(PrintWriter pw : pwtList){
                        pw.println(message.toUpperCase());
                        pw.flush();
                    }
                }
                
            } catch (IOException e) {
                System.out.println("Exception openning streams, shutting down the client");
                return;
            }

            try {
                in.close();
                out.close();
                this.cSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
          

        }
    }

    
    
}

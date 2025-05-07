/*
  * 
  * @ASSESSME.USERID: userID
     @ASSESSME.AUTHOR: author, list of authors
     @ASSESSME.LANGUAGE: JAVA
     @ASSESSME.DESCRIPTION: FINALEXAM
     @ASSESSME.ANALYZE: YES
  */
 
 
 import java.io.IOException;
 import java.io.PrintWriter;
 import java.net.Socket;
 import java.net.UnknownHostException;
 import java.util.Scanner;
 
 public class EchoClientAuction{
 
     PrintWriter out=null;
     Scanner in = null;
 
     public EchoClientAuction() throws UnknownHostException, IOException{
         Socket client = new Socket ("localhost", 54321);
         this.out = new PrintWriter (client.getOutputStream ());
         this.in = new Scanner (client.getInputStream ());
       
         Scanner keyboardScanner = new Scanner(System.in);
 
         System.out.println("Enter your name:");
         String name = keyboardScanner.nextLine();
         out.println(name);
         out.flush();

         Thread threadReader = new Thread(() -> {
            while(in.hasNextLine()){
                String serverMsg = in.nextLine();
                System.out.println("SERVER: " + serverMsg);

                if(serverMsg.startsWith("END: ")){
                    String result = serverMsg.substring(4).trim();
                    System.out.println(result + " Bidding ended!");
                    System.exit(0);
                }
            }
         });
         threadReader.start();

         while (true) {
            System.out.println("Your bid (type -1 for status, exit to quit): ");
            String input = keyboardScanner.nextLine();

            if(input.equals("exit")){
                break;
            } else if(input.equals("-1")){
                out.println("BID: -1");
            } else{
                out.println("BID: " + input);
            }
            out.flush();
         }
         
         keyboardScanner.close();
         in.close();
         client.close ();
     }
 
     public static void main(String[] args) throws IOException {
         new EchoClientAuction();
     }
 }
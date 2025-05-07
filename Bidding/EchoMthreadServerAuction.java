/*
  * 
  * @ASSESSME.USERID: userID
     @ASSESSME.AUTHOR: author, list of authors
     @ASSESSME.LANGUAGE: JAVA
     @ASSESSME.DESCRIPTION: FINALEXAM
     @ASSESSME.ANALYZE: YES
  */
 
 
 import java.net.*;
 import java.io.*;
 import java.util.*;
 
 public class EchoMthreadServerAuction {
 
   private String highestBidder = "No one!";
   private int highestBid = 0;
   private long lastBidTime = System.currentTimeMillis();
   private List<PrintWriter> allClients = new ArrayList<>();
   private Object auctionLock = new Object();

    // Socket stuff
    private ServerSocket sSocket = null;
    public static final int SERVER_PORT = 54321;
    int clientCounter = 1;
 
 
    public EchoMthreadServerAuction() {
 
       System.out.println("MThread started...");
       ServerThread serverThread = new ServerThread();
       serverThread.start();
    }
 
    class ServerThread extends Thread {
       public void run() {
          try {
             sSocket = new ServerSocket(SERVER_PORT);
          } catch (IOException ioe) {
             System.out.println("IO Exception (1): " + ioe + "\n");
             return;
          }
 
          while (true) {
             Socket cSocket = null;
             try {
                System.out.println("Waiting client to connect...");
                cSocket = sSocket.accept();
             } catch (IOException ioe) {
                System.out.println("IO Exception (2): " + ioe + "\n");
                return;
             }
             // Start client thread
             ClientThread ct = new ClientThread(cSocket, "Client" + clientCounter);
             clientCounter++;
             ct.start();
          }
       }
    }// end of ServerThread
 
    // client thread
    class ClientThread extends Thread {
       private Socket cSocket = null;
       private String cName = "";
 
     
       public ClientThread(Socket _cSocket, String _name) {
          this.cSocket = _cSocket;
          this.cName = _name;
       }
 
       public void run() {
          System.out.println(this.cName + " connected");
          PrintWriter pwt = null;
          Scanner scn = null;
          
          try {
             pwt = new PrintWriter(cSocket.getOutputStream());
             scn = new Scanner(cSocket.getInputStream());
            

             this.cName = scn.nextLine();

             synchronized(allClients){
               allClients.add(pwt);
             }

             synchronized(auctionLock){
               long secondsElapsed = (System.currentTimeMillis() - lastBidTime) / 1000;
               pwt.println("CURRENT: " + highestBidder + " " + highestBid + " time: " + secondsElapsed);
               pwt.flush(); 
             }

             while(scn.hasNextLine()){
               String msg = scn.nextLine();

               synchronized(auctionLock){
               long timeElapsed = (System.currentTimeMillis() - lastBidTime) / 1000;
               if(timeElapsed >= 60){
                  pwt.println("END: " + highestBidder + " " + highestBid);
                  pwt.flush();
                  break;
               }
               }
               if(msg.startsWith("BID: ")){
                  int bidAmount = Integer.parseInt(msg.substring(5).trim());
                  synchronized(auctionLock){
                     if(bidAmount == -1){
                        long timeElapsed = (System.currentTimeMillis() - lastBidTime) / 1000;
                        pwt.println("CURRENT: " + highestBidder + " " + highestBid + " time: " + timeElapsed);
                        pwt.flush();
                     } else if(bidAmount <= highestBid){
                        long timeElapsed = (System.currentTimeMillis() - lastBidTime) / 1000;

                        pwt.println("ERROR: too low, time: " + timeElapsed);
                        pwt.flush();
                     } else{
                        highestBid = bidAmount;
                        highestBidder = this.cName;
                        lastBidTime = System.currentTimeMillis();
                        broadcast("Current: " + highestBidder + " " + highestBid + " time: 0");
                     }
                  }  
               }
               
             }

 
          } catch (Exception e) {
             System.out.println("Exception opening streams: " + e);
             System.out.println(this.cName + " disconnected");
          }
 
          try {
             pwt.close();
             scn.close();
             this.cSocket.close();
          } catch (IOException ie) {
          }
 
       }
    }

    private void broadcast(String msg){
      synchronized(allClients){
         for(PrintWriter pwt : allClients){
            pwt.println(msg);
            pwt.flush();
         }
      }
    }
 
    public static void main(String[] args) {
       new EchoMthreadServerAuction();
    }
 
 } // end class
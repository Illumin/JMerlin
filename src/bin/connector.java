package bin;
import java.io.*;
import bin.msg;
import bin.queue;


 public class connector extends Thread {
     public java.net.Socket socket;
     public String incparted;
     queue stackin;
     msg msgBuffer;
	 boolean run = true;
     IRC main;
  
     BufferedReader inc;
     PrintWriter out;
  
     connector(java.net.Socket socket, IRC mainclass) {
         this.socket = socket;
         this.main = mainclass;
     }
  
     public void init() {
  
         this.stackin = new queue();
  
         try {
             inc = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
  
     public void run() {
         while (this.run) {
             try {
                 while ((incparted = inc.readLine()) != null) {
                     System.out.println("< " + incparted);
                     if (!pingcheck(incparted)) {
                         this.msgBuffer = new msg();
                         this.msgBuffer.Message = incparted;
                         this.stackin.enqueue(this.msgBuffer);
                     }
                 }
             } catch (Exception e) {
            	 e.printStackTrace();
             }

             incparted = null;
  
         }
     }
  
     public msg getMessage() {
         msg msg = ((msg) stackin.front());
         stackin.dequeue();
         return msg;
     }
  
     public boolean newMsg() {
         if (stackin.isEmpty())
             return false;
         return true;
     }
  
     public DataInputStream recive() throws IOException {
         DataInputStream Msg;
         Msg = new DataInputStream(this.socket.getInputStream());
         return Msg;
     }
  
      public void Send (String msg) throws IOException {
  
         out = new PrintWriter(this.socket.getOutputStream(), true);
         out.print(msg + "\n\r");
         System.out.println("> " + msg);
         out.flush();
  
     }
  
     boolean pingcheck(String recived) throws IOException {
         if (recived.startsWith("PING")) {
             String[] paramsArray;
             paramsArray = recived.split(" ");
             Send("PONG " + paramsArray[1]);
             return true;
         }
         return false;
     }
 }
 
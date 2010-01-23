package bin;

  
 
import java.io.*;
  
  
 public class connectorout extends Thread {
     public java.net.Socket socket;
     queue stackout;
     msg msgBuffer;
	 boolean run = true;
     IRC main;
  
     PrintWriter out;
  
     connectorout(java.net.Socket socket, IRC mainclass) {
         this.socket = socket;
         this.main = mainclass;
         System.out.println("connout init");
     }
  
     public void init() {
         this.stackout = new queue();
         System.out.println("queue init");
     }
  
     public void run() {
         System.out.println("connout start");
         while (this.run) {
             try { 
         	
                 if (this.stackout.isEmpty()){ 
                 }else{
                	 System.out.println("conn out run");
                 	 System.out.println();
                     System.out.println("!stackout.isEmpty()");
                     System.out.println("this.msgBuffer = ((msg) stackout.front());");
                     this.msgBuffer = ((msg) stackout.front());
                     System.out.println("stackout.dequeue();");
                     stackout.dequeue();
                     System.out.println("Send(this.msgBuffer.Message);");
                     Send(this.msgBuffer.Message);
                	 System.out.println();
                	 System.out.println();
                 }
             } catch (Exception e) {
            	 e.printStackTrace();
             }
         }
    	 System.out.println("ende");
     }
  
  
  
     public void out(String msg) {
         this.msgBuffer = new msg();
         this.msgBuffer.Message = msg;
    	 System.out.println("this.stackout.enqueue(this.msgBuffer);");
         this.stackout.enqueue(this.msgBuffer);
    	 System.out.println(this.stackout.isEmpty());

     }
  
     public void Send(String Msg) throws IOException {
    	 System.out.println("send kommt");
         out = new PrintWriter(this.socket.getOutputStream(), true);
         out.print(Msg+"\n\r");
         System.out.println("> "+Msg);
         out.flush();
     }
 }
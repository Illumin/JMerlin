package bin;

  
 public class msg {
     public String Message = "";
  
     public void Msg(String Msg) {
         this.Message = Msg;
     }
     
     public String GetNick() {
         String[] msgar;
         String[] nick = { "", "" };
         msgar = getPrefix().split(" ");
         nick = msgar[0].split("!");
         nick[0] = nick[0].replace(":", "");
         return nick[0];
     }
  
     String getParam() {
         String[] msgar;
         if(isCommand()) 
             return "";
         msgar = this.Message.split(":");
         if(msgar.length > 2)  //Prevent from empty Messages
             return msgar[2];
         return "";
     }
  
     String getPrefix() {
         String[] msgar;
         msgar = this.Message.split(":");
         return msgar[1];
     }
  
     public String getChannel() {
         String[] ar = this.Message.split(" ");
         return ar[2];
     }
    
    @Deprecated
    boolean isCommand() { //TODO: Ersetzen für Nummern Code Auswertung
         if (countChar(this.Message, ':') > 1)
             return false;
         return true;
     }
  
     private static int countChar(String input, char toCount) {
         int counter = 0;
         for (char c : input.toCharArray()) {
             if (c == toCount)
                 counter++;
         }
         return counter;
     }
 }
 
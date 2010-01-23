package bin;

  
 /**
  * 
  * Beschreibung
  * 
  * @version 1.0 vom 02.09.2009
  * @author
  */
  
 public class knoten {
  
     // Anfang Attribute
     private knoten naechster=null;
     private Object inhalt;
  
     // Ende Attribute
  
     public knoten(Object pInhalt) {
         this.inhalt = pInhalt;
     }
  
     // Anfang Methoden
     public Object getInhalt() {
         return this.inhalt;
     }
  
     public void setNaechsten(knoten pNaechster) {
         this.naechster = pNaechster;
     }
  
     public knoten getNaechsten() {
         return this.naechster;
     }
  
     // Ende Methoden
 }
package bin;

public class queue {
  
     // Anfang Attribute
     private knoten erster=null;
  
     // Ende Attribute
  
     public queue() {
     }
  
     // Anfang Methoden
     public boolean enqueue(Object pObject) {
         if (this.isEmpty()) {
             this.erster = new knoten(pObject);
             return true;
         }
         knoten checkNext = this.erster;
         while (checkNext.getNaechsten() != null) {
             checkNext = checkNext.getNaechsten();
         }
         checkNext.setNaechsten(new knoten(pObject));
         return true;
     }
  
     public void dequeue() {
    	 System.out.println();
         if (this.erster.getNaechsten() == null) {
             this.erster = null;
         } else {
             this.erster = this.erster.getNaechsten();
         }
     }
  
     public Object front() {
         return this.erster.getInhalt();
     }
  
     public boolean isEmpty() {
         if (this.erster == null){
        	 
             return true;
         }else{         	
             return false;        	 
         }
     }
  
     // Ende Methoden
 }
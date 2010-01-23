package bin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
  
import Plugins.*;

@SuppressWarnings("unused")

public class IRC extends Thread{

	 Vector<DefaultPlugin> plugins = new Vector<DefaultPlugin>();
	 Vector<String[]> userlist = new Vector<String[]>();
     String ip;
     int port;
     String pass;
     String name;
     String user;
     public java.net.Socket socket;
     Connection dbConnection;
     connector connin;
     connectorout connout;
     String owner;
     msg msgBuffer;
  
     public static void main(String[] args) {
		IRC irc = new IRC();
     }
  
     public IRC() {
         try {
             init();
             
         }catch (Exception e) {
			e.printStackTrace();
		}
         try {
			this.socket = new java.net.Socket(this.ip, this.port);
		} catch (Exception e1) {
		}

         connin = new connector(this.socket, this);
         connin.init();
         connin.start();
         connout = new connectorout(this.socket, this);
         connout.init();
         connout.start();
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         System.out.println("conn daten kommen");
         connout.out("PASS "+pass);
         connout.out("NICK "+name);
         connout.out("USER "+user);

         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }

         System.out.println("channel join");
         connout.out("JOIN #Lobby");
         connout.out("JOIN #Bot");
         this.start();
     }
     Connection connectMysql (String url,String user, String pw) throws IOException{   
		   Connection con=null;
		   try {
			   String driver="con.mysql.jdbc.Driver";	//Für MySQL Datenbanken
			   Class.forName(driver);
			   con = DriverManager.getConnection(url, user, pw);
			   //return con;
		   } catch (Exception e) {
			   System.out.println("Verbindung zur Datenbank felgeschlagen.");
		   }
		   return con;
	  }
     void init()throws Exception{
		   //init.ini laden
		   Properties pro = new Properties();
		   FileInputStream fi = new FileInputStream("init.ini");
		   pro.load(fi);
		   fi.close();
		   
		   //Namen des Bots holen
		   name=pro.getProperty("name", "MerlinJava");
		   ip = pro.getProperty("server", "paradox-works.de");
		   port = Integer.parseInt(pro.getProperty("port", "6667"));
		   //IRC Verbindungs informationen holen
		   owner=pro.getProperty("owner");
		   pass=pro.getProperty("pass", "asda");
		   user=pro.getProperty("user", "ich meinrech server : Ich").trim();
		  
		   //Datenbankverbindungsvariablen holen
		   String db_url=pro.getProperty("db_url");
		   String db_user=pro.getProperty("db_user");
		   String db_pw=pro.getProperty("db_pw");
		   System.out.println("db_url: "+db_url);
		   System.out.println("db_user: "+db_user);
		   System.out.println("db_pw: "+db_pw);
		   //Datenbank connection holen
		   dbConnection=connectMysql(db_url, db_user, db_pw);
		   //Plugins Holen
		   loadAllPlugins();
		   }
     
		   public Vector<String[]> getUserlist(){
			return userlist;
		   }
		   
		   private void loadAllPlugins(){
			   plugins = null;
			   plugins = new Vector<DefaultPlugin>();
			   File pluginDir = new File("bin"); 
			   pluginDir = new File(pluginDir, "Plugins"); 
			   File[] allFiles = pluginDir.listFiles();
			   String pName;
			   String suffix=".class";
			   for(int i=0; i<allFiles.length; i++){
				   pName = allFiles[i].getName();
				   pName = pName.substring(0, (pName.length()-suffix.length()));
				   if(  allFiles[i].getName().endsWith(suffix)){
					   System.out.println(pName);
					   try {
						plugins.add(DefaultPlugin.class.cast( Class.forName("Plugins."+pName).newInstance() ));
					} catch (Exception e) {
						e.printStackTrace();
					}
				   }
			   }
		   }
		   public String getBotName(){
			   return name;
		   }
			@SuppressWarnings("deprecation")
			public void run(){
		    	 String channel;
		    	 boolean run=true;
		         while(run){
		             if(connin.newMsg()){
		                 this.msgBuffer = connin.getMessage();
		                 if(this.msgBuffer.isCommand()){
		                	 String[] commands;
		                	 commands = this.msgBuffer.Message.split(" ");
		                	 String uName;
	                		 for(int i=0; i<userlist.size(); i++){
	                			 uName=commands[0].split("!")[0];
	                			 uName= uName.replace(":", "");
	                			 if(uName.equals(userlist.get(i)[1])){
	                				 userlist.remove(i);
	                				 i=-1;
	                			 }
	                		 }
		                 }else{
		                	 String[] commands;
		                	 commands = this.msgBuffer.Message.split(" ");
		                	 if(commands[1].equals("353")){
		                			for(int i=5; i<commands.length ; i++){
		                				String[] user= new String[2];
				                		user[0]=commands[4];
		                				System.out.println(i+"  "+commands[i]);
		                				user[1]=commands[i];
		                				if(i==5){
		                					user[1] = user[1].replace(":", "");
		                				}
		                				userlist.add(user);
				                		 user=null;
		                			}
		                			
		                	 }else if(commands[1].equals("JOIN")){
		                		 if(!commands[0].startsWith(":Norasul!")){
		                		 
		                		 String[] user= new String[2];
		                		 user[1]= commands[0].split("!")[0];
		                		 user[1]= user[1].replace(":", "");
		                		 
		                		 user[0]=commands[2];
		                		 user[0] = user[0].replace(":", "");
		                		 userlist.add(user);
		                		 }
		                	 }
		                	 //.............................
		                	 //Rückgabechannel einstellen
		                	 if(this.msgBuffer.getChannel().equals(name)){
		                		 channel=msgBuffer.GetNick();
		                	 }else{
		                		 channel=msgBuffer.getChannel();
		                	 }
		                	 //Befehle für alle User (rein auf den bot bezogen)
			                 if(this.msgBuffer.getParam().startsWith("!info")){
			                     connout.out("PRIVMSG "+channel+" Chatbot "+this.name+", Version 0.0.2");
			                     connout.out("PRIVMSG "+channel+" Part of the IRC-Bot Project of Paradox-Works.de");
			                     connout.out("PRIVMSG "+channel+" Maintained by "+this.owner);
			                 }
			                 //Hilfe Für alle Funktionen ausgeben
			                 if(this.msgBuffer.getParam().startsWith("!hilfe")){
			                	 for(int i=0; i<plugins.size(); i++){
			                		plugins.get(i).hilfe(new Writer(msgBuffer.GetNick(), connout), msgBuffer,dbConnection);
			                	 }
			                 }else{
			                	 //Eingabe Prüfen und Pluginaktionen ausfüren
			                	 for(int i=0; i<plugins.size(); i++){
				                		if(plugins.get(i).testeEingabe(this.msgBuffer.getParam())){
				                			 plugins.get(i).execute(new Writer(channel, connout), msgBuffer, dbConnection, this);
				                	 	}
				                	 }
			                 }
			                 if(this.msgBuffer.GetNick().equals(owner)){
			                	 //Addministratoren befehle
			                	if(this.msgBuffer.getParam().startsWith("!quit")){
			                		try {
			                			if(!this.dbConnection.isClosed()){
			                				this.dbConnection.close();
			                			}
									} catch (Exception e) {
									}
			                	 	connout.out("QUIT");
			                	 	connout.run=false;
			                	 	connin.run=false;
			                	 	run=false;
			                 	}
			                	if(this.msgBuffer.getParam().startsWith("!reload")){
			                		try {
										loadAllPlugins();
									} catch (Exception e) {
										e.printStackTrace();
									}
			                 	}
			                 }
		                 }
		             }
		         }
		     }
 }
     
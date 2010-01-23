package Plugins;

import bin.IRC;

import java.sql.Connection;
import java.util.Vector;

import bin.Writer;
import bin.DefaultPlugin;
import bin.msg;

public class TestPlugin2  implements DefaultPlugin{
	public boolean testeEingabe(String msg){
		if(msg.equals("!test"))
				return true;
		return false;
	}
	public void execute(Writer writer, msg msgBuffer, Connection dbCon, IRC calledBy) {
		writer.write("TestPlugin Nr.1");
		Vector<String[]> userlist = calledBy.getUserlist();
		String[] user;
		for(int i=0; i<userlist.size(); i++){
			user=userlist.get(i);
			writer.write("Channel: "+user[0]+" User: "+user[1]);
		}
	}
	public void hilfe(Writer writer, msg msgBuffer, Connection dbCon) {
		writer.write("Testfunktion");
		
	}
}

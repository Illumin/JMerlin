package bin;
/*
 * Klasse fuers schreiben im chat
 */
public class Writer {
	String channel;
	connectorout conOut;
	Writer(String channel, connectorout conOut){
		this.channel=channel;
		this.conOut=conOut;
	}
	public void write(String msg){
		conOut.out("PRIVMSG "+channel+" "+msg);
	}
	public void command(String msg){
		conOut.out(msg);
	}
}

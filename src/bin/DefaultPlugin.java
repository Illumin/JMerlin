package bin;
import java.sql.Connection;

public interface DefaultPlugin {
	 abstract boolean testeEingabe(String msg);
	 abstract void execute(Writer writer, msg message, Connection dbConnection, IRC calledBy);
	 abstract void hilfe(Writer writer, msg message,Connection dbConnection);
	 
}

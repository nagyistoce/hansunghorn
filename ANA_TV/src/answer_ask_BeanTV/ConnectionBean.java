package answer_ask_BeanTV;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import SOD.SmartTV.AccessManagerServer;
import SOD.SmartTV.ServerConfig;

public class ConnectionBean
{
	public static final int SERVERPORT = 30331;
	public static final String SERVERIP = "127.0.0.1";
	public static String Message;
	public static AccessManagerServer server;
	public static ConnectionBean con;
	public static ServerConfig ServerConfig;
	public static int ClientId;
}

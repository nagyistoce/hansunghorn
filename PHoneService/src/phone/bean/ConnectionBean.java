package phone.bean;

import android.os.StrictMode;
import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.smartphone.*;

public class ConnectionBean
{
	public static final int SERVERPORT = 30331;		// Server port // ������Ʈ
	public static String SERVERIP = "";				//ServerIp // ���� ������
	public static AccessManager client;			// Client���� // Ŭ���̾�Ʈ�Ŵ��� 	
	public static ServerInfo ServerInfomation;	// Connection Server Imfomation // ������ ���� ����
	public static String Service="";
	public void Client_Initalize() {
		ConnectionBean.client = new AccessManager();
		/*//�� �κ��� TVServerLisrtActivity ���� �����˻� �� �ϱ� ������
		 	//�ּ�ó��
		ConnectionBean.ServerInfomation = new ServerInfo();
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		*/
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() {		// client ReceiveHandler// Ŭ���̾�Ʈ ���ú� �ڵ鷯

			public void onReceive(Packet pkt) {
				if (pkt != null) {
					while(pkt.getElementCount() > 0)			//packet element count // ��Ŷ ������Ʈ����
					{
						Object item = pkt.pop();				
						new AnABean().Message += item.toString();
					}
					new AnABean().waithandle.release();
					
				}
			}
 		});
		StrictMode.enableDefaults();			// stack default // ���ÿ��� ����Ʈ �ʱ�ȭ
		
		
		ConnectionBean.client.setStartServiceDelegate(new ActionEx() {	// ���� �����ڵ鷯
			
			public void work(Object arg) {
				// TODO Auto-generated method stub
				//�ڵ鷯�� ������
			}
		});
		
		ConnectionBean.client.connect(ConnectionBean.ServerInfomation);	// ������ ������ ����// connection Server
		
		
	}
}

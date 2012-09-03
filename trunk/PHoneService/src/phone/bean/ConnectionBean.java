package phone.bean;

import android.os.StrictMode;
import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.smartphone.*;

public class ConnectionBean
{
	public static final int SERVERPORT = 30331;		// Server port // 서버포트
	public static String SERVERIP = "";				//ServerIp // 서버 아이피
	public static AccessManager client;			// Client관리 // 클라이언트매니저 	
	public static ServerInfo ServerInfomation;	// Connection Server Imfomation // 접속할 서버 정보
	public static String Service="";
	public void Client_Initalize() {
		ConnectionBean.client = new AccessManager();
		/*//이 부분은 TVServerLisrtActivity 에서 서버검색 후 하기 때문에
		 	//주석처리
		ConnectionBean.ServerInfomation = new ServerInfo();
		ConnectionBean.ServerInfomation.EndPoint = new InetSocketAddress(
				ConnectionBean.SERVERIP, ConnectionBean.SERVERPORT);
		*/
		ConnectionBean.client.setReceiveHandler(new ReceiveHandler() {		// client ReceiveHandler// 클라이언트 리시브 핸들러

			public void onReceive(Packet pkt) {
				if (pkt != null) {
					while(pkt.getElementCount() > 0)			//packet element count // 패킷 엘리먼트갯수
					{
						Object item = pkt.pop();				
						new AnABean().Message += item.toString();
					}
					new AnABean().waithandle.release();
					
				}
			}
 		});
		StrictMode.enableDefaults();			// stack default // 스택영역 디폴트 초기화
		
		
		ConnectionBean.client.setStartServiceDelegate(new ActionEx() {	// 서비스 시작핸들러
			
			public void work(Object arg) {
				// TODO Auto-generated method stub
				//핸들러로 보내기
			}
		});
		
		ConnectionBean.client.connect(ConnectionBean.ServerInfomation);	// 접속할 서버에 연결// connection Server
		
		
	}
}

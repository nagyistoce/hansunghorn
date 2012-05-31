package order.control;

import order.bean.ConnectionBean;
import sod.common.Packet;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;

public class Networking {
	public void TVServerIni() {
		ConnectionBean.server = new AccessManagerServer();
		ConnectionBean.ServerConfig = new ServerConfig();
		ConnectionBean.ServerConfig.Timeout = 30000;
		ConnectionBean.ServerConfig.Port = ConnectionBean.SERVERPORT;
		ConnectionBean.ServerConfig.CheckingPeriod = 4000;
		ConnectionBean.ServerConfig.serviceName = "A&A_Service";

		ConnectionBean.server.setConnectHandler(new ConnectHandler() {

			public void onConnect(int connid) {

				// TODO Auto-generated method stub
				// Toast.makeText(QuestionnairesSettingSecond.this, "立加沁澜",
				// Toast.LENGTH_LONG).show();
			}
		});
		ConnectionBean.server.setDisconnectHandler(new DisconnectHandler() {

			public void onDisconnect(int connid) {
				// TODO Auto-generated method stub
				// Toast.makeText(QuestionnairesSettingSecond.this, "立加 馋辫",
				// Toast.LENGTH_LONG).show();

			}
		});
		ConnectionBean.server.setReceiveHandler(new ServerReceiveHandler() {

			public void onReceive(Packet pkt, int connid) {
				ConnectionBean.ClientId = connid;
				Packet packet;

				if (pkt != null) {
					while (pkt.getElementCount() > 0) {
						ConnectionBean.Message = pkt.pop().toString();
						if (ConnectionBean.Message.equals("download")) {
							packet = new Packet();
//							for (int i = 0; i < DataSturct.vector.size(); i++) {
//								packet.push(DataSturct.vector.elementAt(i));
//
//							}
							ConnectionBean.server.send(packet,
									ConnectionBean.ClientId);
						}
						// else if(ConnectionBean.Message.equals("answerData"))
						// {
						else if (ConnectionBean.Message.equals("Questionnaire")) {
							ConnectionBean.Message = pkt.pop().toString();
//							DataBean.Message += ConnectionBean.Message;
//							try {
//								DataBean.Message=DataBean.Message.substring(2);
//								ReArrange();
//
//							} catch (Exception e) {
//							}

						}
						// }

					}
				}

			}
		});

	}
}

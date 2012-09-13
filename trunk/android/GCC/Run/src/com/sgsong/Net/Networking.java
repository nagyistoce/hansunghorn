package com.sgsong.Net;

import sod.common.Packet;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;

public class Networking {
	public void TVServerIni() { // Tv initalize
		ConnectionBean.server = new AccessManagerServer();
		ConnectionBean.ServerConfig = new ServerConfig();
		ConnectionBean.ServerConfig.Timeout = 100000;
		ConnectionBean.ServerConfig.Port = ConnectionBean.SERVERPORT;
		ConnectionBean.ServerConfig.CheckingPeriod = 4000;
		ConnectionBean.ServerConfig.serviceName = "gcc";

		ConnectionBean.server.setConnectHandler(new ConnectHandler() {

			public void onConnect(int connid) {
				// Connection handler
			}
		});
		ConnectionBean.server.setDisconnectHandler(new DisconnectHandler() {

			public void onDisconnect(int connid) {
				// Disconnection handler
			}
		});
		ConnectionBean.server.setReceiveHandler(new ServerReceiveHandler() {

			public void onReceive(Packet pkt, int connid) {
//				ConnectionBean.ClientId = connid;
//				Packet packet;
//				if (pkt != null) {
//					while (pkt.getElementCount() > 0) {
//						ConnectionBean.Message = pkt.pop().toString();
//						if (ConnectionBean.Message.equals("download")) {
//							packet = new Packet();
//							try {
//
//							} catch (Exception e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							ConnectionBean.server.send(packet,
//									ConnectionBean.ClientId);
//						} else if (ConnectionBean.Message
//								.equals("Questionnaire")) {
//							ConnectionBean.Message = pkt.pop().toString();
//						}
//					}
//				}

			}
		});

	}
}

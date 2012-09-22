package com.sgsong.Net;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.sgsong.Game.GameMain;

import sod.common.NetworkUtils;
import sod.common.Packet;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;

public class Networking {
	private static Packet packet;
	private static int PushCard;
	public static GameMain GM;
	
	
	public GameMain getGM() {
		return GM;
	}
	public static void setGM(GameMain gM) {
		GM = gM;
	}
	public static Packet getPacket() {
		return packet;
	}
	public static void setPacket(Packet packet) {
		Networking.packet = packet;
	}
	public void TVServerIni() { // Tv initalize
		////엄씨가 넣은것/////////////////
		NetworkUtils.setLocalIp(WifiSubService.getLocalIpAddress());
		//////////////////////////////
		
		ConnectionBean.server = new AccessManagerServer();
		ConnectionBean.ServerConfig = new ServerConfig();
		ConnectionBean.ServerConfig.Timeout = 100000;
		ConnectionBean.ServerConfig.Port = ConnectionBean.SERVERPORT;
		ConnectionBean.ServerConfig.CheckingPeriod = 4000;
		ConnectionBean.ServerConfig.serviceName = "gcc";

		ConnectionBean.server.setConnectHandler(new ConnectHandler() {

			public void onConnect(int connid) {
				ConnectionBean.ClientId=connid;
			}
		});
		ConnectionBean.server.setDisconnectHandler(new DisconnectHandler() {

			public void onDisconnect(int connid) {
				// Disconnection handler
			}
		});
		
		ConnectionBean.server.setReceiveHandler(new ServerReceiveHandler() {
			
			public void onReceive(Packet pkt, int connid) {
				 String temp=SignCase(pkt.pop());
				ConnectionBean.ClientId = connid;
				if(temp.equals("")|| temp == null)
				{
					return; 
				}
				else if (pkt != null && temp.equals(("getCard"))) {
					ConnectionBean.server.send(packet, ConnectionBean.ClientId);
//					for(int i=0;i<GCCBean.MAX_COUNT_CARD;i++)
//					{
//						//GCCBean.card_index+=SignCase(pkt.pop())+"|";
//					}
				}
				else if(pkt != null && temp.equals("SendCard")){
					PushCard=Integer.parseInt(pkt.pop().toString());
					GM.onTouchedElement(PushCard);
				}
				else if(temp.equals("Open"))
				{
					ConnectionBean.OpenSigniture=true;
				}
				else{
					while (pkt.getElementCount() > 0) {
						ConnectionBean.Message = pkt.pop().toString();
						if (ConnectionBean.Message.equals("download")) {
							try {
									
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (ConnectionBean.Message
								.equals("Questionnaire")) {
							ConnectionBean.Message = pkt.pop().toString();
						}
					}
				}
			}

		});

	}

	public String SignCase(Object sign) {
		return sign.toString();
	}
}

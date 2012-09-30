package sod.test;

import java.net.InetSocketAddress;

import sod.common.ConsoleLogger;
import sod.common.Constants;
import sod.common.Packet;
import sod.common.Transceiver;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Test.initServer();
		//Test.testSerializer();
		//Test.testTransceiver();	
		//Test.testAccessManager();
		Test.testServerSearch();
		//Test.testServer();
		//Test.testClient();
	}
	/*
	public static void initServer() {
		// server setup
		server = new AccessManagerServer();
		ServerConfig conf = new ServerConfig();
		conf.Port = ServerPort;
		conf.serviceName = "TestService";

		server.setConnectHandler(new ConnectHandler() {
			@Override
			public void onConnect(int connid) {
				logger.log("(server): new connection is accepted - " + connid
						+ "\n");
			}
		});
		server.setDisconnectHandler(new DisconnectHandler() {

			@Override
			public void onDisconnect(int connid) {
				logger.log("(server): client is disconnected - " + connid
						+ "\n");
			}
		});
		server.setReceiveHandler(new ServerReceiveHandler() {
			
			Transceiver trans= new Transceiver(new InetSocketAddress("127.168.0.26", 2013));
			
			@Override
			public void onReceive(Packet pkt, int connid) {
				logger.log("(server): a packet from client - " + connid + "\n");
				server.send(pkt, connid);
				
				trans.send(pkt);	
				
			}
		});

		server.start(conf);
		
		logger.log("press enter to finish.\n");
		waitfor();
		
		server.shutdown();
	}
	*/

}

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
	

}

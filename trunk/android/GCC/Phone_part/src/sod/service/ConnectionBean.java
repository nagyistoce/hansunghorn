package sod.service;

import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;

public class ConnectionBean { // 서비스부분에서 Connection 정보를 담기 위한 클래스파일
	public static final int SERVERPORT = 30331;
	public static String SERVERIP = "";
	public static AccessManager client;
	public static ConnectionBean con;
	public static ServerInfo ServerInfomation;
}

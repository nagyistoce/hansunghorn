package com.sgsong.Net;

import sod.smarttv.AccessManagerServer;
import sod.smarttv.ServerConfig;

public class ConnectionBean {
	public static final int SERVERPORT = 30331;		// Server port // ���� ��Ʈ 
	
	public static String Message;			// Data Message// ������ �޼���
	public static AccessManagerServer server; // server 
	public static ServerConfig ServerConfig; // Server imfomation// ��������
	public static int ClientId;			// client identify Id // Ŭ���̾�Ʈ �ĺ����̵�
	public static boolean OpenSigniture=false;
}

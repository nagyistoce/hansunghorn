package com.phonegapmyplugin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.Enumeration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.webkit.WebSettings.PluginState;
import android.widget.Toast;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class myPlugin extends Plugin {
	static final int LocalPort = 30331;
	static final String HostIP = "192.168.0.25";
	private static String val;
	private DatagramSocket conn,conn1;
	private int flag = 0;
	private DatagramPacket pkt,pkt1;
	private int count;
	private SocketAddress addr;
	private int i=0;
	private static String getLocalAddress() throws IOException { // 내 디바이스 IP
																	// 받아오기
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return "";
	}

	public void Soketinitial() throws SocketException { // 소켓 초기화
		conn = new DatagramSocket();
		conn = new DatagramSocket(LocalPort);
		conn1 = new DatagramSocket();
		pkt = new DatagramPacket(new byte[0xFF], 0xFF);
	}

	public void getData() throws IOException {
		byte[] buf = pkt.getData();
		for (count = 0; count < 0xFF; ++count)
			buf[count] = 0;
		conn.receive(pkt);
		val = new String(buf, "euc-kr");
		this.val =val;
		return;
	}

	public void sendMessage(String msg) throws IOException {
		byte[] buf = msg.getBytes("euc-kr");
		pkt1 = new DatagramPacket(buf, buf.length);
		addr = new InetSocketAddress(HostIP, LocalPort);
		pkt1.setSocketAddress(addr);
		conn1.send(pkt1);
		val=msg;
		return;
	}

	public PluginResult execute(String action, JSONArray arg1,
			String callbackId) {
			
		try{
			String msg=arg1.getString(0);
			if(flag==0)
			{
			flag++;
			Soketinitial();	
			}			
		while (true) {
				if (action.equals("sendData")) {
					JSONObject rv  = new JSONObject();
					if(msg.equals(""))
					sendMessage(""+ i++);
					else
						sendMessage(msg);
					return new PluginResult(PluginResult.Status.OK, val);
				} else if (action.equals("receiveData")) {
					getData();
					return new PluginResult(PluginResult.Status.OK, val);
				} else if(action.equals("TvSerch")){
					return	new PluginResult(PluginResult.Status.OK, "TV를 찾았습니다.");
					}
				else
					return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
			} 
		}catch(Exception e){
			return new PluginResult(PluginResult.Status.OK,""+e);
		}
	}
}
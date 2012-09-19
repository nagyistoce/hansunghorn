package sod.service;

import java.net.InetSocketAddress;
import java.util.concurrent.Semaphore;

import org.json.JSONArray;
import org.json.JSONException;

import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;

import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.webkit.WebSettings.PluginState;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class myPlugin extends Plugin{
	Handler startHandler;;
	public static String mMessage=""; // return value at plugin /// 플러그인을 통해 Message값 전달
	public static Semaphore waithandle;		// Semaphore // 세마포어
	public String[] ag=new String[3];
    public static String DataSign="";
	Packet pk;
	static{
		waithandle = new Semaphore(1);		// 세마포어 //Semaphore
		try {
			waithandle.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getDataSign(JSONArray arg1)throws Exception
	{
		this.DataSign=arg1.getString(0);
		return DataSign;
	}
	public PluginResult execute(String arg0, JSONArray arg1, String arg2) {
		// TODO Auto-generated method stub
			pk=new Packet();
			boolean a;
			if(arg0.equals("sendData")){
				try {
					if(getDataSign(arg1).equals("getCard"))
					{
						String temp=mMessage;
						mMessage="";
						return new PluginResult(PluginResult.Status.OK, temp);
					}
					else
					{
						 pk.push("SendCard");
						 pk.push(getDataSign(arg1));
						 ConnectionBean.client.send(pk);
						 return new PluginResult(PluginResult.Status.OK, "success");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new PluginResult(PluginResult.Status.OK, "success");
			}
			else
				return new PluginResult(PluginResult.Status.ERROR, "Fail");
		
		
		
	//	return null;
	}
  
  
}

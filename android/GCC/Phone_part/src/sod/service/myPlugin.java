package sod.service;

import java.util.concurrent.Semaphore;

import org.json.JSONArray;

import sod.bean.AnA_Bean;
import sod.bean.GCC_Bean;
import sod.common.Packet;

import android.os.Handler;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class myPlugin extends Plugin {
	Handler startHandler;;
	public static String mMessage = ""; // return value at plugin /// �÷������� ����
										// Message�� ����
	public static Semaphore waithandle; // Semaphore // ��������
	public String[] ag = new String[3];
	public static String DataSign = "";
	Packet pk;
	static {
		waithandle = new Semaphore(1); // �������� //Semaphore
		try {
			waithandle.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PluginResult execute(String action, JSONArray data, String callbackId) {
		// TODO Auto-generated method stub
		if (ConnectionBean.ServerInfomation.ServiceName // ���� GCC
				.equals("gcc")) {
			GCC_Bean service_gcc = new GCC_Bean();
			pk = new Packet();
			if (action.equals("sendData")) {
				try {
					if (service_gcc.getDataSign(data).equals("Open")) {
						Packet pkt = new Packet();
						pkt.push("Open");
						ConnectionBean.client.send(pkt);
					} else if (service_gcc.getDataSign(data).equals("getCard")) {
						String temp = mMessage;
						mMessage = "";
						return new PluginResult(PluginResult.Status.OK, temp);
					} else {
						service_gcc.SendData(pk, data);
						return new PluginResult(PluginResult.Status.OK,
								"success");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return new PluginResult(PluginResult.Status.OK, "success");
			} else
				return new PluginResult(PluginResult.Status.ERROR, "Fail");
		} else // ���� Answer & Ask
		{
			AnA_Bean service_ana = new AnA_Bean();
			while (true) {
				if (action.equals("receiveData")) {
					try {
						service_ana.ReceiveData();
						AnA_Bean.waithandle.acquire();
					} catch (Exception e) {
						return new PluginResult(PluginResult.Status.OK, "" + e);
					}
					service_ana.TempMessage = service_ana.Message;
					service_ana.Message = "";
					return new PluginResult(PluginResult.Status.OK,
							service_ana.TempMessage);
				} else if (action.equals("sendData")) {
					try {

						service_ana.SendData(data.toString());
					} catch (Exception e) {
					}
					return new PluginResult(PluginResult.Status.OK, "OK");
				} else
					return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
			}
		}

		// return null;
	}

}

package phone.service;

import org.json.JSONArray;

import phone.bean.AnABean;
import phone.bean.ConnectionBean;
import phone.bean.ORDERBean;
import phone.beanImpl.ServiceBeanImpl;

import sod.common.ActionEx;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.smartphone.AccessManager;

import android.os.Message;
import android.os.StrictMode;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class AnAServicePlugin extends Plugin {
	ConnectionBean ServiceImpl;
	public PluginResult execute(String action, JSONArray data, String callbackId) {

		while (true) {
			if (action.equals("receiveData")) {
				try {
					//stub
					ServiceImpl.Service=data.getString(0);
					if(ServiceImpl.Service.equals("AnA"))
					{
						AnABean ana=new AnABean();
						ana.ReceiveData();
						ana.waithandle.acquire();
					}
					else if(ServiceImpl.Service.equals("ORDER"))
					{
						ORDERBean order=new ORDERBean();
						order.ReceiveData();
					}
				} catch (Exception e) {
					return new PluginResult(PluginResult.Status.OK, "" + e);	// Exception  // ����ó��
				}
				return new PluginResult(PluginResult.Status.OK,	// StringMessage return //��Ʈ�����ڿ��� ����.
						"");
			}else if (action.equals("sendData")) {
				try {
					//stub
					if(ServiceImpl.Service.equals("AnA"))
					{
						AnABean ana=new AnABean();
						ana.SendData(data.toString());
						ana.waithandle.acquire();
					}
					else if(ServiceImpl.Service.equals("ORDER"))
					{
						ORDERBean order=new ORDERBean();
						order.SendData(data.toString());
					}
				} catch (Exception e) {
					
				}
				return new PluginResult(PluginResult.Status.OK, "OK"); // complete! // ������ �ϼ��� OK�� ����
			} else
				return new PluginResult(PluginResult.Status.JSON_EXCEPTION); //  JSONException error // ���̽�����ó�� 
		}
	}
}



package ana.phone;

import org.json.JSONArray;

import ana.phoneBean.DownLoad;

import com.phonegap.api.PluginResult;

public class AnAServicePlugin extends AnAServiceNet {
	public String AnswerData="";
	
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackId) {
			
			while (true) {
				if (action.equals("receiveData")) {
					try{
					ReceiveData();
					DownLoad.waithandle.acquire();
					}catch(Exception e)
					{
					return new PluginResult(PluginResult.Status.OK,""+e);
					}
					DownLoad.TempMessage=DownLoad.Message;
					DownLoad.Message="";
					return new PluginResult(PluginResult.Status.OK,DownLoad.TempMessage);
				} else if (action.equals("sendData")) {
					try{
						
						SendData(data.toString());
				}catch(Exception e)
				{
				}
					return new PluginResult(PluginResult.Status.OK,
						"滴馬馬馬馬");
				} else
					return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
			}
		}}

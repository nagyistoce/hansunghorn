package ana.phone;

import org.json.JSONArray;

import ana.phoneBean.DownLoad;

import com.phonegap.api.PluginResult;

public class AnAServicePlugin extends AnAServiceNet {
	public String AnswerData="";
	// private static String getLocalAddress() throws IOException { // 내 디바이스 IP
	// // 받아오기
	// try {
	// for (Enumeration<NetworkInterface> en = NetworkInterface
	// .getNetworkInterfaces(); en.hasMoreElements();) {
	// NetworkInterface intf = en.nextElement();
	// for (Enumeration<InetAddress> enumIpAddr = intf
	// .getInetAddresses(); enumIpAddr.hasMoreElements();) {
	// InetAddress inetAddress = enumIpAddr.nextElement();
	// if (!inetAddress.isLoopbackAddress()) {
	// return inetAddress.getHostAddress().toString();
	// }
	// }
	// }
	// } catch (SocketException ex) {
	// }
	// return "";
	// }

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
						"크하하하하");
				} else
					return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
			}
		}}

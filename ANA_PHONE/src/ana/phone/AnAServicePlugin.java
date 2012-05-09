package ana.phone;


import org.json.JSONArray;


import com.phonegap.api.PluginResult;

public class AnAServicePlugin extends AnAServiceNet {
//	 private static String getLocalAddress() throws IOException { // 내 디바이스 IP
//	 // 받아오기
//	 try {
//	 for (Enumeration<NetworkInterface> en = NetworkInterface
//	 .getNetworkInterfaces(); en.hasMoreElements();) {
//	 NetworkInterface intf = en.nextElement();
//	 for (Enumeration<InetAddress> enumIpAddr = intf
//	 .getInetAddresses(); enumIpAddr.hasMoreElements();) {
//	 InetAddress inetAddress = enumIpAddr.nextElement();
//	 if (!inetAddress.isLoopbackAddress()) {
//	 return inetAddress.getHostAddress().toString();
//	 }
//	 }
//	 }
//	 } catch (SocketException ex) {
//	 }
//	 return "";
//	 }

	public PluginResult execute(String action, JSONArray arg1, String callbackId) {
		try {
			if (flag == 0) {
				flag++;
				Soketinitial();
			}
			while (true) {
				if (action.equals("receiveData")) {
					getData();
					return new PluginResult(PluginResult.Status.OK, Message);
				} else if (action.equals("TvSerch")) {
					return new PluginResult(PluginResult.Status.OK,
							"TV를 찾았습니다.");
				} else
					return new PluginResult(PluginResult.Status.JSON_EXCEPTION);
			}
		} catch (Exception e) {
			return new PluginResult(PluginResult.Status.OK, "" + e);
		}
	}
}
package sod.common;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;

public class NetworkUtils {
	final static NetworkUtils Instance;
	static{
		Instance = new NetworkUtils();
	}
	
	String localIP;
	
	class IPSet implements Iterator<String> {
		final int Max = 0x100;
		int idx = 0;
		String base;
		
		
		public IPSet(String baseIP){
			base = baseIP.substring(0, baseIP.lastIndexOf('.') + 1);
		}

		@Override
		public boolean hasNext() {
			if(idx < Max)
				return true;
			else
				return false;
		}

		@Override
		public String next() {
			String rv = base + idx;
			++idx;
			return rv;
		}

		@Override
		public void remove() {
			//do nothing.
		}
	}
	
	/**
	 * ��Ʈ��ũ ����� IP������ �����´�.
	 * @param baseIP
	 * @return
	 */
	public static Iterator<String> getIPSet(String baseIP){
		return Instance.new IPSet(baseIP);
	}
	
	/**
	 * LocalIP�� �����´�.
	 * @return LocalIP
	 */
	public static String getLocalIP(){
		/*
		try{
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostAddress();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		*/
		return Instance.localIP;
	}
	
	/**
	 * ��Ƽ ĳ��Ʈ  ������ �����Ѵ�.
	 * @param ip LocalIP
	 * @param port ����ڰ� ������ port
	 * @return ��Ƽĳ��Ʈ ����
	 */
	public static MulticastSocket createMutlicastSocket(String ip, int port){
		try {
			InetAddress group = InetAddress.getByName(ip);
			MulticastSocket s = new MulticastSocket(port);
			s.joinGroup(group);
			return s;
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * ��Ƽĳ��Ʈ �ּҸ� �����´�.
	 * @return ��Ƽĳ��Ʈ �ּ�
	 */
	public static InetAddress getMulticastAddr(){
		try {
			return InetAddress.getByName(Constants.Multicast_IP);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * LocalIP�� �Ҵ��Ѵ�.
	 * @param mLocalIp
	 */
	public static void setLocalIp(String mLocalIp){
		Instance.localIP  = mLocalIp;
	}
	
}

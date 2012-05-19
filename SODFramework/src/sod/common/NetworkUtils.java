package sod.common;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.List;

public class NetworkUtils {
	final static NetworkUtils Instance;
	static{
		Instance = new NetworkUtils();
	}
	
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
	
	public static Iterator<String> getIPSet(String baseIP){
		return Instance.new IPSet(baseIP);
	}
	
	public static String getLocalIP(){
		try{
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostAddress();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return null;
	}
	
}

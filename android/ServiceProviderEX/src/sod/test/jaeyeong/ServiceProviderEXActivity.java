package sod.test.jaeyeong;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import sod.common.ActionEx;
import sod.common.ConsoleLogger;
import sod.common.Logable;
import sod.common.Packet;
import sod.common.ReceiveHandler;
import sod.common.ThreadEx;
import sod.common.Transceiver;
import sod.smartphone.AccessManager;
import sod.smartphone.ServerInfo;
import sod.smartphone.ServiceManager;
import sod.smarttv.AccessManagerServer;
import sod.smarttv.ConnectHandler;
import sod.smarttv.DisconnectHandler;
import sod.smarttv.ServerConfig;
import sod.smarttv.ServerReceiveHandler;
import sod.smarttv.ServiceProvider;
import android.app.Activity;
import android.os.Bundle;

public class ServiceProviderEXActivity extends Activity {
    /** Called when the activity is first created. */
	
	ServiceProvider serviceProvider;
	ServiceManager serviceManager;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        serviceProvider = new ServiceProvider("ana");
        serviceManager = new ServiceManager();
        
        ArrayList<Packet> packets = serviceProvider.getServicePacket();
        
        for(int i =0 ; i<packets.size() ; i++){
        	serviceManager.installService(packets.get(i));
        }
        
		
	
        
        //주고받기
		
		/*서버가 클라이언트에게 해당 서비스가 있는지 확인한다.
		 * 1. SmartTV가 스마트폰에 RESPONSE_SERVICE_NAME 시그니쳐를 이용해, 서비스 유무를 물어보고
		 * 2. 스마트폰 은 서비스가 있으면 바로 시작하고, 없으면  REQUEST_SERVICE_DATA를 보낸다. 
		 * 3. 스마트TV는 REQUEST_SERVICE_DATA를 받으면   RESPONSE_SERVICE_DATA와 HTML 자료를 담아서 보낸다. 
		 * */
		
		
		
		 
    }
}
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
        
		
	
        
        //�ְ�ޱ�
		
		/*������ Ŭ���̾�Ʈ���� �ش� ���񽺰� �ִ��� Ȯ���Ѵ�.
		 * 1. SmartTV�� ����Ʈ���� RESPONSE_SERVICE_NAME �ñ״��ĸ� �̿���, ���� ������ �����
		 * 2. ����Ʈ�� �� ���񽺰� ������ �ٷ� �����ϰ�, ������  REQUEST_SERVICE_DATA�� ������. 
		 * 3. ����ƮTV�� REQUEST_SERVICE_DATA�� ������   RESPONSE_SERVICE_DATA�� HTML �ڷḦ ��Ƽ� ������. 
		 * */
		
		
		
		 
    }
}
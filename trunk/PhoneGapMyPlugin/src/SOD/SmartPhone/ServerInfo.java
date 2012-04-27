package SOD.SmartPhone;

import java.net.InetSocketAddress;

abstract class ServerInfo
{
	/**
	 * 서비스의 끝점(EndPoint, IP Address와 port)
	 */
    InetSocketAddress EndPoint;
    
    /**
     * 서비스 이름
     */
    String ServiceName;


}

package SOD.SmartTV;

public class ServerConfig {
	
	/**
	 * 타임아웃 시간을 지정한다.
	 * 단위: ms
	 */
	public int Timeout;

	/**
	 * 연결 점검용 패킷을 보내는 주기를 지정한다.
	 * 단위: ms
	 */
	public int CheckPeriod;
	
	/**
	 * 최대 접속가능한 연결 수를 지정한다.
	 */
	public int MaxConnection;
	
	/**
	 * 서비스 이름을 지정한다.
	 */
	public String serviceName;
	
	/**
	 * 서비스 포트를 지정한다.
	 */
	public int Port;
}

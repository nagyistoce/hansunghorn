package sod.smarttv;

import sod.common.Constants;

public class ServerConfig {
	
	/**
	 * Ÿ�Ӿƿ� �ð��� �����Ѵ�.
	 * ����: ms
	 */
	public int Timeout;
	
	/**
	 * ���� ���˿� ��Ŷ�� ������ �ֱ⸦ �����Ѵ�.
	 * ����: ms
	 */
	public int CheckingPeriod;
	
	/**
	 * �ִ� ���Ӱ����� ���� ���� �����Ѵ�. (���� ������ ����)
	 */
	public int MaxConnection;
	
	/**
	 * ���� �̸��� �����Ѵ�.
	 */
	public String serviceName;
	
	/**
	 * ���� ��Ʈ�� �����Ѵ�.
	 */
	public int Port;
	
	public ServerConfig(){
		Timeout = Constants.Default_Timeout;
		CheckingPeriod = Constants.Default_CheckingPeriod;
	}
}

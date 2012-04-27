package SOD.SmartTV;

import SOD.Common.Packet;


/**
 * 
 * @author MB
 *
 */
abstract public class ServiceProvider {
	
	/**
	 * set files path where content files are stored.
	 * @param newpath
	 * 변경할 디렉토리 경로
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	abstract void setPath(String newpath) throws IllegalArgumentException;
	
	/**
	 * 서비스 데이터를 담고있는 패킷을 반환
	 * 최초 호출시 createServicePacket()이 내부적으로 호출된다.
	 * @return
	 * 서비스 데이터를 담은 패킷
	 */
    public abstract Packet getServicePacket();
	
	/**
	 * create packet which contains every content files need to service.
	 * @return
	 * 서비스 데이터를 담은 패킷
	 * @throws IllegalStateException
	 * 내부에 지정된 경로가 없거나 잘못되었을 경우 발생
	 */
	protected abstract Packet createServicePacket() throws IllegalStateException;
}

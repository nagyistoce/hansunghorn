package SOD.SmartPhone;

import java.net.SocketException;


/**
 * 
 * @author MB
 *
 */
abstract public class ServiceManager {

	/**
	 * 서비스가 설치되어 있는지 확인
	 * @param serviceName
	 * 서비스 이름
	 * @return
	 * 서비스가 설치되어 있으면 true 아니면 false
	 * @throws IllegalArgumentException
	 * 매게변수가 null일 때 IllegalArgumentException이 발생한다.
	 */
	abstract public boolean findServiceByName(String serviceName) throws IllegalArgumentException;

	/**
	 * 해당 이름의 서비스 시작
	 * @param serviceName
	 * 서비스 이름
	 * @throws IllegalArgumentException
	 * 매개변수가 null이거나 해당 이름의 서비스가 설치되지 않은 경우 발생
	 * @throws IllegalStateException
	 * 두번 이상 호출될 때 발생.
	 */
	abstract public void startService(String serviceName) throws IllegalArgumentException, IllegalStateException;

	/**
	 * 설치되어 있는 서비스 목록 반환
	 * @return
	 * 서비스 목록
	 */
	abstract public String[] getServiceList();

	/**
	 * 특정 서비스를 제거
	 * @param serviceName
	 * 서비스 이름
	 * @throws IllegalArgumentException
	 * 매개변수가 null이거나 해당 serviceName에 일치하는 서비스 데이터가 없을 경우 발생.
	 */
	abstract public void removeService(String serviceName) throws IllegalArgumentException;

	/**
	 * 서비스 다운로드시 호출할 콜백 함수 등록
	 * @param handler
	 * 콜백함수를 구현한 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null인 경우 발생.
	 */
	abstract public void setSeriveReceiveHandler(ServiceReceiveHandler handler) throws IllegalArgumentException;

	/**
	 * 서비스가 없을시 다운로드 요청
	 * 서비스 데이터를 요청하는 패킷 만들어서 전송
	 * @param acc
	 * 데이터를 다운로드하기 위해 사용되는 서버에 연결된 AccessManager 객체
	 * @throws IllegalArgumentException
	 * 매개변수가 null이거나 send를 호출 할 수 없는 상태인 경우 발생.
	 * @throws SocketException
	 * 넘겨준 AccessManager 객체 에서 예외가 발생시 전달
	 */
	abstract public void requestService(AccessManager acc) throws IllegalArgumentException, SocketException;
	
}

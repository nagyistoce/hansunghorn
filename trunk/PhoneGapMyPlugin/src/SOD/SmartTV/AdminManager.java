package SOD.SmartTV;

/**
 * 
 * @author MB
 *
 */
abstract public class AdminManager {

	/**
	 * create new administrator account with password.
	 * @param passwd
	 * 비밀번호
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	abstract void createAccount(String passwd) throws IllegalArgumentException;
	
	/**
	 * try login with password.
	 * @param passwd
	 * 비밀번호
	 * @return
	 * if password is correct, returns true. if not returns false.
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	abstract boolean login(String passwd) throws IllegalArgumentException;
	
	/**
	 * overwrite administrator information.
	 * @param info
	 * 관리자 정보
	 * @throws IllegalArgumentException
	 * 매개변수가 null일때 발생
	 */
	abstract void updateInfo(AdminInformation info) throws IllegalArgumentException;
	
	/**
	 * get current administrator information.
	 * @return
	 * 관리자정보
	 */
	abstract AdminInformation getInfo();
}

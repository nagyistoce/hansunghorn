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
	 * ��й�ȣ
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	abstract void createAccount(String passwd) throws IllegalArgumentException;
	
	/**
	 * try login with password.
	 * @param passwd
	 * ��й�ȣ
	 * @return
	 * if password is correct, returns true. if not returns false.
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	abstract boolean login(String passwd) throws IllegalArgumentException;
	
	/**
	 * overwrite administrator information.
	 * @param info
	 * ������ ����
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	abstract void updateInfo(AdminInformation info) throws IllegalArgumentException;
	
	/**
	 * get current administrator information.
	 * @return
	 * ����������
	 */
	abstract AdminInformation getInfo();
}

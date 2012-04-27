package SOD.Common;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 
 * @author MB
 *
 */
abstract public class Storage {
	
	/**
	 * ���ο� ����Ҹ� ������ �� ����ϴ� �޼ҵ��̴�. ������� ID�� �Ѱ��ָ� ����Ұ� �����ϴ����� Ȯ�� �� ��, ���� �� �ش� ����Ҹ� �����Ѵ�.
	 * @param storageID
	 * ����Ҹ� ���� �����Ҷ� ����� ������� ID�� �Ѱ��ش�. ������� ID�� �� ����Ҹ� ������ ���� ���ȴ�. ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * ������ ����Ҹ� �Ѱ��ش�.
	 * @throws IOException
	 * ������� ID�� ������� ����Ұ� �̹� ����ų� ����Ҹ� �����ϴ� �� �������� ���, IOException�� ������.
	 */
	static Storage create(String storageID) throws IOException{
		return null;
	}
	
	/**
	 * �̹� �����Ǿ� �ִ� ����Ҹ� ����ϱ� ���� �����ö� ����ϴ� �޼ҵ��̴�.
	 * @param storageID 
	 * ���������� �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * ������ ������� ID�� �̿��Ͽ� ������ �����
	 * @throws  IOException
	 * �����������ϴ� ����Ұ� ���� ��,  IOException�� ������.
	 */
	static public Storage getStorage(String storageID) throws IOException{
		return null;
	}	
	/**
	 * �̹� ������ ����Ҹ� �ı��� �� ����ϴ� �޼ҵ��̴�. ����� ���� ������ �����ϴ� ��쿡�� ����� ���� ������ �����ϰ� �ش� ����Ҹ� �ı��Ѵ�.
	 * @param storageID 
	 * �ı��ϰ��� �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @throws IOException
	 * ������� ID�� ���� ���� ������ �� �����Ƿ� IOException�� ������.
	 */
	abstract public void destroy(String storageID) throws IOException;
	
	
	
	/**
	 * ������� ����� ��ȯ�ϴ� �޼ҵ��̴�. ����ڿ��� ��ġ�Ǿ� �ִ� ������� ũ�⸦ �˷��ֱ����� ����Ѵ�.
	 * @param storageID
	 * �뷮�� �˱����� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * �뷮�� Byte ������ ����Ͽ� ���������� ��ȯ�Ѵ�.
	 */
	abstract public int getStorageSize(String storageID) throws IOException;
	
	/**
	 * �Ѱ��� ����� ID�� �ش��ϴ� ����� ���� ���ϵ��� ��� �����Ѵ�.
	 * @param storageID
	 * ������ �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * 
	 */
	abstract public void clear(String storageID) throws IOException;
	
	/**
	 * �Ѱ��� filter�� ȭ���ڸ� ���� ������ ����� ���� ��ȯ�Ѵ�.
	 * @param filter
	 * ��⸦ ���ϴ� ������ Ȯ����
	 * @return
	 *  �Ѱ��� filter�� Ȯ���ڸ� ���� ������ ����Ʈ 
	 */
	abstract public String [] getFileList(String filter) throws IllegalArgumentException;

	/**
	 * ����� ���� �̹� �����ϴ� ������ ����.
	 * @param path
	 * ������ �ϴ� ������ ��ġ�ϴ� ���
	 * @return
	 * ������ �ϴ� ����
	 * @throws FileNotFoundException
	 * �������ϴ� ������ ���� ��, FileNotFoundException�� ������.
	 */
	abstract public StorageFile openFile(String path) throws FileNotFoundException;
	
	/**
	 * ����� ���� ���ο� ������ �����.
	 * @param path
	 * ���ο� ������ �����ϱ� ���� ������ ���
	 * @return
	 * ���� ������ ����
	 * @throws IOException
	 * �̹� ������ ������ �����ϰų� ������ ����µ� ���� ���� ��쿡 IOException�� ������.
	 */
	abstract public StorageFile createFile(String path) throws IOException;
	
	/**
	 * ����� ���� ������ �����Ѵ�.
	 * @param path
	 * �����Ϸ��� �ϴ� ������ ���
	 * @throws  FileNotFoundException
	 * �����Ϸ��� ������ �������� ������ FileNotFoundException�� ������.
	 */
	abstract public void deleteFile(String path) throws FileNotFoundException;
	
	/**
	 * �ش� ����� ������ �����ϴ��� Ȯ���Ѵ�.
	 * @param path
	 * �����ϴ��� Ȯ���Ϸ��� �ϴ� ������ ���
	 * @return 
	 * ���縦 Ȯ���ϰ��� �ϴ� ������ ������ true, ������ false�� ��ȯ
	 */
	abstract public boolean checkIsFileExists(String path);
	
	/**
	 * �̹� �����ϴ� ������ �̸��� �ٲ۴�.
	 * @param oldPath
	 * �̸��� �ٲٱ� ��, ������ ���
	 * @param newPath
	 * �̸��� �ٲ� ��, ������ ���
	 * @throws FileNotFoundException
	 * �̸��� �����Ϸ��� ������ ã�� �� ���� ��, FileNotFoundException�� ������.
	 */
	abstract public void renameFile(String oldPath, String newPath) throws FileNotFoundException;
	
}

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
	 * 새로운 저장소를 생성할 때 사용하는 메소드이다. 저장소의 ID를 넘겨주면 저장소가 존재하는지를 확인 한 후, 없을 때 해당 저장소를 생성한다.
	 * @param storageID
	 * 저장소를 새로 생성할때 사용할 저장소의 ID를 넘겨준다. 저장소의 ID는 각 저장소를 구분할 때도 사용된다. 저장소 ID는 String 객체여야 한다.
	 * @return
	 * 생성된 저장소를 넘겨준다.
	 * @throws IOException
	 * 저장소의 ID로 만들어진 저장소가 이미 존재거나 저장소를 생성하는 데 실패했을 경우, IOException을 던진다.
	 */
	static Storage create(String storageID) throws IOException{
		return null;
	}
	
	/**
	 * 이미 생성되어 있는 저장소를 사용하기 위해 가져올때 사용하는 메소드이다.
	 * @param storageID 
	 * 가져오고자 하는 저장소의 ID이다.  저장소 ID는 String 객체여야 한다.
	 * @return
	 * 가져올 저장소의 ID를 이용하여 가져온 저장소
	 * @throws  IOException
	 * 가져오고자하는 저장소가 없을 때,  IOException을 던진다.
	 */
	static public Storage getStorage(String storageID) throws IOException{
		return null;
	}	
	/**
	 * 이미 생성된 저장소를 파괴할 때 사용하는 메소드이다. 저장소 내에 파일이 존재하는 경우에는 저장소 안의 파일을 삭제하고 해당 저장소를 파괴한다.
	 * @param storageID 
	 * 파괴하고자 하는 저장소의 ID이다.  저장소 ID는 String 객체여야 한다.
	 * @throws IOException
	 * 저장소의 ID가 없을 때는 삭제할 수 없으므로 IOException을 던진다.
	 */
	abstract public void destroy(String storageID) throws IOException;
	
	
	
	/**
	 * 저장소의 사이즈를 반환하는 메소드이다. 사용자에게 설치되어 있는 저장소의 크기를 알려주기위해 사용한다.
	 * @param storageID
	 * 용량을 알기위한 저장소의 ID이다.  저장소 ID는 String 객체여야 한다.
	 * @return
	 * 용량을 Byte 단위로 계산하여 정수형으로 반환한다.
	 */
	abstract public int getStorageSize(String storageID) throws IOException;
	
	/**
	 * 넘겨준 저장소 ID에 해당하는 저장소 안의 파일들을 모두 제거한다.
	 * @param storageID
	 * 비우고자 하는 저장소의 ID이다.  저장소 ID는 String 객체여야 한다.
	 * 
	 */
	abstract public void clear(String storageID) throws IOException;
	
	/**
	 * 넘겨준 filter의 화장자를 가진 파일의 목록을 만들어서 반환한다.
	 * @param filter
	 * 얻기를 원하는 파일의 확장자
	 * @return
	 *  넘겨준 filter의 확장자를 가진 파일의 리스트 
	 */
	abstract public String [] getFileList(String filter) throws IllegalArgumentException;

	/**
	 * 저장소 내에 이미 존재하는 파일을 연다.
	 * @param path
	 * 열고자 하는 파일이 위치하는 경로
	 * @return
	 * 열고자 하는 파일
	 * @throws FileNotFoundException
	 * 열고자하는 파일이 없을 때, FileNotFoundException을 던진다.
	 */
	abstract public StorageFile openFile(String path) throws FileNotFoundException;
	
	/**
	 * 저장소 내에 새로운 파일을 만든다.
	 * @param path
	 * 새로운 파일을 생성하기 위한 파일의 경로
	 * @return
	 * 새로 생성된 파일
	 * @throws IOException
	 * 이미 동명의 파일이 존재하거나 파일을 만드는데 실패 했을 경우에 IOException을 날린다.
	 */
	abstract public StorageFile createFile(String path) throws IOException;
	
	/**
	 * 저장소 내의 파일은 삭제한다.
	 * @param path
	 * 삭제하려고 하는 파일의 경로
	 * @throws  FileNotFoundException
	 * 삭제하려는 파일이 존재하지 않으면 FileNotFoundException을 던진다.
	 */
	abstract public void deleteFile(String path) throws FileNotFoundException;
	
	/**
	 * 해당 경로의 파일이 존재하는지 확인한다.
	 * @param path
	 * 존재하는지 확인하려고 하는 파일의 경로
	 * @return 
	 * 존재를 확인하고자 하는 파일이 있으면 true, 없으면 false를 반환
	 */
	abstract public boolean checkIsFileExists(String path);
	
	/**
	 * 이미 존재하는 파일의 이름을 바꾼다.
	 * @param oldPath
	 * 이름을 바꾸기 전, 파일의 경로
	 * @param newPath
	 * 이름을 바꾼 후, 파일의 경로
	 * @throws FileNotFoundException
	 * 이름을 변경하려는 파일을 찾을 수 없을 때, FileNotFoundException을 던진다.
	 */
	abstract public void renameFile(String oldPath, String newPath) throws FileNotFoundException;
	
}

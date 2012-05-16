package sod.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.os.Environment;

/**
 * 
 * @author MB
 *
 */
public class Storage {
	
	final static String SOD = "/sod/";
	public final static int READ = 0;
	public final static int WRITE = 1;
	
	String sdPath;
	String sodRootPath;
	String sodStoragePath;
	
	String storageID;
	
	File directory;
	
	Storage(String mSdPath, String mStorageID){
		sdPath = mSdPath;
		sodRootPath = sdPath + SOD;
		
		File sod = new File (sodRootPath);
		
		if(!sod.exists())
			sod.mkdir();
		
		sodStoragePath = sodRootPath +mStorageID;
		
		directory = new File(sodStoragePath);
		
	}
	
	protected boolean isExist(){
		return directory.exists();
	}
	
	protected boolean createDirectory(){
		return directory.mkdir();
	}
	
	protected String [] getFileList(){
		return directory.list();
	}
	
	protected File getDirectory(){
		return directory;
	}
	
	public String getSODStoragePath(){
		return sodStoragePath;
	}
	/**
	 * 새로운 저장소를 생성할 때 사용하는 메소드이다. 저장소의 ID를 넘겨주면 저장소가 존재하는지를 확인 한 후, 없을 때 해당 저장소를 생성한다.
	 * storageID 는 serviceName이다.
	 * @param storageID
	 * 저장소를 새로 생성할때 사용할 저장소의 ID를 넘겨준다. 저장소의 ID는 각 저장소를 구분할 때도 사용된다. 저장소 ID는 String 객체여야 한다.
	 * @return
	 * 생성된 저장소를 넘겨준다.
	 * @throws IOException
	 * 저장소의 ID로 만들어진 저장소가 이미 존재거나 저장소를 생성하는 데 실패했을 경우, IOException을 던진다.
	 */
	public static Storage create(String storageID) throws IOException, IllegalArgumentException{
		
		if(storageID == null)
			throw new IllegalArgumentException();
		
		String ext = Environment.getExternalStorageState();
		String mSdPath;
		
        if(ext.equals(Environment.MEDIA_MOUNTED)){
        	mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
        	mSdPath = Environment.MEDIA_UNMOUNTED;
        }
        
        Storage storage = new Storage(mSdPath, storageID);
        
        //해당 storageID에 해당하는 디렉토리(저장소)가 이미 '존재하면' 예외를 던진다..
        if(storage.isExist())
        	throw new IOException();
        else{//존재하지 않으면 해당 디렉토리(저장소)를 생성한다.
        	
        	if( !storage.createDirectory() )
        		throw new IOException();
     
        }
       
        return storage;
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
		
		String ext = Environment.getExternalStorageState();
		String mSdPath;
		
        if(ext.equals(Environment.MEDIA_MOUNTED)){
        	mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
        	mSdPath = Environment.MEDIA_UNMOUNTED;
        }
        
        Storage storage = new Storage(mSdPath, storageID);
        
        //해당 storageID에 해당하는 디렉토리(저장소)가  '존재하지 않으면' 예외를 던진다..
        if(!storage.isExist()){
        	throw new IOException();
        }
       
        return storage;
	}
	
	/**
	 * 이미 생성된 저장소를 파괴할 때 사용하는 메소드이다. 저장소 내에 파일이 존재하는 경우에는 저장소 안의 파일을 삭제하고 해당 저장소를 파괴한다.
	 * @param storageID 
	 * 파괴하고자 하는 저장소의 ID이다.  저장소 ID는 String 객체여야 한다.
	 * @throws IOException
	 * 저장소의 ID가 없을 때는 삭제할 수 없으므로 IOException을 던진다.
	 */
	static public void destroy(String storageID) throws IOException{
		
		Storage storage = getStorage(storageID);
		
		String [] fileNames = storage.getFileList();
		
		if(fileNames == null)
			throw new IOException();
		
		for(int i=0 ; i<fileNames.length; i++){
			File storageFile = new File(storage.getDirectory(), fileNames[i]);
			storageFile.delete();
		}
		
		storage.getDirectory().delete();

	}
	
	
	
	/**
	 * 저장소의 사이즈를 반환하는 메소드이다. 사용자에게 설치되어 있는 저장소의 크기를 알려주기위해 사용한다.
	 * @param storageID
	 * 용량을 알기위한 저장소의 ID이다.  저장소 ID는 String 객체여야 한다.
	 * @return
	 * 용량을 Byte 단위로 계산하여 정수형으로 반환한다.
	 */
	static public int getStorageSize(String storageID) throws IOException{
		
		Storage storage = getStorage(storageID);
		
		String [] fileNames = storage.getFileList();
		
		int returnStorageSize = 0;
		
		if(fileNames == null)
			throw new IOException();
		
		for(int i=0 ; i<fileNames.length; i++){
			File storageFile = new File(storage.getDirectory(), fileNames[i]);
			returnStorageSize += (int) storageFile.length();
		}
		
		return returnStorageSize;
	}
	
	/**
	 * 넘겨준 저장소 ID에 해당하는 저장소 안의 파일들을 모두 제거한다.
	 * @param storageID
	 * 비우고자 하는 저장소의 ID이다.  저장소 ID는 String 객체여야 한다.
	 * 
	 */
	static public void clear(String storageID) throws IOException{
		
		Storage storage = getStorage(storageID);
		String [] fileNames = storage.getFileList();
		
		if(fileNames == null)
			throw new IOException();
		
		for(int i=0 ; i<fileNames.length; i++){
			File storageFile = new File(storage.getDirectory(), fileNames[i]);
			storageFile.delete();
		}
		
	}
	
	/**
	 * 넘겨준 filter의 화장자를 가진 파일의 목록을 만들어서 반환한다.
	 * @param filter
	 * 얻기를 원하는 파일의 확장자
	 * @return
	 *  넘겨준 filter의 확장자를 가진 파일의 리스트 
	 */
	public String [] getFileList(String filter) throws IllegalArgumentException{
		
		String [] returnStringList = null;
		
		if(filter.equals("*"))
			returnStringList = directory.list();
		//else ToDo...
		
		return returnStringList;
	}

	/**
	 * 저장소 내에 이미 존재하는 파일을 연다.
	 * @param filePath
	 * 열고자 하는 파일이 위치하는 경로
	 * @return
	 * 열고자 하는 파일
	 * @throws FileNotFoundException
	 * 열고자하는 파일이 없을 때, FileNotFoundException을 던진다.
	 * @throws NullPointerException
	 * fileName이 null이래서 파일객체 자체 생성에 실패하면 NullPointerException을 던진다.
	 * @throw IOException
	 * StorageFile 객체 생성에 실패하면 IOException을 던진다.
	 * @throw IllegalArgumentException
	 * mode에 READ(0)와 WRITE(1)제외한 값이 발생하면 IllegalArgumentException 을 던진다.
	 */
	public StorageFile openFile(String filePath, int mode) throws FileNotFoundException, NullPointerException, IOException, IllegalArgumentException{
		File file = new File(directory, filePath);
		
		//존재하지 않으면 FileNotFoundException을 던진다.
		if( !file.exists() )
			throw new FileNotFoundException();
		
		StorageFile returnStorageFile = null;
		switch(mode){
		case READ:
			returnStorageFile = StorageFileR.getStorageFile(file);
			break;
			
		case WRITE:
			returnStorageFile = StorageFileW.getStorageFile(file);
			break;
		default:
			throw new IllegalArgumentException();
		}
	
		return returnStorageFile;
	}
	
	
	/**
	 * 저장소 내에 새로운 파일을 만든다.
	 * @param filePath
	 * 새로운 파일을 생성하기 위한 파일의 경로
	 * @return
	 * 새로 생성된 파일
	 * @throws IOException
	 * 이미 동명의 파일이 존재하거나 파일을 만드는데 실패(StrageFile 객체 생성 실패) 했을 경우에 IOException을 날린다. 
	 * @throws NullPointerException
	 * fileName이 null이래서 파일객체 자체 생성에 실패하면 NullPointerException을 던진다.
	 */
	public StorageFile createFile(String filePath) throws IOException, NullPointerException{
		
		File file = new File(directory, filePath);
		
		//이미존재하면 IOException을 던진다.
		if( file.exists() )
			throw new IOException();
		
		StorageFile returnStorageFile = StorageFileW.createStorageFile(file);
	
		return returnStorageFile;
	}
	
	/**
	 * 저장소 내의 파일을 삭제한다.
	 * @param filePath
	 * 삭제하려고 하는 파일의 경로
	 * @throws  FileNotFoundException
	 * 삭제하려는 파일이 존재하지 않으면 FileNotFoundException을 던진다.
	 * @throws NullPointerException
	 * fileName이 null이래서 파일객체 자체 생성에 실패하면 NullPointerException을 던진다.
	 */
	public void deleteFile(String filePath) throws FileNotFoundException, NullPointerException{
		
		File file = new File(directory, filePath);
		
		if( !file.exists() )
			throw new FileNotFoundException();
		
		file.delete();
	}
	
	/**
	 * 해당 경로의 파일이 존재하는지 확인한다. 경로는 /sod/StorageID 밑으로만 검색이 가능하다.
	 * @param filePath
	 * 존재하는지 확인하려고 하는 파일의 경로
	 * @return 
	 * 존재를 확인하고자 하는 파일이 있으면 true, 없으면 false를 반환
	 */
	public boolean checkIsFileExists(String filePath){
		
		File file = new File(directory, filePath);
		
		return file.exists();
	}
	
	/**
	 * 이미 존재하는 파일의 이름을 바꾼다.
	 * @param oldPath
	 * 이름을 바꾸기 전, 파일의 경로
	 * @param newPath
	 * 이름을 바꾼 후, 파일의 경로
	 * @throws FileNotFoundException
	 * 이름을 변경하려는 파일을 찾을 수 없을 때, FileNotFoundException을 던진다.
	 * @throws NullPointerException
	 * Path가 null이래서 파일객체 자체 생성에 실패하면 NullPointerException을 던진다.
	 */
	public void renameFile(String oldPath, String newPath) throws FileNotFoundException, NullPointerException{
		File oldFile = new File(directory, oldPath);
		File newFile = new File(directory, newPath);
		
		if( !oldFile.exists() )
			new FileNotFoundException();
		
		oldFile.renameTo(newFile);
	}
	
}

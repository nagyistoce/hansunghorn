package sod.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

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
	public final static int WRITE_PLUS = 2;
	
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
			sod.mkdirs();
		
		sodStoragePath = sodRootPath +mStorageID;
		
		directory = new File(sodStoragePath);
		
	}
	
	/**
	 * 'directory ���ϰ�ü'�� �����ϴ��� Ȯ���ϴ� �޼ҵ�
	 * @return ���丮�� �����ϸ� true, �ƴϸ� false
	 */
	protected boolean isExist(){
		return directory.exists();
	}
	
	/**
	 * 'directory ���ϰ�ü'�� �ִ� ��δ�� ���ο� ���丮�� �����Ѵ�.
	 * @return
	 */
	protected boolean createDirectory(){
		return directory.mkdirs();
	}
	
	/**
	 * 'directory ���ϰ�ü'�� �ִ� ��ο� �ִ� ���ϵ��� ����Ʈ�� ��ȯ�Ѵ�.
	 * @return
	 */
	public String [] getFileList(){
		return directory.list();
	}
	
	/**
	 * 'directory ���ϰ�ü'�� �޾ƿ´�.
	 * @return
	 */
	protected File getDirectory(){
		return directory;
	}
	
	/**
	 * SOD ��Ʈ ������� �����θ� ��ȯ�Ѵ�.
	 * @return SOD��Ʈ ������� ������ ex(android) : /mnt/sdcard/sod
	 */
	public String getSODrootPath(){
		return sodStoragePath;
	}
	
	/**
	 * ���ο� ����Ҹ� ������ �� ����ϴ� �޼ҵ��̴�. ������� ID�� �Ѱ��ָ� ����Ұ� �����ϴ����� Ȯ�� �� ��, ���� �� �ش� ����Ҹ� �����Ѵ�.
	 * storageID �� serviceName�̴�.
	 * @param storageID
	 * ����Ҹ� ���� �����Ҷ� ����� ������� ID�� �Ѱ��ش�. ������� ID�� �� ����Ҹ� ������ ���� ���ȴ�. ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * ������ ����Ҹ� �Ѱ��ش�.
	 * @throws IOException
	 * ������� ID�� ������� ����Ұ� �̹� ����ų� ����Ҹ� �����ϴ� �� �������� ���, IOException�� ������.
	 * @throws IllegalArgumentException
	 * stroageID�� null�̸� IllegalArgumentException�� ������.
	 */
	public static Storage createStorage(String storageID) throws IOException, IllegalArgumentException{
		
		if(storageID == null)
			throw new IllegalArgumentException();
		
		//getExternalStorageState()�� SDī�尡 ����Ʈ ���ִ��� ���θ� ��ȯ�մϴ�.
		String ext = Environment.getExternalStorageState();
		String mSdPath;
		
        if(ext.equals(Environment.MEDIA_MOUNTED)){
        	//getExternalStorageDirectory()�� SDī�尡 ����Ʈ�� ��θ� ��ȯ�մϴ�
        	mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
        	mSdPath = Environment.MEDIA_UNMOUNTED;
        }
        
        //���� SDī���� ��ο�, StroageID�� Storage�� �����Ѵ�.
        Storage storage = new Storage(mSdPath, storageID);
        
        //�ش� storageID�� �ش��ϴ� ���丮(�����)�� �̹� '�����ϸ�' ���ܸ� ������..
        if(storage.isExist())
        	throw new IOException();
        else{//�������� ������ �ش� ���丮(�����)�� �����Ѵ�.
        	
        	if( !storage.createDirectory() )
        		throw new IOException();
     
        }
       
        return storage;
	}
	
	/**
	 * �̹� �����Ǿ� �ִ� ����Ҹ� ����ϱ� ���� �����ö� ����ϴ� �޼ҵ��̴�.
	 * @param storageID 
	 * ���������� �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * ������ ������� ID�� �̿��Ͽ� ������ �����
	 * @throws  IOException
	 * �����������ϴ� ����Ұ� ���� ��,  IOException�� ������.
	 *  @throws IllegalArgumentException
	 * stroageID�� null�̸� IllegalArgumentException�� ������.
	 */
	static public Storage getStorage(String storageID) throws IOException, IllegalArgumentException{
		
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
        
        //�ش� storageID�� �ش��ϴ� ���丮(�����)��  '�������� ������' ���ܸ� ������..
        if(!storage.isExist()){
        	throw new IOException();
        }
       
        return storage;
	}
	
	/**
	 * �ش� storageID�� ����Ұ� �ִ��� Ȯ���Ѵ�.
	 * @param storageID
	 * �ִ��� ������ Ȯ���� ������� ID
	 * @return
	 * ����Ұ� ������ true ������  false
	 * @throws IllegalArgumentException
	 * stroageID�� null�̸� IllegalArgumentException�� ������.
	 */
	static public boolean checkStorageIs(String storageID) throws IllegalArgumentException{
		
		if(storageID == null)
			throw new IllegalArgumentException();
		
		//getExternalStorageState()�� SDī�尡 ����Ʈ ���ִ��� ���θ� ��ȯ�մϴ�.
		String ext = Environment.getExternalStorageState();
		String mSdPath;
		
        if(ext.equals(Environment.MEDIA_MOUNTED)){
        	//getExternalStorageDirectory()�� SDī�尡 ����Ʈ�� ��θ� ��ȯ�մϴ�
        	mSdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
        	mSdPath = Environment.MEDIA_UNMOUNTED;
        }
        
        //���� SDī���� ��ο�, StroageID�� Storage�� �����Ѵ�.
        Storage storage = new Storage(mSdPath, storageID);
        
        //�ش� storageID�� �ش��ϴ� ���丮(�����)�� �̹� �����ϸ� true
        boolean returnBoolean;
        if(storage.isExist())
        	returnBoolean = true;
        else{//�������� ������ false;
        	returnBoolean = false;
        }
        
        return returnBoolean;
		
	}
	
	/**
	 * �̹� ������ ����Ҹ� �ı��� �� ����ϴ� �޼ҵ��̴�. ����� ���� ������ �����ϴ� ��쿡�� ����� ���� ������ �����ϰ� �ش� ����Ҹ� �ı��Ѵ�.
	 * @param storageID 
	 * �ı��ϰ��� �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @throws IOException
	 * ������� ID�� ���� ���� ������ �� �����Ƿ� IOException�� ������.
	 * @throws IllegalArgumentException
	 * stroageID�� null�̸� IllegalArgumentException�� ������.
	 */
	static public void destroy(String storageID) throws IOException, IllegalArgumentException{
		if(storageID == null)
			throw new IllegalArgumentException();
		
		if( !Storage.checkStorageIs(storageID) )
			throw new IOException();
		
		Storage storage = getStorage(storageID);
		
		deleteFolder(storage.getDirectory());
		/*
		String [] fileNames = storage.getFileList();
		
		if(fileNames == null)
			throw new IOException();
		
		//����� ������ �ִ� ��� ���ϵ��� �����Ѵ�.
		for(int i=0 ; i<fileNames.length; i++){
			File storageFile = new File(storage.getDirectory(), fileNames[i]);
			storageFile.delete();
		}
		
		//����ҿ� �����ϴ� ���丮�� �����Ѵ�.
		storage.getDirectory().delete();
		*/
	}

	/**
	 * ���丮�� �����Ѵ�. 
	 * @param targetFolder
	 * @return
	 */
	static protected boolean deleteFolder(File targetFolder) {

		File[] childFile = targetFolder.listFiles();
		boolean confirm = false;
		int size = childFile.length;

		if (size > 0) {
			for (int i = 0; i < size; i++) {
				if (childFile[i].isFile()) {
					confirm = childFile[i].delete();
				} else {
					deleteFolder(childFile[i]);
				}
			}
		}

		targetFolder.delete();
		return (!targetFolder.exists());

	}
	
	
	/**
	 * ������� ����� ��ȯ�ϴ� �޼ҵ��̴�. ����ڿ��� ��ġ�Ǿ� �ִ� ������� ũ�⸦ �˷��ֱ����� ����Ѵ�.
	 * @param storageID
	 * �뷮�� �˱����� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @return
	 * �뷮�� Byte ������ ����Ͽ� ���������� ��ȯ�Ѵ�.
	 * @throws IllegalArgumentException
	 * stroageID�� null�̸� IllegalArgumentException�� ������.
	 */
	static public int getStorageSize(String storageID) throws IOException, IllegalArgumentException{
		if(storageID == null)
			throw new IllegalArgumentException();
		
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
	 * �Ѱ��� ����� ID�� �ش��ϴ� ����� ���� ���ϵ��� ��� �����Ѵ�.
	 * @param storageID
	 * ������ �ϴ� ������� ID�̴�.  ����� ID�� String ��ü���� �Ѵ�.
	 * @throws IOException
	 * FileList�� �޾ƿ��� ���ϸ� IOException�� ������.
	 * @throws IllegalArgumentException
	 * stroageID�� null�̸� IllegalArgumentException�� ������.
	 */
	static public void clear(String storageID) throws IOException{
		if(storageID == null)
			throw new IllegalArgumentException();
		
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
	 * �Ѱ��� filter�� ������ ������ ����� ���� ��ȯ�Ѵ�.
	 * "*"�� �Է��ϸ� ��� ������ ����� ��ȯ�Ѵ�.
	 * ".txt"��� �ϸ� txt�� Ȯ������ ������ ����� ��ȯ�Ѵ�.
	 * "ew"��� �ϸ� ew��� Ű���尡 ���Ե� ������ ����� ��ȯ�Ѵ�.
	 * @param keyword
	 * ��⸦ ���ϴ� ������ Ȯ����
	 * @return
	 *  �Ѱ��� keyword�� ������ ������ ����Ʈ 
	 */
	public String [] getFileList(String keyword) throws IllegalArgumentException{
		
		String [] returnFileList = null;
		
		if(keyword.equals("*"))
			returnFileList = directory.list();
		else{
			String [] fileList = directory.list();
			ArrayList<String> filteringFileList = new ArrayList<String>();
			
			for(int i = 0 ; i<fileList.length  ; i++){
				if( fileList[i].indexOf(keyword) != -1 )
					filteringFileList.add(fileList[i]);
			}
			
			returnFileList = new String[filteringFileList.size()];
			
			for(int i = 0; i<returnFileList.length ; i++){
				returnFileList[i] = filteringFileList.get(i);
			}
			
			
		}
		//else ToDo...
		
		return returnFileList;
	}

	/**
	 * ����� ���� �̹� �����ϴ� ������ ����.
	 * @param filePath
	 * ������ �ϴ� ������ ��ġ�ϴ� ���
	 * @return
	 * ������ �ϴ� ����
	 * @throws FileNotFoundException
	 * �������ϴ� ������ ���� ��, FileNotFoundException�� ������.
	 * @throws NullPointerException
	 * fileName�� null�̷��� ���ϰ�ü ��ü ������ �����ϸ� NullPointerException�� ������.
	 * @throw IOException
	 * StorageFile ��ü ������ �����ϸ� IOException�� ������.
	 * @throw IllegalArgumentException
	 * mode�� READ(0)�� WRITE(1)������ ���� �߻��ϸ� IllegalArgumentException �� ������.
	 */
	public StorageFile openFile(String filePath, int mode) throws FileNotFoundException, NullPointerException, IOException, IllegalArgumentException{
		File file = new File(directory, filePath);
		
		//�������� ������ FileNotFoundException�� ������.
		if( !file.exists() )
			throw new FileNotFoundException();
		
		StorageFile returnStorageFile = null;
		
		returnStorageFile = StorageFile.getStorageFile(file, mode, filePath);
		
	
		return returnStorageFile;
	}
	
	
	/**
	 * ����� ���� ���ο� ������ �����.
	 * @param filePath
	 * ���ο� ������ �����ϱ� ���� ������ ���
	 * @return
	 * ���� ������ ����
	 * @throws IOException
	 * �̹� ������ ������ �����ϰų� ������ ����µ� ����(StrageFile ��ü ���� ����) ���� ��쿡 IOException�� ������. 
	 * @throws NullPointerException
	 * fileName�� null�̷��� ���ϰ�ü ��ü ������ �����ϸ� NullPointerException�� ������.
	 */
	public StorageFile createFile(String filePath) throws IOException, NullPointerException{
		
		File file = new File(directory, filePath);
		
		//�̹������ϸ� IOException�� ������.
		if( file.exists() )
			throw new IOException();
		
		StorageFile returnStorageFile = StorageFile.createStorageFile(file, filePath);
	
		return returnStorageFile;
	}
	
	/**
	 * ����� ���� ������ �����Ѵ�.
	 * @param filePath
	 * �����Ϸ��� �ϴ� ������ ���
	 * @throws  FileNotFoundException
	 * �����Ϸ��� ������ �������� ������ FileNotFoundException�� ������.
	 * @throws NullPointerException
	 * fileName�� null�̷��� ���ϰ�ü ��ü ������ �����ϸ� NullPointerException�� ������.
	 */
	public void deleteFile(String filePath) throws FileNotFoundException, NullPointerException{
		
		File file = new File(directory, filePath);
		
		if( !file.exists() )
			throw new FileNotFoundException();
		
		file.delete();
	}
	
	/**
	 * �ش� ����� ������ �����ϴ��� Ȯ���Ѵ�. ��δ� /StorageName/StorageID �����θ� �˻��� �����ϴ�.
	 * @param filePath
	 * �����ϴ��� Ȯ���Ϸ��� �ϴ� ������ ���
	 * @return 
	 * ���縦 Ȯ���ϰ��� �ϴ� ������ ������ true, ������ false�� ��ȯ
	 */
	public boolean checkFileIs(String filePath){
		
		File file = new File(directory, filePath);
		
		return file.exists();
	}
	
	/**
	 * �̹� �����ϴ� ������ �̸��� �ٲ۴�.
	 * @param oldName
	 * �̸��� �ٲٱ� ��, ������ ���
	 * @param newName
	 * �̸��� �ٲ� ��, ������ ���
	 * @throws FileNotFoundException
	 * �̸��� �����Ϸ��� ������ ã�� �� ���� ��, FileNotFoundException�� ������.
	 * @throws NullPointerException
	 * Path�� null�̷��� ���ϰ�ü ��ü ������ �����ϸ� NullPointerException�� ������.
	 */
	public void renameFile(String oldName, String newName) throws FileNotFoundException, NullPointerException{
		File oldFile = new File(directory, oldName);
		File newFile = new File(directory, newName);
		
		if( !oldFile.exists() )
			new FileNotFoundException();
		
		oldFile.renameTo(newFile);
	}
	
}

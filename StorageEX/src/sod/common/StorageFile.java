package sod.common;


import java.io.EOFException;
import java.io.File;
import java.io.IOException;


enum SeekOrigin
{
    Begin,
    Current,
    End,
}

/**
 * 
 * @author MB
 *
 */
public abstract class StorageFile {
	File file;
	
	
	StorageFile() {
		
		
	}
	

	
	/**
	 * 
	 * @param mFile
	 * @throws IOException
	 * 파일 생성에 실패하면 IOException을 던진다.
	 * @throws NullPointerException
	 * 인자로 넘어온 값이 Null이면 NullPointerException을 던진다.
	 */
	 static public StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
		return null;
		 
	 }
	
	/**
	 * 
	 * @param mFile
	 * @return
	 * @throws IOException
	 * 파일 생성에 실패하면 IOException을 던진다.
	 * @throws NullPointerException
	 * 인자로 넘어온 값이 Null이면 NullPointerException을 던진다.
	 */
	static public StorageFile getStorageFile(File mFile)throws IOException, NullPointerException{
		return null;
	}
	
	

	/**
	 * 파일내에 파일 포인터가 가르치는 곳을 offset만큼 이동한다.
	 * (아직 미구현)
	 * @param offset
	 * 파일포인터를 이동하고자 하는 위치
	 * @param seekorigin
	 * 파일을 이동하기 전에 파일포인터의 위치
	 * @throws EOFException
	 * 파일 포인터를 이동하다가 파일의 끝에 도달하면 EOFException을 던진다.
	 */
	public void seek(int offset, SeekOrigin seekorigin) throws EOFException{
		
	}
	
	/**
	 * 현재의 파일포인터의 위치를 얻는다.
	 * (아직 미구현)
	 */
	public void getPosition(){
		
	}
	
	/**
	 * 파일의 길이를 가져온다.
	 * 단위는 byte
	 */
	public int getLength(){
		return (int) file.length();
	}
	
	/**
	 * 파일의 내용을 저장소 상에 기록한다.
	 * @throws IOException 
	 * 이미 닫혀있으면 IOException 을 던진다.
	 */
	public abstract void flush() throws IOException;
	
	/**
	 * 파일을 닫고 저장한다.
	 * @throws IOException 
	 * 이미 닫혀있으면 IOException 을 던진다.
	 */
	public abstract void close() throws IOException;
}

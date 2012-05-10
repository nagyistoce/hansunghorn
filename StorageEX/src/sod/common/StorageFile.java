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
public class StorageFile {
	File file;
	
	
	StorageFile(File mFile){
		file = mFile;
	}
	
	/**
	 * 해당 파일에 데이터를 쓴다.
	 * @param buf
	 * 파일에 쓰고자하는 내용을 바이트 배열로 직렬화한 것
	 * @param index
	 * 버퍼에서의 쓰고자하는 부분의 시작위치
	 * @param length
	 * 쓰고자하는 바이트 배열의 index부터의 길이
	 * @throws  IOException
	 * 파일에 쓰기를 실패하면  IOException을 던진다.
	 */
	public void write(byte[] buf, int index, int length) throws IOException{
		
	}
	
	/**
	 * 해당 파일의 데이터를 읽는다.
	 * @param buf
	 * 파일에서 읽은 내용을 담기위한 버퍼
	 * @param index
	 * 파일에서 데이터를 읽을 때, 버퍼에서 담기 시작할 위치
	 * @param length
	 * 버퍼를 사용하여 읽어올 길이
	 * @throws  IOException
	 * 파일에 읽기를 실패하면  IOException을 던진다.
	 * @throws EOFException
	 * 파일을 읽다가 파일의 끝에 도달하면 EOFException을 던진다.
	 */
	public void read(byte[] buf, int index, int length) throws IOException, EOFException{
		
	}
	
	/**
	 * 파일내에 파일 포인터가 가르치는 곳을 offset만큼 이동한다.
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
	 */
	public void getPosition(){
		
	}
	
	/**
	 * 파일의 길이를 가져온다.
	 */
	public void getLength(){
		
	}
	
	/**
	 * 파일의 내용을 저장소 상에 기록한다.
	 */
	public void flush(){
		
	}
	
	/**
	 * 파일을 닫는다.
	 */
	public void close(){
		
	}
}

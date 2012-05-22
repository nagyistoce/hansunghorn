package sod.common;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
public  class StorageFile {
	File file;
	
	FileInputStream fileInputStream;
	BufferedInputStream in;
	
	FileOutputStream fileOutputStream;
	BufferedOutputStream out;
	StringBuilder mBuf;
	
	final static int READ = 0;
	final static int WRITE = 1;
	
	int mode = 0;
	
	int position;
	
	StorageFile() {
		
		
	}
	

	
	/**
	 * 
	 * @param mFile
	 * @throws IOException
	 * ���� ������ �����ϸ� IOException�� ������.
	 * @throws NullPointerException
	 * ���ڷ� �Ѿ�� ���� Null�̸� NullPointerException�� ������.
	 */
	 static public StorageFile createStorageFile(File mFile)throws IOException, NullPointerException{
	 
		StorageFile storageFile = new StorageFile();
	 
		storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
		
		storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
		storageFile.mode = WRITE;
		
		return storageFile;
	 }
	
	/**
	 * 
	 * @param mFile
	 * @return
	 * @throws IOException
	 * ���� ������ �����ϸ� IOException�� ������.
	 * @throws NullPointerException
	 * ���ڷ� �Ѿ�� ���� Null�̸� NullPointerException�� ������.
	 */
	static public StorageFile getStorageFile(File mFile, int mode)throws IOException, NullPointerException{
		StorageFile storageFile = new StorageFile();
		
		storageFile.file = mFile;	
		 
		switch(mode){
		case READ:
			storageFile.fileInputStream = new FileInputStream(storageFile.file);
			
			storageFile.in = new BufferedInputStream(storageFile.fileInputStream);
			storageFile.mode = READ;
			break;
		case WRITE:
			storageFile.fileOutputStream = new FileOutputStream(storageFile.file);
			
			storageFile.out = new BufferedOutputStream(storageFile.fileOutputStream);
			storageFile.mode = WRITE;
			break;
		}
		return storageFile;
	}

	/**
	 * byte�迭�� ���Ͽ� �ִ� ��� �����͸� ��ƿ´�.
	 * 
	 * @param buf
	 * ���Ͽ��� ���� ������ ������� ����
	 * @throws IOException
	 * �̹� close�� ����ưų� ���Ͽ� �б⸦ �����ϸ�  IOException�� ������.
	 * @throws EOFException
	 * ������ �дٰ� ������ ���� �����ϸ� EOFException�� ������
	 * @throws NullPointerException
	 * mode�� WRITE�ε� read�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void read(byte[] buf) throws IOException,  EOFException{
		
		if(mode != READ)
			DebugUtils.throwException();
		
		while(in.read(buf) != -1) {;}
		
	}
	
	/**
	 * �ش� ������ �����͸� �д´�.
	 * @param buf
	 * ���Ͽ��� ���� ������ ������� ����
	 * @param index
	 * ���Ͽ��� �����͸� ���� ��, ���ۿ��� ��� ������ ��ġ
	 * @param length
	 * ���۸� ����Ͽ� �о�� ����
	 * @throws  IOException
	 * �̹� close�� ����ưų� ���Ͽ� �б⸦ �����ϸ�  IOException�� ������.
	 * @throws EOFException
	 * ������ �дٰ� ������ ���� �����ϸ� EOFException�� ������.
	 * @throws NullPointerException
	 * mode�� WRITE�ε� read�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void read(byte[] buf, int index, int length) throws IOException, EOFException{
		
		if(mode != READ)
			DebugUtils.throwException();
		
		for(int i = 0 ; i<index ; i++){
			if (in.read() == -1)
				break;
		}
		
		int returnInt;	
		returnInt = in.read(buf, 0, length);
		
		
		if(returnInt == -1)
			throw new EOFException();
	}
	
	/**
	 * 
	 * @param buf
	 * ���Ͽ��� ���� ������ ������� ����
	 * @throws IOException
	 *  �̹� close�� ����ưų� ���Ͽ� ���⸦ �����ϸ�  IOException�� ������.
	 * @throws NullPointerException
	 * mode�� READ�ε� write�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void write(byte [] buf) throws IOException{
		
		if(mode != WRITE)
			DebugUtils.throwException();
		
		out.write(buf);
	}
	
	/**
	 * �ش� ���Ͽ� �����͸� ����.
	 * @param buf
	 * ���Ͽ� �������ϴ� ������ ����Ʈ �迭�� ����ȭ�� ��
	 * @param index
	 * ���ۿ����� �������ϴ� �κ��� ������ġ
	 * @param length
	 * �������ϴ� ����Ʈ �迭�� index������ ����
	 * @throws  IOException
	 * ���Ͽ� ���⸦ �����ϸ�  IOException�� ������.
	 * @throws NullPointerException
	 * mode�� READ�ε� write�� ȣ���ϸ�  NullPointerException�� ������.
	 */
	public void write(byte[] buf, int index, int length) throws IOException{
		
		if(mode != WRITE)
			DebugUtils.throwException();
		
		out.write(buf, index, length);

	}

	/**
	 * ���ϳ��� ���� �����Ͱ� ����ġ�� ���� offset��ŭ �̵��Ѵ�.
	 * (���� �̱���)
	 * @param offset
	 * ���������͸� �̵��ϰ��� �ϴ� ��ġ
	 * @param seekorigin
	 * ������ �̵��ϱ� ���� ������������ ��ġ
	 * @throws EOFException
	 * ���� �����͸� �̵��ϴٰ� ������ ���� �����ϸ� EOFException�� ������.
	 */
	public void seek(int offset, SeekOrigin seekorigin) throws EOFException{
		
	}
	
	/**
	 * ������ ������������ ��ġ�� ��´�.
	 * (���� �̱���)
	 */
	public void getPosition(){
		
	}
	
	/**
	 * ������ ���̸� �����´�.
	 * ������ byte
	 */
	public int getLength(){
		return (int) file.length();
		
		
	}
	
	/**
	 * ������ ������ ����� �� ����Ѵ�.
	 * (��������)
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 * @throws NullPointerException
	 * mode�� READ�� ���¿��� flush�� ȣ���ϸ� NullPointerException�� ������.
	 */
	public void flush() throws IOException{
		switch(mode){
		case READ:
			DebugUtils.throwException();
			break;
			
		case WRITE:
			out.flush();
			fileOutputStream.flush();
			break;
		}
	}
	
	/**
	 * ������ �ݰ� �����Ѵ�.
	 * @throws IOException 
	 * �̹� ���������� IOException �� ������.
	 */
	public void close() throws IOException{
		switch(mode){
		case READ:
			in.close();
			fileInputStream.close();
			break;
			
		case WRITE:
			out.close();
			fileOutputStream.close();
			break;
		}
	}
	
	
	
}
package sod.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

/**
 * 
 * @author MB
 * Make a connection between remote point
 */
public class Transceiver implements Disposable {
	
	public final static int TransferUnit = 0x10000;//64kb
	
	byte[] recvbuf;
	Serializer serializer;
	SocketAddress dest;
	DatagramSocket conn = null;
	
	/**
	 * ��Ʈ�� �ڵ��Ҵ��Ͽ� Transceiver��ü ����
	 * dest�� null�� �� �� ����(���� ���� Transceiver�� ���)
	 * @param dest ������ �ּ�
	 */
	public Transceiver(SocketAddress dest){
		try {
			this.dest = dest;
			conn = new DatagramSocket(0);			
			serializer = new Serializer();
			recvbuf = new byte[TransferUnit];
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * dest�� null�� �� �� ����(���� ���� Transceiver�� ���)
	 * @param dest ������ �ּ�
	 * @param recvport �����ϴ� ��Ʈ (�ڵ� �Ҵ��Ϸ��� 0 ����)
	 */
	public Transceiver(SocketAddress dest, int recvport){
		try {
			this.dest = dest;
			conn = new DatagramSocket(recvport);			
			serializer = new Serializer();
			recvbuf = new byte[TransferUnit];
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dispose(){
		conn.close();
		conn = null;
	}
	
	@Override
	protected void finalize() throws Throwable {
		if(conn != null)
			dispose();
	};
	
	/**
	 * �Ѱ��� ��Ŷ ��ü�� ���(����ƮTV, ����Ʈ��)���� ������.
	 * @param p
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�� ��, IllegalArgumentException�� ������.
	 * @throws SocketException
	 * �����ϴ� ������ �����ϸ� SocketException�� ������.
	 */
	public boolean send(Packet p) throws IllegalArgumentException{
		if (p == null)
			throw new IllegalArgumentException();

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		serializer.serialize(buf, p);
		byte[] data = buf.toByteArray();
		DatagramPacket pkt = new DatagramPacket(data, data.length);
		pkt.setSocketAddress(dest);
		try {
			conn.send(pkt);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * ����(����ƮTV, ����Ʈ��)���κ��� ��Ŷ�� �����ϴ� �޼ҵ��̴�. (Blocking���� ����)
	 * @param p
	 * ����(����ƮTV, ����Ʈ��)���κ��� ���� ��Ŷ
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�� ��, IllegalArgumentException�� ������.
	 */
	public InetSocketAddress receive(Packet p) throws IllegalArgumentException{
		if (p == null)
			throw new IllegalArgumentException();

		try {
			DatagramPacket pkt = new DatagramPacket(recvbuf, recvbuf.length);
			conn.receive(pkt);
			int length = pkt.getLength();
			ByteArrayInputStream in = new ByteArrayInputStream(recvbuf, 0, length);
			serializer.deserialize(in, p);
			return (InetSocketAddress)pkt.getSocketAddress();
		} catch (IOException e) {
			return null;
		}
	}
}

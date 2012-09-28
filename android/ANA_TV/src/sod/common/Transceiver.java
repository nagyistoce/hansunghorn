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
	
	public final static int TransferUnit = 0x10000*4;//256kb
	
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
	 * @param pkt
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�� ��, IllegalArgumentException�� ������.
	 * @throws SocketException
	 * �����ϴ� ������ �����ϸ� SocketException�� ������.
	 */
	public boolean send(Packet pkt) throws IllegalArgumentException{
		if (pkt == null)
			throw new IllegalArgumentException();

		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		serializer.serialize(buf, pkt);
		byte[] data = buf.toByteArray();
		DatagramPacket sendedPkt = new DatagramPacket(data, data.length);
		sendedPkt.setSocketAddress(dest);
		try {
			conn.send(sendedPkt);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * ����(����ƮTV, ����Ʈ��)���κ��� ��Ŷ�� �����ϴ� �޼ҵ��̴�. (Blocking���� ����)
	 * @param pkt
	 * ����(����ƮTV, ����Ʈ��)���κ��� ���� ��Ŷ
	 * @throws IllegalArgumentException
	 * �Ѿ�� ��Ŷ ��ü�� NULL�� ��, IllegalArgumentException�� ������.
	 */
	public InetSocketAddress receive(Packet pkt) throws IllegalArgumentException{
		if (pkt == null)
			throw new IllegalArgumentException();

		try {
			DatagramPacket receivedPkt = new DatagramPacket(recvbuf, recvbuf.length);
			conn.receive(receivedPkt);
			int length = receivedPkt.getLength();
			ByteArrayInputStream in = new ByteArrayInputStream(recvbuf, 0, length);
			serializer.deserialize(in, pkt);
			return (InetSocketAddress)receivedPkt.getSocketAddress();
		} catch (IOException e) {
			return null;
		}
	}
}

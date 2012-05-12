package SOD.SmartTV;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

import javax.jws.Oneway;

import SOD.Common.Packet;
import SOD.Common.ReceiveHandler;
import SOD.Common.ThreadEx;
import SOD.Common.Transceiver;
import SOD.Common.Tuple;
import SOD.Test.ActionEx;

/**
 * 
 * @author MB
 *
 */
public class AccessManagerServer {
	protected final int CheckingTerm = 4000;
	
	protected ConnectHandler cb_conn;
	protected DisconnectHandler cb_disc;
	protected ServerReceiveHandler cb_recv;
	
	protected Transceiver listener;
	protected ServerConfig config;
	protected boolean isRunning = false;
	protected Map<Integer, Tuple<Transceiver, Long>> connset;
	
	public AccessManagerServer(){
		connset = new ConcurrentHashMap<Integer, Tuple<Transceiver,Long>>();
	}
	
	/**
	 * ���� �ʱ�ȭ �� beginListening, beginCheckingConnection ȣ��
	 * @param conf
	 * ������ ����
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 * @throws IllegalStateException
	 * setReceiveHandler()�� ȣ����� �ʾҰų� �̹� ������ start()�� ȣ��Ǿ��ٸ� �߻�
	 */
	public void start(ServerConfig conf) throws IllegalArgumentException, IllegalStateException{
		if(conf == null) throw new IllegalArgumentException("argument conf should not be null.");
		if(isRunning) throw new IllegalStateException("already server is running.");
		
		this.config = conf;
		listener = new Transceiver(null, conf.Port);
		
		beginListening();
		//beginCheckingConnection();
		isRunning = true;
	}

	/**
	 * beginListening, beginCheckingConnection���� ������ �����带 �����ϰ�,
	 * �ݹ� �Լ��� ���� �����ϰ� �Ҵ�� ��� ���ҽ� ����.
	 * @throws IllegalStateException
	 * start()�� ȣ��Ǳ� ���� ȣ��ǰų� �ι� �̻� ȣ��� �� �߻�
	 */
	public void shutdown() throws IllegalStateException{
		if(!isRunning) throw new IllegalStateException("server is not running.");
		
		isRunning = false;
		connset.clear();
	}

	/**
	 * �� ������ �������� ȣ��Ǵ� �ݹ��Լ� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	public void setConnectHandler(ConnectHandler handler) throws IllegalArgumentException{
		if(handler == null) throw new IllegalArgumentException("argument handler should not be null.");
		
		cb_conn = handler;
	}

	/**
	 * ������ �������� ȣ��Ǵ� �ݹ��Լ� ���
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	public void setDisconnectHandler(DisconnectHandler handler) throws IllegalArgumentException{
		if(handler == null) throw new IllegalArgumentException("argument handler should not be null.");
		
		cb_disc = handler;
	}

	/**
	 * ���� ������ ������ �����Ǿ� ������ Transceiver�� � ���̵� onReceive �ݹ��� ȣ��ɶ�
	 * ���޵� Packet�� �ش� Transceiver�� id�� �Ѱ��ִ� �ݹ� �Լ��� ����Ѵ�.
	 * @param handler
	 * �ݹ��Լ��� ������ ��ü
	 * @throws IllegalArgumentException
	 * �Ű������� null�϶� �߻�
	 */
	public void setReceiveHandler(ServerReceiveHandler handler) throws IllegalArgumentException{
		if(handler == null) throw new IllegalArgumentException("argument handler should not be null.");
		
		cb_recv = handler;
	}
	
	/**
	 * �÷��ǿ� ��ϵ� ���� ������ �����´�.
	 * @return
	 * ���� �پ��ִ� ���� ����
	 */
	public int getConnectionCount(){
		return connset.size();
	}

	/**
	 * Send a Packet via Transceiver which matching connid.
	 * @param pkt
	 * �����͸� ���� ��Ŷ
	 * @param connid
	 * Transceiver ��ü�� ����Ű�� id
	 */
	public boolean send(Packet pkt, int connid){
		if(!isRunning) return false;		
		if(pkt == null) return false;
		if(!connset.containsKey(connid)) return false;
		
		Transceiver t = connset.get(connid).item1;
		return t.send(pkt);
	}

	/**
	 * �ش� id�� ��ġ�ϴ� Transceiver�� ������ ���� �÷��ǿ��� �����Ѵ�.
	 * @param connid
	 * Transceiver ��ü�� ����Ű�� id
	 * @throws IllegalArgumentException
	 * connid�� ��ȿ���� ���� ��� �߻�
	 */
	public void dropConnection(int connid) throws IllegalArgumentException{
		if(!connset.containsKey(connid))
			throw new IllegalArgumentException("connid is not valid.");
		
		connset.remove(connid);
	}

	/**
	 * ���ο� ������ ����(Accept)�ϰų� �����͸� ����(Receive)�ϱ� ���� �۾� �����带 �����Ѵ�.
	 * ������ �ɶ����� ������ �÷��ǿ� ������ �߰��ȴ�.
	 */
	protected void beginListening(){
		ThreadEx.invoke(null, new ActionEx() {		
			
			@Override
			public void work(Object arg) {
				Packet p = new Packet();
				Packet p2 = new Packet();
				InetSocketAddress sender = null;
				Tuple<Transceiver, Long> t = null;
				Transceiver sender_t = null;
				
				while(isRunning){
					p.clear();
					sender = listener.receive(p);	
					
					switch(p.signiture){
					case Packet.REQUEST_ACCEPT:
						t = new Tuple<Transceiver, Long>();
						t.item1 = new Transceiver(sender);
						t.item2 = ThreadEx.getCurrentTime();
						connset.put(sender.hashCode(), t);
						sendServiceName(config.serviceName, sender.hashCode());
						cb_conn.onConnect(sender.hashCode());
						break;
					case Packet.RESPONSE_CLIENT_ALIVE:
						t = connset.get(sender.hashCode());
						t.item2 = ThreadEx.getCurrentTime();
						break;
					case Packet.REQUEST_PING:
						sender_t = new Transceiver(sender);
						p2.clear();
						p2.signiture = Packet.RESPONSE_PING;
						sender_t.send(p2);
						break;
					case Packet.REQUEST_SERVICE_DATA:
						//need to implement
						break;
					default:
						cb_recv.onReceive(p, sender.hashCode());
						break;
					}
				}
			}
			
		});		
	}

	/**
	 * ���� �÷��ǿ� �ִ� �� Transceiver���� �����ϸ� ���� ���˿� ��Ŷ�� ������ ���� �۾� �����带 �����Ѵ�.
	 * ���ŵ��� ��Ŷ�� ���������� ������ �ð��� ���� �ð��� ���̰� Timeout�� �ʰ��ϸ� dropConnection ȣ��.
	 */
	protected void beginCheckingConnection(){
		
		ThreadEx.invoke(null, new ActionEx() {
			
			@Override
			public void work(Object arg) {
				Queue<Integer> expired = new LinkedList<Integer>();
				Packet p = new Packet();
				p.signiture = Packet.REQUEST_CLIENT_ALIVE;
				
				while(isRunning){
					ThreadEx.sleep(CheckingTerm);
					
					Iterator<Integer> h = connset.keySet().iterator();
					while(h.hasNext()){
						Integer connid = h.next();
						Tuple<Transceiver, Long> t = connset.get(connid);
						long elapsed = ThreadEx.getCurrentTime() - t.item2;
						if(elapsed > config.Timeout)
							expired.offer(connid);
						else
							t.item1.send(p);
					}
					
					while(expired.size() > 0)
						connset.remove(expired.poll());
				}
			}
			
		});
		
	}
	
	/**
	 * Ư�� Transceiver���� �� ������ �̸��� �����Ѵ�.
	 * @param name
	 * ������ �̸�
	 * @param connid
	 * Transceiver ��ü�� ����Ű�� id
	 * @throws IllegalArgumentException
	 * String NULL�̰ų� ,connid�� ��ȿ���� ���� ��� �߻�
	 */
	protected void sendServiceName(String name, int connid) throws IllegalArgumentException{
		if(name == null) 
			throw new IllegalArgumentException("argument name should not be null.");
		if(!connset.containsKey(connid))
			throw new IllegalArgumentException("connid is not valid.");
		
		Packet p = new Packet();
		p.signiture = Packet.RESPONSE_SERVICE_NAME;
		p.push(name);
		
		Tuple<Transceiver, Long> t = connset.get(connid);
		t.item1.send(p);
	}

}

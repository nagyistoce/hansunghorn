
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Packet pk = new Packet();
		
		pk.push("����", "�����");
		pk.push("����", "�������");
		pk.push("�̸�", "�ǹ���");
		pk.push("����", "¥���");
		
		System.out.println(pk.pick("����"));
		System.out.println(pk.pick("����"));
		System.out.println(pk.pick("�̸�"));
		System.out.println(pk.pick("����"));
		//1. ���� Ÿ�Կ� ���� ī����
		//2. 
		//3. 

	}

}

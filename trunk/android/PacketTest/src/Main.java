
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Packet pk = new Packet();
		
		pk.push("제목", "울랄라");
		pk.push("문제", "배고프다");
		pk.push("이름", "권문범");
		pk.push("음식", "짜장면");
		
		System.out.println(pk.pick("문제"));
		System.out.println(pk.pick("제목"));
		System.out.println(pk.pick("이름"));
		System.out.println(pk.pick("음식"));
		//1. 같은 타입에 대한 카운터
		//2. 
		//3. 

	}

}

package sod.test;

public class App {
	
	public static void main(String[] args){
		try{
			test();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	static void test() throws Exception{
		Test.test();
	}
	
}

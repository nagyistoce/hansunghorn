package sod.test;

public class ConsoleLog implements ILog {

	@Override
	public void print(Object arg) {
		// TODO Auto-generated method stub
		System.out.print(arg.toString());
	}

	@Override
	public void print(String msg) {
		// TODO Auto-generated method stub		
		System.out.print(msg);		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		System.out.flush();
	}	
	
}

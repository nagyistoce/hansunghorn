package SOD.Test;

public class ConsoleLog implements Logable {

	@Override
	public void write(Object arg) {
		// TODO Auto-generated method stub
		System.out.print(arg.toString());
	}
	
}

package ana.phoneBean;

import java.util.concurrent.Semaphore;

public class DownLoad {
	public final static String DOWNLOADHTML="download";
	public final static String QUSTION="Questionnaire";
	public static String Message="";
	public static int ClientId;
	public static String TempMessage="";
	public static Semaphore waithandle;
	
	static{
		waithandle = new Semaphore(1);
		try {
			waithandle.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

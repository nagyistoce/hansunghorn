package smart.go.Game;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;

public class Client implements Runnable{
	private Socket socket; // º“ƒœ
	private ObjectInputStream ois; // ¿‘∑¬
	private ObjectOutputStream oos; // √‚∑¬
	private String ip; // IP ¡÷º“∏¶ ¿˙¿Â«“ ∫Øºˆ
	private String id; // ¥–≥◊¿”(¥Î»≠∏Ì) ¿˙¿Â«“ ∫Øºˆ
	ArrayList<Integer> list;
	int order;
	int inIndexNum = -1;
	int outIndexNum;
	int gostopIndex = -1;
	int Decidegosop;
	int num = -1;
	String sendMsg = null;
	int exitFlag = -1;
	int check_sun = 0;
	int nScreenFlag;
	int scr_flag = 0;
	int remoteflag = 0;
	int motion_flag = 0;
	int index = -1;
	int g_index = -1;
	ArrayList<Integer> have_cardme = new ArrayList<Integer>();
	ArrayList<Integer> have_cardyou = new ArrayList<Integer>();
	ArrayList<Integer> eat_cardme = new ArrayList<Integer>();
	ArrayList<Integer> eat_cardyou = new ArrayList<Integer>();
	ArrayList<Integer> bottom_card = new ArrayList<Integer>();
	ArrayList<Integer> card_deck = new ArrayList<Integer>();

	ArrayList<Integer> remote_list = new ArrayList<Integer>();

	Common c;
	Thread t;

	public Client(){	
	}

	public void init(Common com) {
		try {
			c = com;
			list = new ArrayList<Integer>();
			socket = new Socket("192.168.56.1", 8080);	  //192.168.0.2
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			t = new Thread(this);
			t.start(); // æ≤∑πµÂ Ω√¿€		

		} catch (Exception e) {
			e.printStackTrace();
		}// catch

	}// init

	public void run() {
		String message = null;
		String[] receiveMsg = null;
		int nscreen_flag = 0;
		int value;
		boolean isStop = false;
		while (!isStop) {

			try {
				message = (String) ois.readObject();// √§∆√≥ªøÎ;
				receiveMsg = message.split("#");// »´±Êµø#æ»≥Á

				if(receiveMsg[0].equals("nscreen"))
					scr_flag = 1;
				if(receiveMsg[0].equals("all")) {
					if(receiveMsg[1].equals("a")){				
						order = 1;	// player_me
						Log.e("RE", receiveMsg[1]);
						c.WakeUp();
						c.Wait();
					}
					else if(receiveMsg[1].equals("b")){
						order = 0;	// player_you	
						Log.e("RE", receiveMsg[1]);
						c.WakeUp();
						c.Wait();
					}
					else
						list.add(Integer.parseInt(receiveMsg[1]));
				}
				else if(receiveMsg[0].equals("you")) {
					if(receiveMsg[1].equals("g")) {
						Decidegosop = 1;	// 1¿Ã∏È ∞Ì
						Log.e("πﬁ¿Ωgotop", "" + Decidegosop);
					}
					else if(receiveMsg[1].equals("s")) {
						Decidegosop = 0;	// 0¿Ã∏È Ω∫≈È
						Log.e("πﬁ¿Ωgotop", "" + Decidegosop);
					}
					else {
						outIndexNum = Integer.parseInt(receiveMsg[1]);	
						Log.e("πﬁ¿Ω", "" + outIndexNum);
					}
					c.WakeUp();
					c.Wait();
				}

				else if(receiveMsg[0].equals("remote")) {
					if(receiveMsg[1].equals("remote")) {
						c.WakeUp();
						c.Wait();
					}
					else if(receiveMsg[1].equals("turn")){
						c.WakeUp();
						c.Wait();
					}
					else {
						value = Integer.parseInt(receiveMsg[1]);
						remote_list.add(value);
					}
				}

				else if(receiveMsg[0].equals("HM")) {	// Have me	
					value = Integer.parseInt(receiveMsg[1]);
					have_cardme.add(value);
					nscreen_flag++;
				}
				else if(receiveMsg[0].equals("HY")) {	// Have you	
					value = Integer.parseInt(receiveMsg[1]);
					have_cardyou.add(value);
					nscreen_flag++;
				}
				else if(receiveMsg[0].equals("EM")) {	// Eat me
					value = Integer.parseInt(receiveMsg[1]);
					eat_cardme.add(value);
					nscreen_flag++;
				}
				else if(receiveMsg[0].equals("EY")) {	// Eat you	
					value = Integer.parseInt(receiveMsg[1]);
					eat_cardyou.add(value);
					nscreen_flag++;
				}
				else if(receiveMsg[0].equals("BB")) {	// Bottom 	
					value = Integer.parseInt(receiveMsg[1]);
					bottom_card.add(value);
					nscreen_flag++;
				}
				else if(receiveMsg[0].equals("BC")) {	// CardDeck
					value = Integer.parseInt(receiveMsg[1]);
					card_deck.add(value);
					nscreen_flag++;
				}
				else if(receiveMsg[0].equals("mo")){
					value = Integer.parseInt(receiveMsg[1]);
					g_index = value;
					c.WakeUp();
					c.Wait();
				}

				if(nscreen_flag == 48) {
					Log.e("nSCREEN", "48¿Â πﬁ¿Ω");
					c.WakeUp();
					c.Wait();
					nscreen_flag = 0;
				}

				if(order == 1 && inIndexNum != -1){
					oos.writeObject("me#" + inIndexNum);
					Log.e("∫∏≥ø", "" + inIndexNum);
					inIndexNum = -1;
					c.WakeUp();
					c.Wait(); 

				}
				else if(order == 0 && inIndexNum != -1) {
					oos.writeObject("you#" + inIndexNum);
					Log.e("∫∏≥ø", "" + inIndexNum);
					inIndexNum = -1;
					c.WakeUp();
					c.Wait();
				}

				if(order == 1 && num != -1){
					oos.writeObject("me#" + num);
					Log.e("∫∏≥ø", "" + num);
					num = -1;
					c.WakeUp();
					c.Wait();
				}
				else if(order == 0 && num != -1) {
					oos.writeObject("you#" + num);
					Log.e("∫∏≥ø", "" + num);
					num = -1;
					c.WakeUp();
					c.Wait();
				}

				if(order == 1 && gostopIndex != -1){
					if(gostopIndex == 1)
						message = "g";
					else 
						message = "s";
					oos.writeObject("me#" + message);
					Log.e("∫∏≥øgostop", message);
					gostopIndex = -1;
					sleep(500);
					c.WakeUp();
					c.Wait(); 

				}
				else if(order == 0 && gostopIndex != -1) {
					if(gostopIndex == 1)
						message = "g";
					else 
						message = "s";
					oos.writeObject("you#" + message);
					Log.e("∫∏≥øgostop", message);
					gostopIndex = -1;
					sleep(500);
					c.WakeUp();
					c.Wait();
				}

				if(order == 1 && exitFlag != -1){
					oos.writeObject("me#" + "exit");
					Log.e("∫∏≥øexit", "" + "exit");
					exitFlag = -1;
					c.WakeUp();
					t = null;
				}
				else if(order == 0 && exitFlag != -1) {
					oos.writeObject("you#" + "exit");
					Log.e("∫∏≥øexit", "" + "exit");
					exitFlag = -1;
					c.WakeUp();
					t = null;
				}
				
				if(motion_flag == 1){
					Log.e("1", "1");
					oos.writeObject("motion#" + index);
					Log.e("2", "2");
					motion_flag = 0;
					sleep(500);
					c.WakeUp();
					c.Wait();

					Log.e("after", ""+motion_flag);
				}

				if(remoteflag == 1) {
					for(int i=0; i<remote_list.size(); i++) {
						oos.writeObject("remote#" + remote_list.get(i));
					}	
					oos.writeObject("remote#remote");
					remoteflag = 0;
					c.WakeUp();
					c.Wait();
				}
				
				if(check_sun == 1){
					oos.writeObject("remote#" + "sun");
					check_sun = 0;
				}

				if(order == 1 && nScreenFlag == 1){
					for(int i=0; i<have_cardme.size(); i++) {
						oos.writeObject("HM#" + have_cardme.get(i));
						Log.e("∫∏≥øHM", "" + have_cardme.get(i));
					}
					have_cardme.clear();
					for(int i=0; i<have_cardyou.size(); i++) {
						oos.writeObject("HY#" + have_cardyou.get(i));
						Log.e("∫∏≥øHY", "" + have_cardyou.get(i));
					}
					have_cardyou.clear();
					for(int i=0; i<eat_cardme.size(); i++) {
						oos.writeObject("EM#" + eat_cardme.get(i));
						Log.e("∫∏≥øEM", "" + eat_cardme.get(i));
					}
					eat_cardme.clear();
					for(int i=0; i<eat_cardyou.size(); i++) {
						oos.writeObject("EY#" + eat_cardyou.get(i));
						Log.e("∫∏≥øEY", "" + eat_cardyou.get(i));
					}
					eat_cardyou.clear();
					for(int i=0; i<bottom_card.size(); i++) {
						oos.writeObject("BB#" + bottom_card.get(i));
						Log.e("∫∏≥øBB", "" + bottom_card.get(i));
					}
					bottom_card.clear();
					for(int i=0; i<card_deck.size(); i++) {
						oos.writeObject("BC#" + card_deck.get(i));
						Log.e("∫∏≥øBC", "" + card_deck.get(i));
					}
					card_deck.clear();
					nScreenFlag = -1;
					c.WakeUp();
				}

				else if(order == 0 && nScreenFlag == 1) {
					for(int i=0; i<have_cardme.size(); i++) {
						oos.writeObject("HM#" + have_cardme.get(i));
						Log.e("∫∏≥øHM", "" + have_cardme.get(i));
					}
					have_cardme.clear();
					for(int i=0; i<have_cardyou.size(); i++) {
						oos.writeObject("HY#" + have_cardyou.get(i));
						Log.e("∫∏≥øHY", "" + "have_cardyou.get(i)");
					}
					have_cardyou.clear();
					for(int i=0; i<eat_cardme.size(); i++) {
						oos.writeObject("EM#" + eat_cardme.get(i));
						Log.e("∫∏≥øEM", "" + "eat_cardme.get(i)");
					}
					eat_cardme.clear();
					for(int i=0; i<eat_cardyou.size(); i++) {
						oos.writeObject("EY#" + eat_cardyou.get(i));
						Log.e("∫∏≥øEY", "" + "eat_cardyou.get(i)");
					}
					eat_cardyou.clear();
					for(int i=0; i<bottom_card.size(); i++) {
						oos.writeObject("BB#" + bottom_card.get(i));
						Log.e("∫∏≥øBB", "" + "bottom_card.get(i)");
					}
					bottom_card.clear();
					for(int i=0; i<card_deck.size(); i++) {
						oos.writeObject("BC#" + card_deck.get(i));
						Log.e("∫∏≥øBC", "" + "card_deck.get(i)");
					}
					card_deck.clear();
					nScreenFlag = -1;
					c.WakeUp();
				}

			}catch (Exception e) {
				e.printStackTrace();
				isStop = true; // π›∫ππÆ ¡æ∑·∑Œ º≥¡§
			}// catch
		}
	}// run

	public List<Integer> getList(){
		return list;
	}
	public void setHaveMeList(List<Integer> nscreen) {
		have_cardme = (ArrayList<Integer>) nscreen;
	}
	public void setHaveYouList(List<Integer> nscreen) {
		have_cardyou = (ArrayList<Integer>) nscreen;
	}
	public void setEatMeList(List<Integer> nscreen) {
		eat_cardme = (ArrayList<Integer>) nscreen;
	}
	public void setEatYouList(List<Integer> nscreen) {
		eat_cardyou = (ArrayList<Integer>) nscreen;
	}
	public void setBottomList(List<Integer> nscreen) {
		bottom_card = (ArrayList<Integer>) nscreen;
	}
	public void setCardDeckList(List<Integer> nscreen) {
		card_deck = (ArrayList<Integer>) nscreen;
	}
	public void setnScreenFlag() {
		nScreenFlag = 1;
	}

	public void setRemote(ArrayList<Integer> remote) {
		remote_list = remote;
		remoteflag = 1;
	}
	
	public ArrayList<Integer> getHaveMeList() {
		return have_cardme;
	}
	
	public ArrayList<Integer> getHaveYouList() {
		return have_cardyou;
	}
	
	public ArrayList<Integer> getEatMeList() {
		return eat_cardme;
	}
	
	public ArrayList<Integer> getEatYouList() {
		return eat_cardyou;
	}
	
	public ArrayList<Integer> getBottomList() {
		return bottom_card;
	}
	
	public ArrayList<Integer> getCardDeckList() {
		return card_deck;
	}
	
	public ArrayList<Integer> getRemoteList() {
		return remote_list;
	}
	
	public int getOrder(){
		return order;
	}

	public void setIndex(int num){
		inIndexNum = num;
	}

	public void setNum(int i){
		num = i;
	}

	public int getIndex(){
		return outIndexNum;
	}

	public int getNum(){
		return num;
	}

	public void setgosopIndex(int i) {
		gostopIndex = i;
	}

	public int getgostopIndex() {
		return Decidegosop;
	}

	public void	setExit() {
		exitFlag = 1;		
	}

	public void sleep(int i){
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getNscreen(){
		return scr_flag;
	}
	
	public void setMotion(int i){
		Log.e("set", "1");
		motion_flag = 1;
		index = i;
		Log.e("set2", index + " " + motion_flag);
	}
	
	public int getmIndex(){
		return g_index;
	}
	
	public void setSun(int i){
		check_sun = i;
	}
}

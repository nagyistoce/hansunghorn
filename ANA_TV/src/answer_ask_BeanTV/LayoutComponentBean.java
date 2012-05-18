package answer_ask_BeanTV;

import SOD.Common.Packet;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import answer_ask_Service.TV.AnA_BootMode;

public class LayoutComponentBean {
	public final static String SEPARATOR = "|";
	
	public static Button statisticsGraph_btn;
	public static Button  QuestionnaireInitial_btn;
	public static Button QuestionnaireImfo_btn;
	public static EditText topic, question;
	public static Button Fileadd, questionAdd, complete;
	public static RadioButton long_answer, short_answer;
	public static Packet packet = null;

	public static EditText Company, During;
	public static CheckBox UserImfo;
	public static Button FileAdd, Next;
	public static int ScreenCount=0;
}

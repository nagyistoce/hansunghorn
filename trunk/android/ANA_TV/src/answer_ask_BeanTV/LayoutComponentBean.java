package answer_ask_BeanTV;

import sod.common.Packet;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LayoutComponentBean {
	public final static String SEPARATOR = "|";
	public static ImageButton statisticsGraph_btn;
	public static ImageButton QuestionnaireInitial_btn;
	public static ImageButton QuestionnaireImfo_btn;
	public static EditText topic, question;
	public static ImageButton Fileadd, questionAdd, complete;
	public static RadioButton long_answer, short_answer;
	public static Packet packet = null;
	public static ListView listview;
	public static EditText Company, During;
	public static CheckBox UserImfo;
	public static ImageButton FileAdd, Next;
	public static int ScreenCount = 0;
	public static RadioGroup radiogroup;
	public static int choice = -1;
	public static boolean resetFlag = false;
}

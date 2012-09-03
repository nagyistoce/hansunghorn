package smart.go.GameCommand;

// 게임처음순서저장
// ex) 바닥패1 -> 상대방패1 -> 내패1 -> 바닥패2 -> 상대방패2 -> 내패2 -> 게임시작
// ex) 바닥패1 -> 내패1 -> 상대방패1 -> 바닥패2 -> 내패2 -> 상대방패2 -> 게임시작
public class GameCommand	
{
	public static final int MAX_LIST = 10;

	public static final int id_send_first_card_to_you = 10;
	public static final int id_send_second_card_to_you = 20;
	public static final int id_send_first_card_to_me = 30;
	public static final int id_send_second_card_to_me = 40;
	public static final int id_send_first_card_to_board = 50;
	public static final int id_send_second_card_to_board = 60;
	
	public static final int id_game_run = 70;		

	private CMD_INFO[] m_cmd = new CMD_INFO[MAX_LIST];

	private long m_curTime;
	
	public GameCommand()
	{
		m_curTime = 0;

		for( int i=0; i<MAX_LIST; i++ )	
		{
			m_cmd[i] = new CMD_INFO();
			m_cmd[i].bSet = false;
			m_cmd[i].nID = -1;
			m_cmd[i].startTime = 0;
		}
	}	

	public void setCurTime( long curTime )
	{
		m_curTime = curTime;
	}

	public int getID()
	{
		int nID = m_cmd[0].nID;

		for( int i=0; i<MAX_LIST-1; i++ )	// for(i=0; i<9; i++)
		{
			m_cmd[i].bSet = m_cmd[i+1].bSet;
			m_cmd[i].nID = m_cmd[i+1].nID;
			m_cmd[i].startTime = m_cmd[i+1].startTime;
		}

		m_cmd[MAX_LIST-1].bSet = false;
		m_cmd[MAX_LIST-1].nID = -1;
		m_cmd[MAX_LIST-1].startTime = 0;

		return nID;
	}

	public boolean isRegistCommand()
	{
		if( (true == m_cmd[0].bSet) && (m_cmd[0].startTime <= m_curTime) )
			return true;

		return false;
	}

	public void registCommand( int nID, float fSec )
	{
		int nIndex = MAX_LIST - 1; // nIndex = 9
		for( int i=0; i<MAX_LIST; i++ )
		{
			if( false == m_cmd[i].bSet )
			{
				nIndex = i;
				break;
			}
		}		
		m_cmd[nIndex].bSet = true;
		m_cmd[nIndex].startTime = m_curTime + (int)(fSec * 1000);
		m_cmd[nIndex].nID = nID;
	}	
}
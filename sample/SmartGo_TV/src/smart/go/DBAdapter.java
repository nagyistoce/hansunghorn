package smart.go;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	private static final String DB_NAME = "KARAMJoin.db";
	private static final String DB_TABLE = "joinss";
	private static final int DB_VERSION = 1;

	private static final String DB_CREATE = "CREATE TABLE " + DB_TABLE + " (num INTEGER PRIMARY KEY AUTOINCREMENT," +
			" id TEXT NOT NULL, pw TEXT NOT NULL, rpw TEXT NOT NULL, name TEXT NOT NULL);";

	private static final String DB_DROP = "DROP TABLE IF EXISTS " + DB_TABLE;

	private SQLiteDatabase mDb;
	private DatabaseHelper mDbHelper;
	private Context context;

	public DBAdapter(Context _context) {
		context = _context;

		mDbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
	}

	private class DatabaseHelper extends SQLiteOpenHelper {

		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DB_CREATE);

		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	
			Log.w("MY_TAG", "Upgrading DB form version"+oldVersion +" to" + newVersion + ", which will destroy all old data");

			db.execSQL(DB_DROP);
			onCreate(db);
		}
	}

	public DBAdapter open() throws SQLException {
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDb.close();
	}

	public long insertEntry(String id, String pw, String rpw, String name) {
		ContentValues values = new ContentValues();
		values.put("id", id);
		values.put("pw", pw);
		values.put("rpw", rpw);
		values.put("name", name);

		return mDb.insert(DB_TABLE, null, values);		
	}

	public boolean updateEntry(String id, String pw, String rpw, String name) {
		ContentValues values = new ContentValues();
		values.put("id", id);
		values.put("pw", pw);
		values.put("rpw", rpw);
		values.put("name", name);
		return mDb.update(DB_TABLE, values, "id=" + id, null) >0;
	}

	public boolean deleteEntry(long rowID) {
		return mDb.delete(DB_TABLE, "num=" + rowID, null) >0;
	}

	public Cursor getAllEntries() {
		return mDb.query(DB_TABLE, new String[] {"num", "id", "pw", "rpw", "name"}, null, null, null, null, null, null);	
	}
	public int getIDEntry(String ID) throws SQLException {
		Cursor mCursor = mDb.query(DB_TABLE, new String[] {"id", "pw", "name"}, null, null, null, null, null);

		int yesno = LogCursorIDInfo(mCursor, ID);
		return yesno;
	}
	public int getIDPWEntry(String ID, String PW) throws SQLException {
		Cursor mCursor = mDb.query(DB_TABLE, new String[] {"id", "pw", "name"}, null, null, null, null, null);

		int yesno = LogCursorPWInfo(mCursor, ID, PW);
		return yesno;
	}
	
	public String getNAMEEntry(String ID) throws SQLException {
		Cursor mCursor = mDb.query(DB_TABLE, new String[] {"id", "pw", "name"}, null, null, null, null, null);

		String name = LogCursorNAMEInfo(mCursor, ID);
		return name;
	}
	
	public String getPWEntry(String ID) throws SQLException {
		Cursor mCursor = mDb.query(DB_TABLE, new String[] {"id", "pw", "name"}, null, null, null, null, null);

		String pw = LogCursorPWInfo(mCursor, ID);
		return pw;
	}
	
	public String LogCursorPWInfo(Cursor c, String ID) {
		String pw = null;
		
		c.moveToFirst();
		while(!c.isAfterLast()) {
			if(ID.equals(c.getString(0))) 
				pw = c.getString(1);
			c.moveToNext();
		}
		return pw;		
	}

	public String LogCursorNAMEInfo(Cursor c, String ID) {
		String name = null;
		
		c.moveToFirst();
		while(!c.isAfterLast()) {
			if(ID.equals(c.getString(0))) 
				name = c.getString(2);
			c.moveToNext();
		}
		return name;		
	}
	
	public int LogCursorIDInfo(Cursor c, String ID) {
		int yesno = 0;

		c.moveToFirst();
		while(!c.isAfterLast()) {
			if(ID.equals(c.getString(0)))
				yesno = 1;
			c.moveToNext();
		}
		return yesno;
	}

	public int LogCursorPWInfo(Cursor c, String ID, String PW) {
		int yesno = 0;

		c.moveToFirst();
		while(!c.isAfterLast()) {
			if(ID.equals(c.getString(0))) {
				if(PW.equals(c.getString(1)))
					yesno = 1;
			}
			c.moveToNext();
		}
		return yesno;
	}

}

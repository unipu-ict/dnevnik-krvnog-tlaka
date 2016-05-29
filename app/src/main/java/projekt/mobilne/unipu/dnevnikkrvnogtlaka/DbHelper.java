package projekt.mobilne.unipu.dnevnikkrvnogtlaka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;


public class DbHelper extends SQLiteOpenHelper {

    // region VARIJABLE
    public static final String DATABASE_NAME = "tlak.db";
    public static final String TABLE_NAME = "krvni_tlak";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "sistolicki";
    public static final String COL_3 = "dijastolicki";
    public static final String COL_4 = "puls";
    public static final String COL_5 = "datum";
    public static final String COl_6 = "vrijeme";
    // endregion

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
    String currentDate = sdfDate.format(new Date());

   // SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
   // String currentTime = sdfTime.format(new Date());

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, sistolicki INTEGER, dijastolicki INTEGER, puls INTEGER, datum INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // region JAVNE METODE
    public boolean insertData(String sistolicki, String dijastolicki, String puls) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, sistolicki);
        contentValues.put(COL_3, dijastolicki);
        contentValues.put(COL_4, puls);
        contentValues.put(COL_5, currentDate);
        // TODO: dogovoriti se oko datuma (i vremena)
        //contentValues.put(COl_6, currentTime);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }
    // endregion

    // region KURSOR
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + " ORDER BY ID DESC", null);
        return res;
    }

    public Cursor getZadnjaTri() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +" ORDER BY ID DESC LIMIT 3", null);
        return res;
    }

    public Cursor getZadnji() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME +" ORDER BY ID DESC LIMIT 1", null);
        return res;
    }

    public Cursor getZadnjiDatum() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select datum from " + TABLE_NAME +" ORDER BY ID DESC LIMIT 1", null);
        return res;
    }
    // endregion

    public String getDatumIVrijeme(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date currenTimeZone = (Date) calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception e) {
        }
        return "";
    }

}

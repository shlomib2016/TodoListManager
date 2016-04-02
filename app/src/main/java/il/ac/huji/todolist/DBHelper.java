package il.ac.huji.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EstherShlomi on 4/1/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "todo_db", null, 1);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table todo(_id integer primary key autoincrement,title string,due long);"
        );
    }
    public void onUpgrade(
            SQLiteDatabase db, int oldVer, int newVer) {}

}

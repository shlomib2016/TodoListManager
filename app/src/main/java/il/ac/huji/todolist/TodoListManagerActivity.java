package il.ac.huji.todolist;


import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

public class TodoListManagerActivity extends AppCompatActivity {

    ArrayList<Model> listItems = new ArrayList<Model>();
    CursorAdapter adapter;

    private EditText editTxt;
    private ListView list;
    private long Dialernumber;


    DBHelper helper;
    SQLiteDatabase db;

    private int[] objId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objId = new int[1000];

        helper = new DBHelper(this);
        db = helper.getWritableDatabase();

        Cursor cursor =
                db.query("todo", new String[] {"_id", "title", "due" }, null,null ,null ,null ,null,null);
        if (cursor.moveToFirst()) {
            do {
                Integer index = cursor.getInt(0);
                String title = cursor.getString(1);
                Long due = cursor.getLong(2);
            } while (cursor.moveToNext());
        }

        String[] from = new String[] {"title","due"};
        int[] to = new int[] { R.id.txtTodoTitle, R.id.txtTodoDueDate };

        setContentView(R.layout.activity_todo_list_manager);

        list = (ListView) findViewById(R.id.lstTodoItems);
        list.setLongClickable(true);
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, int position, long id) {
                menu_task(position);
                return true;
            }
        });

        adapter = new Custom_view2(this,cursor);

        list.setAdapter(adapter);

        update();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                Intent intent = new Intent(this, AddNewTodoItemActivity.class);
                startActivityForResult(intent, 2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);


        switch (requestCode) {
            case 2:
                if (resultCode == Activity.RESULT_OK) {
                    String title = intent.getStringExtra("title");
                    Long date = intent.getLongExtra("date", -1);

                    ContentValues data_point = new ContentValues();
                    data_point.put("title", title);
                    data_point.put("due", date);
                    db.insert("todo", null, data_point);
                    update();

                }
                break;
        }
    }

    public void update() {
        Cursor cursor =
                db.query("todo", new String[] {"_id", "title", "due" }, null,null ,null ,null ,null,null);
        int cnt=0;
        cursor.moveToFirst();

        while(!cursor.isAfterLast()) {
            cnt++;
            objId[cnt] =cursor.getInt(0);
            cursor.moveToNext();
        }
        adapter.swapCursor(cursor);
        adapter.notifyDataSetChanged();
    }



    public void menu_task(final int index) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        Cursor cursor = ((CursorAdapter) adapter).getCursor();
        cursor.moveToPosition(index);

        String title = cursor.getString(1).toString();
        CharSequence[] RemoveItems = {"Remove"};
        CharSequence[] RemoveCallItems = {"Remove", cursor.getString(1).toString()};
        CharSequence[] FinalItems;

        if (title.startsWith("Call ")) {
            FinalItems = RemoveCallItems;
            Dialernumber = Long.valueOf(title.substring(5, title.length()));
            String phonenumber = "Call " + String.valueOf(Dialernumber);
            alertDialogBuilder.setTitle(phonenumber);
        } else {
            FinalItems = RemoveItems;
            alertDialogBuilder.setTitle("Menu");
        }

        alertDialogBuilder.setItems(FinalItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                if (arg1 == 0) {
                    db.delete("todo", "_id="+String.valueOf(objId[index+1]),null);
                    adapter.notifyDataSetChanged();
                    update();
                    arg0.dismiss();
                } else {
                    String phonenumber = "tel:" + String.valueOf(Dialernumber);
                    Intent dial = new Intent(Intent.ACTION_DIAL, Uri.parse(phonenumber));
                    startActivity(dial);
                    arg0.dismiss();


                }
            }
        }).show();


    }

}

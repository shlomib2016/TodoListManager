package il.ac.huji.todolist;


import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.app.AlertDialog;
import android.widget.Toast;

import java.util.ArrayList;

public class TodoListManagerActivity extends AppCompatActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    private EditText editTxt;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);

        editTxt = (EditText) findViewById(R.id.edtNewItem);
        list = (ListView) findViewById(R.id.lstTodoItems);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @ Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TodoListManagerActivity.this, "You clicked Remove", Toast.LENGTH_LONG).show();
                remove_task(position);
            }
        });

        adapter=new Custom_view(this,
                android.R.layout.simple_list_item_1,
                listItems);
        list.setAdapter(adapter);
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
                AddTaskToDoList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void AddTaskToDoList() {
        listItems.add(editTxt.getText().toString());
        adapter.notifyDataSetChanged();
        editTxt.setText("");
    }

    public void remove_task(final int index) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Menu");

        alertDialogBuilder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                listItems.remove(index);
                adapter.notifyDataSetChanged();
                Toast.makeText(TodoListManagerActivity.this, "You clicked Remove", Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

}

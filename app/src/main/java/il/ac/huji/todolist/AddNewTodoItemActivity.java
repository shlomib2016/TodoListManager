package il.ac.huji.todolist;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddNewTodoItemActivity extends Activity {
    private Calendar calendar;
    private int year, month, day;
    private TextView datePicker;
    private EditText edtNewItem;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo_item);
        getIntent();

        edtNewItem = (EditText) findViewById(R.id.edtNewItem);

        datePicker = (TextView)findViewById(R.id.datePicker);
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int cmonth= Integer.valueOf(today.month) +1;
        datePicker.setText(new StringBuilder().append(today.monthDay).append("/")
                .append(String.valueOf(cmonth)).append("/").append(today.year));
        year=today.year;
        month=today.month;
        day=today.monthDay;

        calendar = Calendar.getInstance();
    }

    public void add_tolist(View view){
        Intent result = new Intent();
        result.putExtra("title", edtNewItem.getText().toString());
        Date date=calendar.getTime();
        System.out.println(date.toString());
        result.putExtra("date",date.getTime() );
        setResult(RESULT_OK, result);
        finish();
    }

    public void closedialogaddnewtask(View view){
        Intent result = new Intent();
        setResult(RESULT_CANCELED, result);
        finish();
    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {

            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            day=arg3;
            month=arg2;
            year=arg1;
            calendar.set(calendar.YEAR,year);
            calendar.set(calendar.MONTH,month);
            calendar.set(calendar.DAY_OF_MONTH,day);
            datePicker.setText(new StringBuilder().append(day).append("/")
                    .append(String.valueOf(month+1)).append("/").append(year));

        }
    };
}

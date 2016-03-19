package il.ac.huji.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Custom_view extends ArrayAdapter<Model> {
    private final Context context;
    private ArrayList<Model> modelsArrayList;


    private int[] colors = new int[] { Color.GRAY, Color.WHITE };

    public Custom_view(Context context, int layout, ArrayList<Model> from) {
        super(context, layout, from);
        this.context = context;
        this.modelsArrayList=from;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = null;

        int colorPos = position % colors.length;
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        rowView = inflater.inflate(R.layout.target_item, parent, false);
        rowView.setBackgroundColor(colors[colorPos]);


        TextView titleView = (TextView) rowView.findViewById(R.id.txtTodoTitle);
        TextView dateView = (TextView) rowView.findViewById(R.id.txtTodoDueDate);

        titleView.setText(modelsArrayList.get(position).GetTitle());
        Date date= new Date();
        date.setTime(modelsArrayList.get(position).GetDate());
        Calendar cal;
        cal=Calendar.getInstance();
        int taskdate=(int)(date.getTime()/86400000);
        int currentdate=(int)(cal.getTimeInMillis()/86400000);
        if (taskdate<currentdate) {
            titleView.setTextColor(Color.RED);
            dateView.setTextColor(Color.RED);
        }

        cal.setTime(date);
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        dateView.setText( new StringBuilder().append(day).append("/")
                .append(String.valueOf(month+1)).append("/").append(year));


        return rowView;
    }
}
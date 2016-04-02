package il.ac.huji.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by EstherShlomi on 4/1/2016.
 */
public class Custom_view2 extends CursorAdapter {

    private LayoutInflater  mInflater;
    private Context mycontext;
    private ArrayList<Model> modelsArrayList;


    Custom_view2 (Context context,  Cursor c) {
        super(context, c);
        mInflater = LayoutInflater.from(context);
        mycontext = context;


    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.target_item, parent, false);

    }

    private int[] colors = new int[] { Color.GRAY, Color.WHITE };

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long time = cursor.getLong(cursor.getColumnIndex("due"));
        String title = cursor.getString(cursor.getColumnIndex("title"));


        int position = cursor.getPosition();
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);


        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        String format = "dd/M/y";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateString = sdf.format(cal.getTime());

        TextView titleView = (TextView) view.findViewById(R.id.txtTodoTitle);
        TextView dateView = (TextView) view.findViewById(R.id.txtTodoDueDate);

        ((TextView) view.findViewById(R.id.txtTodoDueDate)).setText(dateString);
        ((TextView) view.findViewById(R.id.txtTodoTitle)).setText(title);

        cal=Calendar.getInstance();
        int taskdate=(int)(time/86400000);
        int currentdate=(int)(cal.getTimeInMillis()/86400000);
        if (taskdate<currentdate) {
            titleView.setTextColor(Color.RED);
            dateView.setTextColor(Color.RED);
        }



    }

}

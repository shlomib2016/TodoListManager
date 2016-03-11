package il.ac.huji.todolist;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_view extends ArrayAdapter<String> {
    private int[] colors = new int[] { Color.RED, Color.BLUE };
    //private int[] colors = new int[] { R., 0x30808080 };
    public Custom_view(Context context, int layout, ArrayList<String> from) {
        super(context, layout, from);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position % colors.length;
        view.setBackgroundColor(colors[colorPos]);
        return view;
    }
}
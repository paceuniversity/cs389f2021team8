package com.pace.myteacher;

import android.content.Context;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class TeacherTable extends TableLayout
{
    public TeacherTable(Context context) {
        super(context);

        setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        TableRow row = new TableRow(context);
        row.setLayoutParams(new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

        Button b = new Button(getContext());
        b.setText("hello");
        b.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        row.addView(b);
        addView(row);
    }
}
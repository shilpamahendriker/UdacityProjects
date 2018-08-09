package com.example.avni.newsapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

/*Creating custom prefernce for date*/
public class DatePreference extends DialogPreference {
    private int lastDate = 0;
    private int lastMonth = 0;
    private int lastYear = 0;
    private String dateval;
    private DatePicker picker = null;

    public DatePreference(Context ctxt, AttributeSet attrs) {
        super(ctxt, attrs);

        setPositiveButtonText("Set");
        setNegativeButtonText("Cancel");
    }

    /* methods for Seperating out month day and year from the default date  */
    public static int getYear(String dateval) {
        String[] pieces = dateval.split(" ");
        return (Integer.parseInt(pieces[2]));
    }

    public static int getMonth(String dateval) {
        String[] pieces = dateval.split(" ");
        String month = pieces[0];
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMM");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(inputFormat.parse(month));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outputFormat = new SimpleDateFormat("MM"); // 01-12

        return (Integer.parseInt(outputFormat.format(cal.getTime())) - 2);
    }

    public static int getDate(String dateval) {
        String[] pieces = dateval.split(" ");
        if (pieces[1].length() > 2) {

            return (Integer.parseInt(pieces[1].substring(0, 2)));
        } else
            return (Integer.parseInt(pieces[1].substring(0, 1)));
    }

    /*Instantiating date picker*/
    @Override
    protected View onCreateDialogView() {
        picker = new DatePicker(getContext());
        return (picker);
    }

    /*Bing Datepicker to dialogview*/
    @Override
    protected void onBindDialogView(View v) {
        super.onBindDialogView(v);
        picker.updateDate(lastYear, lastMonth + 1, lastDate);
    }

    /*pick the selected date from the datepicker and format it as "MMM dd, yyyy"*/
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);

        if (positiveResult) {
            lastYear = picker.getYear();
            lastMonth = picker.getMonth();
            lastMonth = lastMonth + 1;
            lastDate = picker.getDayOfMonth();

            String dateval = "";

            switch (lastMonth) {
                case 1:
                    dateval = "Jan";
                    break;
                case 2:
                    dateval = "Feb";
                    break;
                case 3:
                    dateval = "Mar";
                    break;
                case 4:
                    dateval = "Apr";
                    break;
                case 5:
                    dateval = "May";
                    break;
                case 6:
                    dateval = "Jun";
                case 7:
                    dateval = "Jul";
                    break;
                case 8:
                    dateval = "Aug";
                    break;
                case 9:
                    dateval = "Sep";
                    break;
                case 10:
                    dateval = "Oct";
                    break;
                case 11:
                    dateval = "Nov";
                    break;
                case 12:
                    dateval = "Dec";
                    break;

            }

            dateval = dateval + " " + String.valueOf(lastDate) + ", " + String.valueOf(lastYear);

            if (callChangeListener(dateval)) {
                persistString(dateval);
            }
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return (a.getString(index));
    }

    /*Initialising deafault value*/
    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        dateval = null;

        if (restoreValue) {
            if (defaultValue == null) {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat format1 = new SimpleDateFormat("MMM dd, yyyy");
                String formatted = format1.format(cal.getTime());
                dateval = getPersistedString(formatted);
            } else {
                dateval = getPersistedString(defaultValue.toString());
            }
        } else {
            dateval = defaultValue.toString();
        }
        lastYear = getYear(dateval);
        lastMonth = getMonth(dateval);
        lastDate = getDate(dateval);
    }

}


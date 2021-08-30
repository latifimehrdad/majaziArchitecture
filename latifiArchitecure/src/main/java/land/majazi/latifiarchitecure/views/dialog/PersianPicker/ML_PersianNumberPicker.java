package land.majazi.latifiarchitecure.views.dialog.PersianPicker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;

public class ML_PersianNumberPicker extends NumberPicker {

    private Typeface typeFace;

    public ML_PersianNumberPicker(Context context) {
        super(context);
    }

    public ML_PersianNumberPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ML_PersianNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    public void setTypeFace(Typeface typeFace) {
        this.typeFace = typeFace;
        super.invalidate();
    }

    private void updateView(View view) {
        if (view instanceof TextView) {
            if (PersianDatePickerDialog.typeFace != null)
                ((TextView) view).setTypeface(PersianDatePickerDialog.typeFace);
        }
    }


}
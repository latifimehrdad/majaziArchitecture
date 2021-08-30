package land.majazi.latifiarchitecure.views.customs.timeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import land.majazi.latifiarchitecure.R;

public class ML_AnimationTime extends LinearLayout {

    private Context context;
    private TypedArray ta;

    private TextView textViewHourCurrent;
    private TextView textViewHourNext;

    private TextView textViewMinuteCurrent;
    private TextView textViewMinuteNext;

    private TextView textViewSecondCurrent;
    private TextView textViewSecondNext;

    private Drawable normalBack;
    private boolean elapseTime;
    private Animation animationExit;
    private Animation animationEnter;

    private int showing;
    private int textColor;
    private int textSize;
    private int fontFamilyId;

    private int hour;
    private int minute;
    private int second;


    //______________________________________________________________________________________________ ML_AnimationTime
    public ML_AnimationTime(Context context) {
        super(context);
        this.context = context;
    }
    //______________________________________________________________________________________________ ML_AnimationTime


    //______________________________________________________________________________________________ ML_AnimationTime
    public ML_AnimationTime(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }
    //______________________________________________________________________________________________ ML_AnimationTime


    //______________________________________________________________________________________________ init
    private void init(AttributeSet attrs) {
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.ML_AnimationTime);
        showing = ta.getInt(R.styleable.ML_AnimationTime_showing, 0);
        elapseTime = ta.getBoolean(R.styleable.ML_AnimationTime_elapseTime, false);

        configLayout();
        switch (showing) {
            case 0:
                configSecond();
                break;
            case 1:
                configMinute();
                addSeparator();
                configSecond();
                break;

            case 2:
                configHours();
                addSeparator();
                configMinute();
                addSeparator();
                configSecond();
                break;
        }

    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ configLayout
    private void configLayout() {
        setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent));
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        normalBack = ta.getDrawable(R.styleable.ML_AnimationTime_normalBack);
        textColor = ta.getColor(R.styleable.ML_AnimationTime_android_textColor, 0);
        textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_AnimationTime_textSize, 0) / getResources().getDisplayMetrics().density);
        fontFamilyId = ta.getResourceId(R.styleable.ML_AnimationTime_fontFamily, 0);
        second = ta.getInt(R.styleable.ML_AnimationTime_second, 0);
        hour = second / 3600;
        minute = (second % 3600) / 60;
        second = second % 60;
    }
    //______________________________________________________________________________________________ configLayout


    //______________________________________________________________________________________________ addSeparator
    private void addSeparator() {
        int separatorColor = ta.getColor(R.styleable.ML_AnimationTime_separatorTimeColor, 0);
        if (separatorColor == 0)
            separatorColor = textColor;

        TextView textView = new TextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView.setPadding(10, 10, 10, 10);
        textView.setTextColor(separatorColor);
        textView.setTextSize(Math.round(textSize * 1.5));
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText(":");
        textView.setMaxLines(1);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        int gravity = ta.getInt(R.styleable.ML_AnimationTime_gravity, 0x11);
        textView.setGravity(gravity);
        params.setMargins(5, 0, 5, 0);
        textView.setLayoutParams(params);
        addView(textView, params);
    }
    //______________________________________________________________________________________________ addSeparator


    //______________________________________________________________________________________________ configTextView
    private TextView configTextView() {
        TextView textView = new TextView(context);
        textView.setBackground(normalBack);
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        textView.setPadding(25, 0, 25, 0);
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        textView.setMaxLines(1);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        int gravity = ta.getInt(R.styleable.ML_AnimationTime_gravity, 0x11);
        textView.setGravity(gravity);
        textView.setLayoutParams(params);
        return textView;
    }
    //______________________________________________________________________________________________ configTextView


    //______________________________________________________________________________________________ configHours
    private void configHours() {
        textViewHourCurrent = configTextView();
        textViewHourNext = configTextView();
        textViewHourCurrent.setVisibility(VISIBLE);
        textViewHourNext.setVisibility(GONE);
        textViewHourCurrent.setText(String.format("%02d", hour));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        linearLayout.setLayoutParams(params);
        linearLayout.addView(textViewHourCurrent, textViewHourCurrent.getLayoutParams());
        linearLayout.addView(textViewHourNext, textViewHourNext.getLayoutParams());
        addView(linearLayout, linearLayout.getLayoutParams());
    }
    //______________________________________________________________________________________________ configHours


    //______________________________________________________________________________________________ configMinute
    private void configMinute() {
        textViewMinuteCurrent = configTextView();
        textViewMinuteNext = configTextView();
        textViewMinuteCurrent.setVisibility(VISIBLE);
        textViewMinuteNext.setVisibility(GONE);
        textViewMinuteCurrent.setText(String.format("%02d", minute));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        linearLayout.setLayoutParams(params);
        linearLayout.addView(textViewMinuteCurrent, textViewMinuteCurrent.getLayoutParams());
        linearLayout.addView(textViewMinuteNext, textViewMinuteNext.getLayoutParams());
        addView(linearLayout, linearLayout.getLayoutParams());
    }
    //______________________________________________________________________________________________ configMinute


    //______________________________________________________________________________________________ configMinute
    private void configSecond() {
        textViewSecondCurrent = configTextView();
        textViewSecondNext = configTextView();
        textViewSecondCurrent.setVisibility(VISIBLE);
        textViewSecondNext.setVisibility(GONE);
        textViewSecondCurrent.setText(String.format("%02d", second));

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1);
        linearLayout.setLayoutParams(params);
        linearLayout.addView(textViewSecondCurrent, textViewSecondCurrent.getLayoutParams());
        linearLayout.addView(textViewSecondNext, textViewSecondNext.getLayoutParams());
        addView(linearLayout, linearLayout.getLayoutParams());
    }
    //______________________________________________________________________________________________ configMinute


    //______________________________________________________________________________________________ changeTime
    public void changeTime(int totalSecond) {
        int nHour = totalSecond / 3600;
        int nMinute = (totalSecond % 3600) / 60;
        int nSecond = totalSecond % 60;

        if (showing > 1)
            if (nHour != hour) {
                hour = nHour;
                changeHour();
            }

        if (showing > 0)
            if (nMinute != minute) {
                minute = nMinute;
                changeMinute();
            }

        if (nSecond != second) {
            second = nSecond;
            changeSecond();
        }
    }
    //______________________________________________________________________________________________ changeTime


    //______________________________________________________________________________________________ changeHour
    private void changeHour() {
        configAnimation();
        String time = String.format("%02d", hour);
        if (textViewHourCurrent.getVisibility() == View.VISIBLE) {
            textViewHourNext.setText(time);
            textViewHourCurrent.startAnimation(animationExit);
            textViewHourNext.startAnimation(animationEnter);
            textViewHourCurrent.setVisibility(GONE);
            textViewHourNext.setVisibility(VISIBLE);

        } else {
            textViewHourCurrent.setText(time);
            textViewHourCurrent.startAnimation(animationEnter);
            textViewHourNext.startAnimation(animationExit);
            textViewHourNext.setVisibility(GONE);
            textViewHourCurrent.setVisibility(VISIBLE);
        }
    }
    //______________________________________________________________________________________________ changeHour


    //______________________________________________________________________________________________ changeMinute
    private void changeMinute() {
        configAnimation();
        String time = String.format("%02d", minute);
        if (textViewMinuteCurrent.getVisibility() == View.VISIBLE) {
            textViewMinuteNext.setText(time);
            textViewMinuteCurrent.startAnimation(animationExit);
            textViewMinuteNext.startAnimation(animationEnter);
            textViewMinuteCurrent.setVisibility(GONE);
            textViewMinuteNext.setVisibility(VISIBLE);

        } else {
            textViewMinuteCurrent.setText(time);
            textViewMinuteCurrent.startAnimation(animationEnter);
            textViewMinuteNext.startAnimation(animationExit);
            textViewMinuteNext.setVisibility(GONE);
            textViewMinuteCurrent.setVisibility(VISIBLE);
        }
    }
    //______________________________________________________________________________________________ changeMinute


    //______________________________________________________________________________________________ changeSecond
    private void changeSecond() {
        configAnimation();
        String time = String.format("%02d", second);
        if (textViewSecondCurrent.getVisibility() == View.VISIBLE) {
            textViewSecondNext.setText(time);
            textViewSecondCurrent.startAnimation(animationExit);
            textViewSecondNext.startAnimation(animationEnter);
            textViewSecondCurrent.setVisibility(GONE);
            textViewSecondNext.setVisibility(VISIBLE);

        } else {
            textViewSecondCurrent.setText(time);
            textViewSecondCurrent.startAnimation(animationEnter);
            textViewSecondNext.startAnimation(animationExit);
            textViewSecondNext.setVisibility(GONE);
            textViewSecondCurrent.setVisibility(VISIBLE);
        }
    }
    //______________________________________________________________________________________________ changeSecond


    //______________________________________________________________________________________________ configAnimation
    private void configAnimation() {
        if (elapseTime) {
            animationExit = AnimationUtils.loadAnimation(context, R.anim.slide_out_top);
            animationEnter = AnimationUtils.loadAnimation(context, R.anim.slide_in_bottom);
        } else {
            animationExit = AnimationUtils.loadAnimation(context, R.anim.slide_out_bottom);
            animationEnter = AnimationUtils.loadAnimation(context, R.anim.slide_in_top);
        }
    }
    //______________________________________________________________________________________________ configAnimation

}

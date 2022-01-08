package land.majazi.latifiarchitecure.views.customs.buttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;

import land.majazi.latifiarchitecure.R;
import pl.droidsonroids.gif.GifImageView;

public class ML_Button extends LinearLayout {

    private TypedArray ta;
    private String text;
    private String waitText;

    private TextView textView;
    private GifImageView gifImageView;

//    private Drawable normalBack;
//    private Drawable waitingBack;

    private int iconTint;
    private int textColor;
    private boolean click;

    //______________________________________________________________________________________________ ML_Button
    public ML_Button(Context context) {
        super(context);
    }
    //______________________________________________________________________________________________ ML_Button


    //______________________________________________________________________________________________ ML_Button
    public ML_Button(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.ML_Button);
        configLayout(context);
    }
    //______________________________________________________________________________________________ ML_Button


    //______________________________________________________________________________________________ configLayout
    private void configLayout(Context context) {
        click = false;
        waitText = ta.getString(R.styleable.ML_Button_waitingText);
        setBackground(ta.getDrawable(R.styleable.ML_Button_normalBack));
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        if (ta.getInt(R.styleable.ML_Button_buttonIconDirection, 1) == 1) {
            configTextView(context);
            configDelimiterLayout(context);
            configGif(context);
        } else {
            configGif(context);
            configDelimiterLayout(context);
            configTextView(context);
        }

    }
    //______________________________________________________________________________________________ configLayout


    //______________________________________________________________________________________________ configTextView
    private void configTextView(Context context) {
        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_Button_textSize, 0) / getResources().getDisplayMetrics().density);
        if (textSize == 0)
            return;

        text = ta.getString(R.styleable.ML_Button_text);
        textView = new TextView(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);

        textColor = ta.getColor(R.styleable.ML_Button_android_textColor, 0);
        textView.setTextColor(textColor);


        textView.setTextSize(textSize);

        int maxLine = ta.getInteger(R.styleable.ML_Button_maxLine, 1);
        textView.setMaxLines(maxLine);
        textView.setText(text);

        int maxLength = ta.getInteger(R.styleable.ML_Button_maxLength, 100);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        textView.setFilters(fArray);

        int fontFamilyId = ta.getResourceId(R.styleable.ML_Button_fontFamily, 0);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));

        int gravity = ta.getInt(R.styleable.ML_Button_gravity, 0x11);
        textView.setGravity(gravity);

        textView.setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent));
        textView.setLayoutParams(params);
        textView.setPadding(10, 3, 10, 3);

        addView(textView, params);

    }
    //______________________________________________________________________________________________ configTextView


    //______________________________________________________________________________________________ configDelimiterLayout
    private void configDelimiterLayout(Context context) {


        int delimiterWidth = (int) (ta.getDimension(R.styleable.ML_Button_delimiterWidth, 0));
        if (delimiterWidth == 0)
            return;

        Drawable back = ta.getDrawable(R.styleable.ML_Button_delimiterBack);

        LinearLayout delimiter = new LinearLayout(context);


        LayoutParams params = new LayoutParams(delimiterWidth, LayoutParams.MATCH_PARENT);

        int marginLeft = (int) (ta.getDimensionPixelSize(R.styleable.ML_Button_delimiterMarginLeft, 0));
        int marginTop = (int) (ta.getDimensionPixelSize(R.styleable.ML_Button_delimiterMarginTop, 0));
        int marginRight = (int) (ta.getDimensionPixelSize(R.styleable.ML_Button_delimiterMarginRight, 0));
        int marginBottom = (int) (ta.getDimensionPixelSize(R.styleable.ML_Button_delimiterMarginBottom, 0));
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        delimiter.setLayoutParams(params);
        delimiter.setBackground(back);
        addView(delimiter, params);
    }
    //______________________________________________________________________________________________ configDelimiterLayout


    //______________________________________________________________________________________________ configIcon
    private void configGif(Context context) {
        iconTint = ta.getColor(R.styleable.ML_Button_iconTint, 0);
        gifImageView = new GifImageView(context);
        int width = (int) (ta.getDimension(R.styleable.ML_Button_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_Button_iconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        gifImageView.setLayoutParams(params);
        setIcon(ta.getDrawable(R.styleable.ML_Button_icon), iconTint);
        addView(gifImageView, params);
    }
    //______________________________________________________________________________________________ configIcon


    //______________________________________________________________________________________________ setIcon
    public void setIcon(Drawable icon, int iconTint) {
        gifImageView.setImageDrawable(icon);
        gifImageView.setColorFilter(iconTint);
    }
    //______________________________________________________________________________________________ setIcon


    //______________________________________________________________________________________________ startLoading
    public void startLoading() {
        click = true;

        if (ta.getDrawable(R.styleable.ML_Button_waitingBack) != null)
            setBackground(ta.getDrawable(R.styleable.ML_Button_waitingBack));

        if (textView != null) {
            textView.setTextColor(ta.getColor(R.styleable.ML_Button_textWaitingColor, textColor));
        }

        if (!ta.getBoolean(R.styleable.ML_Button_ml_enableLoading, true))
            return;

        if (waitText != null) {
            if (textView != null) {
                if (waitText.length() < text.length()) {
                    setMinimumWidth(getWidth());
                }
                textView.setText(waitText);
            }
        }

        gifImageView.setImageDrawable(ta.getDrawable(R.styleable.ML_Button_gifLoading));

    }
    //______________________________________________________________________________________________ startLoading


    //______________________________________________________________________________________________ stopLoading
    public void stopLoading() {

        click = false;
        gifImageView.setImageDrawable(ta.getDrawable(R.styleable.ML_Button_icon));

        if (textView != null) {
            textView.setTextColor(textColor);
            textView.setText(text);
        }

        setBackground(ta.getDrawable(R.styleable.ML_Button_normalBack));

    }
    //______________________________________________________________________________________________ stopLoading


    //______________________________________________________________________________________________ isClick
    public boolean isClick() {
        return click;
    }
    //______________________________________________________________________________________________ isClick



    //______________________________________________________________________________________________ setTextAndTintDefaultColor
    public void setTextAndTintDefaultColor() {
        if (textView != null)
            textView.setTextColor(textColor);
        if (gifImageView != null)
            gifImageView.setColorFilter(iconTint);
    }
    //______________________________________________________________________________________________ setTextAndTintDefaultColor


    //______________________________________________________________________________________________ setTextAndTintColor
    public void setTextAndTintColor(int color) {
        if (textView != null)
            textView.setTextColor(color);
        if (gifImageView != null)
            gifImageView.setColorFilter(color);

    }
    //______________________________________________________________________________________________ setTextAndTintColor


    //______________________________________________________________________________________________ setTitle
    public void setTitle(String title) {
        this.text = title;
        textView.setText(title);
    }
    //______________________________________________________________________________________________ setTitle


    //______________________________________________________________________________________________ setText
    @BindingAdapter("text")
    public static void setTitle(ML_Button view, String newValue) {
        if (view == null || newValue == null)
            return;

        if (view.getText() == null || !view.text.equalsIgnoreCase(newValue)) {
            view.text = newValue;
            view.getTextView().setText(view.text);
            return;
        }

    }
    //______________________________________________________________________________________________ setText


    //______________________________________________________________________________________________ getText
    public String getText() {
        return text;
    }
    //______________________________________________________________________________________________ getText


    //______________________________________________________________________________________________ setWaitText
    public void setWaitText(String waitText) {
        this.waitText = waitText;
    }
    //______________________________________________________________________________________________ setWaitText


    //______________________________________________________________________________________________ getTextView
    public TextView getTextView() {
        return textView;
    }
    //______________________________________________________________________________________________ getTextView


}
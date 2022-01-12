package land.majazi.latifiarchitecure.views.customs.buttons;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;

import land.majazi.latifiarchitecure.R;
import pl.droidsonroids.gif.GifImageView;

public class MLButton extends LinearLayout {

    private TypedArray ta;
    private String text;
    private String waitText;

    private TextView textView;
    private GifImageView gifImageView;

    private int textColor;
    private boolean click;

    //______________________________________________________________________________________________ MLButton
    public MLButton(Context context) {
        super(context);
    }
    //______________________________________________________________________________________________ MLButton


    //______________________________________________________________________________________________ MLButton
    public MLButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.MLButton);
        configLayout(context);
    }
    //______________________________________________________________________________________________ MLButton


    //______________________________________________________________________________________________ configLayout
    private void configLayout(Context context) {
        click = false;
        waitText = ta.getString(R.styleable.MLButton_waitingText);
        setBackground(ta.getDrawable(R.styleable.MLButton_normalBack));
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        if (ta.getInt(R.styleable.MLButton_buttonIconDirection, 1) == 1) {
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
        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.MLButton_textSize, 0) / getResources().getDisplayMetrics().density);
        if (textSize == 0)
            return;

        text = ta.getString(R.styleable.MLButton_text);
        textView = new TextView(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);

        textColor = ta.getColor(R.styleable.MLButton_android_textColor, 0);
        textView.setTextColor(textColor);


        textView.setTextSize(textSize);

        int maxLine = ta.getInteger(R.styleable.MLButton_maxLine, 1);
        textView.setMaxLines(maxLine);
        textView.setText(text);

        int maxLength = ta.getInteger(R.styleable.MLButton_maxLength, 100);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        textView.setFilters(fArray);

        int fontFamilyId = ta.getResourceId(R.styleable.MLButton_fontFamily, 0);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));

        int gravity = ta.getInt(R.styleable.MLButton_gravity, 0x11);
        textView.setGravity(gravity);

        textView.setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent, context.getTheme()));
        textView.setLayoutParams(params);
        textView.setPadding(10, 3, 10, 3);

        addView(textView, params);

    }
    //______________________________________________________________________________________________ configTextView


    //______________________________________________________________________________________________ configDelimiterLayout
    private void configDelimiterLayout(Context context) {


        int delimiterWidth = (int) (ta.getDimension(R.styleable.MLButton_delimiterWidth, 0));
        if (delimiterWidth == 0)
            return;

        Drawable back = ta.getDrawable(R.styleable.MLButton_delimiterBack);

        LinearLayout delimiter = new LinearLayout(context);


        LayoutParams params = new LayoutParams(delimiterWidth, LayoutParams.MATCH_PARENT);

        int marginLeft = ta.getDimensionPixelSize(R.styleable.MLButton_delimiterMarginLeft, 0);
        int marginTop = ta.getDimensionPixelSize(R.styleable.MLButton_delimiterMarginTop, 0);
        int marginRight = ta.getDimensionPixelSize(R.styleable.MLButton_delimiterMarginRight, 0);
        int marginBottom = ta.getDimensionPixelSize(R.styleable.MLButton_delimiterMarginBottom, 0);
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        delimiter.setLayoutParams(params);
        delimiter.setBackground(back);
        addView(delimiter, params);
    }
    //______________________________________________________________________________________________ configDelimiterLayout


    //______________________________________________________________________________________________ configIcon
    private void configGif(Context context) {
        gifImageView = new GifImageView(context);
        int width = (int) (ta.getDimension(R.styleable.MLButton_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.MLButton_iconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        gifImageView.setLayoutParams(params);
        setIcon(ta.getDrawable(R.styleable.MLButton_icon), ta.getColor(R.styleable.MLButton_iconTint, 0));
        addView(gifImageView, params);
    }
    //______________________________________________________________________________________________ configIcon


    //______________________________________________________________________________________________ setIcon
    private void setIcon(Drawable icon, int iconTint) {
        gifImageView.setImageDrawable(icon);
        gifImageView.setColorFilter(iconTint);
    }
    //______________________________________________________________________________________________ setIcon


    //______________________________________________________________________________________________ startLoading
    public void startLoading() {
        click = true;

        if (ta.getDrawable(R.styleable.MLButton_waitingBack) != null)
            setBackground(ta.getDrawable(R.styleable.MLButton_waitingBack));

        if (textView != null) {
            textView.setTextColor(ta.getColor(R.styleable.MLButton_textWaitingColor, textColor));
        }

        if (!ta.getBoolean(R.styleable.MLButton_ml_enableLoading, true))
            return;

        if (waitText != null) {
            if (textView != null) {
                if (waitText.length() < text.length()) {
                    setMinimumWidth(getWidth());
                }
                textView.setText(waitText);
            }
        }

        gifImageView.setImageDrawable(ta.getDrawable(R.styleable.MLButton_gifLoading));

    }
    //______________________________________________________________________________________________ startLoading


    //______________________________________________________________________________________________ stopLoading
    public void stopLoading() {

        click = false;
        gifImageView.setImageDrawable(ta.getDrawable(R.styleable.MLButton_icon));

        if (textView != null) {
            textView.setTextColor(textColor);
            textView.setText(text);
        }

        setBackground(ta.getDrawable(R.styleable.MLButton_normalBack));

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
            gifImageView.setColorFilter(ta.getColor(R.styleable.MLButton_iconTint, 0));
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
    public static void setTitle(MLButton view, String newValue) {
        if (view == null || newValue == null)
            return;

        if (view.getText() == null || !view.text.equalsIgnoreCase(newValue)) {
            view.text = newValue;
            view.getTextView().setText(view.text);
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


    //______________________________________________________________________________________________ setNormalBack
    @BindingAdapter("normalBack")
    public static void setNormalBack(MLButton mlButton, Drawable normalBack) {
        mlButton.setBackground(normalBack);
    }
    //______________________________________________________________________________________________ setNormalBack


    //______________________________________________________________________________________________ setIcon
    @BindingAdapter("icon")
    public static void setIcon(MLButton mlButton, Drawable icon) {
        mlButton.gifImageView.setImageDrawable(icon);
    }
    //______________________________________________________________________________________________ setIcon



}
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
import land.majazi.latifiarchitecure.utility.Utility;
import land.majazi.latifiarchitecure.views.customs.text.ML_Editable;
import pl.droidsonroids.gif.GifImageView;

public class ML_Button extends LinearLayout {

    private Context context;
    private TypedArray ta;
    private Utility utility;
    private String text;
    private String waitText;

    private TextView textView;
    private ImageView imageIcon;
    private GifImageView gifImageView;

    private Drawable normalBack;
    private Drawable waitingBack;
    private Drawable icon;
    private int iconTint;

    private int textColor;
    private int textWaitingColor;
    private int iconDirection;

    private boolean click;

    //______________________________________________________________________________________________ ML_Button
    public ML_Button(Context context) {
        super(context);
        this.context = context;
    }
    //______________________________________________________________________________________________ ML_Button


    //______________________________________________________________________________________________ ML_Button
    public ML_Button(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }
    //______________________________________________________________________________________________ ML_Button


    //______________________________________________________________________________________________ init
    private void init(AttributeSet attrs) {
        utility = new Utility();
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.ML_Button);
        configLayout();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ configLayout
    private void configLayout() {
        normalBack = ta.getDrawable(R.styleable.ML_Button_normalBack);
        waitingBack = ta.getDrawable(R.styleable.ML_Button_waitingBack);
        click = false;
        iconDirection = ta.getInt(R.styleable.ML_Button_buttonIconDirection, 1);
        waitText = ta.getString(R.styleable.ML_Button_waitingText);
        setBackground(normalBack);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        if (iconDirection == 1) {
            configTextView();
            configDelimiterLayout();
            configIcon();
            if (waitText != null)
                configGif();
        } else {
            if (waitText != null)
                configGif();
            configIcon();
            configDelimiterLayout();
            configTextView();
        }

    }
    //______________________________________________________________________________________________ configLayout


    //______________________________________________________________________________________________ configTextView
    private void configTextView() {
        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_Button_textSize, 0) / getResources().getDisplayMetrics().density);
        if (textSize == 0)
            return;

        text = ta.getString(R.styleable.ML_Button_text);
        textView = new TextView(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);

        textColor = ta.getColor(R.styleable.ML_Button_android_textColor, 0);
        textWaitingColor = ta.getColor(R.styleable.ML_Button_textWaitingColor, textColor);
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
    private void configDelimiterLayout() {


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
    private void configIcon() {
        imageIcon = new ImageView(context);
        int width = (int) (ta.getDimension(R.styleable.ML_Button_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_Button_iconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        imageIcon.setLayoutParams(params);
        icon = ta.getDrawable(R.styleable.ML_Button_icon);
        iconTint = ta.getColor(R.styleable.ML_Button_iconTint, 0);
        setIcon(icon, iconTint);
        addView(imageIcon, params);
    }
    //______________________________________________________________________________________________ configIcon


    //______________________________________________________________________________________________ configIcon
    private void configGif() {
        int gif = ta.getResourceId(R.styleable.ML_Button_gifLoading, 0);
        if (gif == 0)
            return;

        gifImageView = new GifImageView(context);
        int width = (int) (ta.getDimension(R.styleable.ML_Button_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_Button_iconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        gifImageView.setLayoutParams(params);
        gifImageView.setImageResource(gif);
        gifImageView.setVisibility(GONE);
        addView(gifImageView, params);
    }
    //______________________________________________________________________________________________ configIcon


    //______________________________________________________________________________________________ setIcon
    public void setIcon(Drawable icon, int iconTint) {
        this.icon = icon;
        imageIcon.setImageDrawable(icon);
        imageIcon.setColorFilter(iconTint);
    }
    //______________________________________________________________________________________________ setIcon


    //______________________________________________________________________________________________ startLoading
    public void startLoading() {
        click = true;

        if (waitingBack == null)
            waitingBack = normalBack;

        setBackground(waitingBack);

        if (textView != null) {
            textView.setTextColor(textWaitingColor);
        }


        if (waitText != null) {
            if (textView != null) {
                if (waitText.length() < text.length()) {
                    setMinimumWidth(getWidth());
                }
                textView.setText(waitText);
            }
            gifImageView.setVisibility(VISIBLE);
            imageIcon.setVisibility(GONE);
        }

    }
    //______________________________________________________________________________________________ startLoading


    //______________________________________________________________________________________________ stopLoading
    public void stopLoading() {

        click = false;
        if (gifImageView != null) {
            gifImageView.setVisibility(GONE);
            imageIcon.setVisibility(VISIBLE);
        }

        if (textView != null) {
            textView.setTextColor(textColor);
            textView.setText(text);
        }

        setBackground(normalBack);

    }
    //______________________________________________________________________________________________ stopLoading


    //______________________________________________________________________________________________ isClick
    public boolean isClick() {
        return click;
    }
    //______________________________________________________________________________________________ isClick


    //______________________________________________________________________________________________ setCustomBackgroundDrawable
    public void setCustomBackgroundDrawable(Drawable drawable) {
        normalBack = drawable;
        setBackground(normalBack);
    }
    //______________________________________________________________________________________________ setCustomBackgroundDrawable


    //______________________________________________________________________________________________ setBackgroundDefaultDrawable
    public void setBackgroundDefaultDrawable(int drawable) {
        setBackground(normalBack);
    }
    //______________________________________________________________________________________________ setBackgroundDefaultDrawable


    //______________________________________________________________________________________________ setTextAndTintDefaultColor
    public void setTextAndTintDefaultColor() {
        if (textView != null)
            textView.setTextColor(textColor);
        if (imageIcon != null)
            imageIcon.setColorFilter(iconTint);
    }
    //______________________________________________________________________________________________ setTextAndTintDefaultColor


    //______________________________________________________________________________________________ setTextAndTintColor
    public void setTextAndTintColor(int color) {
        if (textView != null)
            textView.setTextColor(color);
        if (imageIcon != null)
            imageIcon.setColorFilter(color);

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


    //______________________________________________________________________________________________ setNormalBack
    @BindingAdapter("normalBack")
    public static void setNormalBack(ML_Button view, Drawable normalBack) {
        view.normalBack = normalBack;
        view.setBackground(normalBack);
    }
    //______________________________________________________________________________________________ setNormalBack


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


    //______________________________________________________________________________________________ setIcon
    @BindingAdapter("icon")
    public static void setIcon(ML_Button view, Drawable icon) {
        view.icon = icon;
        view.imageIcon.setImageDrawable(icon);
    }
    //______________________________________________________________________________________________ setIcon

}
package land.majazi.latifiarchitecure.views.customs.text;

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

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.utility.Splitter;
import land.majazi.latifiarchitecure.utility.Validator;

public class MLTextView extends LinearLayout {

    private TypedArray ta;
    private Object additionalValue;

    private TextView textView;
    private ImageView imageIcon;

    private Drawable iconError;

    private String text;


    //______________________________________________________________________________________________ ML_TextView
    public MLTextView(Context context) {
        super(context);

    }
    //______________________________________________________________________________________________ ML_TextView


    //______________________________________________________________________________________________ ML_TextView
    public MLTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.MLTextView);
        configLayout(context);
    }
    //______________________________________________________________________________________________ ML_TextView



    //______________________________________________________________________________________________ configLayout
    private void configLayout(Context context) {
        setBackground(ta.getDrawable(R.styleable.MLTextView_normalBack));
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        configTextView(context);
        configDelimiterLayout(context);
        configIcon(context);

    }
    //______________________________________________________________________________________________ configLayout


    //______________________________________________________________________________________________ configTextView
    private void configTextView(Context context) {

        textView = new TextView(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);

        int textColor = ta.getColor(R.styleable.MLTextView_android_textColor, 0);
        textView.setTextColor(textColor);

        String hint = ta.getString(R.styleable.MLTextView_hint);
        textView.setHint(hint);

        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.MLTextView_textSize, 0) / getResources().getDisplayMetrics().density);
        textView.setTextSize(textSize);

        int maxLine = ta.getInteger(R.styleable.MLTextView_maxLine, 1);
        textView.setMaxLines(maxLine);

        int maxLength = ta.getInteger(R.styleable.MLTextView_maxLength, 100);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        textView.setFilters(fArray);

        int fontFamilyId = ta.getResourceId(R.styleable.MLTextView_fontFamily, 0);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));

        text = ta.getString(R.styleable.MLTextView_text);
        textView.setText(text);

        int gravity = ta.getInt(R.styleable.MLTextView_gravity, 0x11);
        textView.setGravity(gravity);

        textView.setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent, context.getTheme()));
        textView.setLayoutParams(params);
        textView.setPadding(10, 1, 10, 1);



        addView(textView, params);

    }
    //______________________________________________________________________________________________ configTextView


    //______________________________________________________________________________________________ configDelimiterLayout
    private void configDelimiterLayout(Context context) {
        LinearLayout delimiter = new LinearLayout(context);

        int delimiterWidth = (int) (ta.getDimension(R.styleable.MLTextView_delimiterWidth, 0));
        LayoutParams params = new LayoutParams(delimiterWidth, LayoutParams.MATCH_PARENT);

        int marginLeft = ta.getDimensionPixelSize(R.styleable.MLTextView_delimiterMarginLeft, 0);
        int marginTop = ta.getDimensionPixelSize(R.styleable.MLTextView_delimiterMarginTop, 0);
        int marginRight = ta.getDimensionPixelSize(R.styleable.MLTextView_delimiterMarginRight, 0);
        int marginBottom = ta.getDimensionPixelSize(R.styleable.MLTextView_delimiterMarginBottom, 0);
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        Drawable back = ta.getDrawable(R.styleable.MLTextView_delimiterBack);
        delimiter.setLayoutParams(params);
        delimiter.setBackground(back);
        addView(delimiter, params);
    }
    //______________________________________________________________________________________________ configDelimiterLayout


    //______________________________________________________________________________________________ configIcon
    private void configIcon(Context context) {
        imageIcon = new ImageView(context);
        int width = (int) (ta.getDimension(R.styleable.MLTextView_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.MLTextView_iconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        imageIcon.setLayoutParams(params);
        iconError = ta.getDrawable(R.styleable.MLTextView_iconError);
        if (iconError == null)
            iconError = ta.getDrawable(R.styleable.MLTextView_icon);
        setIcon(ta.getDrawable(R.styleable.MLTextView_icon), ta.getColor(R.styleable.MLTextView_iconTint, 0));
        addView(imageIcon, params);
    }
    //______________________________________________________________________________________________ configIcon



    //______________________________________________________________________________________________ removeError
    public void removeError() {
        setBackground(ta.getDrawable(R.styleable.MLTextView_normalBack));
        textView.setError(null);
        setIcon(ta.getDrawable(R.styleable.MLTextView_icon), ta.getColor(R.styleable.MLTextView_iconTint, 0));
    }
    //______________________________________________________________________________________________ removeError



    //______________________________________________________________________________________________ setText
    public void setText(String text) {
        this.text = text;
        if (ta.getBoolean(R.styleable.MLTextView_splitter, false)) {
            String value = text;
            value = new Splitter().split(value);
            textView.setText(value);
        } else {
            getTextView().setText(text);
        }
        removeError();
    }
    //______________________________________________________________________________________________ setText


    //______________________________________________________________________________________________ getText
    public String getText() {
        if (text == null)
            return null;

        if (ta.getBoolean(R.styleable.MLTextView_splitter, false)) {
            text = text.replaceAll(",", "");
            text = text.replaceAll("٬", "");
        }
        return text;
    }
    //______________________________________________________________________________________________ getText


    //______________________________________________________________________________________________ getTextByLine
    public String getTextByLine(int line) {
        int start = getTextView().getLayout().getLineStart(1);
        int end = getTextView().getLayout().getLineEnd(1);
        return getText().substring(start, end);
    }
    //______________________________________________________________________________________________ getTextByLine


    //______________________________________________________________________________________________ setErrorLayout
    public void setErrorLayout(String error) {
        setIcon(iconError, ta.getColor(R.styleable.MLTextView_iconErrorTint, ta.getColor(R.styleable.MLTextView_iconTint, 0)));
        setBackground(ta.getDrawable(R.styleable.MLTextView_emptyBack));
        getTextView().setError(error);
        getTextView().requestFocus();
    }
    //______________________________________________________________________________________________ setErrorLayout


    //______________________________________________________________________________________________ getEditText
    public TextView getTextView() {
        return textView;
    }
    //______________________________________________________________________________________________ getEditText



    //______________________________________________________________________________________________ setIcon
    public void setIcon(Drawable icon, int iconTint) {
        imageIcon.setImageDrawable(icon);
        imageIcon.setColorFilter(iconTint);
    }
    //______________________________________________________________________________________________ setIcon


    //______________________________________________________________________________________________ getAdditionalValue
    public Object getAdditionalValue() {
        return additionalValue;
    }
    //______________________________________________________________________________________________ getAdditionalValue


    //______________________________________________________________________________________________ setAdditionalValue
    public void setAdditionalValue(Object additionalValue) {
        this.additionalValue = additionalValue;
    }
    //______________________________________________________________________________________________ setAdditionalValue


    //______________________________________________________________________________________________ checkValidation
    public boolean checkValidation() {
        boolean result = true;
        Validator validator = new Validator();
        switch (ta.getInt(R.styleable.MLTextView_validationType, 0)) {
            case 0://none
                result = true;
                break;

            case 1://mobile
                result = validator.isMobile(getAdditionalValue().toString());
                break;

            case 2://text
                result = validator.isText(getAdditionalValue().toString());
                break;

            case 3://email
                result = validator.isEmail(getAdditionalValue().toString());
                break;

            case 4://national
                result = validator.isNationalCode(getAdditionalValue().toString());
                break;

            case 5://password
                result = validator.isStrongPassword(getAdditionalValue().toString());
                break;
            case 7://phone
                result = validator.isPhone(getAdditionalValue().toString());
                break;
            case 11://shaba
                result = validator.isShaba(getAdditionalValue().toString());
                break;
            case 12:// price
                result = validator.isPriceForGetWay(getAdditionalValue().toString());
                break;
            case 15:// postalCode
                result = validator.isPostalCode(getAdditionalValue().toString());
                break;
            case 16://CardNumber
                result = validator.isCardNumber(getAdditionalValue().toString());
                break;
            case 17://persian name
                result = validator.isPersianName(getAdditionalValue().toString());
                break;
        }

        if (!result)
            setErrorLayout(ta.getString(R.styleable.MLTextView_errorText));

        return result;
    }
    //______________________________________________________________________________________________ checkValidation

}
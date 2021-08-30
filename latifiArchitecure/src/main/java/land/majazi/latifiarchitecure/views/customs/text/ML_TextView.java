package land.majazi.latifiarchitecure.views.customs.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.utility.Utility;

public class ML_TextView extends LinearLayout {

    private Context context;
    private TypedArray ta;
    private Utility utility;
    private Object additionalValue;
    private String text;
    private boolean splitter;
    private int validationType;

    private TextView textView;
    private ImageView imageIcon;
    private String errorText = "";
    private Drawable normalBack;
    private Drawable emptyBack;
    private Drawable icon;
    private Drawable iconError;
    private int iconTint;
    private int iconErrorTint;

    //______________________________________________________________________________________________ ML_TextView
    public ML_TextView(Context context) {
        super(context);
        this.context = context;
    }
    //______________________________________________________________________________________________ ML_TextView


    //______________________________________________________________________________________________ ML_TextView
    public ML_TextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }
    //______________________________________________________________________________________________ ML_TextView


    //______________________________________________________________________________________________ init
    private void init(AttributeSet attrs) {
        utility = new Utility();
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.ML_TextView);
        configLayout();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ configLayout
    private void configLayout() {
        normalBack = ta.getDrawable(R.styleable.ML_TextView_normalBack);
        emptyBack = ta.getDrawable(R.styleable.ML_TextView_emptyBack);
        setBackground(normalBack);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        configTextView();
        configDelimiterLayout();
        configIcon();

    }
    //______________________________________________________________________________________________ configLayout


    //______________________________________________________________________________________________ configTextView
    private void configTextView() {

        textView = new TextView(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);

        int textColor = ta.getColor(R.styleable.ML_TextView_android_textColor, 0);
        textView.setTextColor(textColor);

        String hint = ta.getString(R.styleable.ML_TextView_hint);
        textView.setHint(hint);

        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_TextView_textSize, 0) / getResources().getDisplayMetrics().density);
        textView.setTextSize(textSize);

        int maxLine = ta.getInteger(R.styleable.ML_TextView_maxLine, 1);
        textView.setMaxLines(maxLine);
        errorText = ta.getString(R.styleable.ML_TextView_errorText);
        validationType = ta.getInt(R.styleable.ML_TextView_validationType, 0);

        int maxLength = ta.getInteger(R.styleable.ML_TextView_maxLength, 100);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        textView.setFilters(fArray);

        int fontFamilyId = ta.getResourceId(R.styleable.ML_TextView_fontFamily, 0);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));

        text = ta.getString(R.styleable.ML_TextView_text);
        textView.setText(text);

        int gravity = ta.getInt(R.styleable.ML_TextView_gravity, 0x11);
        textView.setGravity(gravity);

        textView.setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent));
        textView.setLayoutParams(params);
        textView.setPadding(10, 1, 10, 1);

        splitter = ta.getBoolean(R.styleable.ML_TextView_splitter, false);

        addView(textView, params);

    }
    //______________________________________________________________________________________________ configTextView


    //______________________________________________________________________________________________ configDelimiterLayout
    private void configDelimiterLayout() {
        LinearLayout delimiter = new LinearLayout(context);

        int delimiterWidth = (int) (ta.getDimension(R.styleable.ML_TextView_delimiterWidth, 0));
        LayoutParams params = new LayoutParams(delimiterWidth, LayoutParams.MATCH_PARENT);

        int marginLeft = (int) (ta.getDimensionPixelSize(R.styleable.ML_TextView_delimiterMarginLeft, 0));
        int marginTop = (int) (ta.getDimensionPixelSize(R.styleable.ML_TextView_delimiterMarginTop, 0));
        int marginRight = (int) (ta.getDimensionPixelSize(R.styleable.ML_TextView_delimiterMarginRight, 0));
        int marginBottom = (int) (ta.getDimensionPixelSize(R.styleable.ML_TextView_delimiterMarginBottom, 0));
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        Drawable back = ta.getDrawable(R.styleable.ML_TextView_delimiterBack);
        delimiter.setLayoutParams(params);
        delimiter.setBackground(back);
        addView(delimiter, params);
    }
    //______________________________________________________________________________________________ configDelimiterLayout


    //______________________________________________________________________________________________ configIcon
    private void configIcon() {
        imageIcon = new ImageView(context);
        int width = (int) (ta.getDimension(R.styleable.ML_TextView_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_TextView_iconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        imageIcon.setLayoutParams(params);
        icon = ta.getDrawable(R.styleable.ML_TextView_icon);
        iconTint = ta.getColor(R.styleable.ML_TextView_iconTint, 0);
        iconError = ta.getDrawable(R.styleable.ML_TextView_iconError);
        if (iconError == null)
            iconError = icon;
        iconErrorTint = ta.getColor(R.styleable.ML_TextView_iconErrorTint, iconTint);
        setIcon(icon, iconTint);
        addView(imageIcon, params);
    }
    //______________________________________________________________________________________________ configIcon



    //______________________________________________________________________________________________ removeError
    public void removeError() {
        setBackground(normalBack);
        textView.setError(null);
        setIcon(icon, iconTint);
    }
    //______________________________________________________________________________________________ removeError



    //______________________________________________________________________________________________ setText
    public void setText(String text) {
        this.text = text;
        if (splitter) {
            String value = text;
            value = utility.splitNumberOfString(value);
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

        if (splitter) {
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
        setIcon(iconError, iconErrorTint);
        setBackground(emptyBack);
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
        this.icon = icon;
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
        switch (validationType) {
            case 0://none
                result = true;
                break;

            case 1://mobile
                result = utility.getValidations().mobileValidation(getAdditionalValue().toString());
                break;

            case 2://text
                result = utility.getValidations().textValidation(getAdditionalValue().toString());
                break;

            case 3://email
                result = utility.getValidations().emailValidation(getAdditionalValue().toString());
                break;

            case 4://national
                result = utility.getValidations().nationalValidation(getAdditionalValue().toString());
                break;

            case 5://password
                result = utility.getValidations().passwordValidation(getAdditionalValue().toString());
                break;

            case 6://mobile without area code
                result = utility.getValidations().mobileValidationWithoutAreaCode(getAdditionalValue().toString());
                break;
            case 7://phone
                result = utility.getValidations().phoneValidation(getAdditionalValue().toString());
                break;
/*            case 8://decimal
                result = utility.getValidations().decimalValidation(getEditText().getText().toString(), getDecimalCont());
                break;
            case 9://integer
                result = utility.getValidations().integerCountValidation(getEditText().getText().toString());
                break;*/
/*            case 10://wallet
                result = walletValidation;
                break;*/
            case 11://shaba
                result = utility.getValidations().shabaValidation(getAdditionalValue().toString());
                break;
            case 12:// price
                result = utility.getValidations().priceValidation(getAdditionalValue().toString());
                break;
            case 13:// numberInteger
                result = utility.getValidations().numberIsIntegerValidation(getAdditionalValue().toString());
                break;
            case 14:// numberDecimal
                result = utility.getValidations().numberValidation(getAdditionalValue().toString());
                break;
        }

        if (!result)
            setErrorLayout(errorText);

        return result;
    }
    //______________________________________________________________________________________________ checkValidation

}
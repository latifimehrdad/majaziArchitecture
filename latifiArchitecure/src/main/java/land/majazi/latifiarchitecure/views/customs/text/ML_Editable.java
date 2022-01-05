package land.majazi.latifiarchitecure.views.customs.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

import java.text.DecimalFormat;

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.converter.Converter;
import land.majazi.latifiarchitecure.utility.Utility;

public class ML_Editable extends LinearLayout {

    private Context context;
    private TypedArray ta;
    private Utility utility;
    private Object additionalValue;
    private String text;
    private int validationType;
    private boolean splitter;
    private boolean iconLeftClick = false;
    private boolean walletValidation = false;
    private int inputType;
    private EditText editText;
    private LinearLayout linearLayoutLoading;
    private ImageView imageIcon;
    private ImageView imageIconLeft;

    private Drawable normalBack;
    private Drawable emptyBack;
    private Drawable icon;
    private Drawable iconLeft;
    private Drawable iconLeftSecond;
    private Drawable iconError;

    private int iconTint;
    private int iconTintLeft;
    private int iconTintLeftSecond;
    private int iconErrorTint;
    private String errorText = "";

    private int decimalCont = 0;

    private textChangeInterface changeInterface;


    public interface textChangeInterface {
        void getTextWhenChanged(ML_Editable ml_editable, String text);
    }


    //______________________________________________________________________________________________ ML_Editable
    public ML_Editable(Context context) {
        super(context);
        this.context = context;
    }
    //______________________________________________________________________________________________ ML_Editable


    //______________________________________________________________________________________________ ML_Editable
    public ML_Editable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }
    //______________________________________________________________________________________________ ML_Editable


    //______________________________________________________________________________________________ init
    private void init(AttributeSet attrs) {
        utility = new Utility();
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.ML_Editable);
        configLayout();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ configLayout
    private void configLayout() {
        normalBack = ta.getDrawable(R.styleable.ML_Editable_normalBack);
        emptyBack = ta.getDrawable(R.styleable.ML_Editable_emptyBack);
        setBackground(normalBack);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        configIconLeft();
        configEditText();
        configLoading();
        configDelimiterLayout();
        configIcon();

    }
    //______________________________________________________________________________________________ configLayout


    //______________________________________________________________________________________________ configLoading
    private void configLoading() {
        String waitingText = ta.getString(R.styleable.ML_Editable_waitingText);
        if (waitingText == null || waitingText.isEmpty())
            return;

        linearLayoutLoading = new LinearLayout(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        linearLayoutLoading.setLayoutParams(params);
        linearLayoutLoading.setGravity(Gravity.CENTER);
        linearLayoutLoading.setOrientation(HORIZONTAL);
        linearLayoutLoading.setVisibility(GONE);

        TextView textView = new TextView(context);
        int textColor = ta.getColor(R.styleable.ML_Editable_android_textColor, 0);
        textView.setTextColor(textColor);
        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_Editable_textSize, 0) / getResources().getDisplayMetrics().density);
        textView.setTextSize(textSize);
        textView.setPadding(10, 3, 10, 3);

        textView.setText(waitingText);
        textView.setGravity(Gravity.CENTER);
        int fontFamilyId = ta.getResourceId(R.styleable.ML_Editable_fontFamily, 0);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        linearLayoutLoading.addView(textView, params);
        addView(linearLayoutLoading, params);
    }
    //______________________________________________________________________________________________ configLoading


    //______________________________________________________________________________________________ startLoading
    public void startLoading() {
        if (linearLayoutLoading == null)
            return;
        linearLayoutLoading.setVisibility(VISIBLE);
        editText.setVisibility(GONE);
    }
    //______________________________________________________________________________________________ startLoading


    //______________________________________________________________________________________________ stopLoading
    public void stopLoading() {
        if (linearLayoutLoading == null)
            return;
        linearLayoutLoading.setVisibility(GONE);
        editText.setVisibility(VISIBLE);
    }
    //______________________________________________________________________________________________ stopLoading


    //______________________________________________________________________________________________ configEditText
    private void configEditText() {

        editText = new EditText(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);

        int textColor = ta.getColor(R.styleable.ML_Editable_android_textColor, 0);
        editText.setTextColor(textColor);

        String hint = ta.getString(R.styleable.ML_Editable_hint);
        editText.setHint(hint);

        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_Editable_textSize, 0) / getResources().getDisplayMetrics().density);
        editText.setTextSize(textSize);

        int maxLine = ta.getInteger(R.styleable.ML_Editable_maxLine, 1);
        editText.setMaxLines(maxLine);

        errorText = ta.getString(R.styleable.ML_Editable_errorText);

        int maxLength = ta.getInteger(R.styleable.ML_Editable_maxLength, 1);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(fArray);

        inputType = ta.getInt(R.styleable.ML_Editable_inputType, 0);
        if (inputType > 0)
            editText.setInputType(inputType);

        text = ta.getString(R.styleable.ML_Editable_text);
        editText.setText(text);

        int gravity = ta.getInt(R.styleable.ML_Editable_gravity, 0x11);
        editText.setGravity(gravity);

        editText.setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent));
        editText.setLayoutParams(params);
        editText.setPadding(10, 3, 10, 3);

        splitter = ta.getBoolean(R.styleable.ML_Editable_splitter, false);
        editText.addTextChangedListener(textChangeForChangeBack(editText));

        validationType = ta.getInt(R.styleable.ML_Editable_validationType, 0);

        editText.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
        editText.setVerticalScrollBarEnabled(true);

        int fontFamilyId = ta.getResourceId(R.styleable.ML_Editable_fontFamily, 0);
        if (fontFamilyId > 0)
            editText.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));

        addView(editText, params);

    }
    //______________________________________________________________________________________________ configEditText


    //______________________________________________________________________________________________ configDelimiterLayout
    private void configDelimiterLayout() {
        LinearLayout delimiter = new LinearLayout(context);

        int delimiterWidth = (int) (ta.getDimension(R.styleable.ML_Editable_delimiterWidth, 0));
        LayoutParams params = new LayoutParams(delimiterWidth, LayoutParams.MATCH_PARENT);

        int marginLeft = (int) (ta.getDimensionPixelSize(R.styleable.ML_Editable_delimiterMarginLeft, 0));
        int marginTop = (int) (ta.getDimensionPixelSize(R.styleable.ML_Editable_delimiterMarginTop, 0));
        int marginRight = (int) (ta.getDimensionPixelSize(R.styleable.ML_Editable_delimiterMarginRight, 0));
        int marginBottom = (int) (ta.getDimensionPixelSize(R.styleable.ML_Editable_delimiterMarginBottom, 0));
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        Drawable back = ta.getDrawable(R.styleable.ML_Editable_delimiterBack);
        delimiter.setLayoutParams(params);
        delimiter.setBackground(back);
        addView(delimiter, params);
    }
    //______________________________________________________________________________________________ configDelimiterLayout


    //______________________________________________________________________________________________ configIcon
    private void configIcon() {
        imageIcon = new ImageView(context);
        int width = (int) (ta.getDimension(R.styleable.ML_Editable_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_Editable_iconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        imageIcon.setLayoutParams(params);
        icon = ta.getDrawable(R.styleable.ML_Editable_icon);
        iconTint = ta.getColor(R.styleable.ML_Editable_iconTint, 0);
        iconError = ta.getDrawable(R.styleable.ML_Editable_iconError);
        if (iconError == null)
            iconError = icon;
        iconErrorTint = ta.getColor(R.styleable.ML_Editable_iconErrorTint, iconTint);
        setIcon(icon, iconTint);
        addView(imageIcon, params);
    }
    //______________________________________________________________________________________________ configIcon


    //______________________________________________________________________________________________ configIcon
    private void configIconLeft() {
        iconLeft = ta.getDrawable(R.styleable.ML_Editable_iconLeft);
        if (iconLeft == null)
            return;
        imageIconLeft = new ImageView(context);
        int width = (int) (ta.getDimension(R.styleable.ML_Editable_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_Editable_iconHeight, 0));
        width = (int) Math.round(width * 0.85);
        height = (int) Math.round(height * 0.85);
        LayoutParams params = new LayoutParams(width, height);
        imageIconLeft.setLayoutParams(params);
        iconTintLeft = ta.getColor(R.styleable.ML_Editable_iconTintLeft, 0);
        iconLeftSecond = ta.getDrawable(R.styleable.ML_Editable_iconLeftSecond);
        iconTintLeftSecond = ta.getColor(R.styleable.ML_Editable_iconTintLeftSecond, 0);
        imageIconLeft.setImageDrawable(iconLeft);
        imageIconLeft.setColorFilter(iconTintLeft);
        params.setMargins(5, 0, 5, 0);
        imageIconLeft.setOnClickListener(v -> setIconLeft());
        int action = ta.getInt(R.styleable.ML_Editable_iconLeftAction, 0);
        switch (action) {
            case 1:
                imageIconLeft.setVisibility(INVISIBLE);
                break;
        }
        addView(imageIconLeft, params);
    }
    //______________________________________________________________________________________________ configIcon


    //______________________________________________________________________________________________ textChangeForChangeBack
    private TextWatcher textChangeForChangeBack(EditText edit) {

        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeError();
                setIcon(icon, iconTint);
                if (s != null && !s.toString().isEmpty()) {
                    int action = ta.getInt(R.styleable.ML_Editable_iconLeftAction, 0);
                    switch (action) {
                        case 1:
                            imageIconLeft.setVisibility(VISIBLE);
                            break;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                edit.removeTextChangedListener(this);
                if (getDecimalCont() > 0) {
                    String text = editText.getText().toString();
                    try {
                        int indexOfDecimal = text.indexOf(".");
                        if (indexOfDecimal == -1)
                            text = editText.getText().toString();
                        else {
                            String d_s = text.substring(indexOfDecimal, edit.getText().toString().length());
                            if (d_s.length() > decimalCont + 1) {
                                text = text.substring(0, indexOfDecimal) + d_s.substring(0, decimalCont + 1);
                            }

                        }
                    } catch (NumberFormatException ex) {
                        text = "";
                    }
                    edit.setText(text);
                    edit.setSelection(edit.getText().length());
                }

                if (splitter) {
                    String value = edit.getText().toString();
                    value = utility.splitNumberOfString(value);
                    Converter converter = new Converter();
                    value = converter.PersianNumberToEnglishNumber(value);
                    edit.setText(value);
                    edit.setSelection(edit.getText().length());
                }
                edit.addTextChangedListener(this);
                text = editText.getText().toString();
                if (changeInterface != null)
                    changeInterface.getTextWhenChanged(ML_Editable.this, getText());
            }
        };

    }
    //______________________________________________________________________________________________ textChangeForChangeBack


    //______________________________________________________________________________________________ getTextWhenChange
    public void getTextWhenChange(textChangeInterface changeInterface) {
        this.changeInterface = changeInterface;
    }
    //______________________________________________________________________________________________ getTextWhenChange


    //______________________________________________________________________________________________ removeError
    public void removeError() {
        setBackground(normalBack);
        editText.setError(null);
        setIcon(icon, iconTint);
    }
    //______________________________________________________________________________________________ removeError


    //______________________________________________________________________________________________ setText
    @BindingAdapter("text")
    public static void setText(ML_Editable view, String newValue) {
        if (view == null || newValue == null)
            return;

        if (view.getText() == null || !view.text.equalsIgnoreCase(newValue)) {
            view.text = newValue;
            view.getEditText().setText(view.text);
            return;
        }

    }
    //______________________________________________________________________________________________ setText


    //______________________________________________________________________________________________ setText
    @InverseBindingAdapter(attribute = "text")
    public static String getText(ML_Editable view) {
        if (view != null)
            view.text = view.getEditText().getText().toString();
        return view.getText();
    }
    //______________________________________________________________________________________________ setText


    //______________________________________________________________________________________________ setListeners
    @BindingAdapter("textAttrChanged")
    public static void setListeners(ML_Editable view, final InverseBindingListener listener) {
        if (listener != null) {
            view.getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    listener.onChange();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
    //______________________________________________________________________________________________ setListeners


    //______________________________________________________________________________________________ setText
    public void setText(String text) {
        this.text = text;
        getEditText().setText(text);
        removeError();
    }
    //______________________________________________________________________________________________ setText


    //______________________________________________________________________________________________ getText
    public String getText() {
        if (text == null) {
            text = "";
        }

        if (splitter) {
            text = text.replaceAll(",", "");
            text = text.replaceAll("٬", "");
        }
        return text;
    }
    //______________________________________________________________________________________________ getText


    //______________________________________________________________________________________________ getTextByLine
    public String getTextByLine(int line) {
        int start = getEditText().getLayout().getLineStart(1);
        int end = getEditText().getLayout().getLineEnd(1);
        return getText().substring(start, end);
    }
    //______________________________________________________________________________________________ getTextByLine


    //______________________________________________________________________________________________ setErrorLayout
    public void setErrorLayout(String error) {
        setIcon(iconError, iconErrorTint);
        setBackground(emptyBack);
        getEditText().setError(error);
        getEditText().requestFocus();
    }
    //______________________________________________________________________________________________ setErrorLayout


    //______________________________________________________________________________________________ getEditText
    public EditText getEditText() {
        return editText;
    }
    //______________________________________________________________________________________________ getEditText


    //______________________________________________________________________________________________ checkValidation
    public boolean checkValidation() {
        boolean result = true;
        switch (validationType) {
            case 0://none
                result = true;
                break;

            case 1://mobile
                result = utility.getValidations().mobileValidation(getEditText().getText().toString());
                break;

            case 2://text
                result = utility.getValidations().textValidation(getEditText().getText().toString());
                break;

            case 3://email
                result = utility.getValidations().emailValidation(getEditText().getText().toString());
                break;

            case 4://national
                result = utility.getValidations().nationalValidation(getEditText().getText().toString());
                break;

            case 5://password
                result = utility.getValidations().passwordValidation(getEditText().getText().toString());
                break;

            case 6://mobile without area code
                result = utility.getValidations().mobileValidationWithoutAreaCode(getEditText().getText().toString());
                break;
            case 7://phone
                result = utility.getValidations().phoneValidation(getEditText().getText().toString());
                break;
/*            case 8://decimal
                result = utility.getValidations().decimalValidation(getEditText().getText().toString(), getDecimalCont());
                break;
            case 9://integer
                result = utility.getValidations().integerCountValidation(getEditText().getText().toString());
                break;*/
            case 10://wallet
                result = walletValidation;
                break;
            case 11://shaba
                result = utility.getValidations().shabaValidation(getEditText().getText().toString());
                break;
            case 12:// price
                result = utility.getValidations().priceValidation(getEditText().getText().toString());
                break;
            case 13:// numberInteger
                result = utility.getValidations().numberIsIntegerValidation(getEditText().getText().toString());
                break;
            case 14:// numberDecimal
                result = utility.getValidations().numberValidation(getEditText().getText().toString());
                break;
            case 15:// postalCode
                result = utility.getValidations().postalCode(getEditText().getText().toString());
                break;
            case 16://CardNumber
                result = utility.getValidations().cardNumber(getEditText().getText().toString());
                break;
            case 17://persian name
                result = utility.getValidations().persianNameValidation(getEditText().getText().toString());
                break;
        }

        if (!result)
            setErrorLayout(errorText);

        return result;
    }
    //______________________________________________________________________________________________ checkValidation


    //______________________________________________________________________________________________ setIcon
    public void setIcon(Drawable icon, int iconTint) {
        this.icon = icon;
        imageIcon.setImageDrawable(icon);
        imageIcon.setColorFilter(iconTint);
    }
    //______________________________________________________________________________________________ setIcon


    //______________________________________________________________________________________________ setIconLeft
    public void setIconLeft() {
        int action = ta.getInt(R.styleable.ML_Editable_iconLeftAction, 0);
        switch (action) {
            case 0:
                left_action_show_hide_password();
                break;
            case 1:
                left_action_clear_text();
                break;
        }
    }
    //______________________________________________________________________________________________ setIconLeft


    //______________________________________________________________________________________________ left_action_show_hide_password
    private void left_action_show_hide_password() {
        int selection = 0;
        if (editText.getText().length() > 0)
            selection = editText.getSelectionStart();

        if (!iconLeftClick) {
            imageIconLeft.setImageDrawable(iconLeftSecond);
            imageIconLeft.setColorFilter(iconTintLeftSecond);
            iconLeftClick = true;
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            int fontFamilyId = ta.getResourceId(R.styleable.ML_Editable_fontFamily, 0);
            if (fontFamilyId > 0)
                editText.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        } else {
            imageIconLeft.setImageDrawable(iconLeft);
            imageIconLeft.setColorFilter(iconTintLeft);
            iconLeftClick = false;
            if (inputType > 0)
                editText.setInputType(inputType);
            int fontFamilyId = ta.getResourceId(R.styleable.ML_Editable_fontFamily, 0);
            if (fontFamilyId > 0)
                editText.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        }
        editText.setSelection(selection);
    }
    //______________________________________________________________________________________________ left_action_show_hide_password


    //______________________________________________________________________________________________ left_action_clear_text
    private void left_action_clear_text() {
        imageIconLeft.setVisibility(View.INVISIBLE);
        editText.requestFocus();
        setText("");
    }
    //______________________________________________________________________________________________ left_action_clear_text


    //______________________________________________________________________________________________ setInputType
    public void setInputType(int inputType) {
        this.inputType = inputType;
        editText.setInputType(inputType);
        int fontFamilyId = ta.getResourceId(R.styleable.ML_Editable_fontFamily, 0);
        if (fontFamilyId > 0)
            editText.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
    }
    //______________________________________________________________________________________________ setInputType


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


    //______________________________________________________________________________________________ disableEditable
    public void disableEditable() {
        editText.setEnabled(false);
    }
    //______________________________________________________________________________________________ disableEditable


    //______________________________________________________________________________________________ enableEditable
    public void enableEditable() {
        editText.setEnabled(true);
    }
    //______________________________________________________________________________________________ enableEditable


    //______________________________________________________________________________________________ setHint
    public void setHint(String hint) {
        editText.setHint(hint);
    }
    //______________________________________________________________________________________________ setHint

    //______________________________________________________________________________________________ setErrorText
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }
    //______________________________________________________________________________________________ setErrorText


    //______________________________________________________________________________________________ setValidationType
    public void setValidationType(int validationType) {
        this.validationType = validationType;
    }
    //______________________________________________________________________________________________ setValidationType


    //______________________________________________________________________________________________ setWalletValidation
    public void setWalletValidation(boolean walletValidation) {
        this.walletValidation = walletValidation;
    }
    //______________________________________________________________________________________________ setWalletValidation


    //______________________________________________________________________________________________ getDecimalCont
    public int getDecimalCont() {
        return decimalCont;
    }
    //______________________________________________________________________________________________ getDecimalCont


    //______________________________________________________________________________________________ setDecimalCont
    public void setDecimalCont(int desimalCont) {
        this.decimalCont = desimalCont;
    }
    //______________________________________________________________________________________________ setDecimalCont


}

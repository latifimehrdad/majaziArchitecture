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

import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.converter.Converter;
import land.majazi.latifiarchitecure.utility.Splitter;
import land.majazi.latifiarchitecure.utility.Validator;

public class MLEditable extends LinearLayout {


    private TypedArray ta;
    private Object additionalValue;
    private String text;
    private boolean iconLeftClick = false;
    private boolean walletValidation = false;
    private int inputType;
    private EditText editText;
    private LinearLayout linearLayoutLoading;
    private ImageView imageIcon;
    private ImageView imageIconLeft;
    private boolean splitter = false;
    private Drawable iconError;
    private int decimalCont = 0;
    private textChangeInterface changeInterface;



    public interface textChangeInterface {
        void getTextWhenChanged(MLEditable ml_editable, String text);
    }


    //______________________________________________________________________________________________ ML_Editable
    public MLEditable(Context context) {
        super(context);
    }
    //______________________________________________________________________________________________ ML_Editable


    //______________________________________________________________________________________________ ML_Editable
    public MLEditable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.MLEditable);
        configLayout(context);
    }
    //______________________________________________________________________________________________ ML_Editable


    //______________________________________________________________________________________________ configLayout
    private void configLayout(Context context) {
        setBackground(ta.getDrawable(R.styleable.MLEditable_normalBack));
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        configIconLeft(context);
        configEditText(context);
        configLoading(context);
        configDelimiterLayout(context);
        configIcon(context);

    }
    //______________________________________________________________________________________________ configLayout


    //______________________________________________________________________________________________ configLoading
    private void configLoading(Context context) {
        String waitingText = ta.getString(R.styleable.MLEditable_waitingText);
        if (waitingText == null || waitingText.isEmpty())
            return;

        linearLayoutLoading = new LinearLayout(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        linearLayoutLoading.setLayoutParams(params);
        linearLayoutLoading.setGravity(Gravity.CENTER);
        linearLayoutLoading.setOrientation(HORIZONTAL);
        linearLayoutLoading.setVisibility(GONE);

        TextView textView = new TextView(context);
        int textColor = ta.getColor(R.styleable.MLEditable_android_textColor, 0);
        textView.setTextColor(textColor);
        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.MLEditable_textSize, 0) / getResources().getDisplayMetrics().density);
        textView.setTextSize(textSize);
        textView.setPadding(10, 3, 10, 3);

        textView.setText(waitingText);
        textView.setGravity(Gravity.CENTER);
        int fontFamilyId = ta.getResourceId(R.styleable.MLEditable_fontFamily, 0);
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
    private void configEditText(Context context) {

        editText = new EditText(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);

        int textColor = ta.getColor(R.styleable.MLEditable_android_textColor, 0);
        editText.setTextColor(textColor);

        String hint = ta.getString(R.styleable.MLEditable_hint);
        editText.setHint(hint);

        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.MLEditable_textSize, 0) / getResources().getDisplayMetrics().density);
        editText.setTextSize(textSize);

        int maxLine = ta.getInteger(R.styleable.MLEditable_maxLine, 1);
        editText.setMaxLines(maxLine);

        int maxLength = ta.getInteger(R.styleable.MLEditable_maxLength, 1);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(fArray);

        splitter = ta.getBoolean(R.styleable.MLEditable_splitter, false);

        inputType = ta.getInt(R.styleable.MLEditable_inputType, 0);
        if (inputType > 0)
            editText.setInputType(inputType);

        text = ta.getString(R.styleable.MLEditable_text);
        editText.setText(text);

        int gravity = ta.getInt(R.styleable.MLEditable_gravity, 0x11);
        editText.setGravity(gravity);

        editText.setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent, context.getTheme()));
        editText.setLayoutParams(params);
        editText.setPadding(10, 3, 10, 3);

        editText.addTextChangedListener(textChangeForChangeBack(editText));

        editText.setLineSpacing(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5.0f, getResources().getDisplayMetrics()), 1.0f);
        editText.setVerticalScrollBarEnabled(true);

        int fontFamilyId = ta.getResourceId(R.styleable.MLEditable_fontFamily, 0);
        if (fontFamilyId > 0)
            editText.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));

        addView(editText, params);

    }
    //______________________________________________________________________________________________ configEditText


    //______________________________________________________________________________________________ configDelimiterLayout
    private void configDelimiterLayout(Context context) {
        LinearLayout delimiter = new LinearLayout(context);

        int delimiterWidth = (int) (ta.getDimension(R.styleable.MLEditable_delimiterWidth, 0));
        LayoutParams params = new LayoutParams(delimiterWidth, LayoutParams.MATCH_PARENT);

        int marginLeft = ta.getDimensionPixelSize(R.styleable.MLEditable_delimiterMarginLeft, 0);
        int marginTop = ta.getDimensionPixelSize(R.styleable.MLEditable_delimiterMarginTop, 0);
        int marginRight = ta.getDimensionPixelSize(R.styleable.MLEditable_delimiterMarginRight, 0);
        int marginBottom = ta.getDimensionPixelSize(R.styleable.MLEditable_delimiterMarginBottom, 0);
        params.setMargins(marginLeft, marginTop, marginRight, marginBottom);
        Drawable back = ta.getDrawable(R.styleable.MLEditable_delimiterBack);
        delimiter.setLayoutParams(params);
        delimiter.setBackground(back);
        addView(delimiter, params);
    }
    //______________________________________________________________________________________________ configDelimiterLayout


    //______________________________________________________________________________________________ configIcon
    private void configIcon(Context context) {
        imageIcon = new ImageView(context);
        int width = (int) (ta.getDimension(R.styleable.MLEditable_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.MLEditable_iconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        imageIcon.setLayoutParams(params);
        iconError = ta.getDrawable(R.styleable.MLEditable_iconError);
        if (iconError == null)
            iconError = ta.getDrawable(R.styleable.MLEditable_icon);
        setIcon(ta.getDrawable(R.styleable.MLEditable_icon), ta.getColor(R.styleable.MLEditable_iconTint, 0));
        addView(imageIcon, params);
    }
    //______________________________________________________________________________________________ configIcon


    //______________________________________________________________________________________________ configIcon
    private void configIconLeft(Context context) {
        if (ta.getDrawable(R.styleable.MLEditable_iconLeft) == null)
            return;
        imageIconLeft = new ImageView(context);
        int width = (int) (ta.getDimension(R.styleable.MLEditable_iconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.MLEditable_iconHeight, 0));
        width = (int) Math.round(width * 0.85);
        height = (int) Math.round(height * 0.85);
        LayoutParams params = new LayoutParams(width, height);
        imageIconLeft.setLayoutParams(params);
        imageIconLeft.setImageDrawable(ta.getDrawable(R.styleable.MLEditable_iconLeft));
        imageIconLeft.setColorFilter(ta.getColor(R.styleable.MLEditable_iconTintLeft, 0));
        params.setMargins(5, 0, 5, 0);
        imageIconLeft.setOnClickListener(v -> setIconLeft());
        if (ta.getInt(R.styleable.MLEditable_iconLeftAction, 0) == 1)
            imageIconLeft.setVisibility(INVISIBLE);
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
                setIcon(ta.getDrawable(R.styleable.MLEditable_icon), ta.getColor(R.styleable.MLEditable_iconTint, 0));
                if (s != null && !s.toString().isEmpty()) {
                    if (ta.getInt(R.styleable.MLEditable_iconLeftAction, 0) == 1)
                        imageIconLeft.setVisibility(VISIBLE);
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
                    value = new Splitter().split(value);
                    Converter converter = new Converter();
                    value = converter.PersianNumberToEnglishNumber(value);
                    edit.setText(value);
                    edit.setSelection(edit.getText().length());
                }
                edit.addTextChangedListener(this);
                text = editText.getText().toString();
                if (changeInterface != null)
                    changeInterface.getTextWhenChanged(MLEditable.this, getText());
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
        setBackground(ta.getDrawable(R.styleable.MLEditable_normalBack));
        editText.setError(null);
        setIcon(ta.getDrawable(R.styleable.MLEditable_icon), ta.getColor(R.styleable.MLEditable_iconTint, 0));
    }
    //______________________________________________________________________________________________ removeError


    //______________________________________________________________________________________________ setText
    @BindingAdapter("text")
    public static void setText(MLEditable view, String newValue) {
        if (view == null || newValue == null)
            return;

        if (view.getText() == null || !view.text.equalsIgnoreCase(newValue)) {
            view.text = newValue;
            view.getEditText().setText(view.text);
        }

    }
    //______________________________________________________________________________________________ setText


    //______________________________________________________________________________________________ setText
    @InverseBindingAdapter(attribute = "text")
    public static String getText(MLEditable view) {
        if (view != null)
            view.text = view.getEditText().getText().toString();
        assert view != null;
        return view.getText();
    }
    //______________________________________________________________________________________________ setText


    //______________________________________________________________________________________________ setListeners
    @BindingAdapter("textAttrChanged")
    public static void setListeners(MLEditable view, final InverseBindingListener listener) {
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
        setIcon(iconError, ta.getColor(R.styleable.MLEditable_iconErrorTint, ta.getColor(R.styleable.MLEditable_iconTint, 0)));
        setBackground(ta.getDrawable(R.styleable.MLEditable_emptyBack));
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
        Validator validator = new Validator();
        switch (ta.getInt(R.styleable.MLEditable_validationType, 0)) {
            case 0://none
                result = true;
                break;

            case 1://mobile
                result = validator.isMobile(getEditText().getText().toString());
                break;

            case 2://text
                result = validator.isText(getEditText().getText().toString());
                break;

            case 3://email
                result = validator.isEmail(getEditText().getText().toString());
                break;

            case 4://national
                result = validator.isNationalCode(getEditText().getText().toString());
                break;

            case 5://password
                result = validator.isStrongPassword(getEditText().getText().toString());
                break;

            case 7://phone
                result = validator.isPhone(getEditText().getText().toString());
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
                result = validator.isShaba(getEditText().getText().toString());
                break;
            case 12:// price
                result = validator.isPriceForGetWay(getEditText().getText().toString());
                break;
            case 15:// postalCode
                result = validator.isPostalCode(getEditText().getText().toString());
                break;
            case 16://CardNumber
                result = validator.isCardNumber(getEditText().getText().toString());
                break;
            case 17://persian name
                result = validator.isPersianName(getEditText().getText().toString());
                break;
        }

        if (!result)
            setErrorLayout(ta.getString(R.styleable.MLEditable_errorText));

        return result;
    }
    //______________________________________________________________________________________________ checkValidation


    //______________________________________________________________________________________________ setIcon
    public void setIcon(Drawable icon, int iconTint) {
        imageIcon.setImageDrawable(icon);
        imageIcon.setColorFilter(iconTint);
    }
    //______________________________________________________________________________________________ setIcon


    //______________________________________________________________________________________________ setIconLeft
    public void setIconLeft() {
        int action = ta.getInt(R.styleable.MLEditable_iconLeftAction, 0);
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
            imageIconLeft.setImageDrawable(ta.getDrawable(R.styleable.MLEditable_iconLeftSecond));
            imageIconLeft.setColorFilter(ta.getColor(R.styleable.MLEditable_iconTintLeftSecond, 0));
            iconLeftClick = true;
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            int fontFamilyId = ta.getResourceId(R.styleable.MLEditable_fontFamily, 0);
            if (fontFamilyId > 0)
                editText.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        } else {
            imageIconLeft.setImageDrawable(ta.getDrawable(R.styleable.MLEditable_iconLeft));
            imageIconLeft.setColorFilter(ta.getColor(R.styleable.MLEditable_iconTintLeft, 0));
            iconLeftClick = false;
            if (inputType > 0)
                editText.setInputType(inputType);
            int fontFamilyId = ta.getResourceId(R.styleable.MLEditable_fontFamily, 0);
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
        int fontFamilyId = ta.getResourceId(R.styleable.MLEditable_fontFamily, 0);
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
    public void setDecimalCont(int decimalCont) {
        this.decimalCont = decimalCont;
    }
    //______________________________________________________________________________________________ setDecimalCont


    public boolean isSplitter() {
        return splitter;
    }

    public void setSplitter(boolean splitter) {
        this.splitter = splitter;
    }
}

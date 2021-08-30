package land.majazi.latifiarchitecure.views.customs.alerts.alertbox;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import java.util.List;

import land.majazi.latifiarchitecure.R;

public class ML_AlertBox extends CardView {

    private TypedArray ta;
    private List<MD_AlertBoxMessages> alertBoxMessages;
    private TextView textViewTitle;
    private ImageView imageViewIcon;
    private ImageView imageViewNext;
    private ImageView imageViewPrevious;
    private TextView textViewMessage;
    private int currentMessage = 0;
    private Context context;
    private CardView cardView;
    private LinearLayout linearLayoutPrimary;
    private LinearLayout linearLayoutTitle;

    //______________________________________________________________________________________________ ML_AlertBox
    public ML_AlertBox(@NonNull Context context) {
        super(context);
        this.context = context;
    }
    //______________________________________________________________________________________________ ML_AlertBox


    //______________________________________________________________________________________________ ML_AlertBox
    public ML_AlertBox(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }
    //______________________________________________________________________________________________ ML_AlertBox


    //______________________________________________________________________________________________ init
    private void init(AttributeSet attrs) {
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.ML_AlertBox);
        configCardViews();
    }
    //______________________________________________________________________________________________ init


    //______________________________________________________________________________________________ configCardViews
    private void configCardViews() {
        float radius = ta.getDimension(R.styleable.ML_AlertBox_alertCardCornerRadius, 0);
        cardView = new CardView(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.setMargins(1, 1, 15, 1);
        cardView.setRadius(radius);
        setRadius(radius);
        addView(cardView, params);
        configLayout();
    }
    //______________________________________________________________________________________________ configCardViews


    //______________________________________________________________________________________________ configLayout
    private void configLayout() {
        linearLayoutPrimary = new LinearLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        linearLayoutPrimary.setPadding(15, 15, 15, 15);
        linearLayoutPrimary.setGravity(Gravity.CENTER);
        linearLayoutPrimary.setOrientation(LinearLayout.VERTICAL);
        cardView.addView(linearLayoutPrimary, params);
        configTitleLayout();
        configTextViewMessage();
    }
    //______________________________________________________________________________________________ configLayout


    //______________________________________________________________________________________________ configTitleLayout
    private void configTitleLayout() {
        linearLayoutTitle = new LinearLayout(context);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayoutTitle.setGravity(Gravity.CENTER);
        linearLayoutTitle.setOrientation(LinearLayout.HORIZONTAL);
        linearLayoutPrimary.addView(linearLayoutTitle, param);
        configImageNext();
        configImagePrevious();
        configTextViewTitle();
        configImageIcon();
    }
    //______________________________________________________________________________________________ configTitleLayout


    //______________________________________________________________________________________________ configTextViewMessage
    private void configTextViewMessage() {

        ScrollView scrollView = new ScrollView(context);
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0,1);
        linearLayoutPrimary.addView(scrollView, scrollParams);

        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scrollView.addView(layout, layoutParams);

        textViewMessage = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textViewMessage.setGravity(Gravity.RIGHT);
        textViewMessage.setLayoutParams(params);
        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_AlertBox_alertMessageTextSize, 0) / getResources().getDisplayMetrics().density);
        textViewMessage.setTextSize(textSize);

        int fontFamilyId = ta.getResourceId(R.styleable.ML_AlertBox_alertMessageFontFamily, 0);
        if (fontFamilyId > 0)
            textViewMessage.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        params.setMargins(0,20,0,0);

        layout.addView(textViewMessage, params);
    }
    //______________________________________________________________________________________________ configTextViewMessage



    //______________________________________________________________________________________________ configImageNext
    private void configImageNext() {
        imageViewNext = new ImageView(context);
        Drawable icon = ta.getDrawable(R.styleable.ML_AlertBox_alertIconNext);
        int iconTint = ta.getColor(R.styleable.ML_AlertBox_alertIconNextTint, 0);
        imageViewNext.setImageDrawable(icon);
        imageViewNext.setColorFilter(iconTint);
        imageViewNext.setOnClickListener(v -> {
            currentMessage++;
            showMessage();
        });
        configImageArrow(imageViewNext,0,0,0,0);

    }
    //______________________________________________________________________________________________ configImageNext


    //______________________________________________________________________________________________ configImagePrevious
    private void configImagePrevious() {
        imageViewPrevious = new ImageView(context);
        Drawable icon = ta.getDrawable(R.styleable.ML_AlertBox_alertIconPrevious);
        int iconTint = ta.getColor(R.styleable.ML_AlertBox_alertIconPreviousTint, 0);
        imageViewPrevious.setImageDrawable(icon);
        imageViewPrevious.setColorFilter(iconTint);
        imageViewPrevious.setOnClickListener(v -> {
            currentMessage--;
            showMessage();
        });
        configImageArrow(imageViewPrevious,30,0,0,0);
    }
    //______________________________________________________________________________________________ configImagePrevious


    //______________________________________________________________________________________________ configImageIcon
    private void configImageIcon() {
        imageViewIcon = new ImageView(context);
        Drawable icon = ta.getDrawable(R.styleable.ML_AlertBox_alertWarningIcon);
        imageViewIcon.setImageDrawable(icon);
        int width = (int) (ta.getDimension(R.styleable.ML_AlertBox_alertIconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_AlertBox_alertIconHeight, 0));
        width = (int) Math.round(width * 0.7);
        height = (int) Math.round(height * 0.7);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        imageViewIcon.setLayoutParams(params);
        params.setMargins(15,0,0,0);
        linearLayoutTitle.addView(imageViewIcon, params);
    }
    //______________________________________________________________________________________________ configImageIcon



    //______________________________________________________________________________________________ configTextViewTitle
    private void configTextViewTitle() {

        textViewTitle = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
        textViewTitle.setGravity(Gravity.RIGHT);
        textViewTitle.setLayoutParams(params);
        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_AlertBox_alertTitleTextSize, 0) / getResources().getDisplayMetrics().density);
        textViewTitle.setTextSize(textSize);

        int fontFamilyId = ta.getResourceId(R.styleable.ML_AlertBox_alertTitleFontFamily, 0);
        if (fontFamilyId > 0)
            textViewTitle.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));

        linearLayoutTitle.addView(textViewTitle, params);
    }
    //______________________________________________________________________________________________ configTextViewTitle


    //______________________________________________________________________________________________ configImageArrow
    private void configImageArrow(ImageView image, int left, int top, int right, int bottom) {
        int width = (int) (ta.getDimension(R.styleable.ML_AlertBox_alertIconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_AlertBox_alertIconHeight, 0));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        image.setLayoutParams(params);
        params.setMargins(left,top,right,bottom);
        linearLayoutTitle.addView(image, params);
    }
    //______________________________________________________________________________________________ configImageArrow



    //______________________________________________________________________________________________ showMessage
    public void showMessage() {

        if (alertBoxMessages == null || alertBoxMessages.isEmpty())
            return;

        if (currentMessage < 0)
            currentMessage = 0;

        if (currentMessage > alertBoxMessages.size()-1)
            currentMessage = alertBoxMessages.size()-1;

        textViewTitle.setText(alertBoxMessages.get(currentMessage).getTitle());
        String message = "";
        for (String text: alertBoxMessages.get(currentMessage).getMessages()) {
            message = message + text;
            message = message + System.getProperty("line.separator");
        }
        textViewMessage.setText(message);

        switch (alertBoxMessages.get(currentMessage).getAlertBoxType()) {
            case normal:
                normalMessage();
                break;

            case warning:
                warningMessage();
                break;

            case attention:
                attentionMessage();
                break;
        }
    }
    //______________________________________________________________________________________________ showMessage


    //______________________________________________________________________________________________ normalMessage
    private void normalMessage() {
        int backColor1 = ta.getColor(R.styleable.ML_AlertBox_alertNormalBackColor1, 0);
        int backColor2 = ta.getColor(R.styleable.ML_AlertBox_alertNormalBackColor2, 0);
        setCardBackgroundColor(backColor1);
        cardView.setCardBackgroundColor(backColor2);

        Drawable icon = ta.getDrawable(R.styleable.ML_AlertBox_alertNormalIcon);
        int iconTint = ta.getColor(R.styleable.ML_AlertBox_alertNormalIconColor, 0);
        imageViewIcon.setImageDrawable(icon);
        imageViewIcon.setColorFilter(iconTint);
        textViewTitle.setTextColor(iconTint);
        textViewMessage.setTextColor(iconTint);
    }
    //______________________________________________________________________________________________ normalMessage


    //______________________________________________________________________________________________ normalMessage
    private void warningMessage() {
        int backColor1 = ta.getColor(R.styleable.ML_AlertBox_alertWarningBackColor1, 0);
        int backColor2 = ta.getColor(R.styleable.ML_AlertBox_alertWarningBackColor2, 0);
        setCardBackgroundColor(backColor1);
        cardView.setCardBackgroundColor(backColor2);

        Drawable icon = ta.getDrawable(R.styleable.ML_AlertBox_alertWarningIcon);
        int iconTint = ta.getColor(R.styleable.ML_AlertBox_alertWarningColor, 0);
        imageViewIcon.setImageDrawable(icon);
        imageViewIcon.setColorFilter(iconTint);
        textViewTitle.setTextColor(iconTint);
        textViewMessage.setTextColor(iconTint);

    }
    //______________________________________________________________________________________________ normalMessage


    //______________________________________________________________________________________________ normalMessage
    private void attentionMessage() {
        int backColor1 = ta.getColor(R.styleable.ML_AlertBox_alertAttentionBackColor1, 0);
        int backColor2 = ta.getColor(R.styleable.ML_AlertBox_alertAttentionBackColor2, 0);
        setCardBackgroundColor(backColor1);
        cardView.setCardBackgroundColor(backColor2);

        Drawable icon = ta.getDrawable(R.styleable.ML_AlertBox_alertAttentionIcon);
        int iconTint = ta.getColor(R.styleable.ML_AlertBox_alertAttentionColor, 0);
        imageViewIcon.setImageDrawable(icon);
        imageViewIcon.setColorFilter(iconTint);
        textViewTitle.setTextColor(iconTint);
        textViewMessage.setTextColor(iconTint);
    }
    //______________________________________________________________________________________________ normalMessage


    //______________________________________________________________________________________________ setAlertBoxMessages
    public void setAlertBoxMessages(List<MD_AlertBoxMessages> alertBoxMessages) {
        this.alertBoxMessages = alertBoxMessages;
        currentMessage = 0;
        showMessage();
    }
    //______________________________________________________________________________________________ setAlertBoxMessages
}

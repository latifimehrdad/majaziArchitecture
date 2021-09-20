package land.majazi.latifiarchitecure.views.customs.alerts.toast;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import land.majazi.latifiarchitecure.R;

public class ML_Toast extends LinearLayout {

    private Context context;

    private TextView textView;

    private ImageView imageViewIcon;

    private ImageView imageViewClose;

    private Drawable toastBack;

    private static Runnable runnable;

    private static Handler handler;

    private static LinearLayout linearLayoutPrimary;


    private int textColor;
    private int textSize;
    private int fontFamilyId;
    private int minWidth;
    private int maxWidth;

    private int imageWidth;
    private int imageHeight;

    //______________________________________________________________________________________________ ML_Toast
    public ML_Toast(Context context) {
        super(context);
        this.context = context;
    }
    //______________________________________________________________________________________________ ML_Toast


    //______________________________________________________________________________________________ ML_Toast
    public ML_Toast(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.ML_Toast);

        toastBack = ta.getDrawable(R.styleable.ML_Toast_toast_Back);
        textColor = ta.getColor(R.styleable.ML_Toast_toast_TextColor, 0);
        textSize = (int) (ta.getDimension(R.styleable.ML_Toast_toast_TextSize, 0) / getResources().getDisplayMetrics().density);
        fontFamilyId = ta.getResourceId(R.styleable.ML_Toast_fontFamily, 0);
        minWidth = (int) (ta.getDimension(R.styleable.ML_Toast_toast_minWidth, 0));
        maxWidth = (int) (ta.getDimension(R.styleable.ML_Toast_toast_maxWidth, 0));

        imageWidth = (int) (ta.getDimension(R.styleable.ML_Toast_toast_imgWidth, 0));
        imageHeight = (int) (ta.getDimension(R.styleable.ML_Toast_toast_imgHeight, 0));


        setPadding(20, 10, 25, 15);

        setBackground(toastBack);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        configCloseLayout();
        configText();
        configIcon();
    }
    //______________________________________________________________________________________________ ML_Toast



    //______________________________________________________________________________________________ configCloseLayout
    private void configCloseLayout() {
        LinearLayout close = new LinearLayout(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        close.setOrientation(HORIZONTAL);
        close.setGravity(Gravity.BOTTOM);
        close.setPadding(10, 10, 10, 10);

        imageViewClose = new ImageView(context);
        LayoutParams params1 = new LayoutParams(imageWidth, imageHeight);
        imageViewClose.setLayoutParams(params1);
        imageViewClose.setVisibility(VISIBLE);
        imageViewClose.setOnClickListener(v -> hide(linearLayoutPrimary));

        close.addView(imageViewClose, params1);

        addView(close, params);
    }
    //______________________________________________________________________________________________ configCloseLayout




    //______________________________________________________________________________________________ configEditText
    private void configText() {
        textView = new TextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textView.setTextColor(textColor);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(textSize);
        textView.setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent));
        textView.setLayoutParams(params);
        textView.setMinWidth(minWidth);
        textView.setMaxWidth(maxWidth);
        textView.setPadding(20, 4, 20, 10);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        addView(textView, params);
    }
    //______________________________________________________________________________________________ configEditText


    //______________________________________________________________________________________________ configIcon
    private void configIcon() {
        imageViewIcon = new ImageView(context);
        LayoutParams params = new LayoutParams(imageWidth, imageHeight);
        imageViewIcon.setLayoutParams(params);
        imageViewIcon.setVisibility(VISIBLE);
        addView(imageViewIcon, params);
    }
    //______________________________________________________________________________________________ configIcon




    //______________________________________________________________________________________________ mackToast
    public void mackToast(String message, Drawable icon, int iconTintColor) {
        textView.setText(message);
        imageViewIcon.setImageDrawable(icon);
        imageViewIcon.setColorFilter(iconTintColor);
    }
    //______________________________________________________________________________________________ mackToast


    //______________________________________________________________________________________________ showToast
    public static void showToast(Context context, LinearLayout linearLayout, String message, Drawable icon, int iconTintColor) {

        linearLayoutPrimary = linearLayout;
        hide(linearLayout);

        linearLayout.setVisibility(GONE);

        ML_Toast ml_toast = (ML_Toast) linearLayout.getChildAt(0);
        ml_toast.mackToast(message, icon, iconTintColor);

        linearLayout.startAnimation(AnimationUtils.loadAnimation(context, R.anim.toast_in));
        linearLayout.setVisibility(VISIBLE);
        runnable = () -> show(context, message, linearLayout);
        handler = new Handler();
        handler.postDelayed(runnable, 700);

    }
    //______________________________________________________________________________________________ showToast



    //______________________________________________________________________________________________ hide
    public static void hide(LinearLayout viewParent) {

        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }

        if (viewParent != null) {
            viewParent.setAnimation(null);
            viewParent.setVisibility(GONE);
        }
    }
    //______________________________________________________________________________________________ hide


    //______________________________________________________________________________________________ show
    private static void show(Context context, String message, LinearLayout linearLayout) {

        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }

        int delay;
        int titleLength = message.length();
        if (titleLength > 8)
            titleLength = titleLength / 8;
        else
            titleLength = 1;
        delay = 1100 * titleLength;
        if (delay < 1500)
            delay = 2500;

        runnable = () -> {
            if (linearLayout.getVisibility() == View.VISIBLE) {
                linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.toast_out));
                linearLayout.setVisibility(GONE);
                if (handler != null && runnable != null) {
                    handler.removeCallbacks(runnable);
                    handler = null;
                    runnable = null;
                }
            }
        };
        handler = new Handler();
        handler.postDelayed(runnable, delay);

    }
    //______________________________________________________________________________________________ show


}

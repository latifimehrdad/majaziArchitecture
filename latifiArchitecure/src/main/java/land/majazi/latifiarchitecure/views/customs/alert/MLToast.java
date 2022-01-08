package land.majazi.latifiarchitecure.views.customs.alert;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import android.view.animation.AnimationUtils;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import land.majazi.latifiarchitecure.R;

public class MLToast extends androidx.appcompat.widget.AppCompatTextView {

    private Runnable runnable;
    private Handler handler;
    private Drawable closeIcon;
    private static MLToast mlToast;

    //______________________________________________________________________________________________ ML_Toast
    public MLToast(Context context) {
        super(context);
    }
    //______________________________________________________________________________________________ ML_Toast


    //______________________________________________________________________________________________ ML_Toast
    public MLToast(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.MLToast);
        int fontFamilyId = ta.getResourceId(R.styleable.MLToast_fontFamily, 0);
        setTextColor(ta.getColor(R.styleable.MLToast_toast_TextColor, 0));
        setTextSize((int) (ta.getDimension(R.styleable.MLToast_toast_TextSize, 0) / getResources().getDisplayMetrics().density));
        setBackground(ta.getDrawable(R.styleable.MLToast_toast_Back));
        setMinWidth((int) (ta.getDimension(R.styleable.MLToast_toast_minWidth, 0)));
        setMaxWidth((int) (ta.getDimension(R.styleable.MLToast_toast_maxWidth, 0)));
        setPadding(20, 10, 20, 15);
        closeIcon = context.getDrawable(R.drawable.toast_close_icon);
        setCompoundDrawablePadding(7);
        setOnClickListener(v -> hide());
        setGravity(Gravity.CENTER);
        if (fontFamilyId > 0)
            setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        mlToast = this;
    }
    //______________________________________________________________________________________________ ML_Toast




    //______________________________________________________________________________________________ mackToast
    public void mackToast(String message, Drawable icon, int iconTintColor) {
        mlToast.setText(message);
        mlToast.setCompoundDrawablesWithIntrinsicBounds(closeIcon, null, icon, null);
        mlToast.getCompoundDrawables()[0].setTint(getResources().getColor(R.color.white, getContext().getTheme()));
        mlToast.getCompoundDrawables()[2].setTint(iconTintColor);
    }
    //______________________________________________________________________________________________ mackToast


    //______________________________________________________________________________________________ showToast
    public static void showToast(Context context, String message, Drawable icon, int iconTintColor) {
        hide();
        mlToast.setVisibility(GONE);
        mlToast.mackToast(message, icon, iconTintColor);
        mlToast.startAnimation(AnimationUtils.loadAnimation(context, R.anim.toast_in));
        mlToast.setVisibility(VISIBLE);
        mlToast.runnable = () -> show(context, message);
        mlToast.handler = new Handler();
        mlToast.handler.postDelayed(mlToast.runnable, 700);
    }
    //______________________________________________________________________________________________ showToast



    //______________________________________________________________________________________________ hide
    public static void hide() {
        if (mlToast.handler != null && mlToast.runnable != null) {
            mlToast.handler.removeCallbacks(mlToast.runnable);
            mlToast.handler = null;
            mlToast.runnable = null;
        }
        if (mlToast != null) {
            mlToast.setAnimation(null);
            mlToast.setVisibility(GONE);
        }
    }
    //______________________________________________________________________________________________ hide


    //______________________________________________________________________________________________ show
    private static void show(Context context, String message) {
        if (mlToast.handler != null && mlToast.runnable != null) {
            mlToast.handler.removeCallbacks(mlToast.runnable);
            mlToast.handler = null;
            mlToast.runnable = null;
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

        mlToast.runnable = () -> {
            if (mlToast.getVisibility() == View.VISIBLE) {
                mlToast.setAnimation(AnimationUtils.loadAnimation(context, R.anim.toast_out));
                mlToast.setVisibility(GONE);
                if (mlToast.handler != null && mlToast.runnable != null) {
                    mlToast.handler.removeCallbacks(mlToast.runnable);
                    mlToast.handler = null;
                    mlToast.runnable = null;
                }
            }
        };
        mlToast.handler = new Handler();
        mlToast.handler.postDelayed(mlToast.runnable, delay);
    }
    //______________________________________________________________________________________________ show


}

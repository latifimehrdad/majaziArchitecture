package land.majazi.latifiarchitecure.views.customs.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.ArrayList;
import java.util.List;

import land.majazi.latifiarchitecure.R;

public class ML_NavigationBottom extends LinearLayout {

    private Context context;
    private TypedArray ta;
    private Drawable navBack;
    private List<MD_NavCell> navCells;
    public cellClick click;
    private float scale = 0.85f;
    private int current;


    //______________________________________________________________________________________________ ML_NavigationBottom
    public ML_NavigationBottom(Context context) {
        super(context);
        this.context = context;
    }
    //______________________________________________________________________________________________ ML_NavigationBottom


    //______________________________________________________________________________________________ ML_NavigationBottom
    public ML_NavigationBottom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }
    //______________________________________________________________________________________________ ML_NavigationBottom


    //______________________________________________________________________________________________ bitcoinInterface
    public interface cellClick {
        void onClick(int id);
    }
    //______________________________________________________________________________________________ bitcoinInterface


    //______________________________________________________________________________________________ init
    private void init(AttributeSet attrs) {
        ta = getContext().obtainStyledAttributes(attrs, R.styleable.ML_NavigationBottom);
        configLayout();
    }
    //______________________________________________________________________________________________ init



    //______________________________________________________________________________________________ configLayout
    private void configLayout() {
        navBack = ta.getDrawable(R.styleable.ML_NavigationBottom_navBackground);
        setBackground(navBack);
        setGravity(Gravity.CENTER);
        setOrientation(HORIZONTAL);
        navCells = new ArrayList<>();
    }
    //______________________________________________________________________________________________ configLayout



    //______________________________________________________________________________________________ addCell
    public void addCell(MD_NavModel navModel) {

        MD_NavCell md_navCell = new MD_NavCell();
        md_navCell.setId(navModel.getId());
        LinearLayout layout = new LinearLayout(context);
        LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
        layout.setGravity(Gravity.CENTER);
        layout.setOrientation(VERTICAL);
        layout.setLayoutParams(params);
        ImageView icon = configIcon(navModel);
        md_navCell.setIcon(icon);
        layout.addView(icon);
        TextView title = configTitle(navModel);
        md_navCell.setTitle(title);
        layout.addView(title);
        layout.setOnClickListener(v -> {
            if (click != null)
                click.onClick(navModel.getId());
        });
        md_navCell.setCell(layout);
        navCells.add(md_navCell);
        addView(layout, params);
    }
    //______________________________________________________________________________________________ addCell



    //______________________________________________________________________________________________ configIcon
    private ImageView configIcon(MD_NavModel navModel) {

        ImageView imageIcon = new ImageView(context);
        int width = (int) (ta.getDimension(R.styleable.ML_NavigationBottom_navIconWidth, 0));
        int height = (int) (ta.getDimension(R.styleable.ML_NavigationBottom_navIconHeight, 0));
        LayoutParams params = new LayoutParams(width, height);
        imageIcon.setLayoutParams(params);
        int iconTint = ta.getColor(R.styleable.ML_NavigationBottom_navNotSelectColor, 0);
        imageIcon.setImageDrawable(navModel.getIcon());
        imageIcon.setColorFilter(iconTint);
        imageIcon.setScaleX(scale);
        imageIcon.setScaleY(scale);
        return imageIcon;
    }
    //______________________________________________________________________________________________ configIcon



    //______________________________________________________________________________________________ configTitle
    private TextView configTitle(MD_NavModel navModel) {

        TextView textView = new TextView(context);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        int textSize = (int) (ta.getDimensionPixelSize(R.styleable.ML_NavigationBottom_navTitleSize, 0) / getResources().getDisplayMetrics().density);
        int textColor = ta.getColor(R.styleable.ML_NavigationBottom_navNotSelectColor, 0);
        textView.setTextColor(textColor);
        textView.setTextSize(textSize);
        textView.setMaxLines(1);
        textView.setText(navModel.getTitle());
        int fontFamilyId = ta.getResourceId(R.styleable.ML_NavigationBottom_fontFamily, 0);
        if (fontFamilyId > 0)
            textView.setTypeface(ResourcesCompat.getFont(getContext(), fontFamilyId));
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundColor(context.getResources().getColor(R.color.ML_Transparent));
        textView.setLayoutParams(params);
        textView.setPadding(0, 5, 0, 0);
        textView.setScaleX(scale);
        textView.setScaleY(scale);
        return textView;
    }
    //______________________________________________________________________________________________ configTitle


    //______________________________________________________________________________________________ setCurrent
    public void setCurrent(int id) {
        if (id != current) {
            current = id;
            int notSelectColor = ta.getColor(R.styleable.ML_NavigationBottom_navNotSelectColor, 0);
            int textColor = ta.getColor(R.styleable.ML_NavigationBottom_android_textColor, 0);
            int iconTint = ta.getColor(R.styleable.ML_NavigationBottom_navIconColor, 0);

            for (MD_NavCell cell : navCells)
                if (cell.getId() == id) {
                    cell.getIcon().setScaleY(1);
                    cell.getIcon().setScaleX(1);
                    cell.getTitle().setScaleY(1);
                    cell.getTitle().setScaleX(1);
                    cell.getTitle().setTextColor(textColor);
                    cell.getIcon().setColorFilter(iconTint);
                    Animation aniFade = AnimationUtils.loadAnimation(context, R.anim.fade);
                    cell.getIcon().startAnimation(aniFade);
                } else {
                    cell.getIcon().setScaleY(scale);
                    cell.getIcon().setScaleX(scale);
                    cell.getIcon().setAnimation(null);
                    cell.getIcon().setColorFilter(notSelectColor);
                    cell.getTitle().setScaleY(scale);
                    cell.getTitle().setScaleX(scale);
                    cell.getTitle().setAnimation(null);
                    cell.getTitle().setTextColor(notSelectColor);
                }
        }
    }
    //______________________________________________________________________________________________ setCurrent


    //______________________________________________________________________________________________ getCurrent
    public int getCurrent() {
        return current;
    }
    //______________________________________________________________________________________________ getCurrent
}

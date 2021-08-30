package land.majazi.latifiarchitecure.views.customs.navigation;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MD_NavCell {

    private int id;
    private LinearLayout cell;
    private TextView title;
    private ImageView icon;

    public MD_NavCell() {
    }

    public MD_NavCell(int id, LinearLayout cell, TextView title, ImageView icon) {
        this.id = id;
        this.cell = cell;
        this.title = title;
        this.icon = icon;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinearLayout getCell() {
        return cell;
    }

    public void setCell(LinearLayout cell) {
        this.cell = cell;
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }
}

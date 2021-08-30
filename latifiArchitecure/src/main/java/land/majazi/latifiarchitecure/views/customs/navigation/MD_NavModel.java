package land.majazi.latifiarchitecure.views.customs.navigation;

import android.graphics.drawable.Drawable;

public class MD_NavModel {

    private int id;
    private String title;
    private Drawable icon;

    public MD_NavModel(int id, String title, Drawable icon) {
        this.id = id;
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

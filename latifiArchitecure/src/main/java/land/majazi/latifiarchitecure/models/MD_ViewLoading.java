package land.majazi.latifiarchitecure.models;

import android.view.View;

import land.majazi.latifiarchitecure.views.customs.loadings.ViewSkeletonScreen;

public class MD_ViewLoading {

    private ViewSkeletonScreen viewSkeletonScreen;
    private View view;

    public MD_ViewLoading(ViewSkeletonScreen viewSkeletonScreen, View view) {
        this.viewSkeletonScreen = viewSkeletonScreen;
        this.view = view;
    }

    public ViewSkeletonScreen getViewSkeletonScreen() {
        return viewSkeletonScreen;
    }

    public void setViewSkeletonScreen(ViewSkeletonScreen viewSkeletonScreen) {
        this.viewSkeletonScreen = viewSkeletonScreen;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}

package land.majazi.latifiarchitecure.manager;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;

public class DialogManager {

    //______________________________________________________________________________________________ createDialog
    public Dialog createDialog(Context context, @LayoutRes int layoutResID, boolean cancel) {

        Dialog dialog;
        dialog = new Dialog(context);
        dialog.setCancelable(cancel);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layoutResID);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        return dialog;
    }
    //______________________________________________________________________________________________ createDialog

}

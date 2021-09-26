package land.majazi.latifiarchitecure.views.fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.models.MD_GregorianDate;
import land.majazi.latifiarchitecure.models.MD_SolarDate;
import land.majazi.latifiarchitecure.models.MD_ViewLoading;
import land.majazi.latifiarchitecure.models.PrimaryModel;
import land.majazi.latifiarchitecure.utility.Utility;
import land.majazi.latifiarchitecure.views.adapter.AP_Loading;
import land.majazi.latifiarchitecure.views.customs.alerts.toast.ML_Toast;
import land.majazi.latifiarchitecure.views.customs.loadings.RecyclerViewSkeletonScreen;
import land.majazi.latifiarchitecure.views.customs.loadings.Skeleton;
import land.majazi.latifiarchitecure.views.customs.loadings.ViewSkeletonScreen;
import land.majazi.latifiarchitecure.views.customs.text.ML_TextView;
import land.majazi.latifiarchitecure.views.dialog.PersianPicker.ML_PersianPickerDialog;


public class FR_Primary extends Fragment {


    private Activity activity;
    private OnBackPressedCallback pressedCallback;
    private fragmentActions fragmentActions;
    private View view;
    private NavController navController;
    private AP_Loading ap_loading;
    private RecyclerViewSkeletonScreen skeletonScreen;
    private ViewSkeletonScreen viewSkeletonScreen;
    private boolean doubleExitApplication = false;
    public static int firstFragmentAction = 0;
    public final int RESULT_ENABLE = 11;
    public int biometricAction;
    private static String fragmentName;
//    private List<MD_ViewLoading> md_viewLoadings;


    //______________________________________________________________________________________________ fragmentActions
    public interface fragmentActions {

        void actionWhenFailureRequest(String error);

        void backButtonPressed();

        void permissionWasGranted();

        void clickPressed();

        void init();

        void cropImage(Uri uri);

        void unAuthorization(String error);

        void startView();

        void stopView();

        void bioMetric(int biometricAction);

        void bioMetricFailed(int biometricAction);

    }
    //______________________________________________________________________________________________ fragmentActions


    //______________________________________________________________________________________________ FR_Primary
    public FR_Primary() {

    }
    //______________________________________________________________________________________________ FR_Primary


    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        pressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                fragmentActions.backButtonPressed();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, pressedCallback);
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        navController = Navigation.findNavController(getView());
        if (fragmentActions != null)
            fragmentActions.startView();
    }
    //______________________________________________________________________________________________ onStart


    //______________________________________________________________________________________________ getContext
    @Override
    public Activity getContext() {
        return activity;
    }
    //______________________________________________________________________________________________ getContext


    //______________________________________________________________________________________________ onDestroy
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    //______________________________________________________________________________________________ onDestroy


    //______________________________________________________________________________________________ onStop
    @Override
    public void onStop() {
        super.onStop();
    }
    //______________________________________________________________________________________________ onStop


    //______________________________________________________________________________________________ getView
    @Override
    public View getView() {
        return view;
    }
    //______________________________________________________________________________________________ getView


    //______________________________________________________________________________________________ setView
    public void setView(View view, fragmentActions fragmentActions) {
        this.view = view;
        this.fragmentActions = fragmentActions;
        ButterKnife.bind(this, getView());
        this.fragmentActions.init();
        this.fragmentActions.clickPressed();
        fragmentName = fragmentActions.getClass().toString();
    }
    //______________________________________________________________________________________________ setView


    //______________________________________________________________________________________________ checkRequest
    public void checkErrorRequest(PrimaryModel primaryModel) {
        switch (primaryModel.getResponseCode()) {
            case 401:
            case 403:
                this.fragmentActions.unAuthorization(primaryModel.getMessage());
                break;
            default:
                this.fragmentActions.actionWhenFailureRequest(primaryModel.getMessage());
                break;
        }
    }
    //______________________________________________________________________________________________ checkRequest


    //______________________________________________________________________________________________ showToast
    public void showToast(LinearLayout linearLayout, String message, Drawable icon, int tintColor) {

        if (message != null && !message.isEmpty())
            ML_Toast.showToast(getContext(), linearLayout, message, icon, tintColor);
    }
    //______________________________________________________________________________________________ showToast


    //______________________________________________________________________________________________ getUtility
    public Utility getUtility() {

        return new Utility();
    }
    //______________________________________________________________________________________________ getUtility


    //______________________________________________________________________________________________ setPermission
    public boolean setPermission(List<String> permissions) {

        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            int check = ContextCompat.checkSelfPermission(getContext(), permission);
            if (check != PackageManager.PERMISSION_GRANTED)
                listPermissionsNeeded.add(permission);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            int REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 7126;
            requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_PERMISSIONS_CODE_WRITE_STORAGE);
            return false;
        } else
            return true;

    }
    //______________________________________________________________________________________________ setPermission


    //______________________________________________________________________________________________ onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean accessPermission = true;
        for (String permission : permissions) {
            int check = ContextCompat.checkSelfPermission(getContext(), permission);
            if (check != PackageManager.PERMISSION_GRANTED) {
                accessPermission = false;
                break;
            }
        }

        if (accessPermission)
            fragmentActions.permissionWasGranted();
        else
            getContext().onBackPressed();

    }
    //______________________________________________________________________________________________ onRequestPermissionsResult


    //______________________________________________________________________________________________ setVariableToNavigation
    public void setVariableToNavigation(String saveKey, String saveValue) {
        if (getView() != null) {
            NavController navigation = Navigation.findNavController(getView());
            navigation.getPreviousBackStackEntry().getSavedStateHandle().set(saveKey, saveValue);
        }
    }
    //______________________________________________________________________________________________ setVariableToNavigation


    //______________________________________________________________________________________________ getVariableFromNavigation
    public String getVariableFromNavigation(String saveKey) {
        if (getView() != null) {
            NavController navigation = Navigation.findNavController(getView());
            String value = navigation.getCurrentBackStackEntry().getSavedStateHandle().get(saveKey);
            navigation.getCurrentBackStackEntry().getSavedStateHandle().set(saveKey, null);
            return value;
        } else
            return null;
    }
    //______________________________________________________________________________________________ getVariableFromNavigation


    //______________________________________________________________________________________________ goBack
    public void goBack() {
        hideKeyBoard();
        pressedCallback.remove();
        getContext().onBackPressed();
    }
    //______________________________________________________________________________________________ goBack


    //______________________________________________________________________________________________ gotoOtherFragment
    public void gotoOtherFragment(int id, Bundle bundle) {
        hideKeyBoard();
        try {
            navController.navigate(id, bundle);
        } catch (Exception e) {
            gotoFirstFragment();
        }
    }
    //______________________________________________________________________________________________ gotoOtherFragment


    //______________________________________________________________________________________________ hideKeyBoard
    public void hideKeyBoard() {
        getUtility().hideKeyboard(getActivity());
    }
    //______________________________________________________________________________________________ hideKeyBoard


    //______________________________________________________________________________________________ createDialog
    public Dialog createDialog(@LayoutRes int layoutResID, boolean cancel) {

        Dialog dialog;
        dialog = new Dialog(getContext());
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


    //______________________________________________________________________________________________ setRecyclerLoading
    public void setRecyclerLoading(RecyclerView recyclerLoading, int layout, @ColorRes int Color) {

        ap_loading = new AP_Loading();
        recyclerLoading.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        skeletonScreen = Skeleton.bind(recyclerLoading)
                .adapter(ap_loading)
                .load(layout)
                .shimmer(true)      // whether show shimmer animation.                      default is true
                .count(1)          // the recycler view item count.                        default is 10
                .color(Color)       // the shimmer color.                                   default is #a2878787
                .angle(0)          // the shimmer angle.                                   default is 20;
                .duration(1200)     // the shimmer animation duration.                      default is 1000;
                .frozen(false)
                .show();
    }
    //______________________________________________________________________________________________ setRecyclerLoading


    //______________________________________________________________________________________________ setRecyclerLoading
    public void setRecyclerLoading(RecyclerView recyclerLoading, int layout, @ColorRes int Color, int count) {

        ap_loading = new AP_Loading();
        recyclerLoading.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        skeletonScreen = Skeleton.bind(recyclerLoading)
                .adapter(ap_loading)
                .load(layout)
                .shimmer(true)      // whether show shimmer animation.                      default is true
                .count(count)          // the recycler view item count.                        default is 10
                .color(Color)       // the shimmer color.                                   default is #a2878787
                .angle(0)          // the shimmer angle.                                   default is 20;
                .duration(2000)     // the shimmer animation duration.                      default is 1000;
                .frozen(false)
                .show();
    }
    //______________________________________________________________________________________________ setRecyclerLoading


    //______________________________________________________________________________________________ stopLoadingRecycler
    public void stopLoadingRecycler() {

        if (skeletonScreen != null) {
            skeletonScreen.hide();
            skeletonScreen = null;
        }

        if (ap_loading != null)
            ap_loading = null;
    }
    //______________________________________________________________________________________________ stopLoadingRecycler


    //______________________________________________________________________________________________ setViewLoading
    public void setViewLoading(View view, int layout, @ColorRes int Color) {

        viewSkeletonScreen = Skeleton.bind(view)
                .load(layout)
                .angle(0)
                .color(Color)
                .duration(2200)
                .shimmer(true)
                .show();
    }
    //______________________________________________________________________________________________ setViewLoading


    //______________________________________________________________________________________________ stopLoadingView
    public void stopLoadingView() {

        if (viewSkeletonScreen != null) {
            viewSkeletonScreen.hide();
            viewSkeletonScreen = null;
        }
    }
    //______________________________________________________________________________________________ stopLoadingView



/*
    //______________________________________________________________________________________________ setViewLoadings
    public void setViewLoadings(View view, int layout) {

        if (md_viewLoadings == null)
            md_viewLoadings = new ArrayList<>();

        ViewSkeletonScreen loading = Skeleton.bind(view)
                .shimmer(true)
                .color(R.color.dayRecyclerLoading)
                .angle(0)
                .load(layout)
                .duration(2200)
                .show();

        md_viewLoadings.add(new MD_ViewLoading(loading, view));

    }
    //______________________________________________________________________________________________ setViewLoadings
*/




/*
    //______________________________________________________________________________________________ stopLoadingView
    public void stopLoadingViews(View view) {

        if (md_viewLoadings == null)
            return;

        for (MD_ViewLoading md_viewLoading : md_viewLoadings)
            if (md_viewLoading.getView() == view)
                if (md_viewLoading.getViewSkeletonScreen() != null) {
                    md_viewLoading.getViewSkeletonScreen().hide();
                    md_viewLoading.setViewSkeletonScreen(null);
                }
    }
    //______________________________________________________________________________________________ stopLoadingView
*/





    //______________________________________________________________________________________________ exitByDoubleclick
    public void exitByDoubleclick(LinearLayout linearLayout) {
        if (doubleExitApplication)
            System.exit(1);
        else {
            getView().setAlpha(0.1f);
            doubleExitApplication = true;
            showToast(linearLayout, getResources().getString(R.string.doubleExit), getResources().getDrawable(R.drawable.ic_error), getResources().getColor(R.color.white));
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                doubleExitApplication = false;
                getView().setAlpha(1);
            }, 4000);
        }
    }
    //______________________________________________________________________________________________ exitByDoubleclick


    //______________________________________________________________________________________________ gotoFirstFragment
    public void gotoFirstFragment() {

        navController.navigate(firstFragmentAction);

    }
    //______________________________________________________________________________________________ gotoFirstFragment


    //______________________________________________________________________________________________ gotoUrl
    public void gotoUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
    //______________________________________________________________________________________________ gotoUrl


    //______________________________________________________________________________________________ chooseDate
    public void chooseDate(ML_TextView ml_textView, String title, String initDate) {

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = ml_textView.getContext().getTheme();
        @ColorInt int back, text, picker;
        theme.resolveAttribute(R.attr.pdp_back, typedValue, true);
        back = typedValue.data;
        theme.resolveAttribute(R.attr.pdp_text, typedValue, true);
        text = typedValue.data;
        theme.resolveAttribute(R.attr.pdp_picker, typedValue, true);
        picker = typedValue.data;

        ML_PersianPickerDialog persianCalendar = new ML_PersianPickerDialog(getContext())
                .setPositiveButtonString(getContext().getString(R.string.chooseDate))
                .setNegativeButton(getContext().getString(R.string.cancel))
                .setTodayButton(getContext().getString(R.string.todayDate))
                .setTodayButtonVisible(true)
                .setInitDate(getPersianCalendar(initDate))
                .setMaxYear(-1)
                .setMinYear(1300)
                .setActionTextColor(text)
                .setTitleColor(text)
                .setPickerBackgroundColor(picker)
                .setBackgroundColor(back);


        persianCalendar.setListener(new Listener() {
            @Override
            public void onDateSelected(PersianCalendar persianCalendar) {
                StringBuilder sb = new StringBuilder();
                sb.append(persianCalendar.getPersianYear());
                sb.append("/");
                sb.append(String.format("%02d", persianCalendar.getPersianMonth()));
                sb.append("/");
                sb.append(String.format("%02d", persianCalendar.getPersianDay()));
                String chooseDate = sb.toString();
                ml_textView.setAdditionalValue(chooseDate);
                sb = null;
                sb = new StringBuilder();
                sb.append(title);
                sb.append(System.getProperty("line.separator"));
                sb.append(chooseDate);
                ml_textView.setText(sb.toString());
            }

            @Override
            public void onDismissed() {

            }
        });
        persianCalendar.show();

    }
    //______________________________________________________________________________________________ chooseDate


    //______________________________________________________________________________________________ getPersianCalendar
    private PersianCalendar getPersianCalendar(String init) {

        PersianCalendar initDate;

        if (init == null) {
            MD_SolarDate md_solarDate = getUtility().gregorianToSolarDate(new Date());
            initDate = new PersianCalendar();
            initDate.setPersianDate(md_solarDate.getIntYear(), md_solarDate.getIntMonth(), md_solarDate.getIntDay());
        } else {
            initDate = new PersianCalendar();
            initDate.setPersianDate(Integer.valueOf(init.substring(0, 4)).intValue(), Integer.valueOf(init.substring(5, 7)).intValue(), Integer.valueOf(init.substring(8, 10)).intValue());
        }
        return initDate;
    }
    //______________________________________________________________________________________________ getPersianCalendar


    //______________________________________________________________________________________________ dateGenerate
    public String dateGenerate(String date, String time) {

        if (date == null || time == null)
            return "";

        if (date.isEmpty() || time.isEmpty())
            return "";

        date = getUtility().persianToEnglish(date);
        time = getUtility().persianToEnglish(time);

        MD_GregorianDate gregorianDate = getUtility().solarDateToGregorian(date);
        String result = gregorianDate.getDateString("-");
        result = result + "T" + time;
        return result;
    }
    //______________________________________________________________________________________________ dateGenerate


    //______________________________________________________________________________________________ getFragmentActions
    public FR_Primary.fragmentActions getFragmentActions() {
        return fragmentActions;
    }
    //______________________________________________________________________________________________ getFragmentActions


    //______________________________________________________________________________________________ checkAndAuthenticate
    public void checkAndAuthenticate(int biometricAction) {

        this.biometricAction = biometricAction;
        KeyguardManager keyguardManager = (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
        boolean isSecure = keyguardManager.isKeyguardSecure();

        if (isSecure) {
            Intent intent = keyguardManager.createConfirmDeviceCredentialIntent("رمز ورود به گوشی را وارد نمایید", "از وارد کردن رمز مجازی لند خودداری نمایید");
            FR_Primary.this.startActivityForResult(intent, RESULT_ENABLE);
        } else {
            fragmentActions.bioMetric(biometricAction);
        }
    }
    //______________________________________________________________________________________________ checkAndAuthenticate


    //______________________________________________________________________________________________ onActivityResult
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_ENABLE:
                if (resultCode == RESULT_OK)
                    fragmentActions.bioMetric(biometricAction);
                else
                    fragmentActions.bioMetricFailed(biometricAction);
                break;
        }
    }
    //______________________________________________________________________________________________ onActivityResult


    //______________________________________________________________________________________________ blurryTextView
    public void blurryTextView(TextView view) {

        view.setText("0000000000");
        if (Build.VERSION.SDK_INT >= 11) {
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        float radius = view.getTextSize() / 3;
        BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL);
        view.getPaint().setMaskFilter(filter);

    }
    //______________________________________________________________________________________________ blurryTextView



    //______________________________________________________________________________________________ getFragmentName
    public static String getFragmentName() {
        return fragmentName;
    }
    //______________________________________________________________________________________________ getFragmentName

}

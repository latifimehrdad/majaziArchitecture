package land.majazi.latifiarchitecure.views.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.api.PersianPickerDate;
import ir.hamsaa.persiandatepicker.api.PersianPickerListener;
import land.majazi.latifiarchitecure.R;
import land.majazi.latifiarchitecure.converter.Converter;
import land.majazi.latifiarchitecure.enums.EnumUnAuthorization;
import land.majazi.latifiarchitecure.manager.BiometricManager;
import land.majazi.latifiarchitecure.manager.DialogManager;
import land.majazi.latifiarchitecure.manager.KeyBoardManager;
import land.majazi.latifiarchitecure.manager.PermissionManager;
import land.majazi.latifiarchitecure.models.ResponseModel;
import land.majazi.latifiarchitecure.models.SolarDateModel;
import land.majazi.latifiarchitecure.views.activity.MasterActivity;
import land.majazi.latifiarchitecure.views.adapter.AP_Loading;
import land.majazi.latifiarchitecure.views.customs.alert.MLToast;
import land.majazi.latifiarchitecure.views.customs.loadings.RecyclerViewSkeletonScreen;
import land.majazi.latifiarchitecure.views.customs.loadings.Skeleton;
import land.majazi.latifiarchitecure.views.customs.loadings.ViewSkeletonScreen;
import land.majazi.latifiarchitecure.views.customs.text.MLTextView;


public class FR_Primary extends Fragment implements FragmentAction{

    private static String fragmentName;
    public static int firstFragmentAction = 0;
    private NavController navController;

    private OnBackPressedCallback pressedCallback;
    private View view;

    private RecyclerViewSkeletonScreen skeletonScreen;
    private ViewSkeletonScreen viewSkeletonScreen;


    private boolean doubleExitApplication = false;



    //______________________________________________________________________________________________ onCreate
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backButtonPressed();
            }};
        requireActivity().getOnBackPressedDispatcher().addCallback(this, pressedCallback);
    }
    //______________________________________________________________________________________________ onCreate


    //______________________________________________________________________________________________ onStart
    @Override
    public void onStart() {
        super.onStart();
        if (getView() == null)
            return;
        navController = Navigation.findNavController(getView());
        startView();
    }
    //______________________________________________________________________________________________ onStart



    //______________________________________________________________________________________________ getView
    @Override
    public View getView() {
        return view;
    }
    //______________________________________________________________________________________________ getView


    //______________________________________________________________________________________________ setView
    public void setView(View view) {
        this.view = view;
        fragmentName = this.getClass().toString();
        init();
        clickPressed();
    }
    //______________________________________________________________________________________________ setView


    //______________________________________________________________________________________________ checkRequest
    public void checkErrorRequest(ResponseModel responseModel) {
        switch (responseModel.getResponseCode()) {
            case 401:
                this.unAuthorization(responseModel.getMessage(), EnumUnAuthorization.TokenExpire);
                break;
            case 403:
                this.unAuthorization(responseModel.getMessage(), EnumUnAuthorization.Inaccessibility);
                break;
            default:
                this.actionWhenFailureRequest(responseModel.getMessage());
                break;
        }
    }
    //______________________________________________________________________________________________ checkRequest


    //______________________________________________________________________________________________ showToast
    public void showToast(String message, Drawable icon, int tintColor) {

        if (message != null && !message.isEmpty())
            MLToast.showToast(getContext(), message, icon, tintColor);
    }
    //______________________________________________________________________________________________ showToast



    //______________________________________________________________________________________________ isPermissionGranted
    public boolean isPermissionGranted(List<String> permissions) {
        MasterActivity.fragmentAction = this;
        return new PermissionManager().isPermissionGranted(getActivity(), permissions);
    }
    //______________________________________________________________________________________________ isPermissionGranted



    //______________________________________________________________________________________________ setVariableToNavigation
    public void setVariableToNavigation(String saveKey, String saveValue) {
        navController.getPreviousBackStackEntry().getSavedStateHandle().set(saveKey, saveValue);
    }
    //______________________________________________________________________________________________ setVariableToNavigation


    //______________________________________________________________________________________________ getVariableFromNavigation
    public String getVariableFromNavigation(String saveKey) {
        String value = navController.getCurrentBackStackEntry().getSavedStateHandle().get(saveKey);
        navController.getCurrentBackStackEntry().getSavedStateHandle().set(saveKey, null);
        return value;
    }
    //______________________________________________________________________________________________ getVariableFromNavigation


    //______________________________________________________________________________________________ goBack
    public void goBack() {
        hideKeyBoard();
        pressedCallback.remove();
        if (getActivity() == null)
            return;
        this.getActivity().onBackPressed();
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
        KeyBoardManager keyBoardManager = new KeyBoardManager();
        keyBoardManager.hide(getActivity());
    }
    //______________________________________________________________________________________________ hideKeyBoard


    //______________________________________________________________________________________________ createDialog
    public Dialog createDialog(@LayoutRes int layoutResID, boolean cancel) {
        return new DialogManager().createDialog(getContext(), layoutResID, cancel);
    }
    //______________________________________________________________________________________________ createDialog


    //______________________________________________________________________________________________ setRecyclerLoading
    public void setRecyclerLoading(RecyclerView recyclerLoading, int layout, @ColorRes int Color) {

        AP_Loading ap_loading = new AP_Loading();
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

        AP_Loading ap_loading = new AP_Loading();
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


    //______________________________________________________________________________________________ exitByDoubleClick
    public void exitByDoubleClick() {
        if (getContext() == null)
            return;
        if (getView() == null)
            return;

        if (doubleExitApplication)
            System.exit(1);
        else {
            getView().setAlpha(0.1f);
            doubleExitApplication = true;
            showToast(getResources().getString(R.string.doubleExit), ResourcesCompat.getDrawable(getResources(), R.drawable.toast_logout, getContext().getTheme()), ResourcesCompat.getColor(getResources(),R.color.white, requireContext().getTheme()));
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                doubleExitApplication = false;
                getView().setAlpha(1);
            }, 4000);
        }
    }
    //______________________________________________________________________________________________ exitByDoubleClick


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
    public void chooseDate(MLTextView ml_textView, String title, String init) {

        if (getContext() == null)
            return;

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = ml_textView.getContext().getTheme();
        @ColorInt int back, text, picker;
        theme.resolveAttribute(R.attr.pdp_back, typedValue, true);
        back = typedValue.data;
        theme.resolveAttribute(R.attr.pdp_text, typedValue, true);
        text = typedValue.data;
        theme.resolveAttribute(R.attr.pdp_picker, typedValue, true);
        picker = typedValue.data;

        SolarDateModel initDate = getPersianCalendar(init);

        PersianDatePickerDialog persianCalendar = new PersianDatePickerDialog(getContext())
                .setPositiveButtonString(getContext().getString(R.string.chooseDate))
                .setNegativeButton(getContext().getString(R.string.cancel))
                .setTodayButton(getContext().getString(R.string.todayDate))
                .setTodayButtonVisible(true)
                .setInitDate(initDate.getYear(), initDate.getMonth(), initDate.getDay())
                .setMaxYear(-1)
                .setMinYear(1300)
                .setActionTextColor(text)
                .setTitleColor(text)
                .setPickerBackgroundColor(picker)
                .setBackgroundColor(back);

        persianCalendar.setListener(new PersianPickerListener() {
            @Override
            public void onDateSelected(PersianPickerDate persianPickerDate) {
                StringBuilder sb = new StringBuilder();
                sb.append(persianPickerDate.getPersianYear());
                sb.append("/");
                sb.append(String.format(Locale.getDefault(),"%02d", persianPickerDate.getPersianMonth()));
                sb.append("/");
                sb.append(String.format(Locale.getDefault(),"%02d", persianPickerDate.getPersianDay()));
                String chooseDate = sb.toString();
                ml_textView.setAdditionalValue(chooseDate);
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
    private SolarDateModel getPersianCalendar(String init) {

        SolarDateModel solarDateModel;
        if (init == null) {
            Converter converter = new Converter();
            solarDateModel = converter.GregorianDateToSolarDate(LocalDateTime.now());
        } else {
            solarDateModel = new SolarDateModel(Integer.parseInt(init.substring(0, 4)), Integer.parseInt(init.substring(5, 7)), Integer.parseInt(init.substring(8, 10)), DayOfWeek.FRIDAY);
        }

        return solarDateModel;
    }
    //______________________________________________________________________________________________ getPersianCalendar



    //______________________________________________________________________________________________ checkAndAuthenticate
    public void checkAndAuthenticate(int biometricAction) {

        if (getActivity() == null)
            return;
        MasterActivity.fragmentAction = this;
        new BiometricManager().checkBiometric(getActivity(), this, biometricAction);
    }
    //______________________________________________________________________________________________ checkAndAuthenticate



    //______________________________________________________________________________________________ getFragmentName
    public static String getFragmentName() {
        return fragmentName;
    }
    //______________________________________________________________________________________________ getFragmentName

}

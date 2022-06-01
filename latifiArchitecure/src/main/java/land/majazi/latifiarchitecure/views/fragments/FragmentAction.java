package land.majazi.latifiarchitecure.views.fragments;

import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;

import land.majazi.latifiarchitecure.enums.EnumUnAuthorization;

public interface FragmentAction {

    default void actionWhenFailureRequest(String error) { }

    default void backButtonPressed() {}

    default void permissionWasGranted(){}

    default void clickPressed(){}

    default void onViewCreated(){}

    default void init(@NonNull View view){}

    default void cropImage(Uri uri){}

    default void unAuthorization(String error, EnumUnAuthorization enumUnAuthorization){}

    default void bioMetric(int biometricAction){}

    default void bioMetricFailed(int biometricAction){}
}

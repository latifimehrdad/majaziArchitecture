package land.majazi.latifiarchitecure.views.fragments;

import android.net.Uri;

public interface FragmentAction {
    default void actionWhenFailureRequest(String error) { }

    default void backButtonPressed() {}

    default void permissionWasGranted(){}

    default void clickPressed(){}

    default void init(){}

    default void cropImage(Uri uri){}

    default void unAuthorization(String error){}

    default void startView(){}

    default void stopView(){}

    default void bioMetric(int biometricAction){}

    default void bioMetricFailed(int biometricAction){}
}

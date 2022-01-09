package land.majazi.majaziarchitecture;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import land.majazi.latifiarchitecure.views.fragments.FR_Primary;
import land.majazi.majaziarchitecture.databinding.FragmentTwoBinding;

public class TwoFragment extends FR_Primary {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentTwoBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_two, container, false);
            setView(binding.getRoot());
        }
        return getView();
    }

    @Override
    public void init(){
        Log.i("meri", getFragmentName());
    }

    @Override
    public void backButtonPressed() {
        Log.i("meri", "TwoFragment backButtonPressed");
        goBack();
    }

}
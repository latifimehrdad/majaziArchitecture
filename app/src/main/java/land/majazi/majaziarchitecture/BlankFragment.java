package land.majazi.majaziarchitecture;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import land.majazi.latifiarchitecure.views.fragments.FR_Primary;
import land.majazi.majaziarchitecture.databinding.FragmentBlankBinding;


public class BlankFragment extends FR_Primary{

    @BindView(R.id.constraintTest)
    ConstraintLayout constraintTest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentBlankBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false);
            setView(binding.getRoot());
            constraintTest.setOnClickListener(v -> {
                constraintTest.setBackgroundColor(Color.GREEN);
                    gotoOtherFragment(R.id.gotoTwo, null); });
        }

        return getView();
    }

    @Override
    public void init(){
        Log.i("meri", getFragmentName());
    }

    @Override
    public void backButtonPressed() {
        Log.i("meri", "BlankFragment backButtonPressed");
    }

}
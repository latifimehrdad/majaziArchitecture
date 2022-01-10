package land.majazi.majaziarchitecture;

import android.Manifest;
import android.graphics.Color;
import android.net.Uri;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import land.majazi.latifiarchitecure.converter.Converter;
import land.majazi.latifiarchitecure.views.fragments.FR_Primary;
import land.majazi.latifiarchitecure.views.fragments.FR_PrimaryFileSupport;
import land.majazi.majaziarchitecture.databinding.FragmentBlankBinding;


public class BlankFragment extends FR_PrimaryFileSupport {

    @BindView(R.id.constraintTest)
    ConstraintLayout constraintTest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getView() == null) {
            FragmentBlankBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_blank, container, false);
            setView(binding.getRoot());
            constraintTest.setOnClickListener(v -> {
                showDialogChooseImage("عکس ملی", getResources().getString(R.string.app_name), true);
//                    gotoOtherFragment(R.id.gotoTwo, null);
            });

        }

        return getView();
    }


    @Override
    public void cropImage(Uri uri){
        Log.i("meri", uri.getPath());
    }

}
package land.majazi.majaziarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.time.LocalDateTime;

import land.majazi.latifiarchitecure.converter.GregorianDateToSolarDate;
import land.majazi.latifiarchitecure.models.SolarDateModel;
import land.majazi.latifiarchitecure.views.customs.alert.MLToast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MLToast.showToast(getApplicationContext(), "سلام سلام سلام سلام سلام سلام سلام سلام سلام سلام سلام سلام سلام سلام", getResources().getDrawable(R.drawable.toast_warning), getResources().getColor(R.color.red));

    }

    private void p(String message) {
        Log.i("meri", message);
    }
}
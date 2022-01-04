package land.majazi.majaziarchitecture;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Date;

import land.majazi.latifiarchitecure.utility.Utility;
import land.majazi.latifiarchitecure.utility.Validations;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Validations validations = new Validations();
        validations.shabaValidation("430160000000000390784124");
    }
}
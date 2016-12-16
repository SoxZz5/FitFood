package konid.soxzz5.fitfood;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import konid.soxzz5.fitfood.R;

/**
 * Created by Soxzer on 16/12/2016.
 */

public class AfterLogin extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Log.d("AfterLogin","Started");
        setContentView(R.layout.activity_after_login);

    }


}

package konid.soxzz5.fitfood.firebase_fitfood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import konid.soxzz5.fitfood.MainActivity;
import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.utils.utils;

/**
 * Created by Soxzer on 16/12/2016.
 */

public class AfterLogin extends AppCompatActivity{
    boolean validat_form = false;
    DatabaseReference databaseReference;
    String name,surname,pseudo;
    int diet = -1;
    FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_after_login);

        final EditText et_name = (EditText) findViewById(R.id.after_login_et_name);
        final EditText et_surname = (EditText) findViewById(R.id.after_login_et_surname);
        final EditText et_pseudo = (EditText) findViewById(R.id.after_login_et_pseudo);

        final TextView error_name = (TextView) findViewById(R.id.after_login_errorname);
        final TextView error_surname = (TextView) findViewById(R.id.after_login_errorsurname);
        final TextView error_pseudo = (TextView) findViewById(R.id.after_login_errorpseudo);

        final Button bt_valid = (Button) findViewById(R.id.after_login_bt_valid);
        final RadioGroup rg_diet = (RadioGroup) findViewById(R.id.after_login_rg_diet);

        /*firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        et_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_name.getText().length()<=3)
                {
                    error_name.setText("");
                    et_name.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else if(utils.findMatch(et_name.getText().toString(),"^[A-Z][a-z-]{3,20}$"))
                {
                    validat_form = true;
                    error_name.setText("");
                    et_name.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    validat_form = false;
                    error_name.setText(R.string.register_error_name);
                    et_name.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_surname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_surname.getText().length()<=3)
                {
                    error_surname.setText("");
                    et_surname.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else if(utils.findMatch(et_surname.getText().toString(),"^[A-Z][a-z-]{3,20}$"))
                {
                    validat_form = true;
                    error_surname.setText("");
                    et_surname.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    validat_form = false;
                    error_surname.setText(R.string.register_error_surname);
                    et_surname.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_pseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_pseudo.getText().length()<=0)
                {
                    error_pseudo.setText("");
                    et_pseudo.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else if(utils.findMatch(et_pseudo.getText().toString(),"^[a-zA-Z0-9_-]{3,15}$"))
                {
                    validat_form = true;
                    error_pseudo.setText("");
                    et_pseudo.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    validat_form = false;
                    error_pseudo.setText(R.string.register_error_pseudo);
                    et_pseudo.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bt_valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(diet == -1){validat_form=false;}
                if(validat_form)
                {
                    name = et_name.getText().toString().trim();
                    surname = et_surname.getText().toString().trim();
                    pseudo = et_pseudo.getText().toString().trim();
                    saveUserInformation();
                    Intent intent = new Intent(AfterLogin.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    //ERREUR
                }
            }
        });

        rg_diet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                diet=i;
            }
        });*/

    }

    public void saveUserInformation(){

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String email = user.getEmail().toString().trim();
        UserInformation userInformation = new UserInformation(name,surname,pseudo,diet,email);

        databaseReference.child("users").child(user.getUid()).child("user_information").setValue(userInformation);

        Toast.makeText(AfterLogin.this,getString(R.string.after_login_text_validate),Toast.LENGTH_LONG).show();
    }
}

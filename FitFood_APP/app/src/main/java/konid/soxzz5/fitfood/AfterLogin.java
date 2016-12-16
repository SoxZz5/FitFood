package konid.soxzz5.fitfood;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import konid.soxzz5.fitfood.firebase_fitfood.UserInformation;
import konid.soxzz5.fitfood.fitfood_session.SessionManager;
import konid.soxzz5.fitfood.utils.utils;

public class AfterLogin extends AppCompatActivity {
    boolean validat_form = false;
    DatabaseReference databaseReference;
    String name,surname,pseudo;
    int diet;
    FirebaseAuth firebaseAuth;
    SessionManager sessionManager;
    FirebaseUser user;
    boolean isExist;
    Firebase mref;
    boolean valid_name = false;
    boolean valid_surname = false;
    boolean valid_pseudo = false;
    ArrayList<String> mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_after_login);
        final EditText et_name = (EditText) findViewById(R.id.after_login_et_name);
        final EditText et_surname = (EditText) findViewById(R.id.after_login_et_surname);
        final EditText et_pseudo = (EditText) findViewById(R.id.after_login_et_pseudo);

        final TextView error_name = (TextView) findViewById(R.id.after_login_errorname);
        final TextView error_surname = (TextView) findViewById(R.id.after_login_errorsurname);
        final TextView error_pseudo = (TextView) findViewById(R.id.after_login_errorpseudo);

        final Button bt_valid = (Button) findViewById(R.id.after_login_bt_valid);
        final RadioGroup rg_diet = (RadioGroup) findViewById(R.id.after_login_rg_diet);
        diet = -1;
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        sessionManager = new SessionManager();
        user = firebaseAuth.getCurrentUser();
        mUsername = new ArrayList<>();

        Firebase.setAndroidContext(AfterLogin.this);
        mref= new Firebase("https://fir-fitfood.firebaseio.com/username");
        mref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(com.firebase.client.DataSnapshot dataSnapshot, String s) {
                String value = dataSnapshot.getValue(String.class);
                mUsername.add(value);
            }
            @Override
            public void onChildChanged(com.firebase.client.DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(com.firebase.client.DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(com.firebase.client.DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });



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
                    valid_name=true;
                    error_name.setText("");
                    et_name.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    valid_name = false;
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
                    valid_surname = true;
                    error_surname.setText("");
                    et_surname.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    valid_surname = false;
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
                    valid_pseudo = true;
                    error_pseudo.setText("");
                    et_pseudo.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    valid_pseudo = false;
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
                if(valid_pseudo && valid_surname && valid_name){validat_form=true;}
                if(valid_pseudo)
                {
                    pseudo = et_pseudo.getText().toString().trim();
                    String pseudo_lowercase = pseudo.toLowerCase();
                    Log.d("username","pseudo_lowercase = " + pseudo_lowercase);
                    if(mUsername != null){
                        for(int i = 0 ; i < mUsername.size(); i++)
                        {
                            Log.d("username","mUsername " + i + " = " + mUsername.toString().toLowerCase());
                            if(pseudo_lowercase.equals(mUsername.get(i).toString().toLowerCase()))
                            {
                                isExist = true;
                            }
                        }
                    }
                    if(isExist)
                    {
                        Toast.makeText(AfterLogin.this,getString(R.string.register_error_confirm_pseudo),Toast.LENGTH_SHORT).show();
                        validat_form=false;
                        isExist=false;
                    }
                }

                if(validat_form)
                {
                    name = et_name.getText().toString().trim();
                    surname = et_surname.getText().toString().trim();
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
                switch (i)
                {
                    case R.id.after_login_bt_omnivore:
                        diet=1;
                        break;
                    case R.id.after_login_bt_vegetarian:
                        diet=2;
                        break;
                    case R.id.after_login_bt_vegan:
                        diet=3;
                        break;
                }
            }
        });

    }

    public void saveUserInformation(){


        String email = user.getEmail().toString().trim();
        UserInformation userInformation = new UserInformation(name,surname,pseudo,diet,email);
        databaseReference.child("users").child(user.getUid()).setValue(userInformation);
        databaseReference.child("username").child(user.getUid()).setValue(pseudo);
        sessionManager.setPreferences(AfterLogin.this,"first_sign","0");
        Toast.makeText(AfterLogin.this, getString(R.string.after_login_text_validate), Toast.LENGTH_LONG).show();
    }
}

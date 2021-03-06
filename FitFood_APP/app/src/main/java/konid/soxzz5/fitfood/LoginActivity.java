package konid.soxzz5.fitfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import konid.soxzz5.fitfood.fitfood_session.SessionManager;


public class LoginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    ProgressBar progressbar_login;
    LinearLayout block_form;
    boolean first_login;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        //ON RECUPERE L'INSTANCE DE FIREBASE
        firebaseAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager();

        final EditText edit_mail = (EditText) findViewById(R.id.edit_mail);
        final EditText edit_password = (EditText) findViewById(R.id.edit_password);
        progressbar_login = (ProgressBar) findViewById(R.id.progressbar_login_loading);
        final Button button_login = (Button) findViewById(R.id.button_login);
        final ImageView fitfoodText = (ImageView) findViewById(R.id.fitfoodtext);
        block_form = (LinearLayout) findViewById(R.id.login_block_form);
        final TextView text_TP_register = (TextView) findViewById(R.id.text_TP_register);

        //NECESSAIRE AU TOAST, ON RECUPERE LE CONTEXT ET ON DEFINIT LA DUREE DU TOAST
        final Context context = getApplicationContext();
        final int duration = Toast.LENGTH_SHORT;

        //ON CREER UN BUNDLE POUR RECUPERER LES MESSAGES PASSER ENTRE LES INTENTS
        Intent intent_tmp = getIntent();
        Bundle bundle_tmp = intent_tmp.getExtras();
        //SI ON A UN MESSAGE DANS L'INTENT
        if(bundle_tmp != null)
        {

            String response_register = getString(R.string.login_text_after_register) + " " + bundle_tmp.getString("response");
            if(!response_register.isEmpty())
            {
                //AFFICHAGE DE RETOUR DE REPONSE VIA TOAST
                Toast toast = Toast.makeText(context, response_register, duration);
                toast.show();
            }
        }

        if(sessionManager.getPreferences(LoginActivity.this,"mail_sign") != null)
        {
            edit_mail.setText(sessionManager.getPreferences(LoginActivity.this,"mail_sign"));
            edit_password.requestFocus();
        }

        //BOUTON POUR ACCEDER A L'INSCRIPTION
        text_TP_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_register = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent_register);
            }
        });

        //BOUTON DE CONNECTION
       button_login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

                   final String email = edit_mail.getText().toString().trim();
                   final String password = edit_password.getText().toString().trim();

                    //SI LES CHAMPS SONT VIDES
                   if(TextUtils.isEmpty(email))
                   {
                       Toast.makeText(LoginActivity.this,getString(R.string.login_text_error_mail).trim(),Toast.LENGTH_LONG).show();
                   }
                   if(TextUtils.isEmpty(password))
                   {
                       Toast.makeText(LoginActivity.this,getString(R.string.login_text_error_password).trim(),Toast.LENGTH_LONG).show();
                   }

                   button_login.setVisibility(View.GONE);
               edit_mail.setVisibility(View.GONE);
               edit_password.setVisibility(View.GONE);
               text_TP_register.setVisibility(View.GONE);
               fitfoodText.setVisibility(View.GONE);
                   progressbar_login.setVisibility(View.VISIBLE);

               //ON APPELLE FIREBASE ET LA FONCTION SIGN IN AVEC UN ON COMPLETE LISTENER
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Intent intent = LoginUser();
                                    startActivity(intent);
                                    block_form.setVisibility(View.GONE);
                                    progressbar_login.setVisibility(View.GONE);
                                    finish();
                                }
                                else
                                {
                                    button_login.setVisibility(View.VISIBLE);
                                    edit_mail.setVisibility(View.VISIBLE);
                                    edit_password.setVisibility(View.VISIBLE);
                                    text_TP_register.setVisibility(View.VISIBLE);
                                    fitfoodText.setVisibility(View.VISIBLE);
                                    block_form.setVisibility(View.VISIBLE);
                                    progressbar_login.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this,"Erreur",Toast.LENGTH_LONG);
                                }
                            }
                        });
               }
       });
    }

    private Intent LoginUser(){
        user = firebaseAuth.getCurrentUser();
        Intent intent;
        String first_sign = sessionManager.getPreferences(LoginActivity.this,"first_sign");
        if(first_sign.equals("1"))
        {
            intent = new Intent(LoginActivity.this, AfterLogin.class);
        }
        else
        {
            intent = new Intent(LoginActivity.this, MainActivity.class);
        }
        return intent;
    }
}

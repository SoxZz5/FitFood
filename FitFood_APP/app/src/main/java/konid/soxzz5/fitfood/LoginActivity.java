package konid.soxzz5.fitfood;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import konid.soxzz5.fitfood.fitfood_session.SessionManager;

public class LoginActivity extends AppCompatActivity {

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        final EditText edit_pseudo = (EditText) findViewById(R.id.edit_pseudo);
        final EditText edit_password = (EditText) findViewById(R.id.edit_password);
        final ProgressBar progressbar_login = (ProgressBar) findViewById(R.id.progressbar_login_loading);
        final Button button_login = (Button) findViewById(R.id.button_login);
        final ImageView fitfoodText = (ImageView) findViewById(R.id.fitfoodtext);

        final TextView text_TP_register = (TextView) findViewById(R.id.text_TP_register);

        //NECESSAIRE AU TOAST
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

                   final String pseudo = edit_pseudo.getText().toString();
                   final String password = edit_password.getText().toString();
               /*
               // ON AFFICHE LA PROGRESSBAR ET ON DESAFFICHE LES INFORMATIONS DU FORMULAIRE
                   button_login.setVisibility(View.GONE);
                   edit_pseudo.setVisibility(View.GONE);
                   edit_password.setVisibility(View.GONE);
                   text_TP_register.setVisibility(View.GONE);
                   fitfoodText.setVisibility(View.GONE);
                   progressbar_login.setVisibility(View.VISIBLE);
           */
               }
       });
    }
}

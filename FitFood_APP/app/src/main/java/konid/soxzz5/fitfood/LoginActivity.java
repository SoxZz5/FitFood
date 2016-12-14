package konid.soxzz5.fitfood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import konid.soxzz5.fitfood.fitfood_request.LoginRequest;
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

        final TextView text_TP_register = (TextView) findViewById(R.id.text_TP_register);
        final TextView response_login = (TextView) findViewById(R.id.response_login);
        final TextView text_response_register = (TextView) findViewById(R.id.text_response_register);


        //ON CREER UN BUNDLE POUR RECUPERER LES MESSAGES PASSER ENTRE LES INTENTS
        Intent intent_tmp = getIntent();
        Bundle bundle_tmp = intent_tmp.getExtras();
        //SI ON A UN MESSAGE DANS L'INTENT
        if(bundle_tmp != null)
        {

            String response_register = getString(R.string.login_text_after_register) + bundle_tmp.getString("response");
            text_response_register.setTextColor(Color.parseColor("#96CA2D"));
            if(!response_register.isEmpty())
            {
                text_response_register.setText(response_register);
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

               //ON EFFACE LES TEXTES DE RETOUR
               if(text_response_register.getText() != "")
               {
                   text_response_register.setText("");
               }
               if(response_login.getText()!= "")
               {
                   response_login.setText("");
               }

                   final String pseudo = edit_pseudo.getText().toString();
                   final String password = edit_password.getText().toString();

               //DEFINITION DU LISTENER
                   Response.Listener<String> responseListener = new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                          try {
                              JSONObject jsonResponse = new JSONObject(response);
                              boolean success = jsonResponse.getBoolean("success");
                              //SI LE LISTENER RETOURNE SUCCESS LE LOGIN ET VALIDE
                              if (success)
                              {
                                  session.createLoginSession(pseudo);
                                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                  LoginActivity.this.startActivity(intent);
                                  finish();
                              }
                              else
                              {
                                  //SINON ON EFFACE LA BAR DE CHARGEMENT ON REAFFICHE TOUT ET ON AFFICHE L'ERREUR
                                  progressbar_login.setVisibility(View.GONE);
                                  button_login.setVisibility(View.VISIBLE);
                                  edit_pseudo.setVisibility(View.VISIBLE);
                                  edit_password.setVisibility(View.VISIBLE);
                                  text_TP_register.setVisibility(View.VISIBLE);
                                  String response_msg = jsonResponse.getString("error");

                                  if(response_msg.equals("pseudo"))
                                  {
                                      response_login.setText(R.string.login_text_error_pseudo);
                                  }
                                  if(response_msg.equals("password"))
                                  {
                                      response_login.setText(R.string.login_text_error_password);
                                  }

                                  response_login.setTextColor(Color.parseColor("#B9121B"));
                                  edit_password.setText("");
                              }
                          }  catch (JSONException e) {
                              e.printStackTrace();
                          }
                       }
                   };
               //ON CREER NOTRE REQUETE
                   LoginRequest loginRequest = new LoginRequest(pseudo,password,responseListener);
               // ON CREER NOTRE QUEUE
                   RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
               // ON AFFICHE LA PROGRESSBAR ET ON DESAFFICHE LES INFORMATIONS DU FORMULAIRE
                   button_login.setVisibility(View.GONE);
                   edit_pseudo.setVisibility(View.GONE);
                   edit_password.setVisibility(View.GONE);
                   text_TP_register.setVisibility(View.GONE);
                   progressbar_login.setVisibility(View.VISIBLE);
               //ON ENVOIE LA REQUETE AU SERVEUR PHP
                   queue.add(loginRequest);
               }
       });
    }
}

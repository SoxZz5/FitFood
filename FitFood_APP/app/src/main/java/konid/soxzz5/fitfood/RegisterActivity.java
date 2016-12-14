package konid.soxzz5.fitfood;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import konid.soxzz5.fitfood.fitfood_request.RegisterRequest;
import konid.soxzz5.fitfood.utils.utils;

public class RegisterActivity extends AppCompatActivity {
    //VAR GLOBAL UTILISER DANS D'AUTRES FONCTION QUE LE ONCREATE
    Boolean validat_form = false;
    ProgressBar progressbar_password;
    TextView text_password_strength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        //DECLARATION DES VARIABLES
        final EditText edit_name = (EditText) findViewById(R.id.edit_name);
        final EditText edit_surname = (EditText) findViewById(R.id.edit_surname);
        final EditText edit_pseudo = (EditText) findViewById(R.id.edit_pseudo);
        final EditText edit_mail = (EditText) findViewById(R.id.edit_mail);
        final EditText edit_password = (EditText) findViewById(R.id.edit_password);
        final EditText edit_password_confirm = (EditText) findViewById(R.id.edit_password_confirm);
        final Button button_register = (Button) findViewById(R.id.button_register);

        final TextView response_register = (TextView) findViewById(R.id.response_register);
        final TextView error_name = (TextView) findViewById(R.id.error_name);
        final TextView error_surname = (TextView) findViewById(R.id.error_surname);
        final TextView error_pseudo = (TextView) findViewById(R.id.error_pseudo);
        final TextView error_mail = (TextView) findViewById(R.id.error_mail);
        final TextView error_password = (TextView) findViewById(R.id.error_password);
        final TextView error_password_confirm = (TextView) findViewById(R.id.error_password_confirm);


        text_password_strength = (TextView) findViewById(R.id.text_password_strength);
        progressbar_password = (ProgressBar) findViewById(R.id.progressbar_password);


        //DETECTION DU CHANGEMENT DE TEXT DANS LES EDITS
        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edit_name.getText().length()<=3)
                {
                    error_name.setText("");
                    edit_name.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else if(utils.findMatch(edit_name.getText().toString(),"^[A-Z][a-z-]{3,20}$"))
                {
                    validat_form = true;
                    error_name.setText("");
                    edit_name.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    validat_form = false;
                    error_name.setText(R.string.register_error_name);
                    edit_name.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    edit_surname.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(edit_surname.getText().length()<=3)
            {
                error_surname.setText("");
                edit_surname.setTextColor(Color.parseColor("#FFFFFF"));
            }
            else if(utils.findMatch(edit_surname.getText().toString(),"^[A-Z][a-z-]{3,20}$"))
            {
                validat_form = true;
                error_surname.setText("");
                edit_surname.setTextColor(Color.parseColor("#96CA2D"));
            }
            else
            {
                validat_form = false;
                error_surname.setText(R.string.register_error_surname);
                edit_surname.setTextColor(Color.parseColor("#FFFFFF"));
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });

        edit_pseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edit_pseudo.getText().length()<=0)
                {
                    error_pseudo.setText("");
                    edit_pseudo.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else if(utils.findMatch(edit_pseudo.getText().toString(),"^[a-zA-Z0-9_-]{3,15}$"))
                {
                    validat_form = true;
                    error_pseudo.setText("");
                    edit_pseudo.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    validat_form = false;
                    error_pseudo.setText(R.string.register_error_pseudo);
                    edit_pseudo.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edit_mail.getText().length()<=0)
                {
                    error_mail.setText("");
                    edit_mail.setTextColor(Color.parseColor("#FFFFFF"));
                }
                else if(utils.findMatch(edit_mail.getText().toString(),"^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-z0-9.-]+$"))
                {
                    validat_form = true;
                    error_mail.setText("");
                    edit_mail.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    validat_form = false;
                    error_mail.setText(R.string.register_error_mail);
                    edit_mail.setTextColor(Color.parseColor("#FFFFFF"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edit_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(edit_password.getText().length()<=6)
                {
                    progressbar_password.setProgress(0);
                    text_password_strength.setText("");
                    error_password.setText("");
                }
                else if(utils.findMatch(edit_password.getText().toString(),"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"))
                {
                    checkPassword(edit_password.getText().toString());
                    validat_form = true;
                    error_password.setText("");
                }
                else
                {
                    progressbar_password.setProgress(0);
                    text_password_strength.setText("");
                    validat_form = false;
                    error_password.setText(R.string.register_error_password);
                }
            }
        });

        edit_password_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(edit_password_confirm.getText().length()<=6)
                {
                    error_password_confirm.setText("");

                }
                else  if(utils.findMatch(edit_password_confirm.getText().toString(),"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})") && edit_password.getText().toString().equals(edit_password_confirm.getText().toString()))
                {
                    validat_form = true;
                    error_password_confirm.setText(R.string.register_valid_confirm_password);
                    error_password_confirm.setTextColor(Color.parseColor("#96CA2D"));
                }
                else
                {
                    validat_form = false;
                    error_password_confirm.setText(R.string.register_error_confirm_password);
                    error_password_confirm.setTextColor(Color.parseColor("#B9121B"));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //CREATION DU LISTENER SUR LE BOUTON
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //SI ON APPUYE SUR CONNEXION
                response_register.setVisibility(View.GONE);
                error_password_confirm.setText("");
                //RECUPERATION DES CHAMPS DANS DES STRINGS
                final String name = edit_name.getText().toString();
                final String surname = edit_surname.getText().toString();
                final String pseudo = edit_pseudo.getText().toString();
                final String mail = edit_mail.getText().toString();
                final String password = edit_password.getText().toString();

                //RESPONSE LISTENER PERMET GRACE A VOLLEY DE RECUPERER UNE REPONSE SUITE A L'EXECUTION DE RegisterRequest.java
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response); //ON RECUPERE UN OBJET JSON EN REPONSE VIA PHP
                            boolean success = jsonResponse.getBoolean("success"); //ON RECUPERE LE BOOLEAN DE LA REPONSE
                            if(success)
                            {//SI L'INSCRIPTION EST REUSSIE ON RETOURNE AU LOGIN EN ENVOYANT UN MESSAGE DE REMERCIEMENT

                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("response", pseudo);
                                RegisterActivity.this.startActivityForResult(intent, 0);
                                finish();
                            }
                            else{//SI L'INSCRIPTION EST RATER ON AFFICHE UN MESSAGE POUR REESSAYER
                                boolean error_pseudo = jsonResponse.getBoolean("errorpseudo");
                                boolean error_mail = jsonResponse.getBoolean("errormail");
                                if(error_pseudo)
                                {
                                    response_register.setText(R.string.register_error_confirm_pseudo);
                                    response_register.setVisibility(View.VISIBLE);
                                    edit_pseudo.setText("");
                                    validat_form=false;
                                }
                                if(error_mail)
                                {
                                    response_register.setText(R.string.register_error_confirm_mail);
                                    response_register.setVisibility(View.VISIBLE);
                                    edit_pseudo.setText("");
                                    validat_form=false;
                                }
                                response_register.setTextColor(Color.parseColor("#B9121B"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                if(validat_form)
                {
                    //ON LANCE LA REQUETE VIA LE CONSTRUCTEUR DE RegisterRequest AU SERVEUR MYSQL
                    RegisterRequest registerRequest= new RegisterRequest(name,surname,pseudo,mail,password,responseListener);
                    //ON DEFINIE UNE QUEUE POUR LANCER LES REQUETES
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    //ON AJOUTE LA REQUETE A LA QUEUE
                    queue.add(registerRequest);
                }
                else
                {
                    response_register.setText(R.string.register_error_incomplete_form);
                    response_register.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    static int i = 1;

    //CHECKPASSWORD FONCTION DE CALCUL DE PUISSANCE DE MOT DE PASSE EN FONCTION DE LA TAILLE ET DE LA COMPLEXITE RENVOIE SUR UNE PROGRESSBAR ET UN TEXTE
    protected void checkPassword(String password) {

        String temp = password;
        System.out.println(i + " current password is : " + temp);
        i = i + 1;

        int length = 0, uppercase = 0, lowercase = 0, digits = 0, symbols = 0, bonus = 0, requirements = 0;

        int lettersonly = 0, numbersonly = 0, cuc = 0, clc = 0;

        length = temp.length();
        for (int i = 0; i < temp.length(); i++) {
            if (Character.isUpperCase(temp.charAt(i)))
                uppercase++;
            else if (Character.isLowerCase(temp.charAt(i)))
                lowercase++;
            else if (Character.isDigit(temp.charAt(i)))
                digits++;

            symbols = length - uppercase - lowercase - digits;

        }

        for (int j = 1; j < temp.length() - 1; j++) {

            if (Character.isDigit(temp.charAt(j)))
                bonus++;

        }

        for (int k = 0; k < temp.length(); k++) {

            if (Character.isUpperCase(temp.charAt(k))) {
                k++;

                if (k < temp.length()) {

                    if (Character.isUpperCase(temp.charAt(k))) {

                        cuc++;
                        k--;

                    }

                }

            }

        }

        for (int l = 0; l < temp.length(); l++) {

            if (Character.isLowerCase(temp.charAt(l))) {
                l++;

                if (l < temp.length()) {

                    if (Character.isLowerCase(temp.charAt(l))) {

                        clc++;
                        l--;

                    }

                }

            }

        }

        System.out.println("length" + length);
        System.out.println("uppercase" + uppercase);
        System.out.println("lowercase" + lowercase);
        System.out.println("digits" + digits);
        System.out.println("symbols" + symbols);
        System.out.println("bonus" + bonus);
        System.out.println("cuc" + cuc);
        System.out.println("clc" + clc);

        if (length > 7) {
            requirements++;
        }

        if (uppercase > 0) {
            requirements++;
        }

        if (lowercase > 0) {
            requirements++;
        }

        if (digits > 0) {
            requirements++;
        }

        if (symbols > 0) {
            requirements++;
        }

        if (bonus > 0) {
            requirements++;
        }

        if (digits == 0 && symbols == 0) {
            lettersonly = 1;
        }

        if (lowercase == 0 && uppercase == 0 && symbols == 0) {
            numbersonly = 1;
        }

        int Total = (length * 4) + ((length - uppercase) * 2)
                + ((length - lowercase) * 2) + (digits * 4) + (symbols * 6)
                + (bonus * 2) + (requirements * 2) - (lettersonly * length*2)
                - (numbersonly * length*3) - (cuc * 2) - (clc * 2);

        System.out.println("Total" + Total);

        if(Total<30){
            progressbar_password.setProgress(Total-70);
            text_password_strength.setText(R.string.register_progressbar_password_veryweak);
        }

        else if (Total>=40 && Total <50)
        {
            progressbar_password.setProgress(Total-50);
            text_password_strength.setText(R.string.register_progressbar_password_weak);
        }

        else if (Total>=56 && Total <70)
        {
            progressbar_password.setProgress(Total-25);
            text_password_strength.setText(R.string.register_progressbar_password_normal);
        }

        else if (Total>=76)
        {
            progressbar_password.setProgress(Total);
            text_password_strength.setText(R.string.register_progressbar_password_good);
        }
        else{
            progressbar_password.setProgress(Total-15);
            text_password_strength.setText(R.string.register_progressbar_password_verygood);
        }

    }
}

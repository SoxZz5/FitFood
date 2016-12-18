# FitFood

FitFood est une application communautaire de partage de recettes culinaires, FitFood permet:

  - Une recherche par régime alimentaire (Omnivore , Végétarien ou Vegan)
  - L'ajout de recette par un membre de la communauté
  - La création d'un compte personnel
  - L'ajout des ingrédients à votre liste de course
  - La navigation parmis plusieurs catégories :
  -- Recette du jour , la recette séléctionner par notre équipe du jour
  -- Dernières recettes , les dernières recettes actualiser en direct
  -- Top des recettes , les recettes les mieux notés !
 
Les fonctions à venir:
  - Pannel de préférences pour séléctionner les différents allérgenes
  - Algorithme de trie pour la gestion des ingrédients automatiquement
  - Ajout des allergénes à la recherche avancées

## Fitfood est sous licences CC-4.0
<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/creative1.png" alt="alt text" width="394" height="94">


## Fonctionnalité

### Login et Register via Firebase
[![Foo](https://firebase.google.com/_static/254aea64a1/images/firebase/lockup.png)](https://firebase.google.com/)

Dépendences gradle:
```sh 
dependencies {
     compile 'com.google.firebase:firebase-core:10.0.1'
     compile 'com.google.firebase:firebase-auth:10.0.1'
     compile 'com.google.firebase:firebase-database:10.0.1'
}
```
<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/login_layout.png" alt="alt text" width="216" height="384" hspace="15"><img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/register_layout.png" alt="alt text" width="216" height="384" hspace="15">

On commence par définir Firebase dans chaque script.
```sh
FirebaseAuth firebaseAuth;
FirebaseUser user; //Utiliser plus loin
firebaseAuth = FirebaseAuth.getInstance();
```

Ensuite on utilise un session manager afin de conserver l'email de l'utilisateur dans les préférences.
```sh
SessionManager sessionManager = new SessionManager();
if(sessionManager.getPreferences(LoginActivity.this,"mail_sign") != null)
{
    edit_mail.setText(sessionManager.getPreferences(LoginActivity.this,"mail_sign"));
    edit_password.requestFocus();
}
```

Si l'utilisateur arrive sur cette activité via Register il aura alors des extras dans son Bundle afin d'afficher un message de remerciement après l'inscription avec son email.
```sh
Intent intent_tmp = getIntent();
Bundle bundle_tmp = intent_tmp.getExtras();
    if(bundle_tmp != null)
    {
        String response_register = getString(R.string.login_text_after_register) + " " + bundle_tmp.getString("response");
        if(!response_register.isEmpty())
        {
            Toast toast = Toast.makeText(context, response_register, duration);
            toast.show();
        }
    }
```

Lors de l'appuye sur le bouton connexion on utilise une fonction propre à firebase:
- On envoye une requete de login à firebase dans le onComplete on récupére la réponse
```sh
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
            //Error
        }
    }
});
```

Une fonction est appeller le reste est très graphique ``sh LoginUser() ``
- Retourne la bonne Intent en fonction d'une variable de préférences afin de tester si c'est la première connexion de l'utilisateur suite à son enregistrement pour lui afficher l'activity AfterLogin.
```sh
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
```

Passons maintenant à Register.
Tous les editText comporte un TextChangeListener permettant de tester chaque input et ceux pour chaque editText de notre code exemple:
```sh
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
```
Le champ mot de passe est lui vérifier avec une fonction de calcul de puissance de mot de passe universel
<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/register_password.png" alt="alt text">
La fonction de test utilise une regex via une fonction appeler ``sh findMatch(String myString , String RegexPattern)`` qui retourne true or false:
```sh
String match = "";

Pattern regEx = Pattern.compile(pattern); //On compile le pattern

Matcher m = regEx.matcher(myString); //On match en fonction du pattern
    if (m.find()) {
        return true;
    }
    else
    {
        return false;
    }
```

Pour l'inscription tout comme pour la connexion on utilise une fonction bien spécifique à firebase
```sh
firebaseAuth.createUserWithEmailAndPassword(mail,password)
    .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                //ON VA A LOGIN
            }
            else
            {
                //ON AFFICHE UNE ERREUR
            }
        }
    });
```

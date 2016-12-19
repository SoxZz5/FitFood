# FitFood

FitFood est une application communautaire de partage de recettes culinaires, FitFood permet:

  - Une recherche par régime alimentaire (Omnivore , Végétarien ou Vegan)
  - L'ajout de recette par un membre de la communauté
  - La création d'un compte personnel
  - L'ajout des ingrédients à votre liste de course
  - La navigation parmis plusieurs catégories :
    - Recette du jour , la recette séléctionner par notre équipe du jour
    - Dernières recettes , les dernières recettes actualiser en direct
    - Top des recettes , les recettes les mieux notés !
 
Les fonctions à venir:
  - Pannel de préférences pour séléctionner les différents allérgenes
  - Algorithme de trie pour la gestion des ingrédients automatiquement
  - Ajout des allergénes à la recherche avancées
  
## Fitfood est sous licences CC-4.0
<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/creative1.png" alt="alt text" width="394" height="94">

Nous distribuons le code gratuitement afin qu'il puissent vous aidez à titre d'exemple pour vos projets android
### Attention à des fins de test pour l'ajout de recette il faudra modifier la ligne suivante dans utils/utils.java
```sh
public static boolean KEY_VALID_RECIPE = false; // False doit devenir true pour auto valider les recettes sans passer par l'administration
```

### Deuxième point pour que l'application fonctionne après lancement via ADB, vérifier d'avoir bien accepter les droits 
Fitfood nécessite un accès à la caméra et au stockage externe afin de pouvoir garder en mémoire les photos de vos recettes et à des fin de cache pour compression ensuite.
Internet est obligatoire, `il faut aussi avoir les googles services à jour !`
```sh
<uses-feature
        android:name="android.hardware.camera"
        android:required="true" />
    <uses-feature
        android:name="android.hardware.camera.any"
        android:required="true" />
    <uses-feature
        android:name="android.hardware.camera.autofocus"
        android:required="true"/>
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.FLASHLIGHT" />
```
    
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
if(sessionManager.getPreferences(LoginActivity.this,"mail_sign") != null){
    edit_mail.setText(sessionManager.getPreferences(LoginActivity.this,"mail_sign"));
    edit_password.requestFocus();
}
```

Si l'utilisateur arrive sur cette activité via Register il aura alors des extras dans son Bundle afin d'afficher un message de remerciement après l'inscription avec son email.
```sh
Intent intent_tmp = getIntent();
Bundle bundle_tmp = intent_tmp.getExtras();
    if(bundle_tmp != null){
        String response_register = getString(R.string.login_text_after_register) + " " + bundle_tmp.getString("response");
        if(!response_register.isEmpty()){
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
        if(task.isSuccessful()){
            Intent intent = LoginUser();
            startActivity(intent);
            //On termine l'affichage des progress etc
            finish();
        }
        else{
            //Error
        }
    }
});
```

Une fonction est appeller le reste est très graphique ``LoginUser() ``
- Retourne la bonne Intent en fonction d'une variable de préférences afin de tester si c'est la première connexion de l'utilisateur suite à son enregistrement pour lui afficher l'activity AfterLogin.
```sh
private Intent LoginUser(){
        user = firebaseAuth.getCurrentUser();
        Intent intent;
        String first_sign = sessionManager.getPreferences(LoginActivity.this,"first_sign");
        if(first_sign.equals("1")){
            intent = new Intent(LoginActivity.this, AfterLogin.class);
        }
        else{
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
    if(edit_mail.getText().length()<=0){
        //Erreur champ non remplie
    }
    else if(utils.findMatch(edit_mail.getText().toString(),"^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-z0-9.-]+$")){
        validat_form = true;
        //Afficher texte valide
    }
    else{
        validat_form = false;
        //Erreur dans l'editText
    }
}
```
Le champ mot de passe est lui vérifier avec une fonction de calcul de puissance de mot de passe universel

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/register_password.png" alt="alt text" hspace="15">

La fonction de test utilise une regex via ``findMatch(String myString , String RegexPattern)`` qui retourne true or false:
```sh
String match = "";
Pattern regEx = Pattern.compile(pattern); //On compile le pattern
Matcher m = regEx.matcher(myString); //On match en fonction du pattern
    if (m.find()) {
        return true;
    }
    else{
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
            else{
                //ON AFFICHE UNE ERREUR
            }
        }
    });
```

### AfterLogin via l'option database de firebase
[![](https://img.youtube.com/vi/U5aeM5dvUpA/0.jpg)](https://www.youtube.com/watch?v=U5aeM5dvUpA)
<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/afterlogin_layout.png" alt="alt text" width="216" height="384" hspace="15">

On va venir récupérer à notre habitude les variables de firebase en ajoutant une ``DatabaseReference ``

```sh
firebaseAuth = FirebaseAuth.getInstance();
databaseReference = FirebaseDatabase.getInstance().getReference();
sessionManager = new SessionManager();
user = firebaseAuth.getCurrentUser();
```

On va venir aussi récupérer les pseudo déjà présent dans notre base de données pour éviter la duplication

```sh
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
```

On utilise la même façon de faire via findMatch afin de tester les entrées des editText, puis à la validation nous testons alors si le pseudo est déjà présent dans la liste
```sh
if(valid_pseudo)
{
    pseudo = et_pseudo.getText().toString().trim();
    String pseudo_lowercase = pseudo.toLowerCase();
    Log.d("username","pseudo_lowercase = " + pseudo_lowercase);
    if(mUsername != null){
        for(int i = 0 ; i < mUsername.size(); i++){
            if(pseudo_lowercase.equals(mUsername.get(i).toString().toLowerCase())){
                isExist = true;
            }
        }
    }
    if(isExist){
        //On affiche un message
        validat_form=false;
        isExist=false;
    }
}
```

Suite à ça si toutes les informations sont valides et que le pseudo est disponible on appelle la fonction 
``saveUserInformation()``
```sh
public void saveUserInformation(){
    String email = user.getEmail().toString().trim();
    UserInformation userInformation = new UserInformation(name,surname,pseudo,diet,email);
    databaseReference.child("users").child(user.getUid()).setValue(userInformation);
    databaseReference.child("username").child(user.getUid()).setValue(pseudo);
    sessionManager.setPreferences(AfterLogin.this,"first_sign","0");
    //On affiche un message
}
```


<div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

Pour compiler l'application :
- Télécharger l'archive contenant le projet
- Ouvrir le dossier "FitFood_APP" avec votre environnement de développement.
- Compiler l'application

FitFood
=======
FitFood est une application communautaire de partage de recettes culinaires saines et savoureuses.

Elle permet :

  - Une recherche par régime alimentaire (Omnivore , Végétarien ou Vegan)
  - L'ajout d'une ou plusieurs recettes par un membre de la communauté
  - La création d'un compte personnel
  - L'ajout des ingrédients à votre liste de course en fonction des recettes sélectionnées
  - La navigation parmis trois catégories :
    - Recette du jour - La recette sélectionnée par notre équipe au jour le jour
    - Dernières recettes - Les dernières recettes ajoutées sur l'application
    - Top des recettes - Les recettes les mieux notées par la communauté
 
Les fonctionnalités futures :
  - Panel de préférences (permettra notamment d'ajouter les allergènes à éviter)
  - Algorithme de tri pour la gestion automatique des ingrédients
  - Ajout des allergènes à la recherche avancée
  
## FitFood est sous licence APACHE 2.0
```
Copyright 2016 Giffard Lucas "SoxZz5" / Cortella Nicolas "konidk"

Fitfood is licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

Nous distribuons le code gratuitement afin qu'il puisse vous aider (à titre d'exemple) pour vos projets Android.
### Attention ! À des fins de test, pour l'ajout d'une recette il faudra modifier la ligne suivante dans utils/utils.java
```sh
public static boolean KEY_VALID_RECIPE = false; // Passer le booléen "KEY_VALID_RECIPE" à "true" pour éviter la validation de la recette par le panel d'administration. Cette dernière apparaîtra alors directement dans la liste des recettes.
```

###Pour que l'application fonctionne après lancement via ADB, veuillez accorder manuellement les permissions nécessaires
FitFood demande deux permissions :
- Accès à la caméra : Nécessaire pour prendre en photo vos recettes et les partager ensuite
- Accès au stockage externe : Nécessaire pour enregistrer vos photos et stocker des informations en cache

Internet est obligatoire, `il faut posséder les services Google à jour.`

Voici les permissions requises par FitFood (issues du fichier **AndroidManifest.xml**) :
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
    
## Fonctionnalités

### Connexion et inscription via Firebase
[![Foo](https://firebase.google.com/_static/254aea64a1/images/firebase/lockup.png)](https://firebase.google.com/)

Dépendences **gradle**:
```sh 
dependencies {
     compile 'com.google.firebase:firebase-core:10.0.1'
     compile 'com.google.firebase:firebase-auth:10.0.1'
     compile 'com.google.firebase:firebase-database:10.0.1'
}
```

Interface de connexion :

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/login_layout.png" alt="alt text" width="216" height="384" hspace="15"><img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/register_layout.png" alt="alt text" width="216" height="384" hspace="15">

On initialise **Firebase** dans chaque activité :
```sh
FirebaseAuth firebaseAuth;
FirebaseUser user; //Utiliser plus loin
firebaseAuth = FirebaseAuth.getInstance();
```

Ensuite on utilise un **sessionManager** afin de conserver l'email de l'utilisateur dans les préférences.
```sh
SessionManager sessionManager = new SessionManager();
if(sessionManager.getPreferences(LoginActivity.this,"mail_sign") != null){
    edit_mail.setText(sessionManager.getPreferences(LoginActivity.this,"mail_sign"));
    edit_password.requestFocus();
}
```

Si l'utilisateur arrive sur cette activité via **Register** il aura alors des **extras** dans son **Bundle** afin d'afficher un message de remerciement (utilisation d'un **Toast**) après l'inscription avec son email.
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

Lors de l'appuie sur le bouton connexion on utilise une fonction propre à Firebase.
On envoie une requête de connexion à Firebase via la fonction ``onComplete()`` et on récupère la réponse :
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

Une fonction est ensuite appelée :  ``LoginUser()``
Elle retourne la bonne **Intent** en fonction d'une variable de préférence afin de tester si c'est la première connexion de l'utilisateur suite à son enregistrement. Si oui, on affichera l'activité **AfterLogin** qui permet de saisir plus d'informations (pseudo, nom, prénom, ...).
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

Passons maintenant à l'inscription (**Register**).
Tous les *editText* comportent un *TextChangeListener* permettant de tester chaque entrée.
En voici un exemple : 
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
Le champ mot de passe est vérifié avec une fonction de calcul de niveau de sécurité universelle.

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/register_password.png" alt="alt text" hspace="15">

La fonction de test utilise une regex via ``findMatch(String myString , String RegexPattern)`` qui retourne *true* or *false*:
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

Pour l'inscription tout comme pour la connexion on utilise une fonction spécifique à Firebase :
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

Interface permettant de saisir plus d'informations sur l'utilisateur (après sa première connexion) :

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/afterlogin_layout.png" alt="alt text" width="216" height="384" hspace="15">

Comme à notre habitude, on va récupérer les variables de Firebase en ajoutant une ``DatabaseReference `` :

```sh
firebaseAuth = FirebaseAuth.getInstance();
databaseReference = FirebaseDatabase.getInstance().getReference();
sessionManager = new SessionManager();
user = firebaseAuth.getCurrentUser();
```

On récupère également les pseudos déjà présents dans notre base de données pour éviter la duplication :

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

On utilise la même façon de faire via ``findMatch()`` afin de tester les entrées des *editText*, puis à la validation nous testons alors si le pseudo est déjà présent dans la liste :
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

Si toutes les informations sont valides et que le pseudo est disponible, on appelle la fonction  ``saveUserInformation()``
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

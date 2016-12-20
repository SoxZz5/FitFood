Pour compiler l'application :
- Télécharger l'archive contenant le projet
- Ouvrir le dossier "FitFood_APP" avec votre environnement de développement
- Compiler l'application

![Logo FitFood Officiel](http://nicolascortella.fr/projects/GitHub/FitFood/fitfoodlogosmall-x1.png)

#**FitFood** - Des recettes saines qui en valent la peine.
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
    
# Fonctionnalités

## 1. Connexion et inscription via Firebase
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

## 2. AfterLogin via l'option database de firebase
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

## 3. Le menu

Il permet à l'utilisateur de se déplacer à travers les différentes interfaces proposées par l'application.
On y retrouve dans l'ordre :

- Accueil : Liste de recettes en vrac en fonction des préférences de l'utilisateur
- Coin cuisine : Cet espace permet de découvrir des recettes, d'effectuer des recherches sur ces recettes
- Coin perso : Espace personnel de l'utilisateur dans lequel il retrouve ses recettes ainsi que toutes les informations relatives à son compte
- Se déconnecter : Permet la déconnexion de l'utilisateur

Menu :

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/menu.png" alt="alt text" width="216" height="384" hspace="15">

## 4. L'accueil et la visualisation des recettes


<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/accueil.png" alt="alt text" width="216" height="384" hspace="15">

La liste de recettes afichée sur la page d'accueil décrit pour chaque recette :

- Son titre
- Son régime alimentaire associé
- Son type
- Sa difficulté
- Ses portions
- Ses temps de préparations et de cuisson

Il est alors possible de cliquer sur n'importe quelle recette et d'accéder à ses détails :

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/pudding.png" alt="alt text" width="216" height="384" hspace="15">


## 5. Proposer une recette

N'importe quel utilisateur inscrit et connecté peut proposer une recette. Celle-ci doit faire l'objet d'une validation par un administrateur.

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/step0.png" alt="alt text" width="216" height="384" hspace="15">

La proposition d'une recette se fait en 5 étapes. Chaque étape possède des vérifications de champs spécifiques de tel sorte que l'utilisateur ne puisse pas saisir n'importe quoi (exemple: des caractères spéciaux dans le titre de la recette).

#### **1. Définir l'identité de la recette**

L'utilisateur est amené à saisir le titre de la recette ainsi que sa catégorie (c'est-à-dire le régime alimentaire ciblé).

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/step1.png" alt="alt text" width="216" height="384" hspace="15">


#### **2. Renseigner le type de plat et sa difficulté**

L'utilisateur est amené à saisir le type de plat (entrée, plat principal, ...) ainsi que la maîtrise nécessaire pour réaliser la recette.

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/step2.png" alt="alt text" width="216" height="384" hspace="15">

#### **3. Portions, temps de préparation et de cuisson**

Cette étape permet de déterminer pour combien de personnes la recette est destinée. De plus, les temps de préparation et de cuisson de la recette sont requis.

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/step3.png" alt="alt text" width="216" height="384" hspace="15">

#### **4. Ingrédients nécessaires**

Les ingrédients nécessaires à la recette sont renseignés dans cette étape. On spécifie la quantité pour chaque ingrédient et on l'ajoute ensuite à la liste.

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/step4.png" alt="alt text" width="216" height="384" hspace="15">

#### **5. Étapes de préparation**

La préparation nécessite également des étapes. C'est à travers cet interface qu'elles doivent être spécifiées.

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/step5.png" alt="alt text" width="216" height="384" hspace="15">

#### **6. Photo et validation de la recette**

Enfin, l'utilisateur peut poster une photo de sa recette pour ensuite la partager à la communauté. Une fois la recette soumise, elle est automatiquement ajoutée à la liste d'attente des recettes à valider par les administrateurs.

<img src="https://raw.githubusercontent.com/SoxZz5/FitFood/master/image_readme/step6.png" alt="alt text" width="216" height="384" hspace="15">

La suite des fonctionnalités n'est pas encore expliquée ici (recherche avancée, top des recettes, mon compte, ...).
N'hésitez pas à télécharger l'application pour accéder à l'ensemble de ses fonctionnalités.

<img src="http://nicolascortella.fr/projects/GitHub/FitFood/fitfoodtextsmall.png" alt="alt text" width="216" height="55" hspace="15">

<div>Some icons contained in this app have been made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="http://www.flaticon.com" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

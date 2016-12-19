package konid.soxzz5.fitfood.fitfood_fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.firebase_fitfood.Recipe;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Ingredient;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.PrepStep;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

/**
 * Created by CORTELLA and GIFFARD on 18/12/2016.
 */

public class SingleRecipeDisplay extends Fragment {

    private DatabaseReference databaseReference;
    FirebaseStorage storage;
    private TextView ingredients_text;
    private TextView steps_text;
    private TextView recipeTitle;
    private TextView recipeAuthor;
    private TextView recipe_info_tag;
    private TextView recipe_info_date;
    private TextView recipe_info_level;
    private TextView recipe_info_forwho;
    private TextView recipe_tv_heathour;
    private TextView recipe_tv_heatminute;
    private TextView recipe_tv_prephour;
    private TextView recipe_tv_prepminute;
    private TextView recipe_tv_finalhour;
    private TextView recipe_tv_finalminute;
    private TextView info_typedish;
    private ImageView recipe_imageView;
    private ProgressBar recipe_img_bar;
    private Context context;
    private String recipeID;
    private int recipeId = 0;
    Recipe loadedRecipe = new Recipe();
    String name;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = getActivity();
        //DO SOMETHING WITH
        View v = inflater.inflate(R.layout.recipe_display_full_info, container, false);
        ingredients_text = (TextView) v.findViewById(R.id.ingredients_text);
        steps_text = (TextView) v.findViewById(R.id.steps_text);
        recipeTitle = (TextView) v.findViewById(R.id.recip_title);
        //recipeAuthor = (TextView) v.findViewById(R.id.recipe_author);
        recipe_info_tag = (TextView) v.findViewById(R.id.recipe_info_tag);
        recipe_info_forwho = (TextView) v.findViewById(R.id.recipe_info_forwho);
        recipe_tv_heathour = (TextView) v.findViewById(R.id.recipe_tv_heathour);
        recipe_tv_heatminute = (TextView) v.findViewById(R.id.recipe_tv_heatminute);
        recipe_tv_prephour = (TextView) v.findViewById(R.id.recipe_tv_prephour);
        recipe_tv_prepminute = (TextView) v.findViewById(R.id.recipe_tv_prepminute);
        recipe_tv_finalhour = (TextView) v.findViewById(R.id.recipe_tv_finalhour);
        recipe_tv_finalminute = (TextView) v.findViewById(R.id.recipe_tv_finalminute);
        info_typedish = (TextView) v.findViewById(R.id.info_typedish);
        recipe_info_level = (TextView) v.findViewById(R.id.recipe_info_level);
        recipe_info_date = (TextView) v.findViewById(R.id.recipe_info_date);
        recipe_img_bar = (ProgressBar) v.findViewById(R.id.recipe_img_pb);
        recipe_imageView = (ImageView) v.findViewById(R.id.recipe_imageView);

        storage = FirebaseStorage.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("recipe").child(this.recipeID);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("rvalidate").getValue()!=null) {
                    loadedRecipe.setRtitle(String.valueOf(dataSnapshot.child("rtitle").getValue()));
                    loadedRecipe.setRforWho(String.valueOf(dataSnapshot.child("rforWho").getValue()));
                    loadedRecipe.setURL(String.valueOf(dataSnapshot.child("rrecipe_download_img_link").getValue()));
                    loadedRecipe.setRdate((String.valueOf(dataSnapshot.child("rdate").getValue())).substring(0,10));
                    long Rlevel = (Long.parseLong(String.valueOf(dataSnapshot.child("rlevel").getValue())));
                    long Rtypedish = (Long.parseLong(String.valueOf(dataSnapshot.child("rtype").getValue())));
                    long Rcategorie = (Long.parseLong(String.valueOf(dataSnapshot.child("rcategory").getValue())));
                    long Rheathour = (Long.parseLong(String.valueOf(dataSnapshot.child("rheatHour").getValue())));
                    long Rheatminute = (Long.parseLong(String.valueOf(dataSnapshot.child("rheatMinute").getValue())));
                    long Rprephour = (Long.parseLong(String.valueOf(dataSnapshot.child("rprepareHour").getValue())));
                    long Rprepminute = (Long.parseLong(String.valueOf(dataSnapshot.child("rprepareMinute").getValue())));
                    System.out.println("URL:" + String.valueOf(dataSnapshot.child("rrecipe_download_img_link").getValue()));

                    //Récupération de la liste des ingrédients
                    GenericTypeIndicator<List<Ingredient>> temp = new GenericTypeIndicator<List<Ingredient>>() {};
                    List<Ingredient> allIngredients = dataSnapshot.child("ringredients").getValue(temp);

                    String ingredientsString = "";
                    int i=0;
                    for(i=0;i<allIngredients.size();i++) {
                        ingredientsString += (i+1) + ") " + allIngredients.get(i).getName() + " " + allIngredients.get(i).getQuantity();
                        if(i!=allIngredients.size()-1) ingredientsString += "\n\n";
                    }
                    ingredients_text.setText(ingredientsString);

                    //Récupération de la liste des étapes
                    GenericTypeIndicator<List<PrepStep>> temp2 = new GenericTypeIndicator<List<PrepStep>>() {};
                    List<PrepStep> allSteps = dataSnapshot.child("rsteps").getValue(temp2);

                    System.out.println("LISTE: " + allSteps.get(0).getName());

                    String stepsString = "";
                    for(i=0;i<allSteps.size();i++) {
                        stepsString += (i+1) + ") " + allSteps.get(i).getName();
                        if(i!=allSteps.size()-1) stepsString += "\n\n";
                    }
                    steps_text.setText(stepsString);

                    //Type de plat
                    switch ((int)(long)(Rtypedish))
                    {
                        case 1:
                            info_typedish.setText(R.string.step2_rb_starter);
                            break;
                        case 2:
                            info_typedish.setText(R.string.step2_rb_maincourse);
                            break;
                        case 3:
                            info_typedish.setText(R.string.step2_rb_dessert);
                            break;
                        case 4:
                            info_typedish.setText(R.string.step2_rb_sidedish);
                            break;
                        case 5:
                            info_typedish.setText(R.string.step2_rb_appetizer);
                            break;
                        case 6:
                            info_typedish.setText(R.string.step2_rb_drink);
                            break;
                        case 7:
                            info_typedish.setText(R.string.step2_rb_sweets);
                            break;
                        case 8:
                            info_typedish.setText(R.string.step2_rb_sauce);
                            break;
                    }

                    //Omni/Vege/Vegan
                    switch ((int)(long)(Rcategorie))
                    {
                        case 1:
                            recipe_info_tag.setText(R.string.step1_rb_omnivore);
                            break;
                        case 2:
                            recipe_info_tag.setText(R.string.step1_rb_vegetarien);
                            break;
                        case 3:
                            recipe_info_tag.setText(R.string.step1_rb_vegetalien);
                            break;
                    }

                    //Niveau de difficulté
                    switch ((int)(long)Rlevel)
                    {
                        case 1:
                            recipe_info_level.setText(R.string.step2_tv_info_0);
                            recipe_info_level.setTextColor(ContextCompat.getColor(context,R.color.FromGreenToRed1));
                            break;
                        case 2:
                            recipe_info_level.setText(R.string.step2_tv_info_1);
                            recipe_info_level.setTextColor(ContextCompat.getColor(context,R.color.FromGreenToRed2));
                            break;
                        case 3:
                            recipe_info_level.setText(R.string.step2_tv_info_2);
                            recipe_info_level.setTextColor(ContextCompat.getColor(context,R.color.FromGreenToRed3));
                            break;
                        case 4:
                            recipe_info_level.setText(R.string.step2_tv_info_3);
                            recipe_info_level.setTextColor(ContextCompat.getColor(context,R.color.FromGreenToRed4));
                            break;
                        case 5:
                            recipe_info_level.setText(R.string.step2_tv_info_4);
                            recipe_info_level.setTextColor(ContextCompat.getColor(context,R.color.FromGreenToRed5));
                            break;
                    }

                    //Temps de préparation
                    Calendar finalTime = Calendar.getInstance();
                    finalTime.set(Calendar.HOUR_OF_DAY,(int)(long)Rprephour);
                    finalTime.set(Calendar.MINUTE,(int)(long)Rprepminute);
                    finalTime.add(Calendar.HOUR_OF_DAY,(int)(long)Rheathour);
                    finalTime.add(Calendar.MINUTE,(int)(long)Rheatminute);
                    //Date
                    recipe_info_date.setText(loadedRecipe.getRdate());
                    //TITRE
                    final Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                    toolbar.setTitle(loadedRecipe.getRtitle());

                    //Pour combien de personnes
                    recipe_info_forwho.setText(loadedRecipe.getRforWho());
                    //Heure de cuisson
                    recipe_tv_heathour.setText(String.valueOf(Rheathour));
                    //Minutes de cuisson
                    recipe_tv_heatminute.setText(String.valueOf(Rheatminute));
                    //Heures de préparation
                    recipe_tv_prephour.setText(String.valueOf(Rprephour));
                    //Minutes de préparation
                    recipe_tv_prepminute.setText(String.valueOf(Rprepminute));
                    //Heures au total
                    recipe_tv_finalhour.setText(String.valueOf(finalTime.get(Calendar.HOUR_OF_DAY)));
                    //Minuts au total
                    recipe_tv_finalminute.setText(String.valueOf(finalTime.get(Calendar.MINUTE)));
                    System.out.println("FDPFDP2: " + loadedRecipe.getURL());
                    Picasso.with(context).setLoggingEnabled(true);
                    Picasso.with(context)
                            .load(loadedRecipe.getURL())
                            .fit()
                            .centerCrop()
                            .into(recipe_imageView, new Callback() {
                                @Override
                                public void onSuccess() {
                                    recipe_img_bar.setVisibility(View.GONE);
                                }
                                @Override
                                public void onError() {
                                }
                            });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("ERROR");
            }
        });


        return v;
    }

    public String getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(String recipeID) {
        this.recipeID = recipeID;
    }

}

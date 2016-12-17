package konid.soxzz5.fitfood;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

import java.util.List;

import konid.soxzz5.fitfood.firebase_fitfood.Recipe;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Ingredient;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.PrepStep;
import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_final;
import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_step1;
import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_step2;
import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_step3;
import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_step4;
import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_step5;


/**
 * Created by Soxzer on 08/12/2016.
 */

public class AddRecipe extends AppCompatActivity {
    int step;
    String sTitle;
    int iCategory;
    int iLevel;
    int iType;
    int iPrepareHour;
    int iPrepareMinute;
    int iHeatHour;
    int iHeatMinute;
    String sForWho;
    String sWho;
    int iNbWho;
    List<Ingredient> alIngredients;
    List<PrepStep> alSteps;

    int nbIngredients;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    StorageReference firebaseStorage;

    ProgressDialog mProgressDialog;

    boolean validate_upload = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.addrecipe_wizard);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);
        //STEPINFO POUR SAVOIR A QUELLE ETAPE ON EST
        final FrameLayout stepinfo1 = (FrameLayout) findViewById(R.id.stepinfo1);
        final FrameLayout stepinfo2 = (FrameLayout) findViewById(R.id.stepinfo2);
        final FrameLayout stepinfo3 = (FrameLayout) findViewById(R.id.stepinfo3);
        final FrameLayout stepinfo4 = (FrameLayout) findViewById(R.id.stepinfo4);
        final FrameLayout stepinfo5 = (FrameLayout) findViewById(R.id.stepinfo5);
        final TextView info_error = (TextView) findViewById(R.id.recipeadd_info_error);
        final LinearLayout layout_steper = (LinearLayout) findViewById(R.id.step_layout_steper);
        final LinearLayout layout_title = (LinearLayout) findViewById(R.id.step_layout_title);
        final LinearLayout step_container = (LinearLayout) findViewById(R.id.step_container);
        //final ImageView backgroundWizard = (ImageView) findViewById(R.id.backgroundWizard);

        final TextView title = (TextView) findViewById(R.id.recipeadd_wizard_title);
        final Button next = (Button) findViewById(R.id.wizard_bt_next);
        final Button prev = (Button) findViewById(R.id.wizard_bt_prev);
        final Button valid_final = (Button) findViewById(R.id.wizard_bt_final);

        step = 1;

        final addrecipe_step1 step1 = new addrecipe_step1();
        final addrecipe_step2 step2 = new addrecipe_step2();
        final addrecipe_step3 step3 = new addrecipe_step3();
        final addrecipe_step4 step4 = new addrecipe_step4();
        final addrecipe_step5 step5 = new addrecipe_step5();
        final addrecipe_final step_final= new addrecipe_final();

        if(step == 1)
        {
            title.setText(R.string.step1_title);
            Bundle args = new Bundle();
            step1.setArguments(args);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.step_container, step1);
            transaction.commit();
        }

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction;
                switch(step)
                {
                    case 1:
                        finish();
                        break;
                    case 2:
                        info_error.setText("");
                        transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, 0, 0);
                        transaction.replace(R.id.step_container, step1);
                        step = 1;
                        title.setText(R.string.step1_title);
                        stepinfo2.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo1.setBackgroundResource(R.color.colorAccent);
                        transaction.commit();
                        step1.setCategory(iCategory);
                        step1.setTitle(sTitle);
                        //backgroundWizard.setImageResource(R.drawable.recipeadd_step_background);
                        break;
                    case 3:
                        //backgroundWizard.setImageResource(R.drawable.recipeadd_step3_background);
                        info_error.setText("");
                        transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, 0, 0);
                        transaction.replace(R.id.step_container, step2);
                        step = 2;
                        title.setText(R.string.step2_title);
                        stepinfo3.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo2.setBackgroundResource(R.color.colorAccent);
                        transaction.commit();
                        step2.setLevel(iLevel);
                        step2.setType(iType);
                        //backgroundWizard.setImageResource(R.drawable.recipeadd_step2_background);
                        break;
                    case 4:
                        info_error.setText("");
                        transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, 0, 0);
                        transaction.replace(R.id.step_container, step3);
                        step = 3;
                        title.setText(R.string.step3_title);
                        stepinfo4.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo3.setBackgroundResource(R.color.colorAccent);
                        transaction.commit();
                        step3.setHeathour(iHeatHour);
                        step3.setHeatminute(iHeatMinute);
                        step3.setPrepminute(iPrepareMinute);
                        step3.setPrephour(iPrepareHour);
                        step3.setForwho(sWho);
                        step3.setNbwho(iNbWho);
                        //backgroundWizard.setImageResource(R.drawable.recipeadd_step3_background);
                        break;
                    case 5:
                        info_error.setText("");
                        transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.animator.slide_in_right, R.animator.slide_out_left, 0, 0);
                        transaction.replace(R.id.step_container, step4);
                        step=4;
                        title.setText(R.string.step4_title);
                        stepinfo5.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo4.setBackgroundResource(R.color.colorAccent);
                        transaction.commit();
                        break;
                    case 6:
                        layout_title.setVisibility(View.VISIBLE);
                        next.setVisibility(View.VISIBLE);
                        valid_final.setVisibility(View.GONE);
                        stepinfo1.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo2.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo3.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo4.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo5.setBackgroundResource(R.color.colorAccent);
                        break;
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info_error.setText("");
                switch (step) {
                    case 1:
                    boolean validate_title = false;
                    boolean validate_category = false;

                    if (step1.getTitle() != "ERROR_TITLE")
                    {
                        sTitle=step1.getTitle();
                        validate_title = true;
                    }
                    else
                    {
                        info_error.setText(getString(R.string.step_error_title));
                    }

                    if (step1.getCategory() != -1) {
                        iCategory = step1.getCategory();
                        validate_category = true;
                    }
                    else
                    {
                        info_error.setText(getString(R.string.step_error_categorie));
                    }

                    if (validate_category && validate_title)
                    {
                        //backgroundWizard.setImageResource(R.drawable.recipeadd_step2_background);
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                        transaction.replace(R.id.step_container, step2);
                        step = 2;
                        title.setText(R.string.step2_title);
                        stepinfo1.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo2.setBackgroundResource(R.color.colorAccent);
                        transaction.commit();
                    }
                        break;

                    case 2:
                        boolean validate_type = false;
                        boolean validate_difficulty = false;

                        if(step2.getLevel() != -1)
                        {
                            iLevel = step2.getLevel();
                            validate_difficulty = true;
                        }
                        else
                        {
                            info_error.setText(getString(R.string.step_error_difficulty));
                        }
                        if(step2.getType() != -1)
                        {
                            iType = step2.getType();
                            validate_type = true;
                        }
                        else
                        {
                            info_error.setText(getString(R.string.step_error_type));
                        }

                        if(validate_type && validate_difficulty)
                        {
                            //backgroundWizard.setImageResource(R.drawable.recipeadd_step3_background);
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                            transaction.replace(R.id.step_container, step3);
                            step = 3;
                            title.setText(R.string.step3_title);
                            stepinfo2.setBackgroundResource(R.color.material_drawer_dark_selected);
                            stepinfo3.setBackgroundResource(R.color.colorAccent);
                            transaction.commit();
                        }
                        break;

                    case 3:
                        boolean validate_prepare = false;
                        boolean validate_heat = false;
                        boolean validate_forwho = false;

                        if(step3.getHeatminute() != -1)
                        {
                            iHeatHour = 0;
                            iHeatMinute = step3.getHeatminute();
                            if(step3.getHeathour() != -1)
                            {
                                iHeatHour = step3.getHeathour();
                            }
                            validate_heat = true;
                        }
                        else
                        {
                            info_error.setText(getString(R.string.step_error_heat));
                        }
                        if(step3.getPrepminute() != -1)
                        {
                            iPrepareHour = 0;
                            iPrepareMinute = step3.getPrepminute();
                            if(step3.getPrephour() != -1)
                            {
                                iPrepareHour = step3.getPrephour();
                            }
                            validate_prepare = true;
                        }
                        else
                        {
                            info_error.setText(getString(R.string.step_error_prepare));
                        }
                        if(step3.getNbwho() != -1 && step3.getForwho() != "ERROR_FORWHO")
                        {
                            sForWho = Integer.toString(step3.getNbwho()) +" "+ step3.getForwho();
                            sWho = step3.getForwho();
                            iNbWho = step3.getNbwho();
                            validate_forwho = true;
                        }
                        else
                        {
                            info_error.setText(getString(R.string.step_error_forwho));
                        }
                        if(validate_prepare && validate_heat && validate_forwho)
                        {
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                            transaction.replace(R.id.step_container, step4);
                            step = 4;
                            title.setText(R.string.step4_title);
                            stepinfo3.setBackgroundResource(R.color.material_drawer_dark_selected);
                            stepinfo4.setBackgroundResource(R.color.colorAccent);
                            transaction.commit();
                        }
                        break;

                    case 4:
                        boolean validate_ingredient=false;
                        alIngredients = step4.getIngredients();
                        nbIngredients = step4.getNbIngredient();
                        if(alIngredients != null)
                        {
                            validate_ingredient = true;
                        }

                        if(validate_ingredient)
                        {
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                            transaction.replace(R.id.step_container, step5);
                            step = 5;
                            title.setText(R.string.step5_title);
                            stepinfo4.setBackgroundResource(R.color.material_drawer_dark_selected);
                            stepinfo5.setBackgroundResource(R.color.colorAccent);
                            transaction.commit();
                        }
                        else
                        {
                            info_error.setText(getString(R.string.step_error_ingredients));
                        }

                        break;
                    case 5:
                        boolean validate_steps =false;
                        alSteps = step5.getPrepSteps();
                        if(alSteps != null)
                        {
                            validate_steps = true;
                        }

                        if(validate_steps)
                        {
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, 0, 0);
                            transaction.replace(R.id.step_container, step_final);
                            step = 6;
                            next.setVisibility(View.GONE);
                            //layout_steper.setVisibility(View.GONE);
                            //prev.setVisibility(View.GONE);
                            stepinfo1.setBackgroundResource(R.color.colorAccent);
                            stepinfo2.setBackgroundResource(R.color.colorAccent);
                            stepinfo3.setBackgroundResource(R.color.colorAccent);
                            stepinfo4.setBackgroundResource(R.color.colorAccent);
                            stepinfo5.setBackgroundResource(R.color.colorAccent);
                            layout_title.setVisibility(View.GONE);
                            valid_final.setVisibility(View.VISIBLE);
                            transaction.commit();
                        }
                        break;
                }

            }
        });

        /*step=4;
        next.callOnClick();
        backgroundWizard.setImageResource(R.drawable.recipeadd_step3_background);*/

        valid_final.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(step == 6)
                {
                    if(step_final.getBitmap() != null)
                    {
                        mProgressDialog.setMessage("Uploading ...");
                        mProgressDialog.show();
                        StorageReference new_filepath = firebaseStorage.child("Recipe_Image").child(firebaseUser.getUid()).child(step_final.getFilePath().getLastPathSegment());
                        new_filepath.putFile(step_final.getFilePath()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                mProgressDialog.hide();
                                mProgressDialog.setMessage("Sending recipe image ...");
                                mProgressDialog.hide();
                                validate_upload=true;
                            }
                        });

                        DatabaseReference recipe_ref = databaseReference.child("recipe");
                        DatabaseReference user_recipe_ref = databaseReference.child("user_recipe");
                        DatabaseReference recipe_image_ref = databaseReference.child("recipe_image").push();
                        mProgressDialog.setMessage("Sending recipe image ...");
                        mProgressDialog.show();
                        recipe_image_ref.setValue(new_filepath.getPath());
                        mProgressDialog.hide();
                        mProgressDialog.setMessage("Sending user to recipe ...");
                        mProgressDialog.show();
                        String newKey = recipe_image_ref.getKey().toString();
                        user_recipe_ref.child(newKey).setValue(firebaseUser.getUid());
                        mProgressDialog.hide();
                        mProgressDialog.setMessage("Sending recipe ...");
                        mProgressDialog.show();
                        Recipe recipe = new Recipe(sTitle, iCategory, iLevel, iType, iPrepareHour, iPrepareMinute, iHeatHour, iHeatMinute, sForWho, alIngredients, alSteps);
                        recipe_ref.child(newKey).setValue(recipe);
                        recipe_ref.child(newKey).child("validate").setValue(false);
                        mProgressDialog.hide();
                        Toast.makeText(AddRecipe.this,"Recette ajouter à la BDD",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddRecipe.this,MainActivity.class);
                        startActivity(intent);
                    }
                    }
                }
            });
    }
}

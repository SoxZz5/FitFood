package konid.soxzz5.fitfood;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Ingredient;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Item;
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
    List<Ingredient> alIngrendients;
    List<Item> alSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //ON ENLEVE LA STATUS BAR

        setContentView(R.layout.addrecipe_wizard);

        //STEPINFO POUR SAVOIR A QUELLE ETAPE ON EST
        final FrameLayout stepinfo1 = (FrameLayout) findViewById(R.id.stepinfo1);
        final FrameLayout stepinfo2 = (FrameLayout) findViewById(R.id.stepinfo2);
        final FrameLayout stepinfo3 = (FrameLayout) findViewById(R.id.stepinfo3);
        final FrameLayout stepinfo4 = (FrameLayout) findViewById(R.id.stepinfo4);
        final FrameLayout stepinfo5 = (FrameLayout) findViewById(R.id.stepinfo5);
        final TextView info_error = (TextView) findViewById(R.id.recipeadd_info_error);

        final TextView title = (TextView) findViewById(R.id.recipeadd_wizard_title);
        Button next = (Button) findViewById(R.id.wizard_bt_next);
        Button prev = (Button) findViewById(R.id.wizard_bt_prev);

        step = 1;

        final addrecipe_step1 step1 = new addrecipe_step1();
        final addrecipe_step2 step2 = new addrecipe_step2();
        final addrecipe_step3 step3 = new addrecipe_step3();
        final addrecipe_step4 step4 = new addrecipe_step4();
        final addrecipe_step5 step5 = new addrecipe_step5();

        if(step == 1)
        {
            title.setText(R.string.step1_title);
            Bundle args = new Bundle();
            step1.setArguments(args);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.step_container, step1);
                        step = 1;
                        title.setText(R.string.step1_title);
                        stepinfo2.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo1.setBackgroundResource(R.color.colorAccent);
                        transaction.commit();
                        step1.setCategory(iCategory);
                        step1.setTitle(sTitle);
                        break;
                    case 3:
                        info_error.setText("");
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.step_container, step2);
                        step = 2;
                        title.setText(R.string.step2_title);
                        stepinfo3.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo2.setBackgroundResource(R.color.colorAccent);
                        transaction.commit();
                        step2.setLevel(iLevel);
                        step2.setType(iType);
                        break;
                    case 4:
                        info_error.setText("");
                        transaction = getSupportFragmentManager().beginTransaction();
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
                        break;
                    case 5:
                        info_error.setText("");
                        transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.step_container, step4);
                        step=4;
                        title.setText(R.string.step4_title);
                        stepinfo5.setBackgroundResource(R.color.material_drawer_dark_selected);
                        stepinfo4.setBackgroundResource(R.color.colorAccent);
                        transaction.commit();
                        step4.setIngredients(alIngrendients);
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
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                        alIngrendients = step4.getIngredients();
                        if(alIngrendients != null)
                        {
                            validate_ingredient = true;
                        }

                        if(validate_ingredient)
                        {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
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
                        alSteps = step5.getItems();
                        if(alSteps != null)
                        {
                            validate_steps = true;
                        }

                        if(validate_steps)
                        {
                        //TODO UPLOAD IMAGE TO SERVER SEE ADDRECIPE_WIZARD_FINAL
                        }
                        break;
                }

            }
        });

    }
}

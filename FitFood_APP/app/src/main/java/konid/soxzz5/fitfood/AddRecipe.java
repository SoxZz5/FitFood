package konid.soxzz5.fitfood;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_step1;
import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_step2;
import konid.soxzz5.fitfood.fitfood_addrecipe_step.addrecipe_step3;
import konid.soxzz5.fitfood.fitfood_fragment.AddRecipeFragment;

/**
 * Created by Soxzer on 08/12/2016.
 */

public class AddRecipe extends AppCompatActivity {
    int step;
    String sTitle;
    int iCategory;
    int iLevel;
    int iType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ON ENLEVE LA STATUS BAR
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                        break;
                }

            }
        });

    }
}

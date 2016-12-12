package konid.soxzz5.fitfood.fitfood_addrecipe_step;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.utils.utils;

/**
 * Created by Soxzer on 09/12/2016.
 */

public class addrecipe_step1 extends Fragment {
    int category;
    String title;
    boolean title_changed;
    RadioGroup rg_categorie;
    EditText et_title;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //DO SOMETHING WITH
        final View v = inflater.inflate(R.layout.addrecipe_step1, container, false);
        et_title = (EditText) v.findViewById(R.id.step1_et_title);
        rg_categorie = (RadioGroup) v.findViewById(R.id.step1_rg_categorie);
        category = -1;
        title_changed = false;

        et_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(utils.findMatch(et_title.getText().toString(),"^[A-Z][\\s\\w]{10,50}$"))
                {
                    title=et_title.getText().toString();
                    title_changed=true;
                }
                else
                {
                    title_changed=false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        rg_categorie.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i)
                {
                    case R.id.step1_rb_vegetarien:
                        category=1;
                        break;
                    case R.id.step1_rb_vegetalien:
                        category=2;
                        break;
                    case R.id.step1_rb_omnivore:
                        category=3;
                        break;
                }
            }
        });


        return v;


    }

    public String getTitle()
    {
        if(title_changed) {
            return title;
        }
        else
        {
            return "ERROR_TITLE";
        }
    }

    public int getCategory() {
            return category;
    }

    public void setCategory(int category) {
        this.category = category;
        rg_categorie.check(category);
    }

    public void setTitle(String title) {
        this.title = title;
        et_title.setText(title);
    }
}

package konid.soxzz5.fitfood.fitfood_addrecipe_step;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.utils.utils;

/**
 * Created by Soxzer on 12/12/2016.
 */

public class addrecipe_step4 extends Fragment {
    ArrayList<ArrayList<String>> quantity_ingredients;
    ArrayList<String> ingredient;
    boolean valid_ingredient;
    boolean valid_quantity;
    int nb_ingredient;
    boolean firstingredient;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_step4, container, false);


        final LinearLayout layout_ingredient = (LinearLayout) v.findViewById(R.id.step4_layout_ingredient);
        final EditText et_quantity = (EditText) v.findViewById(R.id.step4_et_quantity);
        final EditText et_ingredient = (EditText) v.findViewById(R.id.step4_et_ingredient);
        ImageView bt_add = (ImageView) v.findViewById(R.id.step4_bt_add);

        quantity_ingredients = new ArrayList();
        ingredient = new ArrayList();
        valid_ingredient=false;
        valid_quantity=false;
        nb_ingredient=0;
        firstingredient=false;

        et_ingredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(utils.findMatch(et_ingredient.getText().toString(),"^[\\s\\w]{4,30}$"))
                {
                    valid_ingredient = true;
                }
            }
        });

        et_quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(utils.findMatch(et_quantity.getText().toString(),"^[0-9]*[\\s\\w]{4,30}$"))
                {
                    valid_quantity = true;
                }
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid_quantity && valid_ingredient)
                {
                    if(!firstingredient)
                    {
                        firstingredient=true;
                        layout_ingredient.setVisibility(View.VISIBLE);
                    }
                    ingredient.add(et_ingredient.getText().toString());
                    ingredient.add(et_quantity.getText().toString());
                    quantity_ingredients.add(nb_ingredient,ingredient);
                    String tvString;
                    if(utils.findMatch(quantity_ingredients.get(nb_ingredient).get(1),"^[0-9]{0,2}$")) {
                        tvString =quantity_ingredients.get(nb_ingredient).get(1) +" "+ quantity_ingredients.get(nb_ingredient).get(0);
                    }
                    else
                    {
                        tvString =quantity_ingredients.get(nb_ingredient).get(1) +" de "+ quantity_ingredients.get(nb_ingredient).get(0);
                    }

                    TextView new_ingredient = addText(tvString);
                    LinearLayout horizontal_container = addLayout();
                    ImageView image_delete = addDelete();
                    horizontal_container.addView(new_ingredient);
                    horizontal_container.addView(image_delete);

                    layout_ingredient.addView(horizontal_container);
                    et_ingredient.setText("");
                    et_quantity.setText("");
                    nb_ingredient++;
                    ingredient.clear();
                }
            }
        });


        return v;
    }

    public ImageView addDelete()
    {
        ImageView image_delete = new ImageView(getContext());
        image_delete.setClickable(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,0.1f);
        image_delete.setLayoutParams(params);
        image_delete.setImageResource(R.drawable.ic_delete);
        image_delete.setColorFilter(Color.RED);

        image_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO DELETE INGREDIENT
            }
        });
        return image_delete;
    }

    public TextView addText(String s)
    {
        TextView tvName = new TextView(getContext());
        tvName.setText(s);
        tvName.setTextColor(ContextCompat.getColor(getContext(),R.color.material_drawer_dark_primary_text));
        tvName.setTextSize(18);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1);
        params.setMargins(10,10,0,0);
        tvName.setLayoutParams(params);
        tvName.setGravity(Gravity.CENTER);
        return tvName;
    }

    public LinearLayout addLayout()
    {
        LinearLayout horizontal_container =new LinearLayout(getContext());
        horizontal_container.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(15,10,15,10);
        horizontal_container.setLayoutParams(params);

        return horizontal_container;
    }

    public ArrayList<ArrayList<String>> getQuantity_ingredients() {
        return quantity_ingredients;
    }


    public void setQuantity_ingredients(ArrayList<ArrayList<String>> quantity_ingredients) {
        String tvString ="";
        TextView new_ingredient;
        LinearLayout horizontal_container;
        ImageView image_delete;
        for(int i =0; i< quantity_ingredients.size();i++)
        {
            if(utils.findMatch(quantity_ingredients.get(i).get(1),"^[0-9]{0,2}$")) {
                    tvString =quantity_ingredients.get(i).get(1) +" "+ quantity_ingredients.get(i).get(0);
            }
            else
            {
                    tvString =quantity_ingredients.get(i).get(1) +" de "+ quantity_ingredients.get(i).get(0);
            }

            new_ingredient = addText(tvString);
            horizontal_container = addLayout();
            image_delete = addDelete();
            horizontal_container.addView(new_ingredient);
            horizontal_container.addView(image_delete);
            LinearLayout layout_ingredient = (LinearLayout) getView().findViewById(R.id.step4_layout_ingredient);
            layout_ingredient.addView(horizontal_container);
        }
    }
}

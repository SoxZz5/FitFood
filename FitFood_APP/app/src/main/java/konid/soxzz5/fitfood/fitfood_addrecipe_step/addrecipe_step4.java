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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Ingredient;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.IngredientListAdapter;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Item;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.ListAdapter;
import konid.soxzz5.fitfood.utils.utils;

/**
 * Created by Soxzer on 12/12/2016.
 */

public class addrecipe_step4 extends Fragment {
    ListView mListView;
    List<Ingredient> ingredients;
    boolean first_item;
    IngredientListAdapter adapter;
    String ingredient;
    String quantity;
    boolean valid_quantity;
    boolean valid_ingredient;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_step4, container, false);

        //ON CREE NOTRE LISTVIEW
        mListView = (ListView) v.findViewById(R.id.step4_listview);

        final EditText et_quantity = (EditText) v.findViewById(R.id.step4_et_quantity);
        final EditText et_ingredient = (EditText) v.findViewById(R.id.step4_et_ingredient);
        final ImageView bt_add = (ImageView) v.findViewById(R.id.step4_bt_add);

        ingredients = new ArrayList<Ingredient>();
        first_item =false;
        valid_ingredient = false;
        valid_quantity = false;

        et_ingredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(utils.findMatch(et_ingredient.getText().toString(),"^[\\s\\w]{4,30}$"))
                {
                    ingredient = et_ingredient.getText().toString();
                    valid_ingredient=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
                if(utils.findMatch(et_quantity.getText().toString(),"^[0-9]*[ ][\\s\\w]{4,30}$"))
                {
                    quantity = et_quantity.getText().toString();
                    valid_quantity=true;
                }else if (utils.findMatch(et_quantity.getText().toString(),"^[0-9]{1,3}$"))
                {
                    quantity = et_quantity.getText().toString();
                    valid_quantity=true;
                }
            }
        });
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid_quantity && valid_ingredient) {
                    ingredients.add(new Ingredient(ingredient, quantity));
                    addToList();
                }
            }
        });
        return v;
    }

    private void addToList()
    {
        if(!first_item)
        {
            adapter = new IngredientListAdapter(getContext(),ingredients);
            mListView.setAdapter(adapter);
            first_item=true;
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
}

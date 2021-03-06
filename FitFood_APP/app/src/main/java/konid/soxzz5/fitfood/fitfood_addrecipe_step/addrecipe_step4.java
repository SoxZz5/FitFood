package konid.soxzz5.fitfood.fitfood_addrecipe_step;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Ingredient;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.IngredientListAdapter;
import konid.soxzz5.fitfood.utils.utils;

import static konid.soxzz5.fitfood.utils.utils.accentedCharacters;

/**
 * Created by Soxzer on 12/12/2016.
 */

public class addrecipe_step4 extends Fragment implements OnClickListener, OnItemClickListener{
    ListView mListView;
    List<Ingredient> ingredients;
    private List<Ingredient> parentList;
    boolean first_item;
    IngredientListAdapter adapter;
    String ingredient;
    String quantity;
    boolean valid_quantity;
    boolean valid_ingredient;
    Context _context;
    int nbIngredient;
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_step4, container, false);
        System.out.println("CREATION WEBVIEW4");
        //ON CREE NOTRE LISTVIEW

        mListView = (ListView) v.findViewById(R.id.step4_listview);
        _context=getContext();
        nbIngredient = 0;
        final EditText et_quantity = (EditText) v.findViewById(R.id.step4_et_quantity);
        final EditText et_ingredient = (EditText) v.findViewById(R.id.step4_et_ingredient);
        final ImageView bt_add = (ImageView) v.findViewById(R.id.step4_bt_add);
        final TextView info_error = (TextView) v.findViewById(R.id.info_error);

        first_item =false;
        valid_ingredient = false;
        valid_quantity = false;

        //On récupère le tableau s'il existe déjà
        if(parentList != null && parentList.size()>0) {
            ingredients = parentList;
            adapter = new IngredientListAdapter(_context, ingredients,this);
            mListView.setAdapter(adapter);
            if(this.ingredients.size()==1) {
                first_item=true;
            } else {
                adapter.notifyDataSetChanged();
            }
            nbIngredient = ingredients.size();
        } else ingredients = new ArrayList<Ingredient>();

        et_ingredient.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(utils.findMatch(et_ingredient.getText().toString(),"^[\\s\\w]{2,30}$"))
                {
                    ingredient = et_ingredient.getText().toString();
                    valid_ingredient=true;
                    info_error.setText("");
                } else {
                    valid_ingredient=false;
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
                String regex_match = "^(([0-9]+)([ A-z" + accentedCharacters + "]){3,30})|([0-9]{1,3})$";
                if(utils.findMatch(et_quantity.getText().toString(),regex_match))
                {
                    quantity = et_quantity.getText().toString();
                    valid_quantity=true;
                    info_error.setText("");
                } else {
                    valid_quantity=false;
                }
            }
        });
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid_quantity && valid_ingredient)
                {
                    ingredients.add(new Ingredient(quantity, ingredient, getNbIngredient()+1));
                    et_quantity.setText("");
                    et_ingredient.setText("");
                    addToList();
                    valid_quantity=false;
                    valid_ingredient=false;
                } else if(valid_quantity==false) {
                    info_error.setText(R.string.step4_invalid_quantity);
                } else {
                    info_error.setText(R.string.step4_invalid_ingredient);
                }
            }
        });
        return v;

    }

    public void onClick(View v){
        int position = (Integer) v.getTag(R.id.key_position);
        if(v.getId() == R.id.handler){
            nbIngredient--;
            refreshPositions(position);
            ingredients.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    //Allow to refresh ingredients numbers
    private void refreshPositions(int position) {
        int i=0;
        for (i=position;i<this.ingredients.size();i++) {
            this.ingredients.get(i).decreasePosition();
        }
        adapter.notifyDataSetChanged();
    }

    //Actualisation de la liste
    private void addToList()
    {
        nbIngredient++;
        if(!first_item)
        {
            adapter = new IngredientListAdapter(_context, ingredients,this);
            mListView.setAdapter(adapter);
            first_item=true;
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //NOTHING TO DO
    }

    public int getNbIngredient() {
        return nbIngredient;
    }

    public void setParentList(List<Ingredient> parentList) {
        this.parentList = parentList;
    }
}

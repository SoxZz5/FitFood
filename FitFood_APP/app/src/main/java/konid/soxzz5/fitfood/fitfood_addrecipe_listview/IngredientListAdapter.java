package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import konid.soxzz5.fitfood.R;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class IngredientListAdapter extends ArrayAdapter<Ingredient> {

    public IngredientListAdapter(Context context, List<Ingredient> ingredients)
    {
        super(context,0,ingredients);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ON DEFINIE LE DESIGN DE CHAQUE ITEM
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.addrecipe_step4_listitem,parent, false);
        }
        //ON CREER UNE VIEWHOLDER
        IngredientViewHolder ingredientHolder = (IngredientViewHolder) convertView.getTag();
        if(ingredientHolder == null)
        {
            ingredientHolder = new IngredientViewHolder();
            ingredientHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
            ingredientHolder.ingredient = (TextView) convertView.findViewById(R.id.ingredient);
            ingredientHolder.delete = (ImageView) convertView.findViewById(R.id.handler);
            convertView.setTag(ingredientHolder);
        }

        //ON MODIFIE LE HOLDER DE NOTRE ITEM
        Ingredient ingredient = getItem(position);
        ingredientHolder.quantity.setText(ingredient.getName());
        ingredientHolder.ingredient.setText(ingredient.getQuantity());

        return convertView;
    }

}

class IngredientViewHolder{
    public TextView quantity;
    public TextView ingredient;
    public ImageView delete;
}

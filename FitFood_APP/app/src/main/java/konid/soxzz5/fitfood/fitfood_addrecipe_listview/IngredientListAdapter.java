package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.List;

import konid.soxzz5.fitfood.R;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class IngredientListAdapter extends ArrayAdapter<Ingredient> {
    IngredientViewHolder ingredientHolder;
    private Context mContext;
    private List<Ingredient> mIngredients;
    private View mConvertView;
    OnClickListener clickListener;
    public IngredientListAdapter(Context context, List<Ingredient> ingredients, OnClickListener clickListener)
    {
        super(context,0,ingredients);
        mContext=context;
        mIngredients = ingredients;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ON DEFINIE LE DESIGN DE CHAQUE ITEM
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.addrecipe_step4_listitem,parent, false);
            mConvertView = convertView;
        }
        //ON CREER UNE VIEWHOLDER
        ingredientHolder = (IngredientViewHolder) convertView.getTag();
        if(ingredientHolder == null)
        {
            ingredientHolder = new IngredientViewHolder();
            ingredientHolder.nbRowIngredient = (TextView) convertView.findViewById(R.id.nbRowIngredient);
            ingredientHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
            ingredientHolder.ingredient = (TextView) convertView.findViewById(R.id.ingredient);
            ingredientHolder.delete = (ImageView) convertView.findViewById(R.id.handler);
            convertView.setTag(ingredientHolder);
        }

        //ON MODIFIE LE HOLDER DE NOTRE ITEM
        Ingredient ingredient = getItem(position);
        ingredientHolder.nbRowIngredient.setText(String.valueOf(ingredient.getPosition()));
        ingredientHolder.quantity.setText(ingredient.getName());
        ingredientHolder.ingredient.setText(ingredient.getQuantity());

        setClickListeners(ingredientHolder.delete);

        setTagsToViews(ingredientHolder.delete, position);
        return convertView;
    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setClickListeners(View view)
    {
        view.setOnClickListener(clickListener);
    }

    private void setTagsToViews(View view, int position)
    {
        view.setTag(R.id.key_position, position);
    }
}

class IngredientViewHolder{
    public TextView nbRowIngredient;
    public TextView quantity;
    public TextView ingredient;
    public ImageView delete;
}



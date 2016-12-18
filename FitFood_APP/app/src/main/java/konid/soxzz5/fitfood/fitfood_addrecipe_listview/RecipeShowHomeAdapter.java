package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import konid.soxzz5.fitfood.firebase_fitfood.Recipe;

/**
 * Created by Soxzer on 18/12/2016.
 */

public class RecipeShowHomeAdapter  extends ArrayAdapter<RecipeShowHome> {
    RecipeShowHomeViewHolder recipeShowHomeHolder;
    private Context mContext;
    private List<RecipeShowHome>
    public RecipeShowHomeAdapter(Context context,)

    class RecipeShowHomeViewHolder{
        public TextView nbRowIngredient;
        public TextView quantity;
        public TextView ingredient;
        public ImageView delete;
    }
}

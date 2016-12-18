package konid.soxzz5.fitfood.fitfood_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import konid.soxzz5.fitfood.R;

/**
 * Created by CORTELLA and GIFFARD on 18/12/2016.
 */

public class SingleRecipeDisplay extends Fragment {

    private GridView ingredientsGRID;
    private GridView stepsGRID;
    private TextView recipeTitle;
    private TextView recipeAuthor;
    private int recipeId = 0;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //DO SOMETHING WITH
        View v = inflater.inflate(R.layout.recipe_display_full_info, container, false);
        ingredientsGRID = (GridView) v.findViewById(R.id.ingredients_grid);
        stepsGRID = (GridView) v.findViewById(R.id.steps_grid);
        recipeTitle = (TextView) v.findViewById(R.id.recip_title);
        recipeAuthor = (TextView) v.findViewById(R.id.recipe_author);


        return v;
    }
}

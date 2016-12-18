package konid.soxzz5.fitfood.fitfood_fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import konid.soxzz5.fitfood.R;

/**
 * Created by CORTELLA and GIFFARD on 18/12/2016.
 */

public class SingleRecipeDisplay extends Fragment {
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //DO SOMETHING WITH
        View v = inflater.inflate(R.layout.recipe_display_full_info, container, false);

        return v;
    }
}

package konid.soxzz5.fitfood.fitfood_fragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import konid.soxzz5.fitfood.AddRecipe;
import konid.soxzz5.fitfood.R;


/**
 * Created by Soxzer on 06/12/2016.
 */

public class AddRecipeFragment extends Fragment {
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //DO SOMETHING WITH
        View v = inflater.inflate(R.layout.fragment_addrecipe, container, false);

        Button submit = (Button) v.findViewById(R.id.recipeadd_bt_submit);

        submit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddRecipe.class);
                startActivity(intent);
            }
        });

        return v;
    }

}

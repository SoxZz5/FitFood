package konid.soxzz5.fitfood.fitfood_addrecipe_step;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import konid.soxzz5.fitfood.R;

/**
 * Created by NONE on 10/12/2016.
 */

public class addrecipe_step2 extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_step2, container, false);

        LinearLayout container_new = (LinearLayout) v.findViewById(R.id.step2_container_new);
        LinearLayout object_add = (LinearLayout) v.findViewById(R.id.step2_object_addlayout);
        ImageView add = (ImageView) v.findViewById(R.id.step2_bt_add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return v;
    }
}

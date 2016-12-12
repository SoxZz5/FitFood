package konid.soxzz5.fitfood.fitfood_addrecipe_step;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import konid.soxzz5.fitfood.R;

/**
 * Created by Soxzer on 12/12/2016.
 */

public class addrecipe_step3 extends Fragment{
    EditText et_prephour;
    EditText et_prepminute;
    EditText et_heathour;
    EditText et_heatminute;
    EditText et_nbwho;
    EditText et_forwho;

    int prephour;
    int prepminute;
    int heathour;
    int heatminute;
    int nbwho;
    String forwho;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_step3, container, false);

        et_prephour = (EditText) v.findViewById(R.id.step3_et_prephour);
        et_prepminute = (EditText) v.findViewById(R.id.step3_et_prepminute);
        et_heathour = (EditText) v.findViewById(R.id.step3_et_heathour);
        et_heatminute = (EditText) v.findViewById(R.id.step3_et_heatminute);
        et_nbwho = (EditText) v.findViewById(R.id.step3_et_nbwho);
        et_forwho = (EditText) v.findViewById(R.id.step3_et_forwho);

        et_prephour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }

}

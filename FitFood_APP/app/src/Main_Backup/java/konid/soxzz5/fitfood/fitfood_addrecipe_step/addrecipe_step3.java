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
import konid.soxzz5.fitfood.utils.utils;

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

        prephour = -1;
        prepminute = -1;
        heathour = -1;
        heatminute = -1;
        nbwho = -1;
        forwho = "ERROR_FORWHO";


        et_prephour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_prephour.getText().toString()!="") {
                    if (Integer.parseInt(et_prephour.getText().toString()) >= 0) {
                        prephour = Integer.parseInt(et_prephour.getText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_prepminute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_prepminute.getText().toString() != "") {
                    if (Integer.parseInt(et_prepminute.getText().toString()) >= 0) {
                        prepminute = Integer.parseInt(et_prepminute.getText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_heathour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_heathour.getText().toString()!= "") {
                    if (Integer.parseInt(et_heathour.getText().toString()) >= 0) {
                        heathour = Integer.parseInt(et_heathour.getText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_heatminute.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_heatminute.getText().toString()!="") {
                    if (Integer.parseInt(et_heatminute.getText().toString()) >= 0) {
                        heatminute = Integer.parseInt(et_heatminute.getText().toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_nbwho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(et_nbwho.getText().toString() != " ")
                {//TODO CORRECT VERIF HERE ! USE utils FINDMATCH :)
                    if (Integer.parseInt(et_nbwho.getText().toString()) >= 0)
                    {
                        nbwho = Integer.parseInt(et_nbwho.getText().toString());
                    }
                }
            }
        });

        et_forwho.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(et_forwho.getText().toString()!="") {
                    if (utils.findMatch(et_forwho.getText().toString(), "^[\\s\\w]{4,30}$")) {
                        forwho = et_forwho.getText().toString();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }

    public int getHeathour() {
        return heathour;
    }

    public int getHeatminute() {
        return heatminute;
    }

    public int getNbwho() {
        return nbwho;
    }

    public int getPrephour() {
        return prephour;
    }

    public int getPrepminute() {
        return prepminute;
    }

    public String getForwho() {
        return forwho;
    }

    public void setForwho(String forwho) {
        this.forwho = forwho;
        et_forwho.setText(forwho);
    }

    public void setHeathour(int heathour) {
        this.heathour = heathour;
        et_heathour.setText(Integer.toString(heathour));
    }

    public void setHeatminute(int heatminute) {
        this.heatminute = heatminute;
        et_heatminute.setText(Integer.toString(heatminute));
    }

    public void setNbwho(int nbwho) {
        this.nbwho = nbwho;
        et_nbwho.setText(Integer.toString(nbwho));
    }

    public void setPrephour(int prephour) {
        this.prephour = prephour;
        et_prephour.setText(Integer.toString(prephour));
    }

    public void setPrepminute(int prepminute) {
        this.prepminute = prepminute;
        et_prepminute.setText(Integer.toString(prepminute));
    }
}

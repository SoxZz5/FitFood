package konid.soxzz5.fitfood.fitfood_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import konid.soxzz5.fitfood.R;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        //TODO LAYOUT fragment_home + Fonction de la layout ici
        return v;
    }
}

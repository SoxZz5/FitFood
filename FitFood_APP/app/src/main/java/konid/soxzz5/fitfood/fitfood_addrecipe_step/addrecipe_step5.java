package konid.soxzz5.fitfood.fitfood_addrecipe_step;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.PrepStep;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.PrepStepsListAdapter;
import konid.soxzz5.fitfood.utils.utils;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnClickListener;


/**
 * This application creates a listview where the ordering of the data set
 * can be modified in response to user touch events.
 *
 * An item in the listview is selected via a long press event and is then
 * moved around by tracking and following the movement of the user's finger.
 * When the item is released, it animates to its new position within the listview.
 */
public class addrecipe_step5 extends Fragment implements OnClickListener, OnItemClickListener {

    ListView mListView;
    List<PrepStep> prepSteps;
    String step;
    boolean first_item;
    PrepStepsListAdapter adapter;
    boolean valid_step;
    int nbItem;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_step5, container, false);
        nbItem = 0;
        mListView = (ListView) v.findViewById(R.id.step5_listview);
        final EditText et_step = (EditText) v.findViewById(R.id.step5_et_step);
        final ImageView bt_add = (ImageView) v.findViewById(R.id.step5_bt_add);
        prepSteps = new ArrayList<PrepStep>();
        first_item =false;

        valid_step=false;
        et_step.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(utils.findMatch(et_step.getText().toString(),"^[\\s\\w]{10,100}$")){
                    step = et_step.getText().toString();
                    valid_step=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valid_step) {
                    prepSteps.add(new PrepStep(step,getnbItem()+1));
                    addToList();
                    valid_step=false;
                    et_step.setText("");
                }
            }
        });
        return v;
    }

    private void addToList()
    {
        nbItem++;
        if(!first_item)
        {
            adapter = new PrepStepsListAdapter(getContext(), prepSteps,this);
            mListView.setAdapter(adapter);
            first_item=true;
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }

    //Actualisation de la liste
    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag(R.id.key_position);
        if(v.getId() == R.id.handler){
            nbItem--;
            refreshPositions(position);
            prepSteps.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    //Allow to refresh steps numbers
    private void refreshPositions(int position) {
        int i=0;
        for (i=position;i<this.prepSteps.size();i++) {
            this.prepSteps.get(i).decreasePosition();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //NOTHING TO DO
    }

    public List<PrepStep> getPrepSteps() {
        return prepSteps;
    }

    public int getnbItem() {
        return nbItem;
    }
}


package konid.soxzz5.fitfood.fitfood_addrecipe_step;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Item;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.ListAdapter;
import konid.soxzz5.fitfood.utils.utils;

import static android.R.attr.data;


/**
 * This application creates a listview where the ordering of the data set
 * can be modified in response to user touch events.
 *
 * An item in the listview is selected via a long press event and is then
 * moved around by tracking and following the movement of the user's finger.
 * When the item is released, it animates to its new position within the listview.
 */
public class addrecipe_step5 extends Fragment {

    ListView mListView;
    List<Item> items;
    List<Item> lItems;
    String step;
    boolean first_item;
    ListAdapter adapter;
    int stepId;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_step5, container, false);

        mListView = (ListView) v.findViewById(R.id.step5_listview);
        final EditText et_step = (EditText) v.findViewById(R.id.step5_et_step);
        final ImageView bt_add = (ImageView) v.findViewById(R.id.step5_bt_add);
        items = new ArrayList<Item>();
        lItems = new ArrayList<>();
        first_item =false;
        stepId=1;

        et_step.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(utils.findMatch(et_step.getText().toString(),"^[\\s\\w]{10,100}$")){
                    step = et_step.getText().toString();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lItems.add(new Item(step,Integer.toString(stepId)));
                items.add(new Item(step,Integer.toString(stepId)));
                addToList();
                stepId++;
            }
        });
        return v;
    }

    private void addToList()
    {
        if(!first_item)
        {
            adapter = new ListAdapter(getContext(),items);
            mListView.setAdapter(adapter);
            first_item=true;
        }
        else
        {
            adapter.notifyDataSetChanged();
        }
    }
}


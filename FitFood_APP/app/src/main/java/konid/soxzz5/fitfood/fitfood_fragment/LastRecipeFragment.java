package konid.soxzz5.fitfood.fitfood_fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.firebase_fitfood.Recipe;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.RecipeShowHomeAdapter;

/**
 * Created by Soxzer on 18/12/2016.
 */

public class LastRecipeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Context mContext;
    private ListView listview_container;
    private List<Recipe> recipeList;
    private DatabaseReference databaseReference;
    private RecipeShowHomeAdapter recipeShowHomeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_fragment, container, false);

        mContext = getContext();
        recipeList = new ArrayList<>();
        recipeShowHomeAdapter = new RecipeShowHomeAdapter(mContext, recipeList,this);
        listview_container = (ListView) v.findViewById(R.id.search_list_view);
            databaseReference = FirebaseDatabase.getInstance().getReference().child("recipe");
            databaseReference.orderByChild("rdate").limitToLast(20).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            int temp_category = Integer.parseInt(snapshot.child("rcategory").getValue().toString());
                            String temp_date = snapshot.child("rdate").getValue().toString();
                            String temp_forWho = snapshot.child("rforWho").getValue().toString();
                            int temp_heatHour = Integer.parseInt(snapshot.child("rheatHour").getValue().toString());
                            int temp_heatMinute = Integer.parseInt(snapshot.child("rheatMinute").getValue().toString());
                            int temp_level = Integer.parseInt(snapshot.child("rlevel").getValue().toString());
                            int temp_prepareHour = Integer.parseInt(snapshot.child("rprepareHour").getValue().toString());
                            int temp_prepareMinute = Integer.parseInt(snapshot.child("rprepareMinute").getValue().toString());
                            String temp_dll_link = snapshot.child("rrecipe_download_img_link").getValue().toString();
                            String temp_title = snapshot.child("rtitle").getValue().toString();
                            int temp_type = Integer.parseInt(snapshot.child("rtype").getValue().toString());
                            boolean temp_valide = (Boolean) snapshot.child("rvalidate").getValue();
                            recipeList.add(new Recipe(temp_title, temp_category, temp_level, temp_type, temp_prepareHour, temp_prepareMinute, temp_heatHour, temp_heatMinute, temp_forWho, temp_date, temp_valide, temp_dll_link));
                    }
                    Collections.reverse(recipeList);
                    listview_container.setAdapter(recipeShowHomeAdapter);
                    recipeShowHomeAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    listview_container.setVisibility(View.GONE);
                    ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                    mProgressDialog.setMessage("Tentative de reconnexion");
                    mProgressDialog.show();
                }
            });
        return v;
    }

    @Override
    public void onClick(View view) {
        //TODO GO SINGLE RECIPE
        //View étant l'imageview cliquer :)
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}

package konid.soxzz5.fitfood.fitfood_fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.firebase_fitfood.Recipe;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

/**
 * Created by CORTELLA and GIFFARD on 18/12/2016.
 */

public class SingleRecipeDisplay extends Fragment {

    private DatabaseReference databaseReference;
    FirebaseStorage storage;
    private GridView ingredientsGRID;
    private GridView stepsGRID;
    private TextView recipeTitle;
    private TextView recipeAuthor;
    private TextView recipe_info_tag;
    private TextView recipe_info_forwho;
    private TextView recipe_tv_heathour;
    private TextView recipe_tv_heatminute;
    private TextView recipe_tv_prephour;
    private TextView recipe_tv_prepminute;
    private TextView recipe_tv_finalhour;
    private TextView recipe_tv_finalminute;
    private ImageView recipe_imageView;
    private Context context;
    private int recipeId = 0;
    Recipe loadedRecipe = new Recipe();
    String name;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        context = getActivity();
        //DO SOMETHING WITH
        View v = inflater.inflate(R.layout.recipe_display_full_info, container, false);
        ingredientsGRID = (GridView) v.findViewById(R.id.ingredients_grid);
        stepsGRID = (GridView) v.findViewById(R.id.steps_grid);
        recipeTitle = (TextView) v.findViewById(R.id.recip_title);
        recipeAuthor = (TextView) v.findViewById(R.id.recipe_author);
        recipe_info_tag = (TextView) v.findViewById(R.id.recipe_info_tag);
        recipe_info_forwho = (TextView) v.findViewById(R.id.recipe_info_forwho);
        recipe_tv_heathour = (TextView) v.findViewById(R.id.recipe_tv_heathour);
        recipe_tv_heatminute = (TextView) v.findViewById(R.id.recipe_tv_heatminute);
        recipe_tv_prephour = (TextView) v.findViewById(R.id.recipe_tv_prephour);
        recipe_tv_prepminute = (TextView) v.findViewById(R.id.recipe_tv_prepminute);
        recipe_tv_finalhour = (TextView) v.findViewById(R.id.recipe_tv_finalhour);
        recipe_tv_finalminute = (TextView) v.findViewById(R.id.recipe_tv_finalminute);
        recipe_imageView = (ImageView) v.findViewById(R.id.recipe_imageView);

        storage = FirebaseStorage.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("recipe").child("-KZID8Lhn5Mn2RG8vYhd");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                loadedRecipe.setRtitle(String.valueOf(dataSnapshot.child("rtitle").getValue()));
                loadedRecipe.setRforWho(String.valueOf(dataSnapshot.child("rforWho").getValue()));
                loadedRecipe.setURL(String.valueOf(dataSnapshot.child("rrecipe_download_img_link").getValue()));
                long Rheathour = (Long.parseLong(String.valueOf(dataSnapshot.child("rheatHour").getValue())));
                long Rheatminute = (Long.parseLong(String.valueOf(dataSnapshot.child("rheatMinute").getValue())));
                long Rprephour = (Long.parseLong(String.valueOf(dataSnapshot.child("rprepareHour").getValue())));
                long Rprepminute = (Long.parseLong(String.valueOf(dataSnapshot.child("rprepareMinute").getValue())));
                long Rfinalhour = Rheathour + Rprephour;
                long Rfinalminute = Rheatminute + Rprepminute;
                System.out.println("URL:" + String.valueOf(dataSnapshot.child("rrecipe_download_img_link").getValue()));

                recipeTitle.setText(loadedRecipe.getRtitle());
                recipe_info_forwho.setText(loadedRecipe.getRforWho());
                recipe_tv_heathour.setText(String.valueOf(Rheathour));
                recipe_tv_heatminute.setText(String.valueOf(Rheatminute));
                recipe_tv_prephour.setText(String.valueOf(Rprephour));
                recipe_tv_prepminute.setText(String.valueOf(Rprepminute));
                recipe_tv_finalhour.setText(String.valueOf(Rfinalhour));
                recipe_tv_finalminute.setText(String.valueOf(Rfinalminute));

                //Téléchargement et affichage de l'image de la recette
                StorageReference storageRef = storage.getReferenceFromUrl(loadedRecipe.getURL());
                final long ONE_MEGABYTE = 1024 * 1024;
                storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        recipe_imageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("ERROR");
            }
        });


        return v;
    }
}

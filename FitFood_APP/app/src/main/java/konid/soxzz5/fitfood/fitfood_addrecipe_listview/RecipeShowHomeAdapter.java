package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.firebase_fitfood.Recipe;


public class RecipeShowHomeAdapter  extends ArrayAdapter<Recipe> {
    RecipeShowHomeViewHolder recipeShowHomeHolder;
    private Context mContext;
    private List<Recipe> mRecipeList;
    private OnClickListener clickListener;
    private View mConvertView;
    private Bitmap mBitmap;
    private StorageReference firebaseStorage;
    FirebaseStorage storage;
    Recipe recipe;
    Uri path_dll;
    public RecipeShowHomeAdapter(Context context, List<Recipe> recipeList, OnClickListener clickListener) {
        super(context, 0, recipeList);
        mContext = context;
        mRecipeList = recipeList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //ON DEFINIE LE DESIGN DE CHAQUE ITEM
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recipe_show_home_layout, parent, false);
            mConvertView = convertView;
        }
        //ON CREER UNE VIEWHOLDER
        recipeShowHomeHolder = (RecipeShowHomeViewHolder) convertView.getTag();
        if (recipeShowHomeHolder == null) {
            recipeShowHomeHolder = new RecipeShowHomeViewHolder();
            recipeShowHomeHolder.recipe_title = (TextView) convertView.findViewById(R.id.recip_title);
            recipeShowHomeHolder.recipe_tag = (TextView) convertView.findViewById(R.id.recipe_info_tag);
            recipeShowHomeHolder.recipe_forwho = (TextView) convertView.findViewById(R.id.recipe_info_forwho);
            recipeShowHomeHolder.recipe_heathour = (TextView) convertView.findViewById(R.id.recipe_tv_heathour);
            recipeShowHomeHolder.recipe_heatminute = (TextView) convertView.findViewById(R.id.recipe_tv_heatminute);
            recipeShowHomeHolder.recipe_prephour = (TextView) convertView.findViewById(R.id.recipe_tv_prephour);
            recipeShowHomeHolder.recipe_prepminute = (TextView) convertView.findViewById(R.id.recipe_tv_prepminute);
            recipeShowHomeHolder.recipe_finalhour = (TextView) convertView.findViewById(R.id.recipe_tv_finalhour);
            recipeShowHomeHolder.recipe_finalminute = (TextView) convertView.findViewById(R.id.recipe_tv_finalminute);
            recipeShowHomeHolder.recipe_image = (ImageView) convertView.findViewById(R.id.recipe_imageView);
            recipeShowHomeHolder.recipe_img_pb = (ProgressBar) convertView.findViewById(R.id.recipe_image_pb);
            convertView.setTag(recipeShowHomeHolder);
        }


        //ON MODIFIE LE HOLDER DE NOTRE ITEM
        recipe = getItem(position);
        recipeShowHomeHolder.recipe_title.setText(recipe.getRtitle());
        String type ="";
        switch (recipe.getRcategory())
        {
            case 1:
                type = getContext().getString(R.string.step2_rb_starter);
                break;
            case 2:
                type = getContext().getString(R.string.step2_rb_maincourse);
                break;
            case 3:
                type = getContext().getString(R.string.step2_rb_dessert);
                break;
            case 4:
                type = getContext().getString(R.string.step2_rb_sidedish);
                break;
            case 5:
                type = getContext().getString(R.string.step2_rb_appetizer);
                break;
            case 6:
                type = getContext().getString(R.string.step2_rb_drink);
                break;
            case 7:
                type = getContext().getString(R.string.step2_rb_sweets);
                break;
            case 8:
                type = getContext().getString(R.string.step2_rb_sauce);
                break;
        }

        String category="";
        switch (recipe.getRcategory())
        {
            case 1:
                category = getContext().getString(R.string.step1_rb_omnivore);
                break;
            case 2:
                category = getContext().getString(R.string.step1_rb_vegetarien);
                break;
            case 3:
                category = getContext().getString(R.string.step1_rb_vegetalien);
                break;
        }

        String level="";
        switch (recipe.getRlevel())
        {
            case 1:
                level= getContext().getString(R.string.step2_tv_info_0);
                break;
            case 2:
                level= getContext().getString(R.string.step2_tv_info_1);
                break;
            case 3:
                level= getContext().getString(R.string.step2_tv_info_2);
                break;
            case 4:
                level= getContext().getString(R.string.step2_tv_info_3);
                break;
            case 5:
                level= getContext().getString(R.string.step2_tv_info_4);
                break;
        }

        String tag = category + " / " + type + " / " + level;
        recipeShowHomeHolder.recipe_tag.setText(tag);
        String forwho = getContext().getString(R.string.step3_tv_forwho) + " " + recipe.getRforWho();
        recipeShowHomeHolder.recipe_forwho.setText(forwho);
        recipeShowHomeHolder.recipe_heathour.setText(Integer.toString(recipe.getRheatHour()));
        recipeShowHomeHolder.recipe_heatminute.setText(Integer.toString(recipe.getRheatMinute()));
        recipeShowHomeHolder.recipe_prephour.setText(Integer.toString(recipe.getRprepareHour()));
        recipeShowHomeHolder.recipe_prepminute.setText(Integer.toString(recipe.getRprepareMinute()));

        String timeDisplay = "00:00";
        int totalHourInMin = ((recipe.getRheatHour()*60)+recipe.getRheatMinute())+((recipe.getRprepareHour()*60)+recipe.getRprepareMinute());
        int finalhour = ((totalHourInMin/60)+Integer.parseInt(timeDisplay.substring(0,1)));
        int finalminute = ((totalHourInMin%60)+Integer.parseInt(timeDisplay.substring(3,4)));
        recipeShowHomeHolder.recipe_finalhour.setText(Integer.toString(finalhour));
        recipeShowHomeHolder.recipe_finalminute.setText(Integer.toString(finalminute));
        firebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl(recipe.getRrecipe_download_img_link().toString());
        Picasso.with(mContext).setLoggingEnabled(true);
        recipeShowHomeHolder.recipe_img_pb.setVisibility(View.VISIBLE);
        Picasso.with(mContext)
                .load(recipe.getRrecipe_download_img_link().toString())
                .fit()
                .centerCrop()
                .into(recipeShowHomeHolder.recipe_image, new Callback() {
                    @Override
                    public void onSuccess() {
                        recipeShowHomeHolder.recipe_img_pb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {

                    }
                });


        return convertView;
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private void setClickListeners(View view) {
        view.setOnClickListener(clickListener);
    }

    private void setTagsToViews(View view, int position) {
        view.setTag(R.id.key_position, position);
    }
}
    class RecipeShowHomeViewHolder{
        public TextView recipe_title;
        public TextView recipe_tag;
        public TextView recipe_forwho;
        public TextView recipe_heathour;
        public TextView recipe_heatminute;
        public TextView recipe_prephour;
        public TextView recipe_prepminute;
        public TextView recipe_finalhour;
        public TextView recipe_finalminute;
        public ImageView recipe_image;
        public ProgressBar recipe_img_pb;
    }



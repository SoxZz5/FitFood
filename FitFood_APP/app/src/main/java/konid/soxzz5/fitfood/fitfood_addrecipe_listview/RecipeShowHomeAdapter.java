package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

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
            convertView.setTag(recipeShowHomeHolder);
        }

        //ON MODIFIE LE HOLDER DE NOTRE ITEM
        Recipe recipe = getItem(position);
        recipeShowHomeHolder.recipe_title.setText(recipe.getRtitle());
        //String tag = recipe.getRcategory() + " / " + recipe.getRtype() + " / " + recipe.getRlevel();
        //recipeShowHomeHolder.recipe_tag.setText(tag);
        String forwho = getContext().getString(R.string.step3_tv_forwho) + " " + recipe.getRforWho();
        recipeShowHomeHolder.recipe_forwho.setText(forwho);
        recipeShowHomeHolder.recipe_heathour.setText(recipe.getRheatHour());
        recipeShowHomeHolder.recipe_heatminute.setText(recipe.getRheatMinute());
        recipeShowHomeHolder.recipe_prephour.setText(recipe.getRprepareHour());
        recipeShowHomeHolder.recipe_prepminute.setText(recipe.getRprepareMinute());

        String timeDisplay = "00:00";
        int totalHourInMin = ((recipe.getRheatHour()*60)+recipe.getRheatMinute())+((recipe.getRprepareHour()*60)+recipe.getRprepareMinute());
        int finalhour = ((totalHourInMin/60)+Integer.parseInt(timeDisplay.substring(0,1)));
        int finalminute = ((totalHourInMin%60)+Integer.parseInt(timeDisplay.substring(3,4)));
        recipeShowHomeHolder.recipe_finalhour.setText(finalhour);
        recipeShowHomeHolder.recipe_finalminute.setText(finalminute);
        Picasso.with(getContext()).load(recipe.getRrecipe_download_img_link()).centerCrop().into(recipeShowHomeHolder.recipe_image);

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
    }



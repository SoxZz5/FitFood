package konid.soxzz5.fitfood.firebase_fitfood;

import android.graphics.Bitmap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Ingredient;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.PrepStep;

/**
 * Created by Soxzer on 16/12/2016.
 */

public class Recipe {
    private String Rtitle;
    private int Rcategory;
    private int Rlevel;
    private int Rtype;
    private int RprepareHour;
    private int RprepareMinute;
    private int RheatHour;
    private int RheatMinute;
    private String RforWho;
    private List<Ingredient> Ringredients;
    private List<PrepStep> Rsteps;
    private String Rdate;
    private boolean Rvalidate;
    private String Rrecipe_download_img_link;

    public Recipe(){

    }

    public Recipe(String rtitle, int rcategory, int rlevel, int rtype, int rprepareHour, int rprepareMinute, int rheatHour, int rheatMinute, String rforWho, String rdate, boolean rvalidate, String rrecipe_download_img_link) {
        Rtitle = rtitle;
        Rcategory = rcategory;
        Rlevel = rlevel;
        Rtype = rtype;
        RprepareHour = rprepareHour;
        RprepareMinute = rprepareMinute;
        RheatHour = rheatHour;
        RheatMinute = rheatMinute;
        RforWho = rforWho;
        Rdate = rdate;
        Rvalidate = rvalidate;
        Rrecipe_download_img_link = rrecipe_download_img_link;
    }

    public Recipe(String rtitle, int rcategory, int rlevel, int rtype, int rprepareHour, int rprepareMinute, int rheatHour, int rheatMinute, String rforWho, List<Ingredient> ringredients, List<PrepStep> rsteps, boolean validate, String date, String recipe_download_img_link) {

        Rtitle = rtitle;
        Rcategory = rcategory;
        Rlevel = rlevel;
        Rtype = rtype;
        RprepareHour = rprepareHour;
        RprepareMinute = rprepareMinute;
        RheatHour = rheatHour;
        RheatMinute = rheatMinute;
        RforWho = rforWho;
        Ringredients = ringredients;
        Rsteps = rsteps;
        Rvalidate=validate;
        Rdate = date;
        Rrecipe_download_img_link = recipe_download_img_link;

    }

    public Recipe(Recipe new_recipe)
    {
        Rtitle = new_recipe.getRtitle();
        Rcategory = new_recipe.getRcategory();
        Rlevel = new_recipe.getRlevel();
        Rtype = new_recipe.getRtype();
        RprepareHour = new_recipe.getRprepareHour();
        RprepareMinute = new_recipe.getRprepareMinute();
        RheatHour = new_recipe.getRheatHour();
        RheatMinute = new_recipe.getRheatMinute();
        RforWho = new_recipe.getRforWho();
        Ringredients = new_recipe.getRingredients();
        Rsteps = new_recipe.getRsteps();
        Rvalidate=new_recipe.getRvalidate();
        Rdate = new_recipe.getRdate();
        Rrecipe_download_img_link = new_recipe.getRrecipe_download_img_link();
    }

    public int getRcategory() {return Rcategory;}

    public int getRheatHour() {
        return RheatHour;
    }

    public int getRheatMinute() {
        return RheatMinute;
    }

    public int getRlevel() {
        return Rlevel;
    }

    public int getRprepareHour() {
        return RprepareHour;
    }

    public int getRprepareMinute() {
        return RprepareMinute;
    }

    public int getRtype() {
        return Rtype;
    }

    public List<Ingredient> getRingredients() {
        return Ringredients;
    }

    public List<PrepStep> getRsteps() {
        return Rsteps;
    }

    public String getRdate() {
        return Rdate;
    }

    public String getRforWho() {
        return RforWho;
    }

    public String getRtitle() {
        return Rtitle;
    }

    public String getRrecipe_download_img_link() {
        return Rrecipe_download_img_link;
    }

    public boolean getRvalidate()
    {
        return Rvalidate;
    }
}

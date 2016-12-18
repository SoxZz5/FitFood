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
    public String Rtitle;
    public int Rcategory;
    public int Rlevel;
    public int Rtype;
    public int RprepareHour;
    public int RprepareMinute;
    public int RheatHour;
    public int RheatMinute;
    public String RforWho;
    public List<Ingredient> Ringredients;
    public List<PrepStep> Rsteps;
    public String Rdate;
    public boolean Rvalidate;
    public String Rrecipe_download_img_link;

    public Recipe(){

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

    public int getRcategory() {
        return Rcategory;
    }

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

    public void setRcategory(int rcategory) {
        Rcategory = rcategory;
    }

    public void setRdate(String rdate) {
        Rdate = rdate;
    }

    public void setRforWho(String rforWho) {
        RforWho = rforWho;
    }

    public void setRheatHour(int rheatHour) {
        RheatHour = rheatHour;
    }

    public void setRheatMinute(int rheatMinute) {
        RheatMinute = rheatMinute;
    }

    public void setRingredients(List<Ingredient> ringredients) {
        Ringredients = ringredients;
    }

    public void setRlevel(int rlevel) {
        Rlevel = rlevel;
    }

    public void setRprepareHour(int rprepareHour) {
        RprepareHour = rprepareHour;
    }

    public void setRprepareMinute(int rprepareMinute) {
        RprepareMinute = rprepareMinute;
    }

    public void setRsteps(List<PrepStep> rsteps) {
        Rsteps = rsteps;
    }

    public void setRtitle(String rtitle) {
        Rtitle = rtitle;
    }

    public void setRtype(int rtype) {
        Rtype = rtype;
    }

    public void setRvalidate(boolean rvalidate) {
        Rvalidate = rvalidate;
    }

    public String getRrecipe_download_img_link() {
        return Rrecipe_download_img_link;
    }

    public void setRrecipe_download_img_link(String rrecipe_download_img_link) {
        Rrecipe_download_img_link = rrecipe_download_img_link;
    }
}

package konid.soxzz5.fitfood.firebase_fitfood;

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
    public boolean Rvalidate;

    public Recipe(){

    }

    public Recipe(String rtitle, int rcategory, int rlevel, int rtype, int rprepareHour, int rprepareMinute, int rheatHour, int rheatMinute, String rforWho, List<Ingredient> ringredients, List<PrepStep> rsteps, boolean validate) {
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
    }
}

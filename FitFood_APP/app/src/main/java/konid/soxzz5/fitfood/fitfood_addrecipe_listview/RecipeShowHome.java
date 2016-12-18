package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

import java.util.List;

/**
 * Created by Soxzer on 18/12/2016.
 */

public class RecipeShowHome {
    private String category;
    private String forwho;
    private int heatHour;
    private int heatMinute;
    private int prepHour;
    private int prepMinute;
    private String level;
    private String title;
    private String type;
    private String date;
    private boolean validate;

    public RecipeShowHome() {}

    public RecipeShowHome(String category, String forwho, int heatHour, int heatMinute, int prepHour, int prepMinute, String level, String title, String type, String date, boolean validate) {
        this.category = category;
        this.forwho = forwho;
        this.heatHour = heatHour;
        this.heatMinute = heatMinute;
        this.prepHour = prepHour;
        this.prepMinute = prepMinute;
        this.level = level;
        this.title = title;
        this.type = type;
        this.date = date;
        this.validate = validate;
    }

    public int getHeatHour() {
        return heatHour;
    }

    public int getHeatMinute() {
        return heatMinute;
    }

    public int getPrepHour() {
        return prepHour;
    }

    public int getPrepMinute() {
        return prepMinute;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getForwho() {
        return forwho;
    }

    public String getLevel() {
        return level;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setForwho(String forwho) {
        this.forwho = forwho;
    }

    public void setHeatHour(int heatHour) {
        this.heatHour = heatHour;
    }

    public void setHeatMinute(int heatMinute) {
        this.heatMinute = heatMinute;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setPrepHour(int prepHour) {
        this.prepHour = prepHour;
    }

    public void setPrepMinute(int prepMinute) {
        this.prepMinute = prepMinute;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setValidate(boolean validate) {
        this.validate = validate;
    }
}

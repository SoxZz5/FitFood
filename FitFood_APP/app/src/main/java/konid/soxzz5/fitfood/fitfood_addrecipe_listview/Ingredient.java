package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class Ingredient {
    private String name;
    private String quantity;
    private int position;

    public Ingredient(){

    }

    public Ingredient(String name, String quantity, int position){
        this.position=position;
        this.name=name;
        this.quantity=quantity;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void decreasePosition() {
        this.position--;
    }
}

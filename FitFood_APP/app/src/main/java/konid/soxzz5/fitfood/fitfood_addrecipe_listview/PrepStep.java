package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class PrepStep {
    private String name;
    private int position;

    public PrepStep(String name, int position)
    {
        this.name=name;
        this.position=position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void decreasePosition() {
        this.position--;
    }
}

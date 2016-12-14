package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class Item {
    private String name;
    private String step;
    public Item(String name,String step)
    {
        this.name=name;
        this.step=step;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }
}

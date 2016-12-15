package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.util.List;

import konid.soxzz5.fitfood.R;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class ListAdapter extends ArrayAdapter<Item> {
    private View.OnClickListener clickListener;
    private List<Item> items;
    private View convertView;
    public ListAdapter(Context context, List<Item> items)
    {
        super(context,0,items);
    }

    public ListAdapter(Context context,List<Item> items, OnClickListener clickListener)
    {
        super(context,0,items);
        this.items = items;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.addrecipe_step5_listitem,parent, false);
        }
        ItemViewHolder itemHolder = (ItemViewHolder) convertView.getTag();
        if(itemHolder == null)
        {
            itemHolder = new ItemViewHolder();
            itemHolder.name = (TextView) convertView.findViewById(R.id.text);
            itemHolder.delete = (ImageView) convertView.findViewById(R.id.handler);
            convertView.setTag(itemHolder);
        }

        Item item = getItem(position);
        itemHolder.name.setText(item.getName());

        setClickListeners(itemHolder.delete);

        setTagsToViews(itemHolder.delete,position);

        return convertView;
    }

    private void setClickListeners(View view)
    {
        view.setOnClickListener(clickListener);
    }

    private void setTagsToViews(View view, int position)
    {
        view.setTag(R.id.key_position, position);
    }

}

class ItemViewHolder{
    public TextView name;
    public ImageView delete;
}

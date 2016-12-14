package konid.soxzz5.fitfood.fitfood_addrecipe_listview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import konid.soxzz5.fitfood.R;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class ListAdapter extends ArrayAdapter<Item> {

    public ListAdapter(Context context, List<Item> items)
    {
        super(context,0,items);
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
            itemHolder.stepId = (TextView) convertView.findViewById(R.id.stepId);
            itemHolder.name = (TextView) convertView.findViewById(R.id.text);
            itemHolder.delete = (ImageView) convertView.findViewById(R.id.handler);
            convertView.setTag(itemHolder);
        }

        Item item = getItem(position);
        itemHolder.name.setText(item.getName());
        itemHolder.stepId.setText(item.getStep());

        return convertView;
    }

}

class ItemViewHolder{
    public TextView stepId;
    public TextView name;
    public ImageView delete;
}

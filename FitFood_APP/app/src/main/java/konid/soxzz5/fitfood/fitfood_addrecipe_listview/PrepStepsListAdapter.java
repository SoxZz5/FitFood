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

public class PrepStepsListAdapter extends ArrayAdapter<PrepStep> {
    private View.OnClickListener clickListener;
    private List<PrepStep> prepSteps;
    private View convertView;
    public PrepStepsListAdapter(Context context, List<PrepStep> prepSteps)
    {
        super(context,0, prepSteps);
    }

    public PrepStepsListAdapter(Context context, List<PrepStep> prepSteps, OnClickListener clickListener)
    {
        super(context,0, prepSteps);
        this.prepSteps = prepSteps;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.addrecipe_step5_listitem,parent, false);
        }
        PrepstepsViewHolder itemHolder = (PrepstepsViewHolder) convertView.getTag();
        if(itemHolder == null)
        {
            itemHolder = new PrepstepsViewHolder();
            itemHolder.nbRowPrep = (TextView) convertView.findViewById(R.id.nbRowPrep);
            itemHolder.name = (TextView) convertView.findViewById(R.id.text);
            itemHolder.delete = (ImageView) convertView.findViewById(R.id.handler);
            convertView.setTag(itemHolder);
        }

        PrepStep prepStep = getItem(position);
        itemHolder.nbRowPrep.setText(String.valueOf(prepStep.getPosition()));
        itemHolder.name.setText(prepStep.getName());

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

class PrepstepsViewHolder {
    public TextView nbRowPrep;
    public TextView name;
    public ImageView delete;
}

package konid.soxzz5.fitfood.fitfood_addrecipe_step;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import konid.soxzz5.fitfood.R;

/**
 * Created by NONE on 10/12/2016.
 */

public class addrecipe_step2 extends Fragment
{
    int type;
    int level;
    RadioGroup rg_typedish;
    SeekBar sb_difficulty;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.addrecipe_step2, container, false);

        type=-1;
        level=-1;
        rg_typedish = (RadioGroup) v.findViewById(R.id.step2_rg_typedish);
        sb_difficulty = (SeekBar) v.findViewById(R.id.step2_sb_difficulty);
        final TextView tv_info_difficulty = (TextView) v.findViewById(R.id.step2_tv_info_difficulty);

        sb_difficulty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                level = i;
                switch (i)
                {
                    case 0:
                        tv_info_difficulty.setText(getResources().getString(R.string.step2_tv_info_0));
                        break;
                    case 1:
                        tv_info_difficulty.setText(getResources().getString(R.string.step2_tv_info_1));
                        break;
                    case 2:
                        tv_info_difficulty.setText(getResources().getString(R.string.step2_tv_info_2));
                        break;
                    case 3:
                        tv_info_difficulty.setText(getResources().getString(R.string.step2_tv_info_3));
                        break;
                    case 4:
                        tv_info_difficulty.setText(getResources().getString(R.string.step2_tv_info_4));
                        break;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rg_typedish.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i)
                {
                    case R.id.step2_rb_starter:
                        type = 1;
                        break;
                    case R.id.step2_rb_maincourse:
                        type = 2;
                        break;
                    case R.id.step2_rb_dessert:
                        type = 3;
                        break;
                    case R.id.step2_rb_sidedish:
                        type = 4;
                        break;
                    case R.id.step2_rb_appetizer:
                        type = 5;
                        break;
                    case R.id.step2_rb_drink:
                        type = 6;
                        break;
                    case R.id.step2_rb_sweets:
                        type = 7;
                        break;
                    case R.id.step2_rb_sauce:
                        type = 8;
                        break;
                }
            }
        });


        return v;
    }

    public int getLevel() {
        return level;
    }

    public int getType() {
        return type;
    }

    public void setLevel(int level) {
        this.level = level;
        sb_difficulty.setProgress(level);

    }

    public void setType(int type) {
        this.type = type;
        rg_typedish.check(type);
    }
}

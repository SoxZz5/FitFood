package konid.soxzz5.fitfood.fitfood_fragment;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ViewFlipper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import konid.soxzz5.fitfood.AddRecipe;
import konid.soxzz5.fitfood.LoginActivity;
import konid.soxzz5.fitfood.MainActivity;
import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.firebase_fitfood.Recipe;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.Ingredient;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.IngredientListAdapter;
import konid.soxzz5.fitfood.fitfood_addrecipe_listview.RecipeShowHomeAdapter;
import konid.soxzz5.fitfood.utils.utils;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private static final int DELAY = 10000;
    private static final int DELAY_MSG = 42;
    private ViewFlipper mViewFlipper;
    private GestureDetector detector;
    private Context mContext;
    private boolean isStarted = false;
    private Handler mHandler;
    private ListView listview_container;
    private List<Recipe> recipeList;
    private DatabaseReference databaseReference;
    private RecipeShowHomeAdapter recipeShowHomeAdapter;
    View.OnClickListener mClickListener;
    private int mLastFirstVisibleItem;
    private boolean valid_firstItem=false;
    private int FirstVisibleItem;
    private LinearLayout slider;
    public boolean valid_recipe = false;
    public boolean iscollapsed = false;
    private ProgressBar listview_pb;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView mPrev = (ImageView) v.findViewById(R.id.slider_bt_prev);
        ImageView mNext = (ImageView) v.findViewById(R.id.slider_bt_next);
        detector = new GestureDetector(getContext(), new SwipeGestureDetector());
        mContext = getContext();
        mViewFlipper = (ViewFlipper) v.findViewById(R.id.slider_view_flipper);
        recipeList = new ArrayList<>();
        recipeShowHomeAdapter = new RecipeShowHomeAdapter(mContext, recipeList,this);
        listview_container = (ListView) v.findViewById(R.id.home_listview_container);
        listview_pb = (ProgressBar) v.findViewById(R.id.listview_pb);
        listview_pb.setVisibility(View.VISIBLE);
        //slider = (LinearLayout) v.findViewById(R.id.slider_layout_container);
        listview_container.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                /*if(iscollapsed)
                {
                    iscollapsed=false;
                    utils.expand(slider);
                }*/
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(mLastFirstVisibleItem<firstVisibleItem)
                {
                    /*if(!iscollapsed) {
                        iscollapsed = true;
                        slider.setVisibility(View.GONE);
                    }*/
                }
                if(mLastFirstVisibleItem>firstVisibleItem)
                {
                    /*if(!iscollapsed) {
                        iscollapsed = true;
                        slider.setVisibility(View.GONE);
                    }*/
                }
                if(FirstVisibleItem == firstVisibleItem && mLastFirstVisibleItem<firstVisibleItem)
                {
                    /*if(iscollapsed)
                    {
                        iscollapsed=false;
                        slider.setVisibility(View.VISIBLE);
                    }*/
                }
                mLastFirstVisibleItem=firstVisibleItem;
            }
        });
        mClickListener = this;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("recipe");
        databaseReference.orderByKey().limitToFirst(20).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("rvalidate").getValue()!=null && String.valueOf(snapshot.child("rvalidate").getValue())!="false") {
                        String recipeID = snapshot.getKey();
                        int temp_category = Integer.parseInt(snapshot.child("rcategory").getValue().toString());
                        String temp_date = snapshot.child("rdate").getValue().toString();
                        String temp_forWho = snapshot.child("rforWho").getValue().toString();
                        int temp_heatHour = Integer.parseInt(snapshot.child("rheatHour").getValue().toString());
                        int temp_heatMinute = Integer.parseInt(snapshot.child("rheatMinute").getValue().toString());
                        int temp_level = Integer.parseInt(snapshot.child("rlevel").getValue().toString());
                        int temp_prepareHour = Integer.parseInt(snapshot.child("rprepareHour").getValue().toString());
                        int temp_prepareMinute = Integer.parseInt(snapshot.child("rprepareMinute").getValue().toString());
                        String temp_dll_link = snapshot.child("rrecipe_download_img_link").getValue().toString();
                        String temp_title = snapshot.child("rtitle").getValue().toString();
                        int temp_type = Integer.parseInt(snapshot.child("rtype").getValue().toString());
                        boolean temp_valide = (Boolean) snapshot.child("rvalidate").getValue();
                        recipeList.add(new Recipe(recipeID, temp_title, temp_category, temp_level, temp_type, temp_prepareHour, temp_prepareMinute, temp_heatHour, temp_heatMinute, temp_forWho, temp_date, temp_valide, temp_dll_link));
                    }
                }
                listview_container.setAdapter(recipeShowHomeAdapter);
                listview_container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {
                        Recipe clickedRecipe = (Recipe) parent.getItemAtPosition(position);
                        System.out.println(clickedRecipe.getRecipeID());
                        ((MainActivity) getActivity()).openRecipe(clickedRecipe.getRecipeID());
                    }
                });
                listview_pb.setVisibility(View.GONE);
                recipeShowHomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                    listview_container.setVisibility(View.GONE);
                ProgressDialog mProgressDialog = new ProgressDialog(getContext());
                mProgressDialog.setMessage("Tentative de reconnexion");
                mProgressDialog.show();
            }
        });



        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == DELAY_MSG) {
                    mViewFlipper.showNext();
                    msg = obtainMessage(DELAY_MSG);
                    sendMessageDelayed(msg, DELAY);
                }
            }
        };

        mViewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                detector.onTouchEvent(event);
                return true;
            }
        });

        mPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopFlipper();
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slider_in_right));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slider_out_right));
                mViewFlipper.showPrevious();
                runFlipper();
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopFlipper();
                mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slider_in_left));
                mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slider_out_left));
                mViewFlipper.showPrevious();
                runFlipper();
            }
        });



        return v;
    }

    class SwipeGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            try {
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    stopFlipper();
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slider_in_left));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slider_out_left));
                    mViewFlipper.showNext();
                    runFlipper();
                    return true;
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    stopFlipper();
                    mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slider_in_right));
                    mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(mContext, R.anim.anim_slider_out_right));
                    mViewFlipper.showPrevious();
                    runFlipper();
                    return true;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    private void runFlipper() {
        if (isStarted == false) {
            Message msg = mHandler.obtainMessage(DELAY_MSG);
            mHandler.sendMessageDelayed(msg, DELAY);
            isStarted = true;
        }
    }

    private void stopFlipper() {
        mHandler.removeMessages(DELAY_MSG);
        isStarted = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        runFlipper();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopFlipper();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //DO
    }
}


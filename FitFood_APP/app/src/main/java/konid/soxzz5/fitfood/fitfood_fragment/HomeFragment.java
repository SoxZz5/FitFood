package konid.soxzz5.fitfood.fitfood_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import konid.soxzz5.fitfood.R;
import konid.soxzz5.fitfood.firebase_fitfood.Recipe;

/**
 * Created by Soxzer on 14/12/2016.
 */

public class HomeFragment extends Fragment {
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ImageView mPrev = (ImageView) v.findViewById(R.id.slider_bt_prev);
        ImageView mNext = (ImageView) v.findViewById(R.id.slider_bt_next);
        detector = new GestureDetector(getContext(), new SwipeGestureDetector());
        mContext = getContext();
        mViewFlipper = (ViewFlipper) v.findViewById(R.id.slider_view_flipper);
        listview_container = (ListView) v.findViewById(R.id.home_listview_container);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("recipe");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    // = snapshot.getValue("what i want !")
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
}


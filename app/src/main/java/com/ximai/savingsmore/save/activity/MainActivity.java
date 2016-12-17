package com.ximai.savingsmore.save.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.ximai.savingsmore.R;
import com.ximai.savingsmore.library.toolbox.LogUtils;
import com.ximai.savingsmore.save.fragment.BusinessFragment;
import com.ximai.savingsmore.save.fragment.MapFragment;
import com.ximai.savingsmore.save.fragment.PersonFragment;
import com.ximai.savingsmore.save.modle.MyUserInfoUtils;

/**
 * Created by caojian on 16/11/21.
 */
public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {
    private PersonFragment personFragment;
    private FragmentManager manager;
    private MapFragment mapFragment;
    public SlidingMenu sm;
    private ImageView login, search;
    private TextView location;
    String city1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (ImageView) findViewById(R.id.login);
        login.setOnClickListener(this);
        search = (ImageView) findViewById(R.id.search);
        location = (TextView) findViewById(R.id.location);
        search.setOnClickListener(this);

        // mActivity = this;

        manager = getSupportFragmentManager();

        // check if the content frame contains the menu frame
        if (findViewById(R.id.menu_frame) == null) {
            setBehindContentView(R.layout.menu_frame);
            getSlidingMenu().setSlidingEnabled(true);
//			getSlidingMenu()
//					.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        } else {
            // add a dummy view
            View v = new View(this);
            setBehindContentView(v);
            getSlidingMenu().setSlidingEnabled(false);
            //getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }

        getSlidingMenu().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        // set the Above View Fragment
//		if (savedInstanceState != null) {
//			if(null!=manager)
//			mContent = (com.shopex.westore.activity.MainTabContentFragment)manager.getFragment(savedInstanceState, "mContent");
//		}

        if (mapFragment == null) {
            mapFragment = new MapFragment(MainActivity.this, new MapFragment.CallBack() {
                @Override
                public void location(String city) {
                    location.setText(city);
                    city1=city;
                }
            });
        }
        this.getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mapFragment).commit();

        // set the Behind View Fragment

        //LogUtils.instance.d("用户类型=" + MyUserInfoUtils.getInstance().myUserInfo.UserTypeName);
        if (null != MyUserInfoUtils.getInstance().myUserInfo && MyUserInfoUtils.getInstance().myUserInfo.UserType.equals("3")) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_frame, new BusinessFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.menu_frame, new PersonFragment()).commit();
        }

        // customize the SlidingMenu
        sm = getSlidingMenu();
        WindowManager wm = (WindowManager) this.getWindowManager();
        sm.setBehindOffset(wm.getDefaultDisplay().getWidth() * 1 / 5);
//		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        sm.setFadeEnabled(false);
        sm.setBehindScrollScale(0.25f);
        sm.setFadeDegree(0.25f);
        sm.setBackgroundResource(R.mipmap.point_default);
        sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            if (sm != null) {
                if (!sm.isShown()) {
                    sm.showContent();
                } else {
                    sm.showMenu();
                }
            }
        }
        if (R.id.list == v.getId()) {
            Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
            intent.putExtra("title", city1);
            startActivity(intent);
        }
        if (R.id.search == v.getId()) {
            Intent intent1 = new Intent(MainActivity.this, SearchActivity.class);
            intent1.putExtra("title", city1);
            startActivity(intent1);
        }
    }


}

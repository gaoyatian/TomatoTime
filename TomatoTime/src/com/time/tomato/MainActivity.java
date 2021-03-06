package com.time.tomato;

import java.util.ArrayList;

import com.time.tomato.adapter.TomatoPagerAdapter;
import com.time.tomato.base.BaseActivity;
import com.time.tomato.fragment.HistoryFragment;
import com.time.tomato.fragment.SettingsFragment;
import com.time.tomato.fragment.StatisticalFragment;
import com.time.tomato.fragment.TodoListFragment;
import com.time.tomato.tools.Constants;
import com.time.tomato.view.ProcessActionBar;
import com.time.tomato.view.ProcessActionBar.DrawerClickListener;
import com.time.tomato.view.smoothprogressbar.SmoothProgressBar;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnClickListener{
	private final static String TAG = "MainActivity";
	private FragmentManager fm;
	private TodoListFragment mTodoListFragment;
	private HistoryFragment mHistoryFragment;
	private StatisticalFragment mStatisticalFragment;
	private SettingsFragment mSettingsFragment;
	private SmoothProgressBar top_progressBar;
	private View actionbar_shadow;
	private ProcessActionBar action_bar;
	LinearLayout ll_view_drawer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);
		initDrawer();
		initFragment();
		initView();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				top_progressBar.setVisibility(View.GONE);
				actionbar_shadow.setVisibility(View.VISIBLE);
			}
		}, 2000);
	}

	private DrawerLayout mDrawerLayout;

	private void initDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		ll_view_drawer = (LinearLayout)findViewById(R.id.ll_view_drawer);
		// mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
		TextView btn_todolist = (TextView)findViewById(R.id.btn_todolist);
		TextView btn_history = (TextView)findViewById(R.id.btn_history);
		TextView btn_statistical = (TextView)findViewById(R.id.btn_statistical);
		TextView btn_settings = (TextView)findViewById(R.id.btn_settings);
		btn_todolist.setOnClickListener(this);
		btn_history.setOnClickListener(this);
		btn_statistical.setOnClickListener(this);
		btn_settings.setOnClickListener(this);
		mDrawerLayout.setDrawerListener(mDrawerListener);
	}

	private void initFragment() {
		fm = getSupportFragmentManager();
		mTodoListFragment = new TodoListFragment();
		mHistoryFragment = new HistoryFragment();
		mStatisticalFragment = new StatisticalFragment();
		mSettingsFragment = new SettingsFragment();
		changeFragment(mTodoListFragment);
	}

	private void initView() {
		action_bar = (ProcessActionBar) findViewById(R.id.action_bar);
		top_progressBar = (SmoothProgressBar) findViewById(R.id.top_progressBar);
		actionbar_shadow = (View) findViewById(R.id.actionbar_shadow);
		actionbar_shadow.setVisibility(View.GONE);
		top_progressBar.setVisibility(View.VISIBLE);
		action_bar.setDrawerClickListener(new DrawerClickListener() {

			@Override
			public void clickDrawer() {
				// TODO Auto-generated method stub
				if (mDrawerLayout.isDrawerOpen(ll_view_drawer)) {
					mDrawerLayout.closeDrawer(ll_view_drawer);
				} else {
					mDrawerLayout.openDrawer(ll_view_drawer);
				}

			}
		});
	}

	public void changeFragment(Fragment fragment) {
		fm.beginTransaction().replace(R.id.content_drawer_frame, fragment).commit();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG, "onDestroy");
		// 退出时候关闭服务
		stopService(new Intent(Constants.INTENT_AUTOSERVICE));
	}

	/** 测拉监听类 */
	public DrawerListener mDrawerListener = new DrawerListener() {
		
		@Override
		public void onDrawerStateChanged(int arg0) {
			
		}
		
		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			
		}
		
		@Override
		public void onDrawerOpened(View arg0) {
			mDrawerLayout.computeScroll();
		}
		
		@Override
		public void onDrawerClosed(View arg0) {
			mDrawerLayout.computeScroll();
		}
	};
	
	public void showProgressBar(boolean show){
		if(show){
			top_progressBar.setVisibility(View.VISIBLE);
		}else{
			top_progressBar.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_todolist:
			changeFragment(mTodoListFragment);
			mDrawerLayout.closeDrawer(ll_view_drawer);
			break;
		case R.id.btn_history:
			changeFragment(mHistoryFragment);
			mDrawerLayout.closeDrawer(ll_view_drawer);
			break;
		case R.id.btn_statistical:
			changeFragment(mStatisticalFragment);
			mDrawerLayout.closeDrawer(ll_view_drawer);
			break;
		case R.id.btn_settings:
			changeFragment(mSettingsFragment);
			mDrawerLayout.closeDrawer(ll_view_drawer);
			break;
		default:
			break;
		}
	}
}

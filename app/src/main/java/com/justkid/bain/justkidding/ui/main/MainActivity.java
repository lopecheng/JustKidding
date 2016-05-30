package com.justkid.bain.justkidding.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.justkid.bain.justkidding.R;
import com.justkid.bain.justkidding.data.local.DribbblePrefes;
import com.justkid.bain.justkidding.data.model.Shot;
import com.justkid.bain.justkidding.data.remote.DribbbleService;
import com.justkid.bain.justkidding.ui.base.BaseActivity;
import com.justkid.bain.justkidding.ui.comment.CommentActivity;
import com.justkid.bain.justkidding.ui.login.LoginActivity;
import com.justkid.bain.justkidding.ui.personal.PersonalPageActivity;
import com.justkid.bain.justkidding.util.DetectNetworkStatus;
import com.justkid.bain.justkidding.util.GlideUtil;
import com.justkid.bain.justkidding.widget.CircleImageView;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity implements MainShotAdapter.OnShotItemClickListener,MainMvpView{
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;

    @Bind(R.id.vNavigation)
    NavigationView navigationView;

    private LinearLayout drawer_head;

    private CircleImageView head_avator;

    private TextView head_name;

    @Bind(R.id.loading)
    ProgressBar loading;

    @Bind(R.id.main_swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.main_recycler)
    RecyclerView recyclerView;

    @Inject
    MainShotAdapter mainShotAdapter;
    @Inject
    MainPresenter mainPresenter;
    @Inject
    DribbblePrefes dribbblePrefes;

    boolean ifFirstRefresh = true;

    private LinearLayoutManager layoutManager;

    public static boolean isLoggedIn = false;

    private int lastVisableItem;
    public static final int LOGIN_REQUESTCODE = 1;

    //private long mBackKeyLastClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        View view = navigationView.getHeaderView(0);
        drawer_head = (LinearLayout) view.findViewById(R.id.drawer_head);
        head_avator = (CircleImageView) view.findViewById(R.id.head_avator);
        head_name = (TextView) view.findViewById(R.id.head_name);
        initial();
        if(DetectNetworkStatus.isNetworkConnected(this)) {
            mainPresenter.loadNewestShots();
            ifFirstRefresh = false;
        }else{
            Toast.makeText(this,"Network Connect Error!",Toast.LENGTH_LONG).show();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isLoggedIn) {
                    if (DetectNetworkStatus.isNetworkConnected(MainActivity.this)) {
                        Intent login = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(login, LOGIN_REQUESTCODE, null);
                    } else {
                        Toast.makeText(MainActivity.this, "Network Connect Error!", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Already Authorization!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void initial(){
        mainPresenter.attachView(this);
        toolbar.setNavigationIcon(R.drawable.ic_navigator_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        drawer_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLoggedIn){
                    Intent intent = new Intent(MainActivity.this, PersonalPageActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title","Personal");
                    bundle.putString("figure_id",String.valueOf(dribbblePrefes.getUserId()));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
//        swipeRefreshLayout.setColorSchemeColors(R.color.style_color_primary_dark,
//                R.color.style_color_primary_dark,
//                R.color.style_color_primary_dark,
//                R.color.style_color_primary_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (DetectNetworkStatus.isNetworkConnected(MainActivity.this)) {
                    if(ifFirstRefresh){
                        swipeRefreshLayout.setRefreshing(true);
                        mainPresenter.loadNewestShots();
                        ifFirstRefresh = false;
                    }else {
                        swipeRefreshLayout.setRefreshing(true);
                        mainPresenter.loadTheNewestShots(DribbbleService.PAGE_NUM,DribbbleService.PER_PAGE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Network Connect Error!", Toast.LENGTH_LONG).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        mainShotAdapter.setOnShotItemClickListener(MainActivity.this);
        recyclerView.setAdapter(mainShotAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisableItem = layoutManager.findLastCompletelyVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisableItem == (mainShotAdapter.getItemCount()-1)) {
                    if (DetectNetworkStatus.isNetworkConnected(MainActivity.this)) {
                        mainPresenter.loadMoreShots();
                    } else {
                        Toast.makeText(MainActivity.this, "Network Connect Error!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            if(isLoggedIn){
                isLoggedIn = false;
                dribbblePrefes.deleteInfoWhenLogout();
                head_avator.setVisibility(View.INVISIBLE);
                head_name.setText("Not logged in");
                Toast.makeText(MainActivity.this,"Log out successfully!",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this,"Please login first!",Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case LOGIN_REQUESTCODE:
                if(resultCode == Activity.RESULT_OK) {
                    isLoggedIn = true;
                    if (isLoggedIn) {
                        head_avator.setVisibility(View.VISIBLE);
                        GlideUtil.loadImageWithTargetView(MainActivity.this, dribbblePrefes.getUserAvator(), new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                head_avator.setImageBitmap(resource);
                            }
                        });
                        head_name.setText(dribbblePrefes.getName());
                    }
                }
        }
    }

    @Override
    public void onHeartClick(View v, int position) {

    }

    @Override
    public void onCommentsClick(View v, int position) {
        Intent intent = new Intent(MainActivity.this, CommentActivity.class);
        intent.putExtra("shotId",mainShotAdapter.getListItem(position).id);
        startActivity(intent);
    }

    @Override
    public void onMoreClick(View v, int position) {

    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadFinish() {
        loading.setVisibility(View.GONE);
    }

    @Override
    public void loadTheNewestFinish() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        Toast.makeText(this,"Loading the shots missing the problem.",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShots(List<Shot> shots,int page) {
        mainShotAdapter.addMoreData(shots,page);
        mainShotAdapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyShots() {
        mainShotAdapter.setShots(Collections.<Shot>emptyList());
    }

    @Override
    public void showIsNewest() {
        Toast.makeText(this,"It's the newest data !",Toast.LENGTH_SHORT).show();
    }

    @Override
    public long getFirstShotId() {
        return mainShotAdapter.getListItem(0).id;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPresenter.detachView();
    }

//    @Override
//    public void onBackPressed() {
//        if(mBackKeyLastClickTime <= 0){
//            Toast.makeText(this,"Press the back key once again and exit the application!",Toast.LENGTH_SHORT).show();
//            mBackKeyLastClickTime = System.currentTimeMillis();
//        }else{
//            long currentClickTime = System.currentTimeMillis();
//            if(currentClickTime-mBackKeyLastClickTime<1000){
//                finish();
//            }else{
//                Toast.makeText(this,"Press the back key once again and exit the application!",Toast.LENGTH_SHORT).show();
//                mBackKeyLastClickTime = currentClickTime;
//            }
//        }
//    }
}


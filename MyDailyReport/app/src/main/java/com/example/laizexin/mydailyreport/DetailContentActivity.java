package com.example.laizexin.mydailyreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;

import bean.ZhDetailContentBean;
import pl.droidsonroids.gif.GifImageView;
import retrofit.MyRetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.MyServices;
import utils.HttpUtils;

/**
 * Created by Administrator on 2017/9/11 0011.
 */
public class DetailContentActivity extends AppCompatActivity {

    private MyRetrofitManager retrofitManager;
    private MyServices services;

    private ImageView mIv;
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout mAppBarLayout;
    private WebView mWebview;
    private CoordinatorLayout mCoordinatorLayout;
    private FrameLayout loading_view;
    private GifImageView giv;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_content);
        initView();
        initData();
        getData();
        Logger.init("DetailContentActivity");
    }

    private void initData() {
        retrofitManager = MyRetrofitManager.getInstanc();
        services = retrofitManager.create(MyServices.class);

        id = getIntent().getIntExtra("id", 0);
    }


    public static void toDetailContentActivity(Context context, int id) {
        Intent intent = new Intent(context, DetailContentActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    private void getData() {
        setViewChange(false);

        if(HttpUtils.isNetworkConnected(this)) {
            services.getDetailContent(id).enqueue(new Callback<ZhDetailContentBean>() {
                @Override
                public void onResponse(Call<ZhDetailContentBean> call, Response<ZhDetailContentBean> response) {

                    setViewChange(true);

                    Logger.i(response.body().toString());
                    String title = response.body().getTitle();
                    String content = response.body().getBody();
                    String image = response.body().getImage();

                    //设置标题
                    mCollapsingToolbarLayout.setTitle(title);
                    mCollapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(DetailContentActivity.this, R.color.white));
                    mCollapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(DetailContentActivity.this, R.color.white));

                    //设置图片
                    Glide.with(DetailContentActivity.this)
                            .load(image)
                            .placeholder(R.mipmap.loading)
                            .error(R.mipmap.error)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(mIv);

                    //设置CSS
                    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";

                    String html = "<html><head>" + css + "</head><body>" + content + "</body></html>";
                    html = html.replace("<div class=\"img-place-holder\">", "");
                    //设置内容
                    mWebview.loadDataWithBaseURL("about:blank", html, "text/html", "utf-8", null);
                }

                @Override
                public void onFailure(Call<ZhDetailContentBean> call, Throwable t) {

                }
            });
        }else{
            setErrorView();
        }
    }

    private void setErrorView() {
        loading_view.setVisibility(View.VISIBLE);
        loading_view.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        mCoordinatorLayout.setVisibility(View.GONE);
        giv.setImageResource(R.mipmap.error_loading);
    }

    private void setViewChange(Boolean isConnect){
        loading_view.setVisibility(isConnect? View.GONE: View.VISIBLE);
        mCoordinatorLayout.setVisibility(isConnect? View.VISIBLE: View.GONE);
    }

    private void initView() {
        mIv = (ImageView) findViewById(R.id.iv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mWebview = (WebView) findViewById(R.id.webview);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mCoordinatorLayout.setVisibility(View.GONE);
        loading_view = (FrameLayout) findViewById(R.id.loading_view);
        giv = (GifImageView) findViewById(R.id.giv);

        //Toolbar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //CollapsingToolbarLayout 与Toolbar交互
        mCollapsingToolbarLayout.setTitle("This is Title");

        //webView
        mWebview.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_to_left_from_right);
    }
}

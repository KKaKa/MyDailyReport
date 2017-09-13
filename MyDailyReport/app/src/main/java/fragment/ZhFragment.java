package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.laizexin.mydailyreport.DetailContentActivity;
import com.example.laizexin.mydailyreport.R;
import com.orhanobut.logger.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import adapter.TitleAdapter;
import bean.ZhBaseBean;
import retrofit.MyRetrofitManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import services.MyServices;
import utils.TimeUtils;

/**
 * Created by laizexin on 2017/9/12.
 */

public class ZhFragment extends Fragment {
    private static final String ARG_POSITION = "position";

    private MyRetrofitManager retrofitManager;
    private MyServices services;
    private TitleAdapter adapter;

    private ListView mLvContent;
    private SmartRefreshLayout refreshLayout;

    private List<ZhBaseBean.StoriesBean> contentes;

    private int time;

    public static ZhFragment newInstance(int position) {
        ZhFragment fragment = new ZhFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_zh, container, false);
        initView(v);
        initListener();
        return v;
    }

    private void initView(View v) {
        mLvContent = (ListView) v.findViewById(R.id.lv_content);
        mLvContent.setAdapter(adapter);
        refreshLayout = (SmartRefreshLayout) v.findViewById(R.id.refreshLayout);
    }

    private void initListener() {
        mLvContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                getDetailContent(((ZhBaseBean.StoriesBean)adapterView.getItemAtPosition(position)).getId());
            }
        });

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                time = Integer.parseInt(TimeUtils.getTime());
                getData();
            }
        });


        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                time -= 1;
                loadMore();
            }
        });

        if(getArguments().getInt(ARG_POSITION) != 0){
            refreshLayout.setEnableLoadmore(false);
        }else{
            refreshLayout.setEnableLoadmore(true);
        }

        refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setdata();
        getData();
    }

    private void setdata() {
        contentes = new ArrayList<>();
        adapter = new TitleAdapter(getActivity(),contentes);
        time = Integer.parseInt(TimeUtils.getTime());
        retrofitManager = MyRetrofitManager.getInstanc();
        services = retrofitManager.create(MyServices.class);
    }

    private void getData() {
        if(getArguments().getInt(ARG_POSITION) != 0){
            getThemeNews(getArguments().getInt(ARG_POSITION));
        }else{
            Call<ZhBaseBean> call = services.getNews();
            call.enqueue(new Callback<ZhBaseBean>() {
                @Override
                public void onResponse(Call<ZhBaseBean> call, Response<ZhBaseBean> response) {
                    contentes = response.body().getStories();
                    adapter.update(contentes);
                    refreshLayout.finishRefresh();
                }

                @Override
                public void onFailure(Call<ZhBaseBean> call, Throwable t) {
                    refreshLayout.finishRefresh();
                }
            });
        }
    }

    private void loadMore(){
        Logger.i("time = "+time);

        refreshLayout.setEnableLoadmore(true);
        services.getBeforeNews(time).enqueue(new Callback<ZhBaseBean>() {
            @Override
            public void onResponse(Call<ZhBaseBean> call, Response<ZhBaseBean> response) {
                if(response.body() != null && response.body().getStories() != null)
                    contentes.addAll(response.body().getStories());
                adapter.update(contentes);
                refreshLayout.finishLoadmore();
            }

            @Override
            public void onFailure(Call<ZhBaseBean> call, Throwable t) {
                refreshLayout.finishLoadmore();
            }
        });
    }

    private void getDetailContent(int id) {
        DetailContentActivity.toDetailContentActivity(getActivity(),id);
    }

    private void getThemeNews(int theme_id){
        services.getThemeNews(theme_id).enqueue(new Callback<ZhBaseBean>() {
            @Override
            public void onResponse(Call<ZhBaseBean> call, Response<ZhBaseBean> response) {
                if(response.body() != null && response.body().getStories() != null)
                    contentes = response.body().getStories();
                adapter.update(contentes);
                refreshLayout.finishRefresh();
            }

            @Override
            public void onFailure(Call<ZhBaseBean> call, Throwable t) {
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

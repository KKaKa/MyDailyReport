package services;

import bean.ZhBaseBean;
import bean.ZhDetailContentBean;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
public interface MyServices {

    //获取最新消息
    @GET("4/news/latest")
    Call<ZhBaseBean> getNews();

    //获取对应消息详情
    @GET("4/news/{newsId}")
    Call<ZhDetailContentBean> getDetailContent(@Path("newsId") int newId);

    //获取过往消息
    @GET("4/news/before/{date}")
    Call<ZhBaseBean> getBeforeNews(@Path("date") int date);

    //获取对应主题的消息
    @GET("4/theme/{theme_id}")
    Call<ZhBaseBean> getThemeNews(@Path("theme_id") int theme_id);
}

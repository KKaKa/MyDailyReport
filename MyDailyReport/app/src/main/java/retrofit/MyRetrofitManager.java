package retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/5 0005.
 */
public class MyRetrofitManager {
    private static final String baseUrl = "https://news-at.zhihu.com/api/";
    private static int DEFAULT_TIME_OUT = 10;

    private Retrofit retrofit;

    private MyRetrofitManager(){
        // 创建 OKHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//连接超时时间
                .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)//写操作 超时时间
                .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作超时时间

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    private static class SingletonHolder{
        private static final MyRetrofitManager INSTANCE = new MyRetrofitManager();
    }

    /**
     * 获取RetrofitManager
     * @return
     */
    public static MyRetrofitManager getInstanc(){
        return SingletonHolder.INSTANCE;
    }

    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }
}

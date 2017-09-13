package app;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by laizexin on 2017/9/12.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.init("MyDailyReport");
    }
}

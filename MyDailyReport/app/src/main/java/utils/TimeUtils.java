package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by laizexin on 2017/9/12.
 */

public class TimeUtils {

    public static String getTime(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(new Date());
    }
}

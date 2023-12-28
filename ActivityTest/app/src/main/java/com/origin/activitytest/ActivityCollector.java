package com.origin.activitytest;

import android.app.Activity;
import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    /*将活动加入数组列表中*/
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }
    /*将活动从数组列表中移除*/
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    /*将活动遍历销毁并删除数组*/
    public static void finishAll( ) {
        for (Activity activity:activities) {
            if(!activity.isFinishing())
                activity.finish();
        }
        activities.clear();
    }
}

package com.crazydwarf.comm_library.Utilities;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * class ActivityManager
 *
 * @author chimaeraqm
 * @date 2018/10/15
 *
 * this class is responsible to manager new instance activiy and remove existed activiy.
 */
public class ActivityManager
{
    private ActivityManager(){}

    private static ActivityManager activityManager = new ActivityManager();
    private static List<Activity> activityStack = new ArrayList<Activity>();

    public static ActivityManager getInstance()
    {
        return activityManager;
    }

    public void addActivity(Activity activity)
    {
        activityStack.add(activity);
    }

    public void removeActivity(Activity activity)
    {
        activityStack.remove(activity);
    }

    //结束所有activity
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++)
        {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
}

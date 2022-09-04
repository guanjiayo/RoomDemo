package zs.xmx.room;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;

import java.lang.reflect.InvocationTargetException;

public final class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;


    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(final Context context) {
        if (context == null) {
            init(getApplicationByReflect());
            return;
        }
        init((Application) context.getApplicationContext());
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param app application
     */
    public static void init(final Application app) {
        if (sApplication == null) {
            if (app == null) {
                sApplication = getApplicationByReflect();
            } else {
                sApplication = app;
            }
        } else {
            if (sApplication.equals(app)) return;
           // ActivityUtils.INSTANCE.unInit(sApplication);
            sApplication = app;
        }
      //  ActivityUtils.INSTANCE.init(sApplication);
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApplication() {
        if (sApplication != null) return sApplication;
        return getApplicationByReflect();
    }


    /**
     * 反射获取Application
     */
    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    /**
     * 退出应用程序
     * 需要加权限:
     * <p>
     * <uses-permission android:name="android.permission.KILL_BACKGROUND_PROCESSES"/>
     */
    @SuppressLint("ObsoleteSdkInt")
    public static void exitApp() {
        try {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ECLAIR_MR1) {
              //  ActivityUtils.INSTANCE.finishAllActivities();
                android.os.Process.killProcess(android.os.Process.myPid());    //获取PID
                System.exit(0);
            } else {
              //  ActivityUtils.INSTANCE.finishAllActivities();
                ActivityManager activityMgr = (ActivityManager) sApplication.getSystemService(Context.ACTIVITY_SERVICE);

                if (activityMgr != null) {
                //    activityMgr.killBackgroundProcesses(sApplication.getPackageName());
                }
                System.exit(0);
            }
            //退出应用,顺便做些gc优化
            System.gc();
            System.runFinalization();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

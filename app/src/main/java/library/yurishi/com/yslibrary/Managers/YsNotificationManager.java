package library.yurishi.com.yslibrary.Managers;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import javax.security.auth.Destroyable;

public class YsNotificationManager {

    private static YsNotificationManager _Instance;

    /**
     * 管理器对象
     */
    private NotificationManager manager;
    /**
     * 消息构建者对象
     */
    private NotificationCompat.Builder builder;


    public static YsNotificationManager get_Instance(Context context) {
        return _Instance == null ? new YsNotificationManager(context) : _Instance;
    }

    public YsNotificationManager(Context context) {
        initManager(context);
    }

    private void initManager(Context context) {

        builder = new NotificationCompat.Builder(context, "default").setContentText("真的内容");
    }

    public NotificationCompat.Builder getBuilder() {
        return builder;
    }

    public void setBuilder(NotificationCompat.Builder builder) {
        this.builder = builder;
    }

    public void sendNotification() {
        manager.notify(10, getBuilder().build());
    }

    public void cleanManager() {
        manager = null;
        builder = null;
        _Instance = null;

    }
}

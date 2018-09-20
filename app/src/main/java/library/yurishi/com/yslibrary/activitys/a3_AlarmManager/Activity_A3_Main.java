package library.yurishi.com.yslibrary.activitys.a3_AlarmManager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import library.yurishi.com.yslibrary.Bases.BaseActivity;
import library.yurishi.com.yslibrary.Managers.YsNotificationManager;
import library.yurishi.com.yslibrary.R;
import library.yurishi.com.yslibrary.YurishiModel;

public class Activity_A3_Main extends BaseActivity {

    public final static String INTENT_ALARM_FLAG = "YurishiIntentAlarm";
    private AppCompatSpinner s_spinner;


    private List<YurishiModel.A3Model_One> alarmDataList;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_a3_main;
    }

    @Override
    protected void beforeInitView() {
        alarmDataList = new ArrayList<>();
    }

    @Override
    protected void initView() {
        Toolbar tb_title = findViewById(R.id.tb_title);
        s_spinner = findViewById(R.id.s_sp);

        tb_title.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickListenerEvent(view.getId());
            }
        });
    }

    @Override
    protected void initData() {
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getSpinnerData());
        s_spinner.setAdapter(arrayAdapter);
        s_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                OnSpinnerItemClickListener(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        am = (AlarmManager) getSystemService(ALARM_SERVICE);
    }


    private AlarmManager am;

    /**
     * 初始化Alarm管理器
     */
    private void setAlarm(int second) {
        Intent intent = new Intent(INTENT_ALARM_FLAG);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
//        long intervalMillis = second * 1000;//秒数
        long intervalMillis = System.currentTimeMillis() + (second * 1000);
        am.set(AlarmManager.RTC_WAKEUP, intervalMillis, pi);
    }

    /**
     * 控件点击事件
     *
     * @param vId 控件ID
     */
    private void OnClickListenerEvent(int vId) {
        switch (vId) {
            case R.id.tb_title: {
                //返回键
                this.finish();
            }
            break;
        }
    }

    private boolean isFirst = true;

    /**
     * 下拉选择器选择监听
     *
     * @param position 选中坐标
     */
    private void OnSpinnerItemClickListener(int position) {
        YurishiModel.A3Model_One data = alarmDataList.get(position);
        setAlarm(data.index);
    }


    private List<String> getSpinnerData() {
        ListAdd(alarmDataList, "五秒", 5);
        ListAdd(alarmDataList, "十秒", 10);
        ListAdd(alarmDataList, "三十秒", 30);
        ListAdd(alarmDataList, "一分钟", 60);

        List<String> returnList = new ArrayList<>();

        for (YurishiModel.A3Model_One item : alarmDataList) {
            returnList.add(item.title);
        }
        return returnList;
    }

    private void ListAdd(List<YurishiModel.A3Model_One> list, String title, int index) {
        YurishiModel.A3Model_One data = new YurishiModel.A3Model_One();
        data.title = (title);
        data.index = (index);
        try {
            list.add(data);
        } catch (NullPointerException e) {
            showMsgLong("The List is Null,Plz check the code");
        }
    }


    /**
     * 闹钟广播接收器
     */
    public static class AlarmReceiver extends BroadcastReceiver {

        /**
         * 管理器对象
         */
        private NotificationManager manager;
        /**
         * 消息构建者对象
         */
        private NotificationCompat.Builder builder;

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), INTENT_ALARM_FLAG)) {
                if (manager == null)
                    initManager(context);
                builder = new NotificationCompat.Builder(context, "default");
                builder
                        .setContentTitle("主人主人")
                        .setContentText("来电话啦")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setDefaults(Notification.DEFAULT_ALL);
                manager.notify(0, builder.build());
            }
        }

        private void initManager(Context context) {
            manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

    }


}

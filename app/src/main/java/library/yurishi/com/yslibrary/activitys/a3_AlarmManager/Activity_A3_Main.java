package library.yurishi.com.yslibrary.activitys.a3_AlarmManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.OnItemSelected;
import library.yurishi.com.yslibrary.Bases.BaseActivity;
import library.yurishi.com.yslibrary.R;
import library.yurishi.com.yslibrary.YurishiModel;

public class Activity_A3_Main extends BaseActivity {

    public final static String INTENT_ALARM_FLAG = "YurishiIntentAlarm";
    private AppCompatSpinner s_spinner;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_a3_main;
    }

    @Override
    protected void beforeInitView() {

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
        ArrayAdapter<YurishiModel.A3Model_One> arrayAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_dropdown_item,
                        getSpinnerData());
        s_spinner.setAdapter(arrayAdapter);
        s_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OnSpinnerItemClickListener(i);
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
        long intervalMillis = second * 1000;//秒数
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

    /**
     * 下拉选择器选择监听
     *
     * @param position 选中坐标
     */
    private void OnSpinnerItemClickListener(int position) {
        YurishiModel.A3Model_One data = (YurishiModel.A3Model_One) s_spinner.getItemAtPosition(position);

        showMsgShort(data.title);
        setAlarm(data.index);
    }


    private List<YurishiModel.A3Model_One> getSpinnerData() {
        List<YurishiModel.A3Model_One> dataList = new ArrayList<>();
        ListAdd(dataList, "五秒", 5);
        ListAdd(dataList, "十秒", 10);
        ListAdd(dataList, "三十秒", 30);
        ListAdd(dataList, "一分钟", 60);

        return dataList;
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
    public class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Objects.equals(intent.getAction(), INTENT_ALARM_FLAG)) {
                Toast.makeText(context, "主人主人,来电话啦", Toast.LENGTH_LONG).show();
            }
        }
    }


}

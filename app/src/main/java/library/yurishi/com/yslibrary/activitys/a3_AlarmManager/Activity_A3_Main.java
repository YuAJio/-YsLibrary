package library.yurishi.com.yslibrary.activitys.a3_AlarmManager;

import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnItemSelected;
import library.yurishi.com.yslibrary.Bases.BaseActivity;
import library.yurishi.com.yslibrary.R;
import library.yurishi.com.yslibrary.YurishiModel;

public class Activity_A3_Main extends BaseActivity {

    private Toolbar tb_title;
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
        tb_title = findViewById(R.id.tb_title);
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
}

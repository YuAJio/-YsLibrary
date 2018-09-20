package library.yurishi.com.yslibrary.activitys;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import library.yurishi.com.yslibrary.Bases.BaseActivity;
import library.yurishi.com.yslibrary.Bases.BaseRvAdapter;
import library.yurishi.com.yslibrary.Interfaces.IRvAdapterItemClickListener;
import library.yurishi.com.yslibrary.R;
import library.yurishi.com.yslibrary.activitys.a1_VerificationCodeFill.Activity_A1_Main;
import library.yurishi.com.yslibrary.activitys.a2_OkHttpRequest.Activity_A2_Main;
import library.yurishi.com.yslibrary.activitys.a3_AlarmManager.Activity_A3_Main;

public class Activity_MainHome extends BaseActivity {
    @BindView(R.id.rv_menu_list)
    RecyclerView rv_list;

    private MenuAdapter adapter;
    private List<MenuModel> list_data;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void beforeInitView() {
        adapter = new MenuAdapter(this);
        list_data = new ArrayList<>();
    }

    @Override
    protected void initView() {
        rv_list.setAdapter(adapter);
        rv_list.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {

        addMenuData("获取短信验证码赋值", 0x010);
        addMenuData("OKHttp请求", 0x020);
        addMenuData("AlarmManager", 0x030);

        adapter.setDataList(list_data);
        adapter.setClickListener(new IRvAdapterItemClickListener() {
            @Override
            public void onItemClick(int position) {
                onRvItemClick(position);
            }

            @Override
            public void onItemLongClick(int position) {
            }
        });
    }

    private void addMenuData(String name, int location) {
        MenuModel model1 = new MenuModel();
        model1.name = (name);
        model1.location = (location);
        list_data.add(model1);
    }

    private void onRvItemClick(int position) {
        MenuModel data = list_data.get(position);
        switch (data.location) {
            case 0x010: {
                //前往短信验证码获取页面
                startActivity(new Intent(this, Activity_A1_Main.class));
            }
            break;
            case 0x020: {
                //前往HTTP访问页面
                startActivity(new Intent(this, Activity_A2_Main.class));
            }
            break;
            case 0x030: {
                //前往定时器任务页面
                startActivity(new Intent(this, Activity_A3_Main.class));
            }
            break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            adapter.setDataList(list_data);
            return false;
        } else
            return super.onKeyDown(keyCode, event);
    }

    private class MenuModel {
        private String name;
        private int location;
    }

    private class MenuAdapter extends BaseRvAdapter<MenuModel> {

        public MenuAdapter(Context context) {
            this.context = context;
        }


        @Override
        protected void abOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            ViewHolder holder = (ViewHolder) viewHolder;
            MenuModel data = list_data.get(position);

            Log.i("Data", list_data.toString());
            holder.tv_MenuName.setText(data.name);
            holder.iv_MenuBack.setTag(data.location);
        }

        @Override
        protected RecyclerView.ViewHolder abOnCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_homemenu, parent, false));
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tv_MenuName;
            private ImageView iv_MenuBack;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_MenuName = itemView.findViewById(R.id.tv_menu);
                iv_MenuBack = itemView.findViewById(R.id.iv_menu);
            }
        }

    }

}

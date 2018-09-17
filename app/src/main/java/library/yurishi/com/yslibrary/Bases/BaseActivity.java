package library.yurishi.com.yslibrary.Bases;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.compiler.ButterKnifeProcessor;

public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentLayout() != 0)
            setContentView(getContentLayout());
        beforeInitView();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

//        if (getContentLayout() != 0)
//            setContentView(getContentLayout());
//        beforeInitView();
//        initView();
//        initData();

    }

    protected abstract int getContentLayout();

    protected abstract void beforeInitView();

    protected abstract void initView();

    protected abstract void initData();

    protected void showMsgShort(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showMsgLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}

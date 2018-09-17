package library.yurishi.com.yslibrary.activitys.a1_VerificationCodeFill;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import library.yurishi.com.yslibrary.Bases.BaseActivity;
import library.yurishi.com.yslibrary.Interfaces.ISMSContentCallBack;
import library.yurishi.com.yslibrary.R;
import library.yurishi.com.yslibrary.Utils.SmsContent;

public class Activity_A1_Main extends BaseActivity {
    @BindViews({R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6})
    List<TextView> list_tv;
//    @BindView(R.id.tv_1)
//    TextView tv_1;
//    @BindView(R.id.tv_2)
//    TextView tv_2;
//    @BindView(R.id.tv_3)
//    TextView tv_3;
//    @BindView(R.id.tv_4)
//    TextView tv_4;
//    @BindView(R.id.tv_5)
//    TextView tv_5;
//    @BindView(R.id.tv_6)
//    TextView tv_6;

    private SmsContent content;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_a1_main;
    }

    @Override
    protected void beforeInitView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if (this.checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);

        content = new SmsContent(new Handler(), this);
        content.setListener(new ISMSContentCallBack() {
            @Override
            public void GetSMSBodyContent(String content) {
                getSmsBodyAndSet(content);
            }
        });
        this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 设置获取短信的值
     *
     * @param txt 验证码内容
     */
    private void getSmsBodyAndSet(String txt) {
        char[] chars = txt.toCharArray();

        try {
            for (int i = 0; i < chars.length; i++) {
                list_tv.get(i).setText(chars[i]);
            }
        } catch (Exception e) {
        } finally {
            Toast.makeText(this, "短息内容是:" + txt, Toast.LENGTH_LONG).show();
        }

    }


    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {

            }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.getContentResolver().unregisterContentObserver(content);
    }


}

package library.yurishi.com.yslibrary.activitys;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.logging.Handler;

import library.yurishi.com.yslibrary.Bases.BaseActivity;
import library.yurishi.com.yslibrary.R;

public class Activity_FlashScreen extends BaseActivity {

    @Override
    protected int getContentLayout() {
        return 0;
    }

    @Override
    protected void beforeInitView() {
        ImageView imageView = new ImageView(this);
        this.setContentView(processFlashImage(imageView));

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(Activity_FlashScreen.this, Activity_MainHome.class));
            }
        }, 2 * 1000);

    }

    private ImageView processFlashImage(ImageView imageView) {
        if (imageView == null)
            imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setBackgroundResource(R.mipmap.pic_flash);

        return imageView;
    }
}

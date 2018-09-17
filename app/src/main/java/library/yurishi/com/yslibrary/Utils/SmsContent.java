package library.yurishi.com.yslibrary.Utils;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.RowSetListener;

import library.yurishi.com.yslibrary.Interfaces.ISMSContentCallBack;

/**
 * 短信获取工具
 */
public class SmsContent extends ContentObserver {

    private Context context;
    private ISMSContentCallBack callBack;

    public SmsContent(Handler handler, Context context) {
        super(handler);
        this.context = context;
    }

    public void setListener(ISMSContentCallBack ismsContentCallBack) {
        this.callBack = ismsContentCallBack;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Log.i("SMSTest", "Begin");

        Cursor cursor = context.
                getContentResolver()
                .query(Uri.parse("content://sms/inbox"),
                        new String[]{"_id", "address", "read", "body"},
                        null, null, "_id desc");
        Log.i("SMSTest", "cursor.isBeforeFirst(): " + cursor.isBeforeFirst() + " cursor.getCount(): " + cursor.getCount());
        if (cursor.getCount() > 0) {
            cursor.moveToNext();
            int smsbodyColumn = cursor.getColumnIndex("body");
            String smsBody = cursor.getString(smsbodyColumn);
            Log.i("SMSTest", "smsBody = " + smsBody);

            String content = getDynamicPassword(smsBody);
            if (callBack != null)
                callBack.GetSMSBodyContent(smsBody);
        }

    }

    public static String getDynamicPassword(String str) {
        Pattern continuousNumberPattern = Pattern.compile("[^0-9]");
        Matcher m = continuousNumberPattern.matcher(str);
        String dynamicPassword = "";
        while (m.find()) {
            if (m.group().length() == 6) {
                System.out.print(m.group());
                dynamicPassword = m.group();
            }
        }
        return dynamicPassword;

    }
}

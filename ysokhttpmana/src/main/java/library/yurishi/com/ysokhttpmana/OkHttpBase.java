package library.yurishi.com.ysokhttpmana;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.Dictionary;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class OkHttpBase {

    protected String _BaseUrl;

    private OkHttpClient _okHttpClient;


    protected OkHttpClient getClient() {
        if (_okHttpClient == null)
            _okHttpClient = new OkHttpClient();
        return _okHttpClient;
    }


    /**
     * 异步Get请求网络
     *
     * @param api             网络API
     * @param params          参数集合
     * @param requestCallBack 请求回调
     */
    protected void asyncGetRequest(String api, Map<String, Object> params, Callback requestCallBack) {
        String params_Str;
        StringBuilder params_StrB = new StringBuilder("");
        if (params != null) {
            params_StrB.append("?");
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() == null)
                    params_StrB.append(entry.getKey()).append("=null&");
                else
                    params_StrB.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }

        String realRequestUrl = _BaseUrl + api;//真正的访问地址

        if (!isNullOrEmpty(params_StrB.toString()))
            params_Str = realRequestUrl + params_StrB.toString();
        else
            params_Str = realRequestUrl;
        Request request = new Request.Builder().url(params_Str).method("GET", null).build();

        Call call = getClient().newCall(request);
        call.enqueue(requestCallBack);

    }

    /**
     * 异步Post网络请求方法
     *
     * @param api             网络API
     * @param params          参数集合
     * @param requestCallBack 请求回调
     */
    protected void asyncPostRequest(String api, Map<String, Object> params, Callback requestCallBack) {
        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, Object> entry :
                params.entrySet()) {
            builder.add(entry.getKey(), entry.getValue().toString());
        }
        RequestBody requestBody = builder.build();

        Request request = new Request.Builder().url(_BaseUrl + api).post(requestBody).build();
        Call call = getClient().newCall(request);
        call.enqueue(requestCallBack);
    }

    /**
     * 异步Post网络请求方法
     *
     * @param api             网络API
     * @param jsonParams      Json参数集合
     * @param requestCallBack 请求回调
     */
    protected void asyncPostRequest(String api, String jsonParams, Callback requestCallBack) {
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");//"类型,字节码"
        RequestBody requestBody = RequestBody.create(mediaType, jsonParams);
        Request request = new Request.Builder().url(_BaseUrl + api).post(requestBody).build();
        Call call = getClient().newCall(request);
        call.enqueue(requestCallBack);
    }

    /**
     * 异步Post上传本地文件方法
     *
     * @param api             网络API
     * @param file            本地文件
     * @param requestCallBack 请求回调
     */
    protected void asyncPostLocalFile(String api, File file, Callback requestCallBack) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        Request request = new Request.Builder().url(_BaseUrl + api).post(requestBody).build();
        Call call = getClient().newCall(request);
        call.enqueue(requestCallBack);
    }




    /**
     * 字符串是否是空
     *
     * @param value 字符串
     * @return true:空 false:不空
     */
    private boolean isNullOrEmpty(String value) {
        return value == null || value.equals("");
    }

}

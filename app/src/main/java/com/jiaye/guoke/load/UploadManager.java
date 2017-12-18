package com.jiaye.guoke.load;

import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static javax.xml.transform.OutputKeys.ENCODING;

/**
 * Created by Administrator on 2017/12/18.
 */

public class UploadManager {
    private final String QINIU_ACCESSKEY = "HWBRnB2D2TfP_cRB6qkeyHmHwCE-Yh0QVI8ZJ-4L";
    private final String QINIU_SECRETKEY = "2vy9y2JdSsR-B72WZpLD2VNlTaS0zJEf7dMSZHvj";
    private static UploadManager instance = null;
    com.qiniu.android.storage.UploadManager uploadManager;
    private String key = "zengtian1211@163.com";
    private String screct = "i";
    private UploadManager(){
        Configuration config = new Configuration.Builder()
                .chunkSize(512 * 1024)        // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)   // 启用分片上传阀值。默认512K
                .connectTimeout(10)           // 链接超时。默认10秒
                .useHttps(true)               // 是否使用https上传域名
                .responseTimeout(60)          // 服务器响应超时。默认60秒
                .zone(FixedZone.zone0)        // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        uploadManager = new com.qiniu.android.storage.UploadManager(config);
    }
    public static UploadManager getInstance(){
        if(instance == null){
            instance = new UploadManager();
        }
        return instance;
    }

    public void uploadFile(String fileName,@NonNull final IUpload callBack){
        File file = new File(fileName);
        if(!file.exists()){
            callBack.onFailed("文件不存在");
            return;
        }
        String token = getToken();
        uploadManager.put(file, null, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                if(info.isOK()) {

                } else {
                    Log.i("qiniu", "Upload Fail");
                    //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                }
            }
        },null);
    }

    private String getToken(){
        try {
            JSONObject _json = new JSONObject();
            long _dataline = System.currentTimeMillis() / 1000 + 3600;
            _json.put("deadline", _dataline);// 有效时间为一个小时
            _json.put("scope", "kymobile");
            String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                    .toString().getBytes());
            byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, QINIU_SECRETKEY);
            String _encodedSign = UrlSafeBase64.encodeToString(_sign);
            String _uploadToken = QINIU_ACCESSKEY + ':' + _encodedSign + ':'
                    + _encodedPutPolicy;
            return _uploadToken;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     *
     使用 HMAC-SHA1 签名方法对encryptText进行签名
     *
     * @param encryptText
     *            被签名的字符串
     * @param encryptKey
     *            密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        String type = "HmacSHA1";
        SecretKeySpec secret = new SecretKeySpec(encryptKey.getBytes(), type);
        Mac mac = Mac.getInstance(type);
        mac.init(secret);
        byte[] digest = mac.doFinal(encryptText.getBytes());
        // 完成 Mac 操作
        return digest;
    }
}

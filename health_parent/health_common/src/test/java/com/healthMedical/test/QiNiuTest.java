package com.healthMedical.test;


import com.google.gson.Gson;
import com.healthMedical.utils.QiniuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class QiNiuTest {
    //使用七牛云提供的SDK实现本地图片传到七牛云服务器
    @Test
    public void test1(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "kyNHra9byjQrV7SQGb-mkoIWtBPsR9YgcHr45Ydk";
        String secretKey = "9opx7RkUzV9GAV9wmU-sZctZpq8i6SufdeiNhfJ-";
        String bucket = "qiniuyunspace-learn-fan";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:\\Users\\FanJiangcheng\\Desktop\\头像\\hm2.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = "paidaxing";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }
    @Test
    public void test2(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "kyNHra9byjQrV7SQGb-mkoIWtBPsR9YgcHr45Ydk";
        String secretKey = "9opx7RkUzV9GAV9wmU-sZctZpq8i6SufdeiNhfJ-";
        String bucket = "qiniuyunspace-learn-fan";
        String key = "paidaxing";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
    @Test
    public void del(){
        QiniuUtils.deleteFileFromQiniu("932cd2d1-2175-46ea-a71c-d4221f626384.jpg");
    }
}

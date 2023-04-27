package com.backend.tjtablepartyspringboot.util;

import com.backend.tjtablepartyspringboot.config.TencentCosConfig;
import com.backend.tjtablepartyspringboot.config.TencentCosProperties4Picture;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class FileUtil {
    private static TencentCosProperties4Picture tencentCosProperties4Picture = new TencentCosProperties4Picture("1314655796",
            "AKIDWRQm4HPgv6BlFdt24HTstdHJE5DMPMER",
            "k5WKh6raK3xmVksy0LdwSwS1qhZPB92q",
            "cos-1314655796",
            "ap-shanghai",
            "https://cos-1314655796.cos.ap-shanghai.myqcloud.com");

    private static TencentCosConfig tencentCosConfig = new TencentCosConfig(tencentCosProperties4Picture);

    /**
     * 上传文件
     * @param path：文件在云存储的路径，根目录不用写，例如path可以为/report/001/。只要写目录，文件名会自动生成
     * @param multipartFile：文件对象
     * @return：url为返回的图片url
     */
    public static String uploadFile(String path, MultipartFile multipartFile){
        String key = TencentCosConfig.COS_IMAGE + path + new Date().getTime() + ".jpg";
        String url = tencentCosProperties4Picture.getBaseUrl() + "/" + key;

        //将图片推到文件服务器上
        File localFile = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();

            String[] filename = originalFilename.split("\\.");
            localFile=File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(localFile);
            localFile.deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        PutObjectRequest putObjectRequest =
                new PutObjectRequest(tencentCosProperties4Picture.getBucketName(), key, localFile);
        //设置存储类型 默认标准型
        putObjectRequest.setStorageClass(StorageClass.Standard);

        COSClient cosClient = tencentCosConfig.getCoSClient4Picture();

        try {
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            //putObjectResult 会返回etag
            String etag = putObjectResult.getETag();
        } catch (CosServiceException e) {
            throw new CosServiceException(e.getMessage());
        } catch (CosClientException e) {
            throw new CosClientException(e.getMessage());
        }
        cosClient.shutdown();


        return url;
    }
}

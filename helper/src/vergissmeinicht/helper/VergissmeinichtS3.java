package vergissmeinicht.helper;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.net.URL;

public final class VergissmeinichtS3 {
    private VergissmeinichtS3() {}

    public static String getS3FileKey(String slotKey, String identityId, AmazonS3 s3Client){
        return getS3Object(s3Client, identityId, slotKey).getKey();
    }

    public static S3Object getS3Object(AmazonS3 s3Client, String folder, String file) {
        String bucketName = "vergissmeinnichtbucket145332-dev";
        String path = "private/"+folder+"/"+file;
        return s3Client.getObject(new GetObjectRequest(bucketName, path));
    }

    public static URL getFileUrl(String bucketName, String fileKey, AmazonS3 s3client){
        java.util.Date expiration = new java.util.Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 60;
        expiration.setTime(expTimeMillis);
        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileKey)
                .withMethod(HttpMethod.GET)
                .withExpiration(expiration);
        return s3client.generatePresignedUrl(generatePresignedUrlRequest);
    }
}

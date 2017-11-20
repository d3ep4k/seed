package com.metamug.seed;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.metamug.event.UploadEvent;
import com.metamug.event.UploadListener;
import java.io.File;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.Map;
import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.*;

/**
 *
 * @author Kaisteel
 */
public class ImageUploader implements UploadListener {

    private static final String AWS_S3_BUCKET = "bucketname";
    private static final String AWS_ACCESS_KEY = "access_key";
    private static final String AWS_SECRET_KEY = "secret_key";
    public final static AWSCredentials CREDENTIALS = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
    private final static AmazonS3Client S3CLIENT = new AmazonS3Client(CREDENTIALS);
    
    @Override
    public String uploadPerformed(UploadEvent event, DataSource ds) {
            String random = new RandomString(32).nextString();
    
         	// PutObjectResult por = S3CLIENT.putObject(
          //    	new PutObjectRequest(AWS_S3_BUCKET,  
          //    		"/callmystyle/images/"+ random + "." + event.getFileName().split("\\.")[1], event.getUploadedFile())
          //       .withCannedAcl(CannedAccessControlList.PublicRead));

      QueryRunner run = new QueryRunner( ds );
      try{
          // Execute the SQL update statement and return the number of
          // inserts that were made
          int inserts = run.update( "INSERT INTO Person (name,height) VALUES (?,?)",
                                    "John Doe", 1.82 );
          // The line before uses varargs and autoboxing to simplify the code

          // Now it's time to rise to the occation...
          int updates = run.update( "UPDATE Person SET height=? WHERE name=?",
                                    2.05, "John Doe" );
          // So does the line above
      }catch(SQLException sqle) {
          // Handle it
      }
      
			return "{\"success\":\""+random+"\"}";
    }

}
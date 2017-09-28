package org.lab.insurance.io.service;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.lab.insurance.io.domain.InsuranceS3Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InsuranceS3Service {

	@Value("${insurance.aws.s3.profile}")
	private String profile;

	@Value("${insurance.aws.s3.region}")
	private String region;

	@Value("${insurance.aws.s3.bucket-name}")
	private String bucketName;

	public void upload(InsuranceS3Resource resource, InputStream in) {
		log.debug("Uploading {}", resource);
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentType(resource.getContentType());
		metadata.setContentEncoding(resource.getContentEncoding());
		String key = getKey(resource);
		AmazonS3 client = buildClient();
		client.putObject(new PutObjectRequest(bucketName, key, in, metadata));
	}

	public InputStream download(InsuranceS3Resource resource) {
		log.debug("Downloading {}", resource);
		AmazonS3 client = buildClient();
		String key = getKey(resource);
		S3Object readed = client.getObject(bucketName, key);
		return readed.getObjectContent();
	}

	public boolean exists(InsuranceS3Resource resource) {
		AmazonS3 client = buildClient();
		String key = getKey(resource);
		try {
			client.getObjectMetadata(bucketName, key);
		}
		catch (AmazonServiceException ex) {
			if (ex.getStatusCode() == HttpStatus.SC_NOT_FOUND) {
				return false;
			}
			else {
				throw ex;
			}
		}
		return true;
	}

	private String getKey(InsuranceS3Resource resource) {
		StringBuilder keyBuilder = new StringBuilder();
		if (StringUtils.isNotBlank(resource.getParentPath())) {
			keyBuilder.append(StringUtils.removeEnd(resource.getParentPath(), "/"));
			keyBuilder.append("/");
		}
		keyBuilder.append(resource.getName());
		return keyBuilder.toString();
	}

	private AmazonS3 buildClient() {
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider(profile);
		AmazonS3ClientBuilder clientBuilder = AmazonS3ClientBuilder.standard();
		clientBuilder.setCredentials(credentialsProvider);
		clientBuilder.setRegion(region);
		return clientBuilder.build();
	}

}

package org.lab.insurance.documentation;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.Random;

import org.apache.commons.codec.Charsets;
import org.junit.Assert;
import org.junit.Test;
import org.lab.insurance.documentation.domain.InsuranceS3Resource;
import org.lab.insurance.documentation.service.InsuranceS3Service;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.util.IOUtils;

public class InsuranceS3ServiceTest {

	@Test
	public void test() throws Exception {

		boolean useProxy = true;
		if (useProxy) {
			System.setProperty("http.proxyHost", "proxytal");
			System.setProperty("http.proxyPort", "80");
			System.setProperty("https.proxyHost", "proxytal");
			System.setProperty("https.proxyPort", "80");
		}

		Integer rand = new Random().nextInt(1000);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, Charsets.UTF_8));
		writer.write("test-" + rand + "\n");
		writer.write("this is just a example file\n");
		writer.write("not ascii chars: ñáéíóú\n");
		writer.close();
		byte[] bin = out.toByteArray();

		InsuranceS3Service service = new InsuranceS3Service();

		InsuranceS3Resource resource01 = new InsuranceS3Resource();
		resource01.setContentEncoding("UTF-8");
		resource01.setContentType("text/plain");
		resource01.setParentPath("contract-documentation");
		resource01.setName("resource-" + rand + ".txt");
		service.upload(resource01, new ByteArrayInputStream(bin));

		InsuranceS3Resource resource02 = new InsuranceS3Resource();
		resource02.setName("non-existing-file.txt");

		InputStream readedStream = service.download(resource01);
		ByteArrayOutputStream outMemory = new ByteArrayOutputStream();
		IOUtils.copy(readedStream, outMemory);

		Assert.assertTrue(service.exists(resource01));
		Assert.assertTrue(!service.exists(resource02));

		try {
			service.download(resource02);
			Assert.fail();
		}
		catch (AmazonS3Exception ex) {
		}
	}

}

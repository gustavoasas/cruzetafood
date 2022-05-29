package br.com.cruzetafood.utils;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

public class ResourceUtil {
	
	public static String getContentFromResource(String resourceName) {
		try {
			InputStream stream = ResourceUtils.class.getResourceAsStream(resourceName);
			return StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

}

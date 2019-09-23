package com.ds.allure.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AllureClientApplication.class)
public class AllureClientApplicationTests {

	private static String createPersonUrl;
	private static String updatePersonUrl;
	private static RestTemplate restTemplate;
	private static HttpHeaders headers;
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static JSONObject personJsonObject;

	@BeforeClass
	public static void runBeforeAllTestMethods() throws JSONException {
		createPersonUrl = "https://jsonplaceholder.typicode.com/todos/1";
		updatePersonUrl = "https://jsonplaceholder.typicode.com/todos/1";

		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		personJsonObject = new JSONObject();
		personJsonObject.put("id", 1);
		personJsonObject.put("name", "John");
	}

	@Test
	public void readFiles() throws IOException {
		try (Stream<Path> walk = Files.walk(Paths.get("D:\\allure-results-example"))) {

//			List<Path> result = walk.filter(Files::isRegularFile).collect(Collectors.toList());
			
			List<File> result2 = walk.filter(Files::isRegularFile)
					.map(x -> x.toFile())
					.collect(Collectors.toList());
			
			result2.forEach(f -> {
				try {
					byte[] bArray = FileUtils.readFileToByteArray(f);
//					byte[] bArray = IOUtils.toByteArray(new FileInputStream(f));
					String encodedfile = new String(Base64.encodeBase64(bArray), "UTF-8");
					JSONObject ob = new JSONObject();
					ob.accumulate("file_name", f.getName());
					ob.accumulate("content_base64", Base64.decodeBase64(encodedfile));
					System.out.println(ob.toString());
				} catch (IOException | JSONException e) {
					e.printStackTrace();
				}
			});

//			result2.forEach(f -> {
//				try {
//					String ext = FilenameUtils.getExtension(f.toString());
//					System.out.println("=====>" + ext);
//					String content = Files.readString(f);
//					System.out.println(content);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void givenDataIsJson_whenDataIsPostedByPostForEntity_thenResponseBodyIsNotNull() throws IOException {
		HttpEntity<String> request = new HttpEntity<String>(personJsonObject.toString(), headers);
		ResponseEntity<String> responseEntityStr = restTemplate
				.postForEntity("https://jsonplaceholder.typicode.com/todos", request, String.class);
		JsonNode root = objectMapper.readTree(responseEntityStr.getBody());

		assertNotNull(responseEntityStr.getBody());
		assertNotNull(root.path("name").asText());

		assertNotNull(responseEntityStr.getBody());
		assertNotNull(responseEntityStr.getHeaders());
		assertNotNull(responseEntityStr.getStatusCode());
	}

	@Test
	public void contextLoads() {
	}

}

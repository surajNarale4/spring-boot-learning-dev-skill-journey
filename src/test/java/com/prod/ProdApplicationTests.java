package com.prod;

import com.prod.clients.Student;
import com.prod.clients.StudentClient;
import com.prod.config.RestClientConfig;
import com.prod.services.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@SpringBootTest

class ProdApplicationTests {

	@Autowired
	@Qualifier("fatsApi")
	private RestClient restClient;

	@Autowired
	private PostServiceImpl postService;

	@Test
	void contextLoads() {

		System.out.println(restClient.get().uri("/students/")
				.retrieve()
				.body(new ParameterizedTypeReference<List<Student>>(){}));

	}
	@Test
	void duplicate(){
		Map<String, Object> req = new HashMap<>();
		req.put("id", 1);
		req.put("name", "Babu");
		req.put("age", 20);
		req.put("grade", "A");
		req.put("email", "babu@gmail.com");


		String res = restClient.post()
				.uri("/student/")
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.body(req)
				.retrieve()
				.body(String.class);

		System.out.println(res);
	}

	@Test
	void s(){

		System.out.println(restClient.post().uri("/s").body("Hello").retrieve().body(String.class));

	}

	@Test
	void test(){
		String m = restClient.post()
				.uri("/test")
				.retrieve()
				.body(String.class);
		System.out.println(m);
	}

	@Test
	void createStudent(){
		Student s = new Student();
		s.setAge(1);
		s.setId(1L);
		s.setName("Babu");
		s.setGrade("A+");
		s.setEmail("@fd");

		String m = restClient.post()
				.uri("/student")
				.contentType(MediaType.APPLICATION_JSON)
				.body(s)
				.retrieve()
				.body(String.class);

		System.out.println(m);
	}
	@Test
	void createStudent1(){
		Student s = new Student();
		s.setAge(1);
		s.setId(1L);
		s.setName("Babu");
		s.setGrade("A+");
		s.setEmail("@fd");

		RestTemplate restTemplate = new RestTemplate();
		String m = restTemplate.postForObject("http://localhost:8000/student", s, String.class);
		System.out.println(m);
	}

	@Test
	void t(){
		postService.getAllPost();
	}

}

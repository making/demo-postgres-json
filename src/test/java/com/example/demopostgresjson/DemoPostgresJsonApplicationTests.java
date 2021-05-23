package com.example.demopostgresjson;

import java.util.List;
import java.util.Map;

import com.example.demopostgresjson.readmodel.Author;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
		webEnvironment = WebEnvironment.RANDOM_PORT,
		properties = {
				"spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
				"spring.datasource.url=jdbc:tc:postgresql:11:///demo"
		})
class DemoPostgresJsonApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void readme() {
		final ResponseEntity<JsonNode> data1 = restTemplate.postForEntity("/data", Map.of("role", "author", "name", "foo", "books", List.of("foo", "bar")), JsonNode.class);
		assertThat(data1.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(data1.getBody().get("id").intValue()).isEqualTo(1);
		assertThat(data1.getBody().get("data").get("role").asText()).isEqualTo("author");
		assertThat(data1.getBody().get("data").get("name").asText()).isEqualTo("foo");
		assertThat(data1.getBody().get("data").get("books").size()).isEqualTo(2);
		assertThat(data1.getBody().get("data").get("books").get(0).asText()).isEqualTo("foo");
		assertThat(data1.getBody().get("data").get("books").get(1).asText()).isEqualTo("bar");

		final ResponseEntity<JsonNode> data2 = restTemplate.postForEntity("/data", Map.of("foo", "bar"), JsonNode.class);
		assertThat(data2.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(data2.getBody().get("id").intValue()).isEqualTo(2);
		assertThat(data2.getBody().get("data").get("foo").asText()).isEqualTo("bar");

		final ResponseEntity<JsonNode> data3 = restTemplate.postForEntity("/data", Map.of("role", "author", "name", "bar", "books", List.of("baz")), JsonNode.class);
		assertThat(data3.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(data3.getBody().get("id").intValue()).isEqualTo(3);
		assertThat(data3.getBody().get("data").get("role").asText()).isEqualTo("author");
		assertThat(data3.getBody().get("data").get("name").asText()).isEqualTo("bar");
		assertThat(data3.getBody().get("data").get("books").size()).isEqualTo(1);
		assertThat(data3.getBody().get("data").get("books").get(0).asText()).isEqualTo("baz");

		final ResponseEntity<Author> author1 = restTemplate.getForEntity("/authors/1", Author.class);
		assertThat(author1.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(author1.getBody().id()).isEqualTo(1);
		assertThat(author1.getBody().name()).isEqualTo("foo");
		assertThat(author1.getBody().books()).hasSize(2);
		assertThat(author1.getBody().books().get(0).name()).isEqualTo("foo");
		assertThat(author1.getBody().books().get(1).name()).isEqualTo("bar");

		final ResponseEntity<Author> author3 = restTemplate.getForEntity("/authors/3", Author.class);
		assertThat(author3.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(author3.getBody().id()).isEqualTo(3);
		assertThat(author3.getBody().name()).isEqualTo("bar");
		assertThat(author3.getBody().books()).hasSize(1);
		assertThat(author3.getBody().books().get(0).name()).isEqualTo("baz");
	}

}

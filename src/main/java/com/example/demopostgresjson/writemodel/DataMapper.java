package com.example.demopostgresjson.writemodel;

import java.io.UncheckedIOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DataMapper {
	private final JdbcTemplate jdbcTemplate;

	private final ObjectMapper objectMapper;

	public DataMapper(JdbcTemplate jdbcTemplate, ObjectMapper objectMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.objectMapper = objectMapper;
	}

	@Transactional
	public Data insert(Map<String, Object> data) {
		try {
			final String json = this.objectMapper.writeValueAsString(data);
			final Integer id = this.jdbcTemplate.queryForObject("INSERT INTO data(data) values(?::JSON) RETURNING id", Integer.class, json);
			return new Data(id, data);
		}
		catch (JsonProcessingException e) {
			throw new UncheckedIOException(e);
		}
	}
}

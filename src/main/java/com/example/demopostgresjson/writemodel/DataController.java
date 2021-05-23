package com.example.demopostgresjson.writemodel;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataController {
	public final DataMapper dataMapper;

	public DataController(DataMapper dataMapper) {
		this.dataMapper = dataMapper;
	}

	@PostMapping(path = "data")
	public Data postData(@RequestBody Map<String, Object> data) {
		return this.dataMapper.insert(data);
	}
}

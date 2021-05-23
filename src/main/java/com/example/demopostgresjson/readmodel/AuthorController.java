package com.example.demopostgresjson.readmodel;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
	private final AuthorMapper authorMapper;

	public AuthorController(AuthorMapper authorMapper) {
		this.authorMapper = authorMapper;
	}

	@GetMapping(path = "authors/{authorId}")
	public ResponseEntity<Author> getAuthor(@PathVariable("authorId") Integer authorId) {
		return ResponseEntity.of(this.authorMapper.findById(authorId));
	}
}

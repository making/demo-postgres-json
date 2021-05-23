package com.example.demopostgresjson.readmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorMapper {
	private final JdbcTemplate jdbcTemplate;

	private final ResultSetExtractor<Optional<Author>> authorResultSetExtractor = rs -> {
		Integer authorId = null;
		String authorName = null;
		final List<Book> books = new ArrayList<>();
		while (rs.next()) {
			if (authorId == null) {
				authorId = rs.getInt("author_id");
			}
			if (authorName == null) {
				authorName = rs.getString("author_name");
			}
			books.add(new Book(rs.getString("book_name")));
		}
		if (authorId == null) {
			return Optional.empty();
		}
		return Optional.of(new Author(authorId, authorName, books));
	};

	public AuthorMapper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Optional<Author> findById(Integer authorId) {
		return this.jdbcTemplate.query("SELECT a.author_id, a.author_name, b.book_name FROM author AS a INNER JOIN book AS b ON a.author_id = b.author_id WHERE a.author_id = ?", this.authorResultSetExtractor, authorId);
	}

}

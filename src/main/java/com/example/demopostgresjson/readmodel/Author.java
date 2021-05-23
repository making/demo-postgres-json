package com.example.demopostgresjson.readmodel;

import java.util.List;

public record Author(Integer id, String name, List<Book> books) {
}

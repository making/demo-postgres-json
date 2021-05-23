package com.example.demopostgresjson.writemodel;

import java.util.Map;

public record Data(Integer id, Map<String, Object> data) {
}

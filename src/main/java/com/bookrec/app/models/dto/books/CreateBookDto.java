package com.bookrec.app.models.dto.books;

import java.util.List;

public record CreateBookDto(String title, String author, String description, List<Long> interestsIds) {

}

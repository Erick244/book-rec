package com.bookrec.app.models.dto.books;

import java.util.List;

public record AddBookInterestsDto(Long bookId, List<Long> interestsIds) {

}

package com.bookrec.app.models.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.bookrec.app.models.entities.Book;

public interface BookRepository extends CrudRepository<Book, Long>, PagingAndSortingRepository<Book, Long> {

	@Query("SELECT DISTINCT b FROM books b " +
		   "JOIN b.interests bi " +
		   "JOIN bi.users ui " +
		   "WHERE ui.id = :userId")
	Page<Book> findBooksByUserCommonInterests(@Param("userId") Long userId, Pageable pageable);
}

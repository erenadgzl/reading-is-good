package com.readingisgood.readingisgood.book.repository;

import com.readingisgood.readingisgood.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}

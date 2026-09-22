package com.antony.madr.book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<BookEntity, Integer> {

}

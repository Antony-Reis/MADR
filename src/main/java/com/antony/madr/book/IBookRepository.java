package com.antony.madr.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBookRepository extends JpaRepository<BookEntity, Integer> {
    Page<BookEntity> findByTitleContainingAndYear(String title,Integer year,
                                       Pageable pageable);


}

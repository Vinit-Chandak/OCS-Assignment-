package com.knf.dev.demo.springvaadincrud.backend.repository;

import com.knf.dev.demo.springvaadincrud.backend.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository
                extends JpaRepository<Search, Long> {

}
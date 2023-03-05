package com.knf.dev.demo.springvaadincrud.backend.repository;

import com.knf.dev.demo.springvaadincrud.backend.model.Gitrepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GitrepositoryRepository
                extends JpaRepository<Gitrepository, Long> {

}
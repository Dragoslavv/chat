package com.example.demo.repository;

import com.example.demo.entity.UploadFile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadFileRepository extends CrudRepository<UploadFile, Long> {
}

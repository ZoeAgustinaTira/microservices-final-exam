package com.finalexam.seriesservice.repository;

import com.finalexam.seriesservice.model.Serie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SerieRepository extends MongoRepository<Serie, String> {
}

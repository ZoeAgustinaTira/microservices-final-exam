package com.finalexam.catalogservice.model;

import com.finalexam.catalogservice.model.DTO.MovieDTO;
import com.finalexam.catalogservice.model.DTO.SerieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "series-service")
public interface SeriesFeingClient {

    @GetMapping("/series/{genre}")
    ResponseEntity<List<SerieDTO>> getSerieByGenre(@PathVariable(value = "genre") String genre);

    @GetMapping("/series/withErrors/{genre}")
    ResponseEntity<List<SerieDTO>> getSerieByGenreWithThrowError(@PathVariable(value = "genre") String genre,
                                                                 @RequestParam("throwError") boolean throwError);

}

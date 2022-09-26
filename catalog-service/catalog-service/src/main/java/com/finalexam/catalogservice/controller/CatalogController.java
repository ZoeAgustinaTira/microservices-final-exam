package com.finalexam.catalogservice.controller;

import com.finalexam.catalogservice.model.DTO.MovieDTO;
import com.finalexam.catalogservice.model.DTO.SerieDTO;
import com.finalexam.catalogservice.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {
    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService){
        this.catalogService = catalogService;
    }

    @GetMapping("/movies/{genre}")
    public ResponseEntity<List<MovieDTO>> getMoviesByGenre(@PathVariable String genre){
        return catalogService.findMovieByGenre(genre);
    }
    @GetMapping("/series/{genre}")
    public ResponseEntity<List<SerieDTO>> getSeriesByGenre(@PathVariable String genre){
        return catalogService.findSerieByGenre(genre);
    }

    @GetMapping("/withErrors/{genre}")
    public ResponseEntity<List<MovieDTO>> getGenre(@PathVariable String genre, @RequestParam("throwError") boolean throwError){
        return catalogService.findMovieByGenre(genre, throwError);
    }

    // RabbitMQ
    @PostMapping("/save")
    public ResponseEntity<String> saveMovie(@RequestBody MovieDTO movieDTO){
        catalogService.saveMovie(movieDTO);
        return ResponseEntity.ok("Movie was save");
    }

}

package com.finalexam.catalogservice.controller;

import com.finalexam.catalogservice.model.DTO.MovieDTO;
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

    @GetMapping("/{genre}")
    public ResponseEntity<List<MovieDTO>> getGenre(@PathVariable String genre){
        return catalogService.findMovieByGenre(genre);
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

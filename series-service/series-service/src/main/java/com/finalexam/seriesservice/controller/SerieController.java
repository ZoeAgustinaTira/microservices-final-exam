package com.finalexam.seriesservice.controller;

import com.finalexam.seriesservice.model.Serie;
import com.finalexam.seriesservice.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/series")
public class SerieController {
    private final SerieService serieService;

    @Autowired
    public SerieController(SerieService serieService){
        this.serieService = serieService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        List<Serie> series = serieService.findAll();
        return series.isEmpty()
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(series);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") String id){
        Serie serie = serieService.findById(id);
        return Objects.isNull(serie)
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : ResponseEntity.ok(serie);
    }

    @PostMapping
    public ResponseEntity<?> saveSerie(@RequestBody Serie serie){
        return ResponseEntity.ok(serieService.saveSeries(serie));
    }
}

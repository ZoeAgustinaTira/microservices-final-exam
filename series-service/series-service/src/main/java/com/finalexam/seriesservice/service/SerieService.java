package com.finalexam.seriesservice.service;

import com.finalexam.seriesservice.model.Serie;
import com.finalexam.seriesservice.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {
    private final SerieRepository serieRepository;

    @Autowired
    public SerieService(SerieRepository serieRepository){
        this.serieRepository = serieRepository;
    }

    public Serie findById(String id){
        return serieRepository.findById(id).orElse(null);
    }

    public List<Serie> findAll(){
        return serieRepository.findAll();
    }

    public Serie saveSeries(Serie serie){
        return serieRepository.save(serie);
    }

}

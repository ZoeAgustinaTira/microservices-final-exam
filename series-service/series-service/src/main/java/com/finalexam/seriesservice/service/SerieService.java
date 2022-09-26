package com.finalexam.seriesservice.service;

import com.finalexam.seriesservice.model.Serie;
import com.finalexam.seriesservice.repository.SerieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {
    private static final Logger LOG = LoggerFactory.getLogger(SerieService.class);

    private final SerieRepository serieRepository;

    @Autowired
    public SerieService(SerieRepository serieRepository){
        this.serieRepository = serieRepository;
    }

    public List<Serie> findAll(){
        return serieRepository.findAll();
    }

    public List<Serie> findbyGenre(String genre){
        LOG.info("Searching series by genre: " + genre);
        return serieRepository.findByGenre(genre);
    }

    public List<Serie> findByGenre(String genre, Boolean throwError){
        LOG.info("Searching series by genre: " + genre);
        if (throwError){
            LOG.error("Error when searching series by genre " + genre);
            throw new RuntimeException("Forced error");
        }
        return serieRepository.findByGenre(genre);
    }

    public Serie findById(String id){
        return serieRepository.findById(id).orElse(null);
    }



    public Serie saveSeries(Serie serie){
        return serieRepository.save(serie);
    }


}

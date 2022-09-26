package com.finalexam.catalogservice.service;

import com.finalexam.catalogservice.model.DTO.MovieDTO;
import com.finalexam.catalogservice.model.DTO.SerieDTO;
import com.finalexam.catalogservice.model.MovieFeingClient;
import com.finalexam.catalogservice.model.SeriesFeingClient;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class CatalogService {

    //Variable de entorno para el nombre de la cola que configuramos
    @Value("${queue.movie.name}")
    private String queueName;
    private final Logger LOG = LoggerFactory.getLogger(CatalogService.class);
    private final MovieFeingClient movieFeingClient;
    private final SeriesFeingClient seriesFeingClient;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public CatalogService(MovieFeingClient movieFeingClient, SeriesFeingClient seriesFeingClient, RabbitTemplate rabbitTemplate){
        this.movieFeingClient = movieFeingClient;
        this.seriesFeingClient = seriesFeingClient;
        this.rabbitTemplate = rabbitTemplate;
    }

    public ResponseEntity<List<MovieDTO>> findMovieByGenre(String genre){
        LOG.info("Looking for movies of the genre: " + genre);
        return movieFeingClient.getMovieByGenre(genre);
    }
    public ResponseEntity<List<SerieDTO>> findSerieByGenre(String genre){
        LOG.info("Looking for series of the genre: " + genre);
        return seriesFeingClient.getSerieByGenre(genre);
    }

    @CircuitBreaker(name = "movies", fallbackMethod = "moviesFallBackMethod")
    public ResponseEntity<List<MovieDTO>> findMovieByGenre(String genre, Boolean throwError){
        LOG.info("Looking for movies of the genre" + genre);
        return movieFeingClient.getMovieByGenreWithThrowError(genre, throwError);
    }

    //fallback solo movies
    public ResponseEntity<List<MovieDTO>> moviesFallBackMethod(CallNotPermittedException exception){
        LOG.info("Error when searching movies by genre, CIRCUIT BREAKER ACTIVATED");
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
    }

    public void saveMovie(MovieDTO movieDTO){
        rabbitTemplate.convertAndSend(queueName, movieDTO);
    }
}

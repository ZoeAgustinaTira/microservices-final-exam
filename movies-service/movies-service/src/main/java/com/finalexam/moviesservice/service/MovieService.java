package com.finalexam.moviesservice.service;

import com.finalexam.moviesservice.model.Movie;
import com.finalexam.moviesservice.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private static final Logger LOG = LoggerFactory.getLogger(MovieService.class);

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findbyGenre(String genre){
        LOG.info("Searching movies by genre: " + genre);
        return movieRepository.findByGenre(genre);
    }

   public List<Movie> findByGenre(String genre, Boolean throwError){
        LOG.info("Searching movies by genre: " + genre);
        if (throwError){
            LOG.error("Error when searching movies by genre " + genre);
            throw new RuntimeException("Forced error");
        }
        return movieRepository.findByGenre(genre);
    }

    @RabbitListener(queues = "${queue.movie.name}")
    public void save(Movie movie) {
        LOG.info("A movie was received through rabbit " + movie.toString());
        saveMovie(movie);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }


}

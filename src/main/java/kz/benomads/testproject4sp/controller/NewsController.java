package kz.benomads.testproject4sp.controller;

import kz.benomads.testproject4sp.dto.NewsDto;
import kz.benomads.testproject4sp.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllNews() {
        return new ResponseEntity<>(newsService.getAllNews(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.getNewsById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<NewsDto> createNews(@RequestBody NewsDto newsDto) {
        return new ResponseEntity<>(newsService.createNews(newsDto), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(@PathVariable Long id,
                                              @RequestBody NewsDto newsDto) {

        return new ResponseEntity<>(newsService.updateNews(id, newsDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

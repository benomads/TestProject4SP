package kz.benomads.testproject4sp.service.impl;

import kz.benomads.testproject4sp.dto.NewsDto;
import kz.benomads.testproject4sp.exception.NewsNotFoundException;
import kz.benomads.testproject4sp.model.News;
import kz.benomads.testproject4sp.repository.NewsRepository;
import kz.benomads.testproject4sp.repository.ProductRepository;
import kz.benomads.testproject4sp.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final ProductRepository productRepository;

    @Autowired
    public NewsServiceImpl(NewsRepository newsRepository,
                           ProductRepository productRepository) {
        this.newsRepository = newsRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<NewsDto> getAllNews() {
        List<News> news = newsRepository.findAll();
        return news.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    @Override
    public NewsDto getNewsById(Long id) {
        News news = newsRepository.findById(id)
            .orElseThrow(() ->
                new NewsNotFoundException("New not found with id: " + id));
        return convertToDto(news);
    }

    @Override
    public NewsDto createNews(NewsDto newsDto) {
        News news = convertToEntity(newsDto);
        news.setProduct(
            productRepository.findProductById(newsDto.getProductId()));
        newsRepository.save(news);
        return convertToDto(news);
    }

    @Override
    public NewsDto updateNews(Long id, NewsDto newsDto) {
        News news = newsRepository.findById(id)
            .orElseThrow(() ->
                new NewsNotFoundException("News not found with id: " + id));


        newsRepository.save(checkAndReturnNews(news, newsDto));
        return convertToDto(news);
    }

    private News checkAndReturnNews(News news, NewsDto newsDto) {
        if (newsDto.getTitle() != null) {
            news.setTitle(newsDto.getTitle());
        }
        if (newsDto.getContent() != null) {
            news.setContent(newsDto.getContent());
        }
        if (newsDto.getProductId() != null) {
            news.setProduct(productRepository.findProductById(newsDto.getProductId()));
        }
        return news;
    }

    @Override
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new NewsNotFoundException("News not found with id: " + id);
        }
        newsRepository.deleteById(id);
    }

    private NewsDto convertToDto(News news) {
        NewsDto newsDto = new NewsDto();
        newsDto.setId(news.getId());
        newsDto.setTitle(news.getTitle());
        newsDto.setContent(news.getContent());
        newsDto.setProductId(news.getProduct().getId());
        return newsDto;
    }

    private News convertToEntity(NewsDto newsDto) {

        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setContent(newsDto.getContent());
        news.setProduct(
            productRepository.findProductById(newsDto.getProductId()));

        return news;

    }

}
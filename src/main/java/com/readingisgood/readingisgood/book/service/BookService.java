package com.readingisgood.readingisgood.book.service;

import com.readingisgood.readingisgood.base.exception.ConflictException;
import com.readingisgood.readingisgood.base.exception.RecordNotFoundException;
import com.readingisgood.readingisgood.book.entity.Book;
import com.readingisgood.readingisgood.book.model.BookDto;
import com.readingisgood.readingisgood.book.repository.BookRepository;
import com.readingisgood.readingisgood.order.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private BookRepository bookRepository;
    private OrderService orderService;

    public BookService(BookRepository bookRepository, OrderService orderService) {
        this.bookRepository = bookRepository;
        this.orderService = orderService;
    }

    public BookDto save(BookDto bookDto){
        Book book = new Book();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());
        book.setStock(bookDto.getStock());

        bookRepository.save(book);
        return bookDto;
    }

    public BookDto update(BookDto bookDto, Long id){
        Optional<Book> byId = bookRepository.findById(id);
        if(byId.isEmpty()){
            throw new RecordNotFoundException("Kitap bulunamadı!");
        }
        Book book = byId.get();
        book.setName(bookDto.getName());
        book.setAuthor(bookDto.getAuthor());
        book.setPrice(bookDto.getPrice());

        Long stock = orderService.getOrderCountByBookId(book.getId());
        if( stock > bookDto.getStock()){
            throw new ConflictException("Alınmış siparişleriniz ile stok bilginiz uyuşmuyor. Önce siparişleri iptal ediniz.");
        }
        book.setStock(bookDto.getStock());

        bookRepository.save(book);
        return bookDto;
    }

    public Book findById(Long id){
        Optional<Book> bookOptional = bookRepository.findById(id);
        return bookOptional.orElse(null);
    }

    public void saveAll(List<Book> books){
        bookRepository.saveAll(books);
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }
}

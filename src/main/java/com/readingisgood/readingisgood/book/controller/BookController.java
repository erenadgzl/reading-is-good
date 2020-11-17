package com.readingisgood.readingisgood.book.controller;

import com.readingisgood.readingisgood.base.controller.BaseController;
import com.readingisgood.readingisgood.book.model.BookDto;
import com.readingisgood.readingisgood.book.service.BookService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController extends BaseController {

    private BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Book",
            notes = "This method creates a new Book",
            response = BookDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity save(@Valid @RequestBody BookDto bookDto) {
        bookService.save(bookDto);
        return new ResponseEntity<>(getGenericApiResponse(bookDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update Book")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity update(@RequestParam(name = "id") Long id,
                                 @Valid @RequestBody BookDto bookDto) {
        bookService.update(bookDto, id);
        return new ResponseEntity<>(getGenericApiResponse(bookDto), HttpStatus.OK);
    }
}

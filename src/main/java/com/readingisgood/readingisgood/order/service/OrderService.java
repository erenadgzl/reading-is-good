package com.readingisgood.readingisgood.order.service;

import com.readingisgood.readingisgood.applicationuser.entity.ApplicationUser;
import com.readingisgood.readingisgood.applicationuser.entity.Role;
import com.readingisgood.readingisgood.applicationuser.service.ApplicationUserService;
import com.readingisgood.readingisgood.base.exception.ConflictException;
import com.readingisgood.readingisgood.base.exception.ForbiddenException;
import com.readingisgood.readingisgood.base.exception.RecordNotFoundException;
import com.readingisgood.readingisgood.book.entity.Book;
import com.readingisgood.readingisgood.book.service.BookService;
import com.readingisgood.readingisgood.order.entity.Order;
import com.readingisgood.readingisgood.order.entity.OrderDetail;
import com.readingisgood.readingisgood.order.entity.OrderStatus;
import com.readingisgood.readingisgood.order.model.BookOrderDto;
import com.readingisgood.readingisgood.order.model.OrderDetailResponse;
import com.readingisgood.readingisgood.order.model.OrderDto;
import com.readingisgood.readingisgood.order.model.OrderResponse;
import com.readingisgood.readingisgood.order.repository.OrderDetailRepository;
import com.readingisgood.readingisgood.order.repository.OrderRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;
    private ApplicationUserService applicationUserService;
    private BookService bookService;
    private OrderDetailRepository orderDetailRepository;

    public OrderService(OrderRepository orderRepository, ApplicationUserService applicationUserService, @Lazy BookService bookService, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.applicationUserService = applicationUserService;
        this.bookService = bookService;
        this.orderDetailRepository = orderDetailRepository;
    }

    public void save(OrderDto orderDto, String loginUser){
        ApplicationUser user = applicationUserService.findByEmail(loginUser);
        if ( user == null){
            throw new RecordNotFoundException("Kullanıcı bulunamadı!");
        }

        Order order = new Order();
        order.setOrderStatus(OrderStatus.PREPARING);
        order.setApplicationUser(user);

        List<OrderDetail> orderDetailList = new ArrayList<>();
        List<Book> bookList = new ArrayList<>();
        for (BookOrderDto item : orderDto.getBookList()){
            Book book = bookService.findById(item.getId());
            if ( book == null){
                throw new RecordNotFoundException("Kitap bulunamadı!");
            }

            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setBook(book);
            orderDetail.setOrder(order);
            if(item.getQuantity() > book.getStock()){
                throw new ConflictException("Sipariş ettiğiniz ürün stokta kalmamıştır!");
            }else{
                orderDetail.setQuantity(item.getQuantity());
                book.setStock( book.getStock() - item.getQuantity() );
                bookList.add(book);
            }
            orderDetail.setPrice(book.getPrice());
            orderDetail.setTotalPrice(BigDecimal.valueOf( item.getQuantity() ).multiply( book.getPrice() ));
            orderDetailList.add(orderDetail);
        }

        orderRepository.save(order);
        orderDetailRepository.saveAll(orderDetailList);
        bookService.saveAll(bookList);
    }

    public OrderResponse getOrderDetailById( Long id ){
        OrderResponse orderResponse = orderRepository.findByOrderId(id);
        if (orderResponse == null){
            throw new RecordNotFoundException("Sipariş bulunamadı!");
        }
        List<OrderDetailResponse> orderDetailResponseList = orderDetailRepository.findByOrderId(id);
        orderResponse.setOrderDetailList(orderDetailResponseList);
        return orderResponse;
    }

    public List<OrderResponse> getAllOrderList(String loginUser) {
        ApplicationUser user = applicationUserService.findByEmail(loginUser);
        if ( user == null){
            throw new RecordNotFoundException("Kullanıcı bulunamadı!");
        }
        List<OrderResponse> orderResponseList = orderRepository.findAllByApplicationUserId(user.getId());
        orderResponseList.forEach(item -> item.setOrderDetailList(orderDetailRepository.findByOrderId(item.getId())));

        return orderResponseList;
    }

    public Long getOrderCountByBookId( Long id ){
        return orderDetailRepository.getOrderCountByBookId(id);
    }

    public void updateOrderStatus(OrderStatus orderStatus, Long orderId , String loginUser){
        ApplicationUser user = applicationUserService.findByEmail(loginUser);
        if ( user == null){
            throw new RecordNotFoundException("Kullanıcı bulunamadı!");
        }

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if( orderOptional.isEmpty() || !orderOptional.get().getOrderStatus().equals(OrderStatus.PREPARING)){
            throw new RecordNotFoundException("Sipariş bulunamadı!");
        }
        Order order = orderOptional.get();

        if(orderStatus.equals(OrderStatus.DONE) && user.getRole().equals(Role.ROLE_ADMIN)){
            order.setOrderStatus(orderStatus);
        }else if ( orderStatus.equals(OrderStatus.CANCEL)){
            if(user.getRole().equals(Role.ROLE_USER) && !order.getApplicationUser().getId().equals(user.getId())){
                throw new RecordNotFoundException("Sipariş bulunamadı!");
            }
            order.setOrderStatus(orderStatus);
            List<OrderDetail> orderDetailList = orderDetailRepository.findByOrder_Id(orderId);
            List<Book> bookList = new ArrayList<>();
            orderDetailList.forEach(item -> {
                Book book = item.getBook();
                book.setStock(book.getStock() + item.getQuantity());
                bookList.add(book);
            });

            bookService.saveAll(bookList);
        }
    }
}

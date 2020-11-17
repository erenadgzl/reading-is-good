package com.readingisgood.readingisgood.order.controller;

import com.readingisgood.readingisgood.base.controller.BaseController;
import com.readingisgood.readingisgood.order.entity.OrderStatus;
import com.readingisgood.readingisgood.order.model.OrderDto;
import com.readingisgood.readingisgood.order.model.OrderResponse;
import com.readingisgood.readingisgood.order.model.OrderStatusDto;
import com.readingisgood.readingisgood.order.service.OrderService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create Order",
            notes = "This method creates a new Order",
            response = OrderDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    public ResponseEntity save(HttpServletRequest httpServletRequest,  @Valid @RequestBody OrderDto orderDto) {
        String loginUser = httpServletRequest.getUserPrincipal().getName();
        orderService.save(orderDto, loginUser);
        return new ResponseEntity<>(getGenericApiResponse(orderDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get Order Detail by id",
            response = OrderResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping
    public ResponseEntity findById(@RequestParam(name = "id") Long id){
        return new ResponseEntity<>(getGenericApiResponse(orderService.getOrderDetailById(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "Get All Order List",
            response = OrderResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @GetMapping("/all")
    public ResponseEntity findAll(HttpServletRequest httpServletRequest){
        String loginUser = httpServletRequest.getUserPrincipal().getName();
        return new ResponseEntity<>(getGenericApiResponse(orderService.getAllOrderList(loginUser)), HttpStatus.OK);
    }

    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update Order Status",
            response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    public ResponseEntity updateStatus(HttpServletRequest httpServletRequest, @Valid @RequestBody OrderStatusDto orderStatusDto,
                                       @RequestParam(name = "id") Long id) {
        String loginUser = httpServletRequest.getUserPrincipal().getName();
        orderService.updateOrderStatus(orderStatusDto.getOrderStatus(), id, loginUser);
        return new ResponseEntity<>(getGenericApiResponse("Başarılı"), HttpStatus.OK);
    }
}

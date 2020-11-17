package com.readingisgood.readingisgood.order.model;

import com.readingisgood.readingisgood.order.entity.OrderStatus;

import java.io.Serializable;
import java.util.List;

public class OrderResponse implements Serializable {

    private Long id;
    private OrderStatus orderStatus;
    private String cityName;
    private String townName;
    private String districtName;
    private String line;
    private List<OrderDetailResponse> orderDetailList;

    public OrderResponse(Long id, OrderStatus orderStatus, String cityName, String townName, String districtName, String line) {
        this.id = id;
        this.orderStatus = orderStatus;
        this.cityName = cityName;
        this.townName = townName;
        this.districtName = districtName;
        this.line = line;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public List<OrderDetailResponse> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetailResponse> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}

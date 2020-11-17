package com.readingisgood.readingisgood.base.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class Address implements Serializable {

    @Column(name = "CITY_NAME")
    private String cityName;
    @Column(name = "TOWN_NAME")
    private String townName;
    @Column(name = "DISTRICT_NAME")
    private String districtName;
    @Column(name = "LINE1")
    private String line;
}

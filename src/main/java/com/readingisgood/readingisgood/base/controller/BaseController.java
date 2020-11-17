package com.readingisgood.readingisgood.base.controller;

import com.readingisgood.readingisgood.base.entity.CustomResponseStatus;
import com.readingisgood.readingisgood.base.entity.GenericResponse;

public class BaseController {
    protected GenericResponse getGenericApiResponse(Object object){
        GenericResponse genericResponse = new GenericResponse();
        genericResponse.setData(object);
        genericResponse.setStatus(CustomResponseStatus.SUCCESS.getValue());

        return genericResponse;
    }
}
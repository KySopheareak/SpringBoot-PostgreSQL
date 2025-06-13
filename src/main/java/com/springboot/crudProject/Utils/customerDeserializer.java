package com.springboot.crudProject.Utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.springboot.crudProject.Model.userModel;

public class customerDeserializer extends JsonDeserializer<userModel> {
    @Override
    public userModel deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Long id = p.getLongValue();
        userModel customer = new userModel();
        customer.setId(id);
        return customer;
    }
}

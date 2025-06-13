package com.springboot.crudProject.Utils;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.springboot.crudProject.Model.productModel;

public class productDeserializer extends JsonDeserializer<productModel> {
    @Override
    public productModel deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Long id = p.getLongValue();
        productModel product = new productModel();
        product.setId(id);
        return product;
    }
}

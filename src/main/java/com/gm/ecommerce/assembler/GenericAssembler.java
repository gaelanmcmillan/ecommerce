package com.gm.ecommerce.assembler;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

public class GenericAssembler <T> implements RepresentationModelAssembler <T, EntityModel<T>> {

    private final Function<T, EntityModel<T>> modelCallback;

    public GenericAssembler (Function<T, EntityModel<T>> modelCallback) {
        this.modelCallback = modelCallback;
    }

    @Override
    public EntityModel<T> toModel(T object) {
        return this.modelCallback.apply(object);
    }
}

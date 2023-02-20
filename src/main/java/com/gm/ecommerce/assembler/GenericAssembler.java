package com.gm.ecommerce.assembler;
import java.util.function.Function;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

/**
 * <code>GenericAssembler&lt;T&gt;</code> facilitates simplifying the RESTful practice of
 * adding <a href="https://www.rfc-editor.org/rfc/rfc8288">web links</a> to API endpoints.
 * The utility of an assembler class is demonstrated well in
 * <a href="https://spring.io/guides/tutorials/rest/#:~:text=Simplifying%20Link%20Creation">this Spring tutorial</a>.
 * <br/><br/>
 *
 * The basic purpose of this class is to expose a function <code>GenericAssembler&lt;T&gt;::toModel(T)</code> which converts
 * an instance of type T to an instance of type <code>EntityModel&lt;T&gt;</code>. Having this utility in generic form
 * means we can cut down significantly on the number of Assembler classes we make.
 * */
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

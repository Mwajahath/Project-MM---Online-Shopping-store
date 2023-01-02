package com.massmutual.demo.exceptions;

import java.util.function.Supplier;

public class AddressNotFoundException extends RuntimeException  {
    public AddressNotFoundException(String message) {
        super(message);
    }
}

package com.example.limsebatchmanagement.CustomInterface;

@FunctionalInterface
public interface BiFunctionException<T,U,R> {
    R apply(T t,U u) throws Exception;
}

package com.example.limsebatchmanagement.CustomInterface;

import java.io.IOException;

@FunctionalInterface
public interface TriFunctionIOChecked<T,U,V,R> {
    R apply(T t,U u,V v) throws IOException;
}

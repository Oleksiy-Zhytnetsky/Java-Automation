package ua.edu.ukma.Zhytnetsky;

import java.io.Serializable;

@FunctionalInterface
public interface Operation<T extends Number> extends Serializable {

    T perform(final T first, final T second);

}

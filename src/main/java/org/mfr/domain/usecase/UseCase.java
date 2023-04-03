package org.mfr.domain.usecase;

import java.util.function.Function;

@FunctionalInterface
public interface UseCase<T, R> extends Function<T, R> {
    @Override
    R apply(T t);

    interface BaseUseCase<T> {
        void apply(T t);
    }
}

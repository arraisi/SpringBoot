package io.arraisi.theta.helper;

public class Decorator<T> {

    public T decorate(T entity) {
        return entity;
    }

    public Iterable<T> decorate(Iterable<T> entityList) {
        for (T entity : entityList) {
            decorate(entity);
        }
        return entityList;
    }

}

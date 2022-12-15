package com.example.natour2022.dao.listeners;

public abstract class RetrievalEventListener<T> extends AbstractEventListener {
    public abstract void OnDataRetrieved(T t);
}

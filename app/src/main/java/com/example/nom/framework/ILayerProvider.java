package com.example.nom.framework;

public interface ILayerProvider<E extends Enum<E>> extends IGameObject {
    public E getLayer();
}

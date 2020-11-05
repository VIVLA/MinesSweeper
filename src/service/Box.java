/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

/**
 *
 * @author SK-2018
 */
public enum Box {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    BOMBED,
    CLOSED,
    FLAGED,
    NOBOMB,
    OPENED;
    
    public Object image;
    
    public Box getNextNumber() {
        return Box.values() [ordinal() + 1];
    }
    
    public int getNumber() {
        return this.ordinal();
    }
}

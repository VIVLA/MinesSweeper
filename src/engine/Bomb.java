/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import service.Box;
import service.Coord;
import service.Matrix;
import service.Ranges;

/**
 *
 * @author SK-2018
 */
public class Bomb {
    
    private Matrix bombMap;
    private int totalBombs;
    
    public Bomb(int totalBombs) {
        this.totalBombs = totalBombs;
        fixMaxBombsAmount();
    }
    
    public void start() {
        bombMap = new Matrix(Box.ZERO);
        for (int i = 0; i < totalBombs; i++) {
            placeBombs();
        }
    }
    
    public Box getBombMapImageBoxes(Coord coord) {
        return bombMap.getBox(coord);
    }

    private void placeBombs() {
        while (true) {            
            Coord coord = Ranges.getRandomCoord();
            if (bombMap.getBox(coord) == Box.BOMB) continue;
            bombMap.setBox(coord, Box.BOMB);
            setNumbersAroundBombBox(coord);
            break;
        }
    }

    private void setNumbersAroundBombBox(Coord coord) {
        for (Coord around : Ranges.getCoordsAroundBox(coord))
            if (bombMap.getBox(around) != Box.BOMB)
                bombMap.setBox(around, bombMap.getBox(around).getNextNumber());
    }
    
    private void fixMaxBombsAmount() {
        int maxBombs = Ranges.getSize().x * Ranges.getSize().y / 5;
        if (totalBombs > maxBombs)
            totalBombs = maxBombs;
    }

    public int getAmountOfBombs() {
        return totalBombs;
    }

    
}

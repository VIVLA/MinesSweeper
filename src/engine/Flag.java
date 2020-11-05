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
public class Flag {
    
    private Matrix flagMap;
    private int amountOfClosedBoxes;
    
    public void start() {
        flagMap = new Matrix(Box.CLOSED);
        amountOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y;
    }
    
    public Box getFlagMapImageBoxes(Coord coord) {
        return flagMap.getBox(coord); 
    }

    public void toggleRightButton(Coord coord) {
        if (flagMap.getBox(coord) == Box.CLOSED)
            flagMap.setBox(coord, Box.FLAGED);
        else if (flagMap.getBox(coord) == Box.FLAGED)
            flagMap.setBox(coord, Box.CLOSED); 
    }

    public void openBox(Coord coord) {
        if (flagMap.getBox(coord) == Box.CLOSED)
            flagMap.setBox(coord, Box.OPENED);
        amountOfClosedBoxes--;
    }

    public void openBombedBox(Coord coord) {
        flagMap.setBox(coord, Box.BOMBED);
    }

    public void setOpenedToClosedBombBoxes(Coord coord) {
        if (flagMap.getBox(coord) == Box.CLOSED)
            flagMap.setBox(coord, Box.OPENED);
    }

    public void setNoBombToFlagedSafeBox(Coord coord) {
        if (flagMap.getBox(coord) == Box.FLAGED)
            flagMap.setBox(coord, Box.NOBOMB);
    }

    public int getCountOfFlagedBoxesAroundNumberBox(Coord coord) {
        int amountOfFlags = 0;
        for (Coord around : Ranges.getCoordsAroundBox(coord))
            if (flagMap.getBox(around) == Box.FLAGED)
                amountOfFlags++;
        return amountOfFlags;
    }

    public int getAmountOfClosedBoxes() {
        return amountOfClosedBoxes;
    }

    public int getAmountOfFlagedBoxes() {
        int amountOfFlagedBoxes = 0;
        for (Coord coord : Ranges.getAllCoords())
            if (flagMap.getBox(coord) == Box.FLAGED)
                amountOfFlagedBoxes++;
        return amountOfFlagedBoxes;
    }
    
}

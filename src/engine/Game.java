/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import service.Box;
import service.Coord;
import service.Ranges;

/**
 *
 * @author SK-2018
 */
public class Game {
    
    private final Bomb bomb;
    private final Flag flag;
    private GameState state;
    
    public Game(int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bomb = new Bomb(bombs);
        flag = new Flag();
    }
    
    public void startGame() {
        state = GameState.PLAYING;
        bomb.start();
        flag.start();
    }
    
    public GameState getState() {
        return state;
    }
    
    public Box getBoxImage(Coord coord) {
        if (flag.getFlagMapImageBoxes(coord) == Box.OPENED)
            return bomb.getBombMapImageBoxes(coord);
        else
            return flag.getFlagMapImageBoxes(coord);
    }

    public void leftMouseButtonPressed(Coord coord) {
        if (gameOver()) return;
        openBoxes(coord);
        checkWinner();
    }
    
    public void rightMouseButtonPressed(Coord coord) {
        if (gameOver()) return;
        flag.toggleRightButton(coord);
        checkWinner();
    }
    
    private void openBoxes(Coord coord) {
        switch(flag.getFlagMapImageBoxes(coord)) {
            case FLAGED: return;
            case OPENED: setOpenedToBoxesAroundNumberBox(coord); return;
            case CLOSED: 
                switch(bomb.getBombMapImageBoxes(coord)) {
                    case ZERO: openZeroBoxes(coord); return;
                    case BOMB: openBomb(coord); return;
                    default:   openNumberBox(coord); return;
                }
        }
    }
    
    private void setOpenedToBoxesAroundNumberBox(Coord coord) {
        if (flag.getCountOfFlagedBoxesAroundNumberBox(coord) == bomb.getBombMapImageBoxes(coord).getNumber())
            for (Coord around : Ranges.getCoordsAroundBox(coord))
                if (flag.getFlagMapImageBoxes(around) == Box.CLOSED)
                    openBoxes(around);
    }
    
    private void openBomb(Coord bombed) {
        state = GameState.BOMBED;
        flag.openBombedBox(bombed);
        for (Coord coord : Ranges.getAllCoords())
            if (bomb.getBombMapImageBoxes(coord) == Box.BOMB)
                flag.setOpenedToClosedBombBoxes(coord);
            else 
                flag.setNoBombToFlagedSafeBox(coord);
    }
    
    private void openNumberBox(Coord coord) {
        flag.openBox(coord);
    }
    
    private void openZeroBoxes(Coord coord) {
        flag.openBox(coord);
        for (Coord around : Ranges.getCoordsAroundBox(coord)) {
            if (flag.getFlagMapImageBoxes(around) == Box.CLOSED)
                openBoxes(around);
        }
        
    }
    
    private boolean gameOver() {
        if (getState() == GameState.PLAYING)
            return false;
        else {
            startGame();
            return true;
        }
    }

    private void checkWinner() {
        if (flag.getAmountOfClosedBoxes() == bomb.getAmountOfBombs())
        if (flag.getAmountOfClosedBoxes() == flag.getAmountOfFlagedBoxes())
            state = GameState.WINNED;   
    }
    
}

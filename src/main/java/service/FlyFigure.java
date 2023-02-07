package service;

import Model.Coord;
import Model.Figures;
import Model.Mapable;
import ui.Config;

public class FlyFigure {

    private Figures figure;
    private Coord coord;
    private boolean landed;
    private int ticks;
    Mapable map;

    public FlyFigure(Mapable map){
        this.map = map;
        figure = Figures.getRandom();
        coord = new Coord(Config.WIDTH / 2 - 2,figure.top.y - figure.bot.y - 1);
        landed = false;
        ticks = 2;
    }

    public Figures getFigure() {
        return figure;
    }

    public Coord getCoord() {
        return coord;
    }



    public boolean isLanded() {
        return landed;
    }



    private boolean camMoveFigure (Figures figure, int sx, int sy){

        if(coord.x + sx + figure.top.x <0) return false;
        if(coord.x + sx + figure.bot.x >= Config.WIDTH) return false;
        if(coord.y + sy + figure.top.y <0) return false;
        if(coord.y + sy + figure.bot.y >= Config.HEIGHT) return false;

        for(Coord dot : figure.dots)
            if (map.getBoxColor(coord.x + dot.x + sx, coord.y + dot.y + sy) != 0)
                return false;
        return true;
    }

    public void moveFigure (int sx, int sy){
        if(camMoveFigure(figure,sx, sy))
            coord = coord.plus(sx, sy);
        else
        if (sy == 1){
            if(ticks > 0)
                ticks --;
            else
                landed = true;
        }

    }

    public void turnFigure(int direction){
        Figures rotated = figure.turnRight();
        if(camMoveFigure(rotated,0,0)) {
            figure = rotated;
            return;
        }
        if(camMoveFigure(rotated,1,0)) {
            figure = rotated;
            moveFigure(1,0);
            return;
        }
        if(camMoveFigure(rotated,-1,0)) {
            figure = rotated;
            moveFigure(-1,0);
            return;
        }
        figure = rotated;
    }
}

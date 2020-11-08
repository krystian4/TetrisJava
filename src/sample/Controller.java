package sample;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;

public class Controller {
    //Variables form Tetris.java
    public static final int MOVE = Tetris.MOVE;
    public static final int BLOCKSIZE = Tetris.BLOCKSIZE;
    public static int WIDTH = Tetris.WIDTH;
    public static int HEIGHT = Tetris.HEIGHT;
    public static int[][] BOARD = Tetris.BOARD;

    public static void MoveFigureRight(Figure figure) {
        int xOfA = (int) figure.a.getX();
        int xOfB = (int) figure.b.getX();
        int xOfC = (int) figure.c.getX();
        int xOfD = (int) figure.d.getX();
        if (xOfA + MOVE <= WIDTH - BLOCKSIZE &&
                xOfB + MOVE <= WIDTH - BLOCKSIZE &&
                xOfC + MOVE <= WIDTH - BLOCKSIZE &&
                xOfD + MOVE <= WIDTH - BLOCKSIZE) {
            int movea = BOARD[(xOfA / BLOCKSIZE) + 1][((int) figure.a.getY() / BLOCKSIZE)];
            int moveb = BOARD[(xOfB / BLOCKSIZE) + 1][((int) figure.b.getY() / BLOCKSIZE)];
            int movec = BOARD[(xOfC / BLOCKSIZE) + 1][((int) figure.c.getY() / BLOCKSIZE)];
            int moved = BOARD[(xOfD / BLOCKSIZE) + 1][((int) figure.d.getY() / BLOCKSIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                figure.a.setX(xOfA + MOVE);
                figure.b.setX(xOfB + MOVE);
                figure.c.setX(xOfC + MOVE);
                figure.d.setX(xOfD + MOVE);
            }
        }
    }

    public static void MoveFigureLeft(Figure figure) {
        int xOfA = (int) figure.a.getX();
        int xOfB = (int) figure.b.getX();
        int xOfC = (int) figure.c.getX();
        int xOfD = (int) figure.d.getX();
        if (xOfA - MOVE >= 0 && xOfB - MOVE >= 0 &&
                xOfC - MOVE >= 0 && xOfD - MOVE >= 0) {
            int movea = BOARD[(xOfA / BLOCKSIZE) - 1][((int) figure.a.getY() / BLOCKSIZE)];
            int moveb = BOARD[(xOfB / BLOCKSIZE) - 1][((int) figure.b.getY() / BLOCKSIZE)];
            int movec = BOARD[(xOfC / BLOCKSIZE) - 1][((int) figure.c.getY() / BLOCKSIZE)];
            int moved = BOARD[(xOfD / BLOCKSIZE) - 1][((int) figure.d.getY() / BLOCKSIZE)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                figure.a.setX(xOfA - MOVE);
                figure.b.setX(xOfB - MOVE);
                figure.c.setX(xOfC - MOVE);
                figure.d.setX(xOfD - MOVE);
            }
        }
    }

    public static Figure makeFigure() {
        //random figure
        int randomFig = (int) (Math.random() * 105);
        //create 4 blocks
        Rectangle a = new Rectangle(BLOCKSIZE - 1, BLOCKSIZE - 1);
        Rectangle b = new Rectangle(BLOCKSIZE - 1, BLOCKSIZE - 1);
        Rectangle c = new Rectangle(BLOCKSIZE - 1, BLOCKSIZE - 1);
        Rectangle d = new Rectangle(BLOCKSIZE - 1, BLOCKSIZE - 1);
        //create shape from blocks on top of the board
        String name = "";
        int distanceFromTop = 30;
        int nextBlockDistance = 62;
        if (randomFig <= 15) {
            name = "i";
            a.setX(WIDTH + 75 - (2 * BLOCKSIZE));
            a.setY(distanceFromTop);
            b.setX(WIDTH + 75 - BLOCKSIZE);
            b.setY(distanceFromTop);
            c.setX(WIDTH + 75);
            c.setY(distanceFromTop);
            d.setX(WIDTH + 75 + BLOCKSIZE);
            d.setY(distanceFromTop);
        } else if (randomFig <= 30) {
            name = "o";
            a.setX(WIDTH + 75 - BLOCKSIZE);
            a.setY(distanceFromTop);
            b.setX(WIDTH + 75);
            b.setY(distanceFromTop);
            c.setX(WIDTH + 75 - BLOCKSIZE);
            c.setY(BLOCKSIZE + distanceFromTop);
            d.setX(WIDTH + 75);
            d.setY(BLOCKSIZE + distanceFromTop);
        } else if (randomFig <= 45) {
            name = "t";
            a.setX(WIDTH + nextBlockDistance - BLOCKSIZE);
            a.setY(distanceFromTop);
            b.setX(WIDTH + nextBlockDistance);
            b.setY(distanceFromTop);
            c.setX(WIDTH + nextBlockDistance);
            c.setY(BLOCKSIZE + distanceFromTop);
            d.setX(WIDTH + nextBlockDistance + BLOCKSIZE);
            d.setY(distanceFromTop);
        } else if (randomFig <= 60) {
            name = "s";
            a.setX(WIDTH + nextBlockDistance + BLOCKSIZE);
            a.setY(distanceFromTop);
            b.setX(WIDTH + nextBlockDistance);
            b.setY(distanceFromTop);
            c.setX(WIDTH + nextBlockDistance);
            c.setY(BLOCKSIZE + distanceFromTop);
            d.setX(WIDTH + nextBlockDistance - BLOCKSIZE);
            d.setY(BLOCKSIZE + distanceFromTop);
        } else if (randomFig <= 75) {
            name = "z";
            a.setX(WIDTH + nextBlockDistance );
            a.setY(distanceFromTop);
            b.setX(WIDTH + nextBlockDistance - BLOCKSIZE);
            b.setY(distanceFromTop);
            c.setX(WIDTH + nextBlockDistance);
            c.setY(BLOCKSIZE + distanceFromTop);
            d.setX(WIDTH + nextBlockDistance + + BLOCKSIZE);
            d.setY(BLOCKSIZE + distanceFromTop);
        } else if (randomFig <= 90) {
            name = "j";
            a.setX(WIDTH + nextBlockDistance - BLOCKSIZE);
            a.setY(distanceFromTop);
            b.setX(WIDTH + nextBlockDistance - BLOCKSIZE);
            b.setY(BLOCKSIZE + distanceFromTop);
            c.setX(WIDTH + nextBlockDistance);
            c.setY(BLOCKSIZE + distanceFromTop);
            d.setX(WIDTH + nextBlockDistance + BLOCKSIZE);
            d.setY(BLOCKSIZE + distanceFromTop);
        } else if (randomFig <= 105) {
            name = "l";
            a.setX(WIDTH + nextBlockDistance + BLOCKSIZE);
            a.setY(distanceFromTop);
            b.setX(WIDTH + nextBlockDistance - BLOCKSIZE);
            b.setY(BLOCKSIZE + distanceFromTop);
            c.setX(WIDTH + nextBlockDistance);
            c.setY(BLOCKSIZE + distanceFromTop);
            d.setX(WIDTH + nextBlockDistance + BLOCKSIZE);
            d.setY(BLOCKSIZE + distanceFromTop);
        }
        return new Figure(a, b, c, d, name);
    }

    public static boolean figureStuckOnTop(Figure figure){
        if ((figure.a.getY() == 0 || figure.b.getY() == 0 || figure.c.getY() == 0 || figure.d.getY() == 0)
                && (cannotMoveA(figure) || cannotMoveB(figure) || cannotMoveC(figure) || cannotMoveD(figure))) {
            return true;
        } else return false;
    }
    public static void MoveFigureDown(Figure figure) {
        //if figure should not move
        if(figure.a.getY() == HEIGHT - BLOCKSIZE || figure.b.getY() == HEIGHT - BLOCKSIZE ||
                figure.c.getY() == HEIGHT - BLOCKSIZE || figure.d.getY() == HEIGHT - BLOCKSIZE ||
                cannotMoveA(figure) || cannotMoveB(figure) || cannotMoveC(figure) || cannotMoveD(figure)){
            BOARD[(int)figure.a.getX() / BLOCKSIZE][(int) figure.a.getY() / BLOCKSIZE] = 1;
            BOARD[(int)figure.b.getX() / BLOCKSIZE][(int) figure.b.getY() / BLOCKSIZE] = 1;
            BOARD[(int)figure.c.getX() / BLOCKSIZE][(int) figure.c.getY() / BLOCKSIZE] = 1;
            BOARD[(int)figure.d.getX() / BLOCKSIZE][(int) figure.d.getY() / BLOCKSIZE] = 1;
            RemoveRows(Tetris.group);

            //create new figure
            Figure a = Tetris.nextFigure;
            a.putOnBoard();
            Tetris.nextFigure = makeFigure();
            Figure prev = Tetris.nextFigure;
            Tetris.figure = a;
            Tetris.group.getChildren().addAll(prev.a, prev.b, prev.c, prev.d);
            moveOnKeyPress(a);
        }
        //moving down
        if(figure.a.getY() + MOVE < HEIGHT && figure.b.getY() + MOVE < HEIGHT &&
                figure.c.getY() + MOVE < HEIGHT && figure.d.getY() + MOVE < HEIGHT ){
            int movea = BOARD[(int)figure.a.getX() / BLOCKSIZE][((int) figure.a.getY() / BLOCKSIZE) + 1];
            int moveb = BOARD[(int)figure.b.getX() / BLOCKSIZE][((int) figure.b.getY() / BLOCKSIZE) + 1];
            int movec = BOARD[(int)figure.c.getX() / BLOCKSIZE][((int) figure.c.getY() / BLOCKSIZE) + 1];
            int moved = BOARD[(int)figure.d.getX() / BLOCKSIZE][((int) figure.d.getY() / BLOCKSIZE) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                figure.a.setY(figure.a.getY() + MOVE);
                figure.b.setY(figure.b.getY() + MOVE);
                figure.c.setY(figure.c.getY() + MOVE);
                figure.d.setY(figure.d.getY() + MOVE);
            }
        }
    }

    public static void moveOnKeyPress(Figure figure) {
        Tetris.scene.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.P  && !Tetris.isGameOver()){
                Tetris.pauseGame();
            }
            /*else if (event.getCode() == KeyCode.P && Tetris.gamePaused){
                Tetris.pauseGame();
            }*/
            else if(!Tetris.gamePaused && !Tetris.isGameOver()){
                switch (event.getCode()) {
                    case RIGHT:
                        MoveFigureRight(figure);
                        break;
                    case DOWN:
                        MoveFigureDown(figure);
                        Tetris.score++;
                        break;
                    case LEFT:
                        MoveFigureLeft(figure);
                        break;
                    case UP:
                        RotateFigure(figure);
                        break;

                }
            }
        });
    }

    private static void RotateFigure(Figure figure) {
        int r = figure.rotation;
        Rectangle a = figure.a;
        Rectangle b = figure.b;
        Rectangle c = figure.c;
        Rectangle d = figure.d;

        switch (figure.getName()) {
            case "i":
                if (r == 1 && checkR(a, 2, 2) && checkR(b, 1, 1) && checkR(d, -1, -1)) {
                    MoveUp(figure.a);
                    MoveUp(figure.a);
                    MoveRight(figure.a);
                    MoveRight(figure.a);
                    MoveUp(figure.b);
                    MoveRight(figure.b);
                    MoveDown(figure.d);
                    MoveLeft(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 2 && checkR(a, -2, -2) && checkR(b, -1, -1) && checkR(d, 1, 1)) {
                    MoveDown(figure.a);
                    MoveDown(figure.a);
                    MoveLeft(figure.a);
                    MoveLeft(figure.a);
                    MoveDown(figure.b);
                    MoveLeft(figure.b);
                    MoveUp(figure.d);
                    MoveRight(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 3 && checkR(a, 2, 2) && checkR(b, 1, 1) && checkR(d, -1, -1)) {
                    MoveUp(figure.a);
                    MoveUp(figure.a);
                    MoveRight(figure.a);
                    MoveRight(figure.a);
                    MoveUp(figure.b);
                    MoveRight(figure.b);
                    MoveDown(figure.d);
                    MoveLeft(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 4 && checkR(a, -2, -2) && checkR(b, -1, -1) && checkR(d, 1, 1)) {
                    MoveDown(figure.a);
                    MoveDown(figure.a);
                    MoveLeft(figure.a);
                    MoveLeft(figure.a);
                    MoveDown(figure.b);
                    MoveLeft(figure.b);
                    MoveUp(figure.d);
                    MoveRight(figure.d);
                    figure.rotate();
                    break;
                }
                break;
            case "o":
                break;
            case "t":
                if (r == 1 && checkR(a, 1, 1) && checkR(d, -1, -1) && checkR(c, -1, 1)) {
                    MoveUp(figure.a);
                    MoveRight(figure.a);
                    MoveDown(figure.d);
                    MoveLeft(figure.d);
                    MoveLeft(figure.c);
                    MoveUp(figure.c);
                    figure.rotate();
                    break;
                }
                if (r == 2 && checkR(a, 1, -1) && checkR(d, -1, 1) && checkR(c, 1, 1)) {
                    MoveRight(figure.a);
                    MoveDown(figure.a);
                    MoveLeft(figure.d);
                    MoveUp(figure.d);
                    MoveUp(figure.c);
                    MoveRight(figure.c);
                    figure.rotate();
                    break;
                }
                if (r == 3 && checkR(a, -1, -1) && checkR(d, 1, 1) && checkR(c, 1, -1)) {
                    MoveDown(figure.a);
                    MoveLeft(figure.a);
                    MoveUp(figure.d);
                    MoveRight(figure.d);
                    MoveRight(figure.c);
                    MoveDown(figure.c);
                    figure.rotate();
                    break;
                }
                if (r == 4 && checkR(a, -1, 1) && checkR(d, 1, -1) && checkR(c, -1, -1)) {
                    MoveLeft(figure.a);
                    MoveUp(figure.a);
                    MoveRight(figure.d);
                    MoveDown(figure.d);
                    MoveDown(figure.c);
                    MoveLeft(figure.c);
                    figure.rotate();
                    break;
                }
                break;
            case "s":
                if (r == 1 && checkR(a, -1, -1) && checkR(c, -1, 1) && checkR(d, 0, 2)) {
                    MoveDown(figure.a);
                    MoveLeft(figure.a);
                    MoveLeft(figure.c);
                    MoveUp(figure.c);
                    MoveUp(figure.d);
                    MoveUp(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 2 && checkR(a, 1, 1) && checkR(c, 1, -1) && checkR(d, 0, -2)) {
                    MoveUp(figure.a);
                    MoveRight(figure.a);
                    MoveRight(figure.c);
                    MoveDown(figure.c);
                    MoveDown(figure.d);
                    MoveDown(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 3 && checkR(a, -1, -1) && checkR(c, -1, 1) && checkR(d, 0, 2)) {
                    MoveDown(figure.a);
                    MoveLeft(figure.a);
                    MoveLeft(figure.c);
                    MoveUp(figure.c);
                    MoveUp(figure.d);
                    MoveUp(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 4 && checkR(a, 1, 1) && checkR(c, 1, -1) && checkR(d, 0, -2)) {
                    MoveUp(figure.a);
                    MoveRight(figure.a);
                    MoveRight(figure.c);
                    MoveDown(figure.c);
                    MoveDown(figure.d);
                    MoveDown(figure.d);
                    figure.rotate();
                    break;
                }
            case "z":
                if (r == 1 && checkR(b, 1, 1) && checkR(c, -1, 1) && checkR(d, -2, 0)) {
                    MoveUp(figure.b);
                    MoveRight(figure.b);
                    MoveLeft(figure.c);
                    MoveUp(figure.c);
                    MoveLeft(figure.d);
                    MoveLeft(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 2 && checkR(b, -1, -1) && checkR(c, 1, -1) && checkR(d, 2, 0)) {
                    MoveDown(figure.b);
                    MoveLeft(figure.b);
                    MoveRight(figure.c);
                    MoveDown(figure.c);
                    MoveRight(figure.d);
                    MoveRight(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 3 && checkR(b, 1, 1) && checkR(c, -1, 1) && checkR(d, -2, 0)) {
                    MoveUp(figure.b);
                    MoveRight(figure.b);
                    MoveLeft(figure.c);
                    MoveUp(figure.c);
                    MoveLeft(figure.d);
                    MoveLeft(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 4 && checkR(b, -1, -1) && checkR(c, 1, -1) && checkR(d, 2, 0)) {
                    MoveDown(figure.b);
                    MoveLeft(figure.b);
                    MoveRight(figure.c);
                    MoveDown(figure.c);
                    MoveRight(figure.d);
                    MoveRight(figure.d);
                    figure.rotate();
                    break;
                }
                break;
            case "j":
                if (r == 1 && checkR(a, 1, -1) && checkR(c, -1, -1) && checkR(d, -2, -2)) {
                    MoveRight(figure.a);
                    MoveDown(figure.a);
                    MoveDown(figure.c);
                    MoveLeft(figure.c);
                    MoveDown(figure.d);
                    MoveDown(figure.d);
                    MoveLeft(figure.d);
                    MoveLeft(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 2 && checkR(a, -1, -1) && checkR(c, -1, 1) && checkR(d, -2, 2)) {
                    MoveDown(figure.a);
                    MoveLeft(figure.a);
                    MoveLeft(figure.c);
                    MoveUp(figure.c);
                    MoveLeft(figure.d);
                    MoveLeft(figure.d);
                    MoveUp(figure.d);
                    MoveUp(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 3 && checkR(a, -1, 1) && checkR(c, 1, 1) && checkR(d, 2, 2)) {
                    MoveLeft(figure.a);
                    MoveUp(figure.a);
                    MoveUp(figure.c);
                    MoveRight(figure.c);
                    MoveUp(figure.d);
                    MoveUp(figure.d);
                    MoveRight(figure.d);
                    MoveRight(figure.d);
                    figure.rotate();
                    break;
                }
                if (r == 4 && checkR(a, 1, 1) && checkR(c, 1, -1) && checkR(d, 2, -2)) {
                    MoveUp(figure.a);
                    MoveRight(figure.a);
                    MoveRight(figure.c);
                    MoveDown(figure.c);
                    MoveRight(figure.d);
                    MoveRight(figure.d);
                    MoveDown(figure.d);
                    MoveDown(figure.d);
                    figure.rotate();
                    break;
                }
                break;
            case "l":
                if (r == 1 && checkR(a, 1, -1) && checkR(c, 1, 1) && checkR(b, 2, 2)) {
                    MoveRight(figure.a);
                    MoveDown(figure.a);
                    MoveUp(figure.c);
                    MoveRight(figure.c);
                    MoveUp(figure.b);
                    MoveUp(figure.b);
                    MoveRight(figure.b);
                    MoveRight(figure.b);
                    figure.rotate();
                    break;
                }
                if (r == 2 && checkR(a, -1, -1) && checkR(b, 2, -2) && checkR(c, 1, -1)) {
                    MoveDown(figure.a);
                    MoveLeft(figure.a);
                    MoveRight(figure.b);
                    MoveRight(figure.b);
                    MoveDown(figure.b);
                    MoveDown(figure.b);
                    MoveRight(figure.c);
                    MoveDown(figure.c);
                    figure.rotate();
                    break;
                }
                if (r == 3 && checkR(a, -1, 1) && checkR(c, -1, -1) && checkR(b, -2, -2)) {
                    MoveLeft(figure.a);
                    MoveUp(figure.a);
                    MoveDown(figure.c);
                    MoveLeft(figure.c);
                    MoveDown(figure.b);
                    MoveDown(figure.b);
                    MoveLeft(figure.b);
                    MoveLeft(figure.b);
                    figure.rotate();
                    break;
                }
                if (r == 4 && checkR(a, 1, 1) && checkR(b, -2, 2) && checkR(c, -1, 1)) {
                    MoveUp(figure.a);
                    MoveRight(figure.a);
                    MoveLeft(figure.b);
                    MoveLeft(figure.b);
                    MoveUp(figure.b);
                    MoveUp(figure.b);
                    MoveLeft(figure.c);
                    MoveUp(figure.c);
                    figure.rotate();
                    break;
                }
                break;
        }
    }

    //Moving single rectangle by one BLOCKSIZE
    private static void MoveUp(Rectangle a) {
        if (a.getY() - MOVE > 0) a.setY(a.getY() - MOVE);
    }

    private static void MoveDown(Rectangle a) {
        if (a.getY() + MOVE < HEIGHT) a.setY(a.getY() + MOVE);
    }

    private static void MoveRight(Rectangle a) {
        if (a.getX() + MOVE <= WIDTH - BLOCKSIZE) a.setX(a.getX() + MOVE);
    }

    private static void MoveLeft(Rectangle a) {
        if (a.getX() - MOVE >= 0) a.setX(a.getX() - MOVE);
    }

    //return true if we can move ractangle x,y direction
    private static boolean checkR(Rectangle a, int x, int y) {
        boolean xb = false;
        boolean yb = false;

        if (x >= 0) xb = a.getX() + x * MOVE <= WIDTH - BLOCKSIZE;
        if (x < 0) xb = a.getX() + x * MOVE >= 0;
        if (y >= 0) yb = a.getY() - y * MOVE > 0;
        if (y < 0) yb = a.getY() + y * MOVE < HEIGHT;
        //boolean freeSpace = BOARD[((int) a.getX() / BLOCKSIZE) + x][((int) a.getY() / BLOCKSIZE) - y] == 0;
        return xb && yb && BOARD[((int) a.getX() / BLOCKSIZE) + x][((int) a.getY() / BLOCKSIZE) - y] == 0;


    }

    private static void RemoveRows(Pane pane) {
        ArrayList<Node> rects = new ArrayList<Node>();
        ArrayList<Integer> lines = new ArrayList<Integer>();
        ArrayList<Node> newRects = new ArrayList<Node>();
        int full = 0;
        //check each x axis if there is a full line
        for (int i = 0; i < BOARD[0].length; i++) {
            for (int j = 0; j < BOARD.length; j++) {
                if (BOARD[j][i] == 1) full++;
            }
            if (full == BOARD.length) {
                lines.add(i);
            }
            full = 0;
        }
        //removing rectangles since theres no full lines
        if (lines.size() > 0) {
            do {
                for(Node node : pane.getChildren()){
                    if(node instanceof Rectangle) rects.add(node);
                }
                Tetris.score += 50;
                Tetris.numOfLines++;
                //delete block on row
                for(Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    //remove rectangle if inside full line
                    if(a.getY() == lines.get(0) * BLOCKSIZE){
                        BOARD[(int) a.getX() / BLOCKSIZE][(int) a.getY() / BLOCKSIZE] = 0;
                        pane.getChildren().remove(node);
                    }
                    //add rectangle for moving down purposes if not inside full line
                    else if(a.getX() > WIDTH){}
                    else newRects.add(node);
                }
                //move down not deleted rectangles
                for(Node node : newRects) {
                    Rectangle a = (Rectangle) node;
                    if (a.getY() < lines.get(0) * BLOCKSIZE) {
                        BOARD[(int) a.getX() / BLOCKSIZE][(int) a.getY() / BLOCKSIZE] = 0;
                        a.setY(a.getY() + BLOCKSIZE);
                    }
                }
                    lines.remove(0);
                    rects.clear();
                    newRects.clear();
                //get new rectangles
                for(Node node : pane.getChildren()){
                    if(node instanceof Rectangle) rects.add(node);
                }
                //update BOARD positions
                for(Node node : rects) {
                    Rectangle a = (Rectangle) node;
                    try {
                        BOARD[(int) a.getX() / BLOCKSIZE][(int) a.getY() / BLOCKSIZE] = 1;
                    } catch (ArrayIndexOutOfBoundsException e){
                    }
                }
                rects.clear();
            }while(lines.size() > 0);
        }
    }

    private static boolean cannotMoveA(Figure figure){
        return (BOARD[(int) figure.a.getX() / BLOCKSIZE][((int) figure.a.getY()/BLOCKSIZE) + 1] == 1);
    }
    private static boolean cannotMoveB(Figure figure){
        return (BOARD[(int) figure.b.getX() / BLOCKSIZE][((int) figure.b.getY()/BLOCKSIZE) + 1] == 1);
    }
    private static boolean cannotMoveC(Figure figure){
        return (BOARD[(int) figure.c.getX() / BLOCKSIZE][((int) figure.c.getY()/BLOCKSIZE) + 1] == 1);
    }
    private static boolean cannotMoveD(Figure figure){
        return (BOARD[(int) figure.d.getX() / BLOCKSIZE][((int) figure.d.getY()/BLOCKSIZE) + 1] == 1);
    }
}
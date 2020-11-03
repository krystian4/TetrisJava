package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Figure {
    public static final int BLOCKSIZE = Tetris.BLOCKSIZE;
    public static int WIDTH = Tetris.WIDTH;
    public static int HEIGHT = Tetris.HEIGHT;
    //built from 4 blocks
    Rectangle a;
    Rectangle b;
    Rectangle c;
    Rectangle d;
    private Color color;
    private String name;
    public int rotation = 1;
    public Figure(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.name = name;

        switch(name){
            case "i":
                color = Color.AQUA;
                break;
            case "o":
                color = Color.YELLOW;
                break;
            case "t":
                color = Color.PURPLE;
                break;
            case "s":
                color = Color.GREEN;
                break;
            case "z":
                color = Color.RED;
                break;
            case "j":
                color = Color.BLUE;
                break;
            case "l":
                color = Color.ORANGE;
                break;
        }
        this.a.setFill(color);
        this.b.setFill(color);
        this.c.setFill(color);
        this.d.setFill(color);
    }

    public void putOnBoard(){
        if (name == "i") {
            a.setX(WIDTH / 2 - (2 * BLOCKSIZE));
            a.setY(0);
            b.setX(WIDTH / 2 - BLOCKSIZE);
            b.setY(0);
            c.setX(WIDTH / 2);
            c.setY(0);
            d.setX(WIDTH / 2 + BLOCKSIZE);
            d.setY(0);
        } else if (name == "o") {
            a.setX(WIDTH / 2 - BLOCKSIZE);
            a.setY(0);
            b.setX(WIDTH / 2);
            b.setY(0);
            c.setX(WIDTH / 2 - BLOCKSIZE);
            c.setY(BLOCKSIZE);
            d.setX(WIDTH / 2);
            d.setY(BLOCKSIZE);
        } else if (name == "t") {
            a.setX(WIDTH / 2 - BLOCKSIZE);
            a.setY(0);
            b.setX(WIDTH / 2);
            b.setY(0);
            c.setX(WIDTH / 2);
            c.setY(BLOCKSIZE);
            d.setX(WIDTH / 2 + BLOCKSIZE);
            d.setY(0);
        } else if (name == "s") {
            a.setX(WIDTH / 2 + BLOCKSIZE);
            a.setY(0);
            b.setX(WIDTH / 2);
            b.setY(0);
            c.setX(WIDTH / 2);
            c.setY(BLOCKSIZE);
            d.setX(WIDTH / 2 - BLOCKSIZE);
            d.setY(BLOCKSIZE);
        } else if (name == "z") {
            a.setX(WIDTH / 2);
            a.setY(0);
            b.setX(WIDTH / 2 - BLOCKSIZE);
            b.setY(0);
            c.setX(WIDTH / 2);
            c.setY(BLOCKSIZE);
            d.setX(WIDTH / 2 + BLOCKSIZE);
            d.setY(BLOCKSIZE);
        } else if (name == "j") {
            a.setX(WIDTH / 2 - BLOCKSIZE);
            a.setY(0);
            b.setX(WIDTH / 2 - BLOCKSIZE);
            b.setY(BLOCKSIZE);
            c.setX(WIDTH / 2);
            c.setY(BLOCKSIZE);
            d.setX(WIDTH / 2 + BLOCKSIZE);
            d.setY(BLOCKSIZE);
        } else if (name == "l") {
            a.setX(WIDTH / 2 + BLOCKSIZE);
            a.setY(0);
            b.setX(WIDTH / 2 - BLOCKSIZE);
            b.setY(BLOCKSIZE);
            c.setX(WIDTH / 2);
            c.setY(BLOCKSIZE);
            d.setX(WIDTH / 2 + BLOCKSIZE);
            d.setY(BLOCKSIZE);
        }
    }
    public String getName(){
        return this.name;
    }

    public void rotate(){
        if(this.rotation < 4){
            this.rotation++;
        }
        else rotation = 1;
    }
}

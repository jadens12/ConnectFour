package connectfour.gui;


import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ConnectFourButton extends Button{

    private Image empty = new Image(getClass().getResourceAsStream("empty.png"));
    private Image p1black = new Image(getClass().getResourceAsStream("p1black.png"));
    private Image p2red = new Image(getClass().getResourceAsStream("p2red.png"));

    private String current;
    private static Image[] imageList = new Image[3];
    private int column;

    public ConnectFourButton(String current, int column){
        this.column = column;
        this.current = current;
        Image image;
        imageList[0] = empty;
        imageList[1] = p1black;
        imageList[2] = p2red;

        if (current.equals("empty")){
            image = empty;
        }
        else if (current.equals("p1")){
            image = p1black;
        }
        else{
            image = p2red;
        }
        this.setGraphic(new ImageView(image));
    }

    public int getColumn(){
        return this.column;
    }
    public static Image getImage(int i){
        return imageList[i];
    }
}

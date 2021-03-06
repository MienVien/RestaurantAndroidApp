package icon.melb.melbicon;

import android.graphics.Bitmap;

import java.io.Serializable;

public class MenuItem implements Serializable{
    private boolean available;
    private String title;
    private String description;
    private double price;
    private boolean vegetarian;
    private boolean glutenfree;
    private String img_src;
    private transient Bitmap imageBitmap;


    public MenuItem() {
        this.title = "";
        this.description = "";
        this.price = 0;
        this.vegetarian = false;
        this.glutenfree = false;
        this.img_src = "";
        this.available = false;
    }

    public MenuItem(String title, String description, double price, boolean vegetarian, boolean glutenfree, String img_src, boolean available) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.vegetarian = vegetarian;
        this.glutenfree = glutenfree;
        this.img_src = img_src;
        this.available = available;
    }
    
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public boolean isGlutenfree() {
        return glutenfree;
    }

    public String getImg_src() {
        return img_src;
    }

    public boolean getAvailable() {
        return available;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public String toString() {
        return getClass().getName()
                + "\ntitle: " + title
                +"\ndescription: " + description
                +"\nprice: " + price
                +"\nvegetarian: " + vegetarian
                +"\nglutenfree: " + glutenfree
                +"\nimg_src: " +img_src
                +"\navailable: " + available;
    }
}

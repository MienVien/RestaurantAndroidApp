package icon.melb.melbicon;

public class Item {
    private boolean available;
    private String title;
    private String description;
    private double price;
    private boolean vegetarian;
    private boolean glutenfree;
    private String img_src;

    public Item () {
        this.title = "";
        this.description = "";
        this.price = 0;
        this.vegetarian = false;
        this.glutenfree = false;
        this.img_src = "";
        this.available = false;
    }

    public Item(String title, String description, double price, boolean vegetarian, boolean glutenfree, String img_src, boolean available) {
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setVegetarian(boolean vegetarian) {
        vegetarian = vegetarian;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }

    public void setGlutenfree(boolean glutenfree) {
        this.glutenfree = glutenfree;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

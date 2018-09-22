package icon.melb.melbicon;

public class Item {
    private String name;
    private String description;
    private int price;
    private boolean isVegetarian;
    private int thumbnail;

    public Item () {
        this.name = "";
        this.description = "";
        this.price = 0;
        this.isVegetarian = false;
        this.thumbnail = 0;
    }

    public Item(String name, String description, int price, boolean isVegetarian, int thumbnail) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isVegetarian = isVegetarian;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setVegetarian(boolean vegetarian) {
        isVegetarian = vegetarian;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}

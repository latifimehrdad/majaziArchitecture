package land.majazi.latifiarchitecure.models;

public class MD_ImageSize {

    private int Width;
    private int Height;

    public MD_ImageSize(int width, int height) {
        Width = width;
        Height = height;
    }


    public int getWidth() {
        return Width;
    }

    public void setWidth(int width) {
        Width = width;
    }

    public int getHeight() {
        return Height;
    }

    public void setHeight(int height) {
        Height = height;
    }
}

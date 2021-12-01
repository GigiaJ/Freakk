/**
 * @author		GigiaJ
 * @filename	ImageInformation.java
 * @date		Mar 27, 2020
 * @description 
 */

package screenCapturer;

class ImageInformation {
    private int height;
    private int width;
    private int topRightX;
    private int topRightY;

    public ImageInformation(int pressDownX, int pressDownY, int releaseX, int releaseY) {
        this.height = Math.abs(pressDownX - releaseX);
        this.width = Math.abs(pressDownY - releaseY);
        if (pressDownX > releaseX) {
            this.topRightX = releaseX;
        } else {
            this.topRightX = pressDownX;
        }

        if (pressDownY > releaseY) {
            this.topRightY = releaseY;
        } else {
            this.topRightY = pressDownY;
        }

    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getTopRightX() {
        return this.topRightX;
    }

    public void setTopRightX(int topRightX) {
        this.topRightX = topRightX;
    }

    public int getTopRightY() {
        return this.topRightY;
    }

    public void setTopRightY(int topRightY) {
        this.topRightY = topRightY;
    }
}

package ru.kpfu.itis.gadelev.helpers;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class SpriteAnimation  extends Transition{
    private final ImageView imageView;
    private final int count;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final int width;
    private final int height;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int count,
            int columns,
            int offsetX,
            int offsetY,
            int width,
            int height

            ){
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
        setCycleCount(Animation.INDEFINITE);
        setInterpolator(Interpolator.LINEAR);
        this.imageView.setViewport(new Rectangle2D(offsetX, offsetY, width, height));
    }

    protected synchronized void interpolate(double frac) {
        final int index = Math.min((int)Math.floor(count*frac), count-1);
        final int x = (index%columns)*width+offsetX;
        final int y = (index/columns)*height+offsetY;
        imageView.setViewport(new Rectangle2D(x, y, width, height));
    }
    public synchronized void setParameters(int spriteIndex, int spriteColumns, int spriteWidth, int spriteHeight,
                                           int spriteOffsetX, int spriteOffsetY) {
        javafx.application.Platform.runLater(() -> {
            int x = (spriteIndex % spriteColumns) * spriteWidth + spriteOffsetX;
            int y = (spriteIndex / spriteColumns) * spriteHeight + spriteOffsetY;

            imageView.setViewport(new Rectangle2D(x, y, spriteWidth, spriteHeight));
        });
    }
    public void setX(double x) {
        imageView.setX(x);
    }

    public void setY(double y) {
        imageView.setY(y);
    }
}
package ru.kpfu.itis.gadelev.models;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ru.kpfu.itis.gadelev.Game;
import ru.kpfu.itis.gadelev.GameView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Block extends Pane {
    Image blocksImg = new Image(new FileInputStream("src/ru/kpfu/itis/gadelev/images/2.png"));
    ImageView block;
    public enum BlockType {
        PLATFORM, BRICK, BONUS, PIPE_TOP, PIPE_BOTTOM, INVISIBLE_BLOCK, STONE
    }
    public Block(BlockType blockType, int x, int y) throws FileNotFoundException {
        block = new ImageView(blocksImg);
        block.setFitWidth(GameView.BLOCK_SIZE);
        block.setFitHeight(GameView.BLOCK_SIZE);
        setTranslateX(x);
        setTranslateY(y);

        switch (blockType) {
            case PLATFORM -> block.setViewport(new Rectangle2D(0, 0, 16, 16));
            case BRICK -> block.setViewport(new Rectangle2D(16, 0, 16, 16));
            case BONUS -> block.setViewport(new Rectangle2D(384, 0, 16, 16));
            case PIPE_TOP -> {
                block.setViewport(new Rectangle2D(0, 128, 32, 16));
                block.setFitWidth(GameView.BLOCK_SIZE * 2);
            }
            case PIPE_BOTTOM -> {
                block.setViewport(new Rectangle2D(0, 145, 32, 14));
                block.setFitWidth(GameView.BLOCK_SIZE * 2);
            }
            case INVISIBLE_BLOCK -> {
                block.setViewport(new Rectangle2D(0, 0, 16, 16));
                block.setOpacity(0);
            }
            case STONE -> block.setViewport(new Rectangle2D(0, 16, 16, 16));
        }
        getChildren().add(block);
        GameView.platforms.add(this);
        GameView.gameRoot.getChildren().add(this);
    }
}
package assignment3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MiniPart1Test {   // =======  12 points =======
    @Test
    @Tag("score:1")
    @DisplayName("Block constructor test1")
    void BlockConstructorTest1() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(50);
        Block b = new Block(0, 2);

        Field childrenField = Block.class.getDeclaredField("children");
        childrenField.setAccessible(true);

        Block[] children = (Block[]) childrenField.get(b);

        assertEquals(4, children.length);

        Block[] urChildren = (Block[]) childrenField.get(children[0]);
        Block[] ulChildren = (Block[]) childrenField.get(children[1]);
        Block[] llChildren = (Block[]) childrenField.get(children[2]);
        Block[] lrChildren = (Block[]) childrenField.get(children[3]);

        assertEquals(4, urChildren.length);
        assertEquals(0, ulChildren.length);
        assertEquals(0, llChildren.length);
        assertEquals(4, lrChildren.length);

    }

    @Test
    @Tag("score:3")
    @DisplayName("Block constructor test2")
    void BlockConstructorTest2() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(4);
        Block b = new Block(0, 2);

        Field childrenField = Block.class.getDeclaredField("children");
        Field colorField = Block.class.getDeclaredField("color");
        childrenField.setAccessible(true);
        colorField.setAccessible(true);

        Block[] children = (Block[]) childrenField.get(b);

        ArrayList<Color> expectedColor = new ArrayList<>();
        expectedColor.add(GameColors.YELLOW);
        expectedColor.add(null);
        expectedColor.add(GameColors.YELLOW);
        expectedColor.add(GameColors.RED);

        ArrayList<Color> actualColor = new ArrayList<>();

        for (Block child : children) {
            actualColor.add((Color) colorField.get(child));
        }

        assertEquals(expectedColor, actualColor);  // checking if the colors at level 1 are correct

        Block[] ulChildren = (Block[]) childrenField.get(children[1]);

        assertEquals(4, ulChildren.length);

        List<Color> expectedColorUL = List.of(GameColors.GREEN, GameColors.RED, GameColors.GREEN, GameColors.RED);
        List<Color> actualColorUL = new ArrayList<>();

        for (Block child : ulChildren) {
            actualColorUL.add((Color) colorField.get(child));
        }

        assertEquals(expectedColorUL, actualColorUL);  // checking if the colors at level 2 are correct for the upper left child
    }

    @Test
    @Tag("score:3")
    @DisplayName("Block updateSizeAndPosition() test1")
    void UpdateSizeAndPositionTest1() throws NoSuchFieldException, IllegalAccessException {
        Block[] children = new Block[4];
        children[0] = new Block(0, 0, 0, 1, 2, GameColors.YELLOW, new Block[0]);
        children[1] = new Block(0, 0, 0, 1, 2, GameColors.RED, new Block[0]);
        children[2] = new Block(0, 0, 0, 1, 2, GameColors.GREEN, new Block[0]);
        children[3] = new Block(0, 0, 0, 1, 2, GameColors.RED, new Block[0]);
        Block b = new Block(0, 0, 0, 0, 2, null, children);

        b.updateSizeAndPosition(16, 0, 0);

        Field childrenField = Block.class.getDeclaredField("children");
        Field sizeField = Block.class.getDeclaredField("size");
        Field xcoordField = Block.class.getDeclaredField("xCoord");
        Field ycoordField = Block.class.getDeclaredField("yCoord");

        childrenField.setAccessible(true);
        sizeField.setAccessible(true);
        xcoordField.setAccessible(true);
        ycoordField.setAccessible(true);

        assertEquals(16, (int) sizeField.get(b));
        assertEquals(0, (int) xcoordField.get(b));
        assertEquals(0, (int) ycoordField.get(b));


        ArrayList<Integer> actualSize = new ArrayList<>();
        ArrayList<Integer> Coords = new ArrayList<>();

        for (Block child : (Block[]) childrenField.get(b)) {
            actualSize.add((int) sizeField.get(child));
            Coords.add((int) xcoordField.get(child));
            Coords.add((int) ycoordField.get(child));
        }

        List<Integer> expectedSize = List.of(8, 8, 8, 8);
        List<Integer> expectedCoords = List.of(8, 0, 0, 0, 0, 8, 8, 8);  // UL x, UL y, UR x, UR y, LL x, LL y, LR x, LR y

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedCoords, Coords);
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block updateSizeAndPosition() test2")
    void UpdateSizeAndPositionTest2() {
        Block b = new Block(0, 0, 0, 0, 2, null, new Block[0]);

        assertThrows(IllegalArgumentException.class, () -> b.updateSizeAndPosition(-1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> b.updateSizeAndPosition(3, 0, 0));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getBlocksToDraw() test1")
    void GetBlocksToDrawTest1() {
        Block.gen = new Random(4);
        Block b = new Block(0, 2);
        b.updateSizeAndPosition(16, 0, 0);

        ArrayList<BlockToDraw> blocksToDraw = b.getBlocksToDraw();

        assertEquals(14, blocksToDraw.size());

        int frameCount = 0;
        int blockCount = 0;

        for (BlockToDraw btd : blocksToDraw) {
            if (btd.getColor() == GameColors.FRAME_COLOR) {
                frameCount++;
            } else if (btd.getStrokeThickness() == 0) {
                blockCount++;
            }
        }

        assertEquals(7, frameCount);
        assertEquals(7, blockCount);
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getBlocksToDraw() test2")
    void GetBlocksToDrawTest2() {
        Block[] children = new Block[0];
        Block b = new Block(0, 0, 16, 0, 2, GameColors.YELLOW, children);

        ArrayList<BlockToDraw> blocksToDraw = b.getBlocksToDraw();
        assertEquals(2, blocksToDraw.size());

        for (BlockToDraw btd : blocksToDraw) {
            boolean frame = btd.getStrokeThickness() == 0 && btd.getColor() == GameColors.YELLOW;
            boolean block = btd.getStrokeThickness() == 3 && btd.getColor() == GameColors.FRAME_COLOR;
            assertTrue(frame || block);
        }
    }
}
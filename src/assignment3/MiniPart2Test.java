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

public class MiniPart2Test {  // ========= 12 points =========
    @Test
    @Tag("score:2")
    @DisplayName("Block getSelectedBlock() test1")
    void getSelectedBlock1() {
        Block b = new Block(0, 0, 0, 0, 2, null, new Block[0]);

        assertThrows(IllegalArgumentException.class, () -> b.getSelectedBlock(2,15,4));
        assertThrows(IllegalArgumentException.class, () -> b.getSelectedBlock(15,2,-1));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getSelectedBlock() test2")
    void getSelectedBlock2() throws NoSuchFieldException, IllegalAccessException {
        Block[] children = new Block[4];
        children[0] = new Block(1, 0, 1, 1, 1, GameColors.YELLOW, new Block[0]);
        children[1] = new Block(0, 0, 1, 1, 1, GameColors.RED, new Block[0]);
        children[2] = new Block(0, 1, 1, 1, 1, GameColors.GREEN, new Block[0]);
        children[3] = new Block(1, 1, 1, 1, 1, GameColors.BLUE, new Block[0]);

        Block b = new Block(0, 0, 2, 0, 1, null, children);

        Field xCoordField = Block.class.getDeclaredField("xCoord");
        Field yCoordField = Block.class.getDeclaredField("yCoord");
        Field colorField = Block.class.getDeclaredField("color");

        xCoordField.setAccessible(true);
        yCoordField.setAccessible(true);
        colorField.setAccessible(true);

        Block res = b.getSelectedBlock(0, 0, 1);

        assertEquals(0, (int) xCoordField.get(res));
        assertEquals(0, (int) yCoordField.get(res));
        assertEquals(GameColors.RED, colorField.get(res));
    }

    @Test
    @Tag("score:2")
    @DisplayName("Block getSelectedBlock() test3")
    void getSelectedBlock3() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(4);
        Block b = new Block(0, 3);
        b.updateSizeAndPosition(16,0,0);

        Block res = b.getSelectedBlock(9,10, 2);

        Field xCoordField = Block.class.getDeclaredField("xCoord");
        Field yCoordField = Block.class.getDeclaredField("yCoord");
        Field colorField = Block.class.getDeclaredField("color");
        Field childrenField = Block.class.getDeclaredField("children");

        xCoordField.setAccessible(true);
        yCoordField.setAccessible(true);
        colorField.setAccessible(true);
        childrenField.setAccessible(true);

        assertEquals(8, (int) xCoordField.get(res));
        assertEquals(8, (int) yCoordField.get(res));
        assertNull(colorField.get(res));
        assertEquals(4, ((Block[]) childrenField.get(res)).length);

        List<Color> colors = List.of(GameColors.BLUE, GameColors.YELLOW, GameColors.GREEN, GameColors.YELLOW);

        Block[] children = (Block[]) childrenField.get(res);

        for (int i = 0; i < 4; i++) {
            Block child = children[i];
            assertEquals(colors.get(i), colorField.get(child));
        }
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block reflect() test1")
    void reflect1() {
        Block b = new Block(0, 0, 0, 0, 2, null, new Block[0]);

        assertThrows(IllegalArgumentException.class, () ->  b.reflect(2));
        assertThrows(IllegalArgumentException.class, () ->  b.reflect(-1));
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block reflect() test2")
    void reflect2() throws NoSuchFieldException, IllegalAccessException {
        Block[] children = new Block[4];
        children[0] = new Block(1, 0, 1, 1, 1, GameColors.YELLOW, new Block[0]);  // UR
        children[1] = new Block(0, 0, 1, 1, 1, GameColors.RED, new Block[0]);   // UL
        children[2] = new Block(0, 1, 1, 1, 1, GameColors.GREEN, new Block[0]); // LL
        children[3] = new Block(1, 1, 1, 1, 1, GameColors.BLUE, new Block[0]);  // LR
        Block b = new Block(0, 0, 2, 0, 1, null, children);

        b.reflect(0);  // reflect horizontally

        Field childrenField = Block.class.getDeclaredField("children");
        Field colorField = Block.class.getDeclaredField("color");
        childrenField.setAccessible(true);
        colorField.setAccessible(true);

        Block[] childrenLevel1 = (Block[]) childrenField.get(b);

        List<Color> expected = List.of(GameColors.BLUE, GameColors.GREEN, GameColors.RED, GameColors.YELLOW);
        List<Color> actual = new ArrayList<>();

        for (Block child : childrenLevel1) {
            actual.add((Color) colorField.get(child));
        }

        assertEquals(expected, actual);

    }

    @Test
    @Tag("score:1")
    @DisplayName("Block rotate() test1")
    void rotate1() {
        Block b = new Block();
        assertThrows(IllegalArgumentException.class, () ->  b.rotate(2));
        assertThrows(IllegalArgumentException.class, () ->  b.rotate(-1));
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block rotate() test2")
    void rotate2() throws NoSuchFieldException, IllegalAccessException {
        Block[] children = new Block[4];
        children[0] = new Block(1, 0, 1, 1, 1, GameColors.GREEN, new Block[0]);  // UR
        children[1] = new Block(0, 0, 1, 1, 1, GameColors.BLUE, new Block[0]);   // UL
        children[2] = new Block(0, 1, 1, 1, 1, GameColors.RED, new Block[0]); // LL
        children[3] = new Block(1, 1, 1, 1, 1, GameColors.BLUE, new Block[0]);  // LR

        Block b = new Block(0, 0, 2, 0, 1, null, children);

        b.rotate(1); // rotate counter-clockwise

        Field childrenField = Block.class.getDeclaredField("children");
        Field colorField = Block.class.getDeclaredField("color");
        childrenField.setAccessible(true);
        colorField.setAccessible(true);

        Block[] childrenLevel1 = (Block[]) childrenField.get(b);

        List<Color> expected = List.of(GameColors.BLUE, GameColors.RED, GameColors.BLUE, GameColors.GREEN);

        List <Color> actual = new ArrayList<>();
        for (Block child : childrenLevel1) {
            actual.add((Color) colorField.get(child));
        }

        assertEquals(expected, actual);

    }

    @Test
    @Tag("score:1")
    @DisplayName("Block smash() test1")
    void smash1() {
        Block b = new Block();

        assertFalse(b.smash());

        Block[] children = new Block[4];
        children[0] = new Block(1, 0, 1, 1, 1, GameColors.YELLOW, new Block[0]);  // UR
        children[1] = new Block(0, 0, 1, 1, 1, GameColors.BLUE, new Block[0]);   // UL
        children[2] = new Block(0, 1, 1, 1, 1, GameColors.GREEN, new Block[0]); // LL
        children[3] = new Block(1, 1, 1, 1, 1, GameColors.BLUE, new Block[0]);  // LR

        b = new Block(0, 0, 2, 1, 2, null, children);

        assertTrue(b.smash());
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block smash() test2")
    void smash2() throws NoSuchFieldException, IllegalAccessException {
        Block.gen = new Random(0);
        Block b = new Block(1, 2);
        b.updateSizeAndPosition(4, 0,0);

        b.smash();

        Field childrenField = Block.class.getDeclaredField("children");
        Field colorField = Block.class.getDeclaredField("color");
        childrenField.setAccessible(true);
        colorField.setAccessible(true);

        Block[] childrenLevel1 = (Block[]) childrenField.get(b);

        List<Color> expected = List.of(GameColors.BLUE, GameColors.RED, GameColors.BLUE, GameColors.YELLOW);
        List<Color> actual = new ArrayList<>();

        for (Block child : childrenLevel1) {
            actual.add((Color) colorField.get(child));
        }

        assertEquals(expected, actual);
    }
}
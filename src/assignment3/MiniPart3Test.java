package assignment3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MiniPart3Test {  // ======== 16 points ========

    @Test // same as the one in the pdf
    @Tag("score:2")
    @DisplayName("Block flatten() test1")
    void Blockflatten1() {
        Block.gen = new Random(2);
        Block b = new Block(0, 2);
        b.updateSizeAndPosition(16, 0,0);

        Color[][] c = b.flatten();

        Color[][] expected = new Color[][]{
                {GameColors.RED, GameColors.RED, GameColors.GREEN, GameColors.GREEN},
                {GameColors.RED, GameColors.RED, GameColors.GREEN, GameColors.GREEN},
                {GameColors.YELLOW, GameColors.YELLOW, GameColors.RED, GameColors.BLUE},
                {GameColors.YELLOW, GameColors.YELLOW, GameColors.YELLOW, GameColors.BLUE}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], c[i][j]);
            }
        }
    }

    @Test
    @Tag("score:1")
    @DisplayName("Block flatten() test2")
    void Blockflatten2() {
        Block[] children = new Block[4];
        children[0] = new Block(8, 0, 8, 1, 1, GameColors.BLUE, new Block[0]);
        children[1] = new Block(0, 0, 8, 1, 1, GameColors.YELLOW, new Block[0]);
        children[2] = new Block(0, 8, 8, 1, 1, GameColors.RED, new Block[0]);
        children[3] = new Block(8, 8, 8, 1, 1, GameColors.GREEN, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 1, null, children);

        Color[][] c = b.flatten();

        Color[][] expected = new Color[][]{
                {GameColors.YELLOW, GameColors.BLUE},
                {GameColors.RED, GameColors.GREEN}
        };

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                assertEquals(expected[i][j], c[i][j]);
            }
        }
    }

    @Test
    @Tag("score:3")
    @DisplayName("Block flatten() test3")
    void Blockflatten3() {
        Block[] children = new Block[4];
        Block[] llChildren = new Block[4];

        llChildren[0] = new Block(4, 8, 4, 2, 2, GameColors.RED, new Block[0]);
        llChildren[1] = new Block(0, 8, 4, 2, 2, GameColors.GREEN, new Block[0]);
        llChildren[2] = new Block(0, 12, 4, 2, 2, GameColors.GREEN, new Block[0]);
        llChildren[3] = new Block(4, 12, 4, 2, 2, GameColors.YELLOW, new Block[0]);

        children[0] = new Block(8, 0, 8, 1, 2, GameColors.RED, new Block[0]);
        children[1] = new Block(0, 0, 8, 1, 2, GameColors.BLUE, new Block[0]);
        children[2] = new Block(0, 8, 8, 1, 2, null, llChildren);
        children[3] = new Block(8, 8, 8, 1, 2, GameColors.YELLOW, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 2, null, children);

        Color[][] c = b.flatten();

        Color[][] expected = new Color[][]{
                {GameColors.BLUE, GameColors.BLUE, GameColors.RED, GameColors.RED},
                {GameColors.BLUE, GameColors.BLUE, GameColors.RED, GameColors.RED},
                {GameColors.GREEN, GameColors.RED, GameColors.YELLOW, GameColors.YELLOW},
                {GameColors.GREEN, GameColors.YELLOW, GameColors.YELLOW, GameColors.YELLOW}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(expected[i][j], c[i][j]);
            }
        }
    }


    @Test
    @Tag("score:1")
    @DisplayName("PerimeterGoal score() test1")
    void PGscore1() {
        Block[] children = new Block[4];

        children[0] = new Block(8, 0, 8, 1, 2, GameColors.GREEN, new Block[0]);
        children[1] = new Block(0, 0, 8, 1, 2, GameColors.BLUE, new Block[0]);
        children[2] = new Block(0, 8, 8, 1, 2, GameColors.RED, new Block[0]);
        children[3] = new Block(8, 8, 8, 1, 2, GameColors.YELLOW, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 2, null, children);

        PerimeterGoal p = new PerimeterGoal(GameColors.RED);
        assertEquals(4, p.score(b));
    }

    @Test
    @Tag("score:1")
    @DisplayName("PerimeterGoal score() test2")
    void PGscore2() {
        Block.gen = new Random(8);
        Block b = new Block(0, 2);
        b.updateSizeAndPosition(16, 0, 0);

        PerimeterGoal p = new PerimeterGoal(GameColors.YELLOW);
        assertEquals(3, p.score(b));
    }

    @Test
    @Tag("score:2")
    @DisplayName("BlobGoal undiscoveredBlobSize() test1")
    void BGBlobSize1() {
        BlobGoal g = new BlobGoal(GameColors.BLUE);

        Color[][] c = new Color[][]{
                {GameColors.YELLOW, GameColors.BLUE},
                {GameColors.RED, GameColors.RED}
        };

        assertEquals(0, g.undiscoveredBlobSize(0, 0, c, new boolean[2][2]));
    }

    @Test
    @Tag("score:2")
    @DisplayName("BlobGoal undiscoveredBlobSize() test2")
    void BGBlobSize2() {
        BlobGoal g = new BlobGoal(GameColors.RED);

        Color[][] c = new Color[][]{
                {GameColors.BLUE, GameColors.RED, GameColors.GREEN},
                {GameColors.RED, GameColors.YELLOW, GameColors.RED},
                {GameColors.RED, GameColors.YELLOW, GameColors.GREEN},
                {GameColors.RED, GameColors.RED, GameColors.YELLOW}
        };

        assertEquals(1, g.undiscoveredBlobSize(0, 1, c, new boolean[4][3]));
    }

    @Test
    @Tag("score:2")
    @DisplayName("BlobGoal undiscoveredBlobSize() test3")
    void BGBlobSize3() {
        Block.gen = new Random(8);
        Block b = new Block(0, 2);
        b.updateSizeAndPosition(16, 0, 0);

        BlobGoal g = new BlobGoal(GameColors.YELLOW);
        assertEquals(2, g.undiscoveredBlobSize(1, 1, b.flatten(), new boolean[4][4]));
    }

    @Test
    @Tag("score:1")
    @DisplayName("BlobGoal score() test1")
    void BGscore1() {
        Block[] children = new Block[4];
        Block[] urChildren = new Block[4];

        urChildren[0] = new Block(12, 0, 4, 2, 2, GameColors.GREEN, new Block[0]);
        urChildren[1] = new Block(8, 0, 4, 2, 2, GameColors.BLUE, new Block[0]);
        urChildren[2] = new Block(8, 4, 4, 2, 2, GameColors.RED, new Block[0]);
        urChildren[3] = new Block(12, 4, 4, 2, 2, GameColors.YELLOW, new Block[0]);

        children[0] = new Block(8, 0, 8, 1, 2, null, urChildren);
        children[1] = new Block(0, 0, 8, 1, 2, GameColors.BLUE, new Block[0]);
        children[2] = new Block(0, 8, 8, 1, 2, GameColors.RED, new Block[0]);
        children[3] = new Block(8, 8, 8, 1, 2, GameColors.YELLOW, new Block[0]);

        Block b = new Block(0, 0, 16, 0, 2, null, children);

        BlobGoal g = new BlobGoal(GameColors.BLUE);
        assertEquals(5, g.score(b));
    }

    @Test
    @Tag("score:1")
    @DisplayName("BlobGoal score() test2")
    void BGscore2() {
        Block.gen = new Random(500);
        Block b = new Block(0, 3);
        b.updateSizeAndPosition(16, 0, 0);

        BlobGoal g = new BlobGoal(GameColors.RED);
        assertEquals(18, g.score(b));
    }

}
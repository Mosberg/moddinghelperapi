package dk.mosberg.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import net.minecraft.util.math.Vec3d;

/**
 * Unit tests for {@link VectorHelper} utilities.
 *
 * @since 1.0.0
 */
class VectorHelperTest {

    @Test
    void testDistance() {
        var v1 = new Vec3d(0, 0, 0);
        var v2 = new Vec3d(3, 4, 0);
        assertEquals(5.0, VectorHelper.distance(v1, v2), 0.001);
    }

    @Test
    void testDistanceSquared() {
        var v1 = new Vec3d(0, 0, 0);
        var v2 = new Vec3d(3, 4, 0);
        assertEquals(25.0, VectorHelper.squaredDistance(v1, v2), 0.001);
    }

    @Test
    void testDirection() {
        var v1 = new Vec3d(0, 0, 0);
        var v2 = new Vec3d(1, 0, 0);
        var dir = VectorHelper.direction(v1, v2);

        // Direction should be normalized
        assertEquals(1.0, VectorHelper.length(dir), 0.001);
    }

    @Test
    void testNormalize() {
        var vec = new Vec3d(3, 4, 0);
        var normalized = VectorHelper.normalize(vec);

        assertEquals(1.0, VectorHelper.length(normalized), 0.001);
    }

    @Test
    void testScale() {
        var vec = new Vec3d(1, 2, 3);
        var scaled = VectorHelper.scale(vec, 2.0);

        assertEquals(new Vec3d(2, 4, 6), scaled);
    }

    @Test
    void testMidpoint() {
        var v1 = new Vec3d(0, 0, 0);
        var v2 = new Vec3d(4, 4, 4);
        var mid = VectorHelper.midpoint(v1, v2);

        assertEquals(new Vec3d(2, 2, 2), mid);
    }

    @Test
    void testLength() {
        var vec = new Vec3d(3, 4, 0);
        assertEquals(5.0, VectorHelper.length(vec), 0.001);
    }

    @Test
    void testAdd() {
        var v1 = new Vec3d(1, 2, 3);
        var v2 = new Vec3d(4, 5, 6);
        var result = VectorHelper.add(v1, v2);

        assertEquals(new Vec3d(5, 7, 9), result);
    }

    @Test
    void testSubtract() {
        var v1 = new Vec3d(5, 7, 9);
        var v2 = new Vec3d(1, 2, 3);
        var result = VectorHelper.subtract(v1, v2);

        assertEquals(new Vec3d(4, 5, 6), result);
    }

    @Test
    void testDotProduct() {
        var v1 = new Vec3d(1, 2, 3);
        var v2 = new Vec3d(4, 5, 6);
        double dot = VectorHelper.dotProduct(v1, v2);

        // 1*4 + 2*5 + 3*6 = 4 + 10 + 18 = 32
        assertEquals(32.0, dot, 0.001);
    }

    @Test
    void testDistanceZeroVectors() {
        var vec = new Vec3d(5, 5, 5);
        assertEquals(0.0, VectorHelper.distance(vec, vec), 0.001);
    }

    @Test
    void testNormalizeZeroVector() {
        var vec = new Vec3d(0, 0, 0);
        var normalized = VectorHelper.normalize(vec);

        assertEquals(new Vec3d(0, 0, 0), normalized);
    }

    @Test
    void testThrowsOnNullVectors() {
        var vec = new Vec3d(1, 2, 3);

        assertThrows(NullPointerException.class, () -> VectorHelper.distance(null, vec));
        assertThrows(NullPointerException.class, () -> VectorHelper.distance(vec, null));
        assertThrows(NullPointerException.class, () -> VectorHelper.normalize(null));
    }
}

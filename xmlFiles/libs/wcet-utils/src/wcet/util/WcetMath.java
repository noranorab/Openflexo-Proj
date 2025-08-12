package wcet.util;

/**
 * Small math helpers for WCET-related conversions.
 * Avoids using floating-point Math.log in FML by exposing static methods
 * you can call from FML as wcet.util.WcetMath.method(...).
 */
public final class WcetMath {

    private WcetMath() { }

    /** Returns floor(log2(x)) for x > 0. */
    public static int log2(int x) {
        if (x <= 0) {
            throw new IllegalArgumentException("x must be > 0");
        }
        return 31 - Integer.numberOfLeadingZeros(x);
    }

    /** Returns 2^bits. */
    public static int pow2(int bits) {
        if (bits < 0 || bits >= 31) {
            throw new IllegalArgumentException("bits must be in [0,30]");
        }
        return 1 << bits;
    }

    /** cachelinesize = 2^(block_bits) */
    public static int cacheLineSizeFromBlockBits(int blockBits) {
        return pow2(blockBits);
    }

    /** block_bits = log2(cachelinesize) ; cachelinesize must be a power of two. */
    public static int blockBitsFromCacheLineSize(int cacheLineSize) {
        ensurePowerOfTwo(cacheLineSize, "cacheLineSize");
        return log2(cacheLineSize);
    }

    /** way_bits = log2(nbways) ; nbways must be a power of two. */
    public static int wayBitsFromWays(int nbWays) {
        ensurePowerOfTwo(nbWays, "nbWays");
        return log2(nbWays);
    }

    /** row_bits = log2(nbsets) ; nbsets must be a power of two. */
    public static int rowBitsFromSets(int nbSets) {
        ensurePowerOfTwo(nbSets, "nbSets");
        return log2(nbSets);
    }

    private static void ensurePowerOfTwo(int v, String name) {
        if (v <= 0 || (v & (v - 1)) != 0) {
            throw new IllegalArgumentException(name + " must be a positive power of two");
        }
    }
	
	public static Byte cacheLineSizeFromBlockBits(Byte blockBits) {
        int v = cacheLineSizeFromBlockBits(blockBits.intValue());
        return toByteExact(v, "cacheLineSize");
    }

    public static Byte blockBitsFromCacheLineSize(Byte cacheLineSize) {
        int v = blockBitsFromCacheLineSize(cacheLineSize.intValue());
        return toByteExact(v, "blockBits");
    }

    public static Byte wayBitsFromWays(Byte nbWays) {
        int v = wayBitsFromWays(nbWays.intValue());
        return toByteExact(v, "wayBits");
    }

    public static Byte rowBitsFromSets(Byte nbSets) {
        int v = rowBitsFromSets(nbSets.intValue());
        return toByteExact(v, "rowBits");
    }

    /* ---------- alias éventuels si tu as utilisé ces noms ---------- */
    public static Integer log2int(Integer x) { return log2(x.intValue()); }
    public static Integer pow2int(Integer bits) { return pow2(bits.intValue()); }

    /* ---------- utilitaires ---------- */



    private static Byte toByteExact(int v, String name) {
        // xs:byte == Java byte signé (-128..127). Tes valeurs doivent être >=0 et <=127.
        if (v < -128 || v > 127) {
            throw new IllegalArgumentException(name + " out of byte range: " + v);
        }
        return (byte) v;
    }
}

package Entity;

import com.sun.tools.classfile.ConstantPool;

// Adapted from https://adrianb.io/2014/08/09/perlinnoise.html
public class PerlinNoise {
    private static int seed = 0;

    public static void setSeed(int newSeed) {
        seed = newSeed;
    }

    public static int getSeed() {
        return seed;
    }

    private static int intHash(int x, int y, int z) {
        int p1 = 73856093;
        int p2 = 19349663;
        int p3 = 83492791;

        // random hash might change to something else later
        int result = (x * p1) ^ (y * p2) ^ (z * p3) ^ seed;
        result  = result * result * result * 60493;
        return result ^ (result >> 13);
    }

    public static double octavePerlin(double x, double y, double z, int octaves, double persistence) {
        if (octaves <= 0) return 0;
        double total = 0;
        double frequency = 1;
        double amplitude = 1;
        double maxValue = 0;    // Used to normalize values
        for (int i = 0; i < octaves; i++) {
            total += perlin(x * frequency, y * frequency, z * frequency) * amplitude;
            maxValue += amplitude;
            amplitude *= persistence;
            frequency *= 2;
        }
        return total/maxValue;
    }

    public static double perlin(double x, double y, double z) {
        int xi = (int) Math.floor(x);
        int yi = (int) Math.floor(y);
        int zi = (int) Math.floor(z);

        double xf = x - xi;
        double yf = y - yi;
        double zf = z - zi;

        double u = fade(xf);
        double v = fade(yf);
        double w = fade(zf);

        // Hash values to add randomness
        int aaa = intHash(xi, yi, zi);
        int aba = intHash(xi, yi + 1, zi);
        int aab = intHash(xi, yi, zi+1);
        int abb = intHash(xi , yi+1, zi+1);
        int baa = intHash(xi+1, yi, zi);
        int bba = intHash(xi+1, yi+1, zi);
        int bab = intHash(xi+1, yi, zi+1);
        int bbb = intHash(xi+1, yi+1, zi+1);

        // The gradient function calculates the dot product between a pseudorandom
        // gradient vector and the vector from the input coordinate to the 8
        // surrounding points in its unit cube.
        // This is all then lerped together as a sort of weighted average based on the faded (u,v,w)
        // values we made earlier.
        double x1, x2, y1, y2;
        x1 = lerp(
            grad(aaa,xf,yf,zf),
            grad(baa,xf-1,yf,zf),
            u
        );
        x2 = lerp(
            grad(aba,xf,yf-1,zf),
            grad(bba,xf-1,yf-1,zf),
            u
        );
        y1 = lerp(x1, x2, v);

        x1 = lerp(
            grad(aab,xf,yf,zf-1),
            grad(bab,xf-1,yf,zf-1),
            u
        );
        x2 = lerp(
            grad(abb,xf,yf-1,zf-1),
            grad(bbb,xf-1,yf-1,zf-1),
            u
        );
        y2 = lerp(x1, x2, v);
        return (lerp(y1, y2, w) + 1)/2;
    }

    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10); // 6t^5 - 15t^4 + 10t^3
    }

    // Source: http://riven8192.blogspot.com/2010/08/calculate-perlinnoise-twice-as-fast.html
    public static double grad(int hash, double x, double y, double z) {
        switch(hash & 0xF) {
            case 0x0: return  x + y;
            case 0x1: return -x + y;
            case 0x2: return  x - y;
            case 0x3: return -x - y;
            case 0x4: return  x + z;
            case 0x5: return -x + z;
            case 0x6: return  x - z;
            case 0x7: return -x - z;
            case 0x8: return  y + z;
            case 0x9: return -y + z;
            case 0xA: return  y - z;
            case 0xB: return -y - z;
            case 0xC: return  y + x;
            case 0xD: return -y + z;
            case 0xE: return  y - x;
            case 0xF: return -y - z;
            default: return 0; // never happens
        }
    }

    // Linear interpolation
    private static double lerp(double a, double b, double x) {
        return a + x * (b - a);
    }
}

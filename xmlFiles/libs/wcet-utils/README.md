# wcet-utils (Java helper)

This small utility exposes static methods to be called from FML (Openflexo) to avoid
using the reserved token `log` in FML expressions.

## Provided class

- `wcet.util.WcetMath`
  - `int log2(int x)` — floor(log2(x)) for x>0
  - `int pow2(int bits)` — 2^bits
  - `int cacheLineSizeFromBlockBits(int blockBits)` — line size from bits
  - `int blockBitsFromCacheLineSize(int cacheLineSize)` — bits from line size (requires power of two)
  - `int wayBitsFromWays(int nbWays)` — bits from number of ways (power of two)
  - `int rowBitsFromSets(int nbSets)` — bits from number of sets (power of two)

## Compile to JAR

```bash
# Linux/macOS
javac -d out src/wcet/util/WcetMath.java
jar cf WcetUtils.jar -C out .
```

```bat
REM Windows (Command Prompt or PowerShell)
javac -d out src\wcet\util\WcetMath.java
jar cf WcetUtils.jar -C out .
```

The resulting `WcetUtils.jar` will be in the current directory.

## Use in Openflexo (FML)

Place `WcetUtils.jar` on the classpath of Openflexo (e.g., in the `lib/` folder) and restart.
Then you can call static methods in FML:

```fml
property block_bits : Integer
  get = wcet.util.WcetMath.blockBitsFromCacheLineSize(cachelinesize)
  set = cachelinesize = wcet.util.WcetMath.cacheLineSizeFromBlockBits(value);

property way_bits : Integer
  get = wcet.util.WcetMath.wayBitsFromWays(nbways)
  set = nbways = wcet.util.WcetMath.pow2(value);

property row_bits : Integer
  get = wcet.util.WcetMath.rowBitsFromSets(nbsets)
  set = nbsets = wcet.util.WcetMath.pow2(value);
```

This avoids any direct `log(...)` usage in FML.

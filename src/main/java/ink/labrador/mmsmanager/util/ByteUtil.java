package ink.labrador.mmsmanager.util;

import ink.labrador.mmsmanager.constant.ByteLen;
import ink.labrador.mmsmanager.constant.Endian;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteUtil {
    private static final Logger logger = LoggerFactory.getLogger(ByteUtil.class);
    /**
     * 将整型值转为一个字节
     * @param i 整型值
     * @return 字节
     */
    public static byte dumpToOneByteUnsigned(int i) {
        return (byte)(i & 0xff);
    }

    public static byte[] dumpUnsigned(long i, ByteLen byteLen, Endian endian) {
        byte[] bytes = new byte[byteLen.getValue()];
        bytes[0] = dumpToOneByteUnsigned((int) i);
        if (byteLen.getValue() >= ByteLen.BYTE.getValue()) {
            bytes[1] = (byte) (i >> 8 & 0xff);
        }
        if (byteLen.getValue() >= ByteLen.INT3.getValue()) {
            bytes[2] = (byte) (i >> 16 & 0xff);
        }
        if (byteLen.getValue() >= ByteLen.INT.getValue()) {
            bytes[3] = (byte) (i >> 24 & 0xff);
        }
        if (byteLen.getValue() >= ByteLen.INT6.getValue()) {
            bytes[4] = (byte) (i >> 32 & 0xff);
        }
        if (byteLen.getValue() >= ByteLen.LONG.getValue()) {
            bytes[5] = (byte) (i >> 40 & 0xff);
        }
        if (endian == Endian.BE) {
            ArrayUtil.reverse(bytes);
        }
        return bytes;
    }

    /**
     * 转为无符号字节数组
     * @param i 数值
     * @param byteLen 字节个数
     * @return 无符号字节数组
     */
    public static byte[] dumpUnsignedBE(long i, ByteLen byteLen) {
        return dumpUnsigned(i, byteLen, Endian.BE);
    }



    /**
     * 将int转为2个字节的byte数组，小端在前
     * @param i 要转的整形值
     * @return 2个字节的数组
     */
    public static byte[] dumpUnsignedLE(long i, ByteLen byteLen) {
        return dumpUnsigned(i, byteLen, Endian.LE);
    }

    public static int readBCDNumber(byte b) {
        return Integer.parseInt(Integer.toHexString(b));
    }

    public static long readBCDNumber(byte[] bytes, ByteLen len, Endian endian) {
        byte[] readBytes = bytes.length == len.getValue() ? bytes : copyWithEndian(bytes, len.getValue(), endian);
        String bcdStr = toBCDString(readBytes, endian);
        return Long.parseLong(bcdStr);
    }

    public static long readBCDNumberBE(byte[] bytes, ByteLen len) {
        return readBCDNumber(bytes, len, Endian.BE);
    }
    public static long readBCDNumberLE(byte[] bytes, ByteLen len) {
        return readBCDNumber(bytes, len, Endian.LE);
    }


    public static long readBCDNumber(byte[] bytes, Endian endian) {
        return readBCDNumber(bytes, ByteLen.of(bytes.length), endian);
    }

    public static long readBCDNumberBE(byte[] bytes) {
        return readBCDNumber(bytes, Endian.BE);
    }

    public static long readBCDNumberLE(byte[] bytes) {
        return readBCDNumber(bytes, Endian.LE);
    }

    public static long readLongUnsignedLE(final byte[] bytes, ByteLen len) {
        if (bytes.length < len.getValue()) {
            throw new RuntimeException("要读取的字节数大于字节数组长度");
        }
        if (len == ByteLen.BYTE) {
            return bytes[0] & 0xff;
        } else if (len == ByteLen.SHORT) {
            return ((bytes[1] & 0xff) << 8) | (bytes[0] & 0xff);
        } else if (len == ByteLen.INT3) {
            return ((bytes[2] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF);
        } else if (len == ByteLen.INT) {
            return ((long) (bytes[3] & 0xFF) << 24) | ((bytes[2] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF);
        } else if (len == ByteLen.INT6) {
            return (((long) (bytes[4] & 0xFF) << 32) | (long) (bytes[3] & 0xFF) << 24) | ((bytes[2] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF);
        } else {
            return (((long) (bytes[4] & 0xFF) << 40) | ((long) (bytes[4] & 0xFF) << 32) | (long) (bytes[3] & 0xFF) << 24) | ((bytes[2] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[0] & 0xFF);
        }
    }

    public static long readLongUnsignedBE(final byte[] bytes, ByteLen len) {
        if (bytes.length < len.getValue()) {
            throw new RuntimeException("要读取的字节数大于字节数组长度");
        }
        if (len == ByteLen.BYTE) {
            return bytes[0] & 0xff;
        } else if (len == ByteLen.SHORT) {
            return ((bytes[0] & 0xff) << 8) | (bytes[1] & 0xff);
        } else if (len == ByteLen.INT3) {
            return ((bytes[0] & 0xFF) << 16) | ((bytes[1] & 0xFF) << 8) | (bytes[2] & 0xFF);
        } else if (len == ByteLen.INT) {
            return ((long) (bytes[0] & 0xFF) << 24) | ((bytes[1] & 0xFF) << 16) | ((bytes[2] & 0xFF) << 8) | (bytes[3] & 0xFF);
        } else if (len == ByteLen.INT6) {
            return (((long) (bytes[0] & 0xFF) << 32) | (long) (bytes[1] & 0xFF) << 24) | ((bytes[2] & 0xFF) << 16) | ((bytes[3] & 0xFF) << 8) | (bytes[4] & 0xFF);
        } else {
            return (((long) (bytes[0] & 0xFF) << 40) | ((long) (bytes[1] & 0xFF) << 32) | (long) (bytes[2] & 0xFF) << 24) | ((bytes[3] & 0xFF) << 16) | ((bytes[4] & 0xFF) << 8) | (bytes[5] & 0xFF);
        }
    }

    public static long readLongUnsigned(byte[] bytes, ByteLen len, Endian endian) {
        return endian == Endian.BE ? readLongUnsignedBE(bytes, len) : readLongUnsignedLE(bytes, len);
    }

    public static long readLongUnsigned(byte[] bytes,  Endian endian) {
        return readLongUnsigned(bytes, ByteLen.of(bytes.length), endian);
    }

    public static long readLongUnsignedLE(byte[] bytes) {
        return readLongUnsignedLE(bytes, ByteLen.of(bytes.length));
    }

    public static long readLongUnsignedBE(byte[] bytes) {
        return readLongUnsignedBE(bytes, ByteLen.of(bytes.length));
    }

    public static int readIntUnsigned(byte b) {
        return b & 0xff;
    }

    public static int readIntUnsigned(final byte[] bytes, ByteLen len, Endian endian) {
        return (int) readLongUnsigned(bytes, len, endian);
    }

    // 获取符号位,最高为1为复数
    private static int getSymbol(final byte[] bytes) {
        if ((bytes[0] & 0x80) == 0x80) {
            return -1;
        }
        return 1;
    }

    public static long readLong(final byte[] bytes, ByteLen len, Endian endian) {
        int symbol = getSymbol(bytes);
        byte b = bytes[0];
        bytes[0] = (byte) (b & 0x7f); // 最高为置0
        long unsigned = readLongUnsigned(bytes, len, endian);
        bytes[0] = b;
        return symbol * unsigned;
    }

    public static long readLongLE(final byte[] bytes, ByteLen len) {
        return readLong(bytes, len, Endian.LE);
    }

    public static long readLongBE(final byte[] bytes, ByteLen len) {
        return readLong(bytes, len, Endian.BE);
    }

    public static int readInt(final byte[] bytes, ByteLen len, Endian endian) {
        return (int) readLong(bytes, len, endian);
    }

    public static int readIntLE(final byte[] bytes, ByteLen len) {
        return readInt(bytes, len, Endian.LE);
    }

    public static int readIntBE(final byte[] bytes, ByteLen len) {
        return readInt(bytes, len, Endian.BE);
    }


    public static int readIntUnsignedLE(final byte[] bytes, ByteLen len) {
        return readIntUnsigned(bytes, len, Endian.LE);
    }

    public static int readIntUnsignedBE(final byte[] bytes, ByteLen len) {
        return readIntUnsigned(bytes, len, Endian.BE);
    }

    public static int readIntUnsigned(final byte[] bytes, Endian endian) {
        return readIntUnsigned(bytes, ByteLen.of(bytes.length), endian);
    }

    public static int readIntUnsignedLE(final byte[] bytes) {
        return readIntUnsigned(bytes, ByteLen.of(bytes.length), Endian.LE);
    }

    public static int readIntUnsignedBE(final byte[] bytes) {
        return readIntUnsigned(bytes, ByteLen.of(bytes.length), Endian.BE);
    }

    public static float readFloat32(final byte[] bytes, Endian endian) {
        if (bytes.length < 4) {
            logger.error("Read float 32 error, need 4 bytes, give " + bytes.length + " bytes, will return 0 ...");
            return 0;
        }
        byte[] floatBytes = bytes.length == 4 ? bytes : copyWithEndian(bytes, 4, endian);
        return Float.intBitsToFloat(readInt(floatBytes, ByteLen.INT, endian));
    }

    public static float readFloat32BE(final byte[] bytes) {
        return readFloat32(bytes, Endian.BE);
    }

    public static float readFloat32LE(final byte[] bytes) {
        return readFloat32(bytes, Endian.LE);
    }

    public static double readDouble64(final byte[] bytes, Endian endian) {
        if (bytes.length < 8) {
            logger.error("Read float 32 error, need 4 bytes, give " + bytes.length + " bytes, will return 0 ...");
            return 0;
        }
        byte[] doubleBytes = bytes.length == 8 ? bytes : copyWithEndian(bytes, 8, endian);
        return Double.longBitsToDouble(readLong(bytes, ByteLen.LONG, endian));
    }

    public static double readDouble64BE(final byte[] bytes) {
        return readDouble64(bytes, Endian.BE);
    }

    public static double readDouble64LE(final byte[] bytes) {
        return readDouble64(bytes, Endian.LE);
    }

    private static byte[] copyWithEndian(final byte[] originBytes, final int length, Endian endian) {
        if (endian == Endian.BE) {
            return Arrays.copyOf(originBytes, length);
        }
        byte[] bytes = new byte[length];
        for (int i = 1; i <= length; i ++) {
            bytes[i - 1] = originBytes[originBytes.length - i];
        }
        return bytes;
    }

    public static String toBCDString(byte[] bytes, Endian endian) {
        byte[] readBytes = getReadBytesAndMakeBE(bytes, endian, bytes.length);
        StringBuilder sb = new StringBuilder();
        for (byte b: readBytes) {
            sb.append(Integer.toHexString(b));
        }
        return sb.toString();
    }

    public static String toBCDStringBE(byte[] bytes) {
        return toBCDString(bytes, Endian.BE);
    }

    public static String toBCDStringLE(byte[] bytes) {
        return toBCDString(bytes, Endian.LE);
    }

    public static String toASCIIString(byte[] bytes, Endian endian) {
        byte[] readBytes = endian == Endian.BE ? bytes : getReadBytesAndMakeBE(bytes, endian, bytes.length);
        return new String(readBytes, StandardCharsets.UTF_8);
    }

    public static String toASCIIStringBE(byte[] bytes) {
        return toASCIIString(bytes, Endian.BE);
    }

    public static String toASCIIStringLE(byte[] bytes) {
        return toASCIIString(bytes, Endian.LE);
    }

    public static String toASCII_BCDString(byte[] bytes, Endian endian) {
        byte[] readBytes = endian == Endian.BE ? bytes : getReadBytesAndMakeBE(bytes, endian, bytes.length);
        String asciiString = toASCIIStringBE(readBytes);
        byte[] bcdBytes = new byte[asciiString.length()];
        for (int i = 0; i < asciiString.length(); i ++) {
            bcdBytes[i] = dumpToOneByteUnsigned(asciiString.charAt(i));
        }
        return toHexString(bcdBytes);
    }

    private static byte[] getReadBytesAndMakeBE(byte[] bytes, Endian endian, int len) {
        if (endian == Endian.BE) {
            return len == bytes.length ? bytes : Arrays.copyOf(bytes, len);
        }
        byte[] readBytes = new byte[len];
        for (int i = 0; i < len; i ++) {
            readBytes[i] = bytes[bytes.length - i - 1];
        }
        return readBytes;
    }

    public static String toASCII_BCDStringBE(byte[] bytes) {
        return toASCII_BCDString(bytes, Endian.BE);
    }

    public static String toASCII_BCDStringLE(byte[] bytes) {
        return toASCII_BCDString(bytes, Endian.LE);
    }


    public static String toHexString(byte[] bytes) {
        return toHexString(bytes, true);
    }

    public static String toHexString(byte[] bytes, boolean toLowerCase) {
        return Hex.encodeHexString(bytes, toLowerCase);
    }

    public static byte[] parseHexString(String hexStr) {
        try {
            return Hex.decodeHex(hexStr);
        } catch (DecoderException e) {
            return null;
        }
    }

    public static Byte parseHex(String hex) {
        if (hex.length() > 2) {
            throw new RuntimeException("要转换为单个字节,字符串长度不能超过2");
        }
        byte[] bytes = parseHexString(hex);
        return bytes == null ? null : bytes[0];
    }

    public static String toHexString(byte b) {
        return toHexString(b, true);
    }


    public static String toHexString(byte b, boolean toLowerCase) {
        return Hex.encodeHexString(new byte[]{b}, toLowerCase);
    }
}
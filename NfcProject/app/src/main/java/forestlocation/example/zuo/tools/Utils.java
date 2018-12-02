package forestlocation.example.zuo.tools;

/**
 * Created by zuo on 2018/12/2.
 */
public class Utils {
    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars ="0123456789ABCDEF".toCharArray();
        StringBuilder sb =new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for(int i =0; i < bs.length; i++) {
            bit = (bs[i] &0x0f0) >>4;
            sb.append(chars[bit]);
            bit = bs[i] &0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
            }
        return sb.toString().trim();
    }

    /**
     * 16进制转Byte
     * @param hex
     * @return
     */
    public static byte[] hexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static int toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }
    /**
     * 16进制转换成为string类型字符串
     * @param s
     * @return
     */
    public static String hexStringToString(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        s = s.replace(" ", "");
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            s = new String(baKeyword, "UTF-8");
            new String();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return s;
    }
    
}

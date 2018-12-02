package forestlocation.example.zuo.bean;

/**
 * Created by zuo on 2018/12/2.
 */
public class InitS {
    byte type;         //类型
    byte rc1;         //类型
    short ANo;          //村号
    short UserNo;       //户号
    short MianFeiShui;  //免费水量
    byte[] Rcv;     //备用


    public static byte[] toByte(InitS initS) {
        byte[] data = new byte[16];


        return data;
    }
}

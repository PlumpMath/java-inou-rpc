import java.io.IOException;

import inou.net.RemoteRuntimeException;
import inou.net.rpc.BinClient;
import inou.net.rpc.IMessageHandler;

public class SampleClient {

    public static void main(String[] args) { 
        BinClient client = new BinClient("localhost",10024);

        //足し算をするメソッドを追加。サーバー側から呼ばれる
        client.addHandler("add",handler);
        
        try {
            client.start();//接続開始
        } catch (RuntimeException e) {
            //相手がいなくて timeout したときなど。
            e.printStackTrace();
            return;
        }
        Object obj = null;
        try {
            //server側の echo メソッドを引数 hello で実行
            obj = client.send("echo",new Object[]{"hello"});
        } catch (RemoteRuntimeException e) {
            //サーバー側のプログラムでエラーが起きた場合
            e.printStackTrace();
        } catch (IOException e) {
            //通信自体に何か問題があった場合
            e.printStackTrace();
        }
        System.out.println(obj);
        
        //サーバー側がこちら側の add メソッドを呼ぶのを待つ 
        while(true) {
            synchronized (calledFlag) {
                if (calledFlag[0]) break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        client.shutdown();
    }
    
    private static boolean[] calledFlag = {false};
    
    private static IMessageHandler handler = new IMessageHandler(){
        public Object send(Object[] args) throws Exception {
            //引数を2個とって、足し算をする。
            //インタフェース（名前や引数の型など）は双方のプログラマで決めておく。
            try {
                Integer i1 = (Integer)args[0];
                Integer i2 = (Integer)args[1];
                return new Integer(i1.intValue()+i2.intValue());
            } finally {
                synchronized (calledFlag) {
                    calledFlag[0] = true;
                }
            }
        }};

}

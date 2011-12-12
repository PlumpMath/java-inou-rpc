import java.io.IOException;

import inou.net.RemoteRuntimeException;
import inou.net.rpc.BinClient;
import inou.net.rpc.IMessageHandler;

public class SampleClient {

    public static void main(String[] args) { 
        BinClient client = new BinClient("localhost",10024);

        //�����Z�����郁�\�b�h��ǉ��B�T�[�o�[������Ă΂��
        client.addHandler("add",handler);
        
        try {
            client.start();//�ڑ��J�n
        } catch (RuntimeException e) {
            //���肪���Ȃ��� timeout �����Ƃ��ȂǁB
            e.printStackTrace();
            return;
        }
        Object obj = null;
        try {
            //server���� echo ���\�b�h������ hello �Ŏ��s
            obj = client.send("echo",new Object[]{"hello"});
        } catch (RemoteRuntimeException e) {
            //�T�[�o�[���̃v���O�����ŃG���[���N�����ꍇ
            e.printStackTrace();
        } catch (IOException e) {
            //�ʐM���̂ɉ�����肪�������ꍇ
            e.printStackTrace();
        }
        System.out.println(obj);
        
        //�T�[�o�[���������瑤�� add ���\�b�h���ĂԂ̂�҂� 
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
            //������2�Ƃ��āA�����Z������B
            //�C���^�t�F�[�X�i���O������̌^�Ȃǁj�͑o���̃v���O���}�Ō��߂Ă����B
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

import java.io.IOException;

import inou.net.rpc.ICommunicator;
import inou.net.rpc.IMessageHandler;
import inou.net.rpc.MultiBinServer;

public class SampleServer {

    public static void main(String[] args) throws IOException {
        MultiBinServer serverGenerator = new MultiBinServer(10024);//port=10024
        
        //����Ă�������������H���ĕԂ����\�b�h�@echo ��ǉ�
        serverGenerator.addHandler("echo",new IMessageHandler() {
            public Object send(Object[] args) throws Exception {
                return "ECHO: "+args[0];
            }
        });
        
        //�N���C�A���g���ڑ����Ă���̂�҂�
        ICommunicator client = serverGenerator.getClientConnection();
        
        //�N���C�A���g���̃��\�b�h add ������ 1,2 �Ŏ��s
        Object obj = client.send("add",new Object[]{new Integer(1),new Integer(2)});
        
        System.out.println(obj);
        
        //���̌�N���C�A���g������ؒf�����B
        //�T�[�o�[�̃|�[�g�͂܂���L����Ă���̂ŁACtrl-C�Ŏ~�߂�B
    }

}

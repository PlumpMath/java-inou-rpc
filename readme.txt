INOU-RPC

  Integrated Numerical Operation Utilities,
  RPC Extension.

  Version 0.4.3
  Time-stamp: "2012-08-31 14:15:33 sakurai"

�������H

Java�̊�{�^�Ƃ��̔z��AList��Hash���T�|�[�g�����Ȉ�RPC�B
�o�C�i���[�G���R�[�f�B���O�ŁARPC���Ƃɐڑ��������Ȃ�����XMLRPC����
�����ŁA�o��������̌Ăяo�����\�B

���g�p��

�Q�ƁF sample/SampleServer.java, SampleClient.java

���T�[�o�[

  MultiBinServer serverGenerator = new MultiBinServer(10024);//port=10024
          
  //����Ă�������������H���ĕԂ����\�b�h�@echo ��ǉ�
  serverGenerator.addHandler("echo",new IMessageHandler() {
     public Object send(Object[] args) throws Exception {
         return "ECHO: "+args[0];
     }
  });
          
  //�N���C�A���g���ڑ����Ă���̂�҂�
  ICommunicator client = serverGenerator.getClientConnection();
  
���N���C�A���g

  BinClient client = new BinClient("localhost",10024);
  
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
  
  client.shutdown();

���C���X�g�[���Ɛݒ�

inourpc.jar ���N���X�p�X�ɉ�����B
�܂��A����ɂ�log4j���K�v�B

log4j�̐ݒ�� conf/log4j.properties ���Q�ƁB
�ʏ��log4j.logger.inou.net�ɂ���WARN�ȏ��ݒ肷��B

���g�p���@�ڍ�

���T�[�o�[���FMultiBinServer

�E�N��

�N���ɂ̓|�[�g�ԍ����w�肷����@�ƁA�w�肵�Ȃ����@������B
�w�肵�Ȃ���΋󂢂Ă���|�[�g��T���Ă��Ďg���B
���Ԃ��g�p���Ă��邩�� getPortNumber() �œ�����B

�E�A�N�Z�X����

IConnectionAccepter �����������I�u�W�F�N�g�� setConnectionAccepter ��
�ݒ肷��B������Ƃ��ă��[�J���z�X�g�݂̂���̐ڑ���������
 LocalhostAccepter ������B

�ERPC�n���h���̐ݒ�

IMessageHandler �����������I�u�W�F�N�g�� addHandler �Őݒ肷��B
�n���h���I�u�W�F�N�g�͕����̐ڑ��ŋ��L�����̂ŁA�X�e�[�g���X��
�X���b�h�Z�[�t�ɍ���Ă����K�v������B

�����A�e�ڑ����ƂɃn���h���I�u�W�F�N�g�𕪂������ꍇ�́A 
getClientConnection �̈����ɓ���� ServiceManager �𖈉�new���� 
addHandler ����BServiceManager �ɂ͐e�q�֌W�������āA MultiBinServer 
��addHandler �������̂͊e�ڑ��ŋ��L����āA�e�ڑ��Ńn���h�����`����
�ꍇ�́A���̐ڑ��̂ݗL���ɂȂ�B�������O�̃n���h���́A�e�ڑ��Œ�`����
���̂��D�悳���B

 getClientConnection ������� addHandler ���Ă��悢���A���̏ꍇ�̓N��
�C�A���g���ڑ����Ă���O�� addHandler ���I�����Ă���K�v������B

���N���C�A���g���FBinClient

�E�T�[�o�[�֐ڑ�

�R���X�g���N�^�[�Ńz�X�g���ƃ|�[�g�ԍ����w�肵�āA start ����Ɛڑ��J
�n�B setReconnect(true) �ōĐڑ�������悤�ɂȂ�i�����ݒ��false�j���A
�X�e�[�g�t���Ȑڑ��ł���ꍇ�A�ڑ������̒��ۉ������Ă����Ȃ���΍Đڑ����Ă�
���܂�Ӗ������������B

setConnectionTimeoutMiliSec �Őڑ��܂ł̃^�C���A�E�g���Ԃ�ݒ�ł���B
�f�t�H���g�� 2000msec �ɂȂ��Ă���̂ŁA�T�[�o�[�̉������x���ꍇ��
30�b�Ƃ��ɐݒ肵�Ă����K�v������B

���T�|�[�g�����f�[�^�^

null
Boolean, String, boolean[], String[]
Byte, Short, Integer, Long, BigDecimal, Float, Double
byte[], short[], int[], long[], BigDecimal[], float[], double[]
List, HashMap
��L�̂ǂꂩ�������� Object[]

����e�ʃf�[�^�̓]��

����ȃt�@�C���𑗂肽���ꍇ�A���ʂ� byte[] ����ƃq�[�v�������[������
�ς��ɂȂ�̂� OutOfMemoryError �ɂȂ�B���̏ꍇ�̓X�g���[���`�����g��
�āA�������[�ɑS�����悹�Ȃ��悤�ɑ���M���邱�Ƃ��o����B���ӂƂ��āA
���炩���߃T�C�Y�̂킩��Ȃ����̂ɂ͎g���Ȃ��B�����܂�RPC�Ȃ̂� byte[] 
�̕ʌ`���Ƃ��Ďg���B

�ʉ��Ƃ��ăv���g�R���I�ɋ���f�[�^��؂�o���Ď�M���Ō�������Ƃ�����
�v�ɂ���Ƃ������@������B

�E���M��

IStreamHolder ��K���Ɏ������Ĉ������A��l�ɓ����B
onFinished �͑��M�I������ close �������Ƃ��Ƃ��Ɏg���B
onException �͑��M����IO�G���[�����������Ƃ��ɁA�t�@�C���Ȃǂ��ꎟ�ޔ�
���������Ƃ��Ƃ��Ɏg���B
�K���Ȏ����Ƃ���StreamHolderClass������BonFinished �� close ���āA
onException �͉������Ȃ������ɂȂ��Ă���B�K�v�ɉ����ăI�[�o�[���C�h��
�Ďg���ƕ֗��B

�E��M��

IStreamGenerator ���������āABinClient �� 
MultiBinServer.getClientConnection �œ����I�u�W�F�N�g��
setStreamGenerator �Őݒ肷��B IStreamGenerator �́A�P��
InputStream�œ��� byte[] ��ʂ̃I�u�W�F�N�g�ɕϊ�������́B

�K���Ȏ����Ƃ��āA FileStreamGenerator ������B����́A�P�� 
InputStream �����[�J���̈ꎞ�t�@�C���ɏ����o���B�o�����t�@�C����������
�͊e�t�@�C�����󂯎�����n���h���̐ӔC�Ȃ̂ŁA�g���I�����������Ȃ��
������K�v������B

��Ruby����

MultiBinServer, BinClient �ɑΉ�����Ruby����������B
Java�łƂقړ����ȃf�[�^�^���T�|�[�g���Ă���A�قړ��ߓI��
Java�̃T�[�o�[��RPC�ʐM���邱�Ƃ��o����B

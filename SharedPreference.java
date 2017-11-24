//SharedPreference save Object  һ��Ҫ�㣬����ʹ��������Ķ��󣬱��� implement Serializable������ᱨ��

/**
 * SharedPreferences�����࣬���Ա���object����
 * <p>
 * �洢ʱ��object�洢�����أ���ȡʱ���ص�Ҳ��object������Ҫ�Լ�����ǿ��ת��
 * <p>
 * Ҳ����˵������˺�ȡ����Ҫ��ͬһ���˲�֪��ȡ�����Ķ��������Ǹ�ɶ ^_^
 */
public class SharedPreferenceUtil {

    /**
     * writeObject ��������д���ض���Ķ����״̬���Ա���Ӧ�� readObject �������Ի�ԭ��
     * �����Base64.encode���ֽ��ļ�ת����Base64���뱣����String��
     *
     * @param object �����ܵ�ת��ΪString�Ķ���
     * @return String   ���ܺ��String
     */
    private static String Object2String(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * ʹ��Base64����String������Object����
     *
     * @param objectString �����ܵ�String
     * @return object      ���ܺ��object
     */
    private static Object String2Object(String objectString) {
        byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * ʹ��SharedPreference�������
     *
     * @param fileKey    �����ļ���key
     * @param key        ��������key
     * @param saveObject ����Ķ���
     */
    public static void save(String fileKey, String key, Object saveObject) {
        SharedPreferences sharedPreferences = ȫ����.getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String string = Object2String(saveObject);
        editor.putString(key, string);
        editor.commit();
    }

    /**
     * ��ȡSharedPreference����Ķ���
     *
     * @param fileKey �����ļ���key
     * @param key     ��������key
     * @return object ���ظ���key�õ��Ķ���
     */
    public static Object get(String fileKey, String key) {
        SharedPreferences sharedPreferences =  ȫ����.getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        if (string != null) {
            Object object = String2Object(string);
            return object;
        } else {
            return null;
        }
    }
}




//�������Object����ʵ�� Serializable ��
public class ClassTest implements Serializable {

    public String mAppId;

    public int mPlatForm;

    public List<ClassInner> mList;

    public static class ClassInner implements Serializable{
        public int id;
        public List<ClassInnerOther> mInnerList;

    }

    public static class ClassInnerOther implements Serializable{
        public String name;
        public int value;
    }
}

//ʹ��SP�����ౣ������
ClassTest testClass = new ClassTest();
SharedPreferenceUtil.save("file_key","value_key",testClass );


//ʹ��SP�������ȡ����
Object object = SharedPreferenceUtil.get("file_key","value_key");
if(object != null) {
      ClassTest testClass = (ClassTest) object;
}






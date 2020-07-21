package main.com.itlucky.classloader;

import java.io.*;

/**
 * @author itlucky
 * @version 0.0.1
 * @description jdk不建议重写loadClass()方法，避免打破双亲委派模型。
 *              所以可以重写当父类都找不到Class的情况下的findClass()方法。
 * @since 2020/6/7 11:19 上午
 */
public class MyClassLoader extends ClassLoader{

    private String root;

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException{
        byte[] classData = loadClassData(name);
        if(classData == null){
            throw new ClassNotFoundException();
        } else {
            return defineClass(name,classData,0,classData.length);
        }
    }

    /**
     * 获取类的二进制数据
     * @param className 类的全路径名
     * @return
     *
     *   File.separatorChar : 文件路径的斜杠符号 /
     */
    private byte[] loadClassData(String className){
        String fileName = root + File.separator
                + className.replace('.',File.separatorChar) + ".class";

        try {
            InputStream ins = new FileInputStream(fileName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length = 0;
            while ((length =ins.read(buffer)) != -1){
                baos.write(buffer,0,length);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //通过断点执行可以查看执行过程
    public static void main(String[] args) {

        MyClassLoader classLoader = new MyClassLoader();
        classLoader.setRoot("/Users/itlucky/proj/study/tmp");

        Class<?> testClass = null;
        try {
            //这里执行的结果是MyClassLoader，这个是项目之外的一个class，通过双亲委派模型没有找到父类对应的classLoader,所以就用了这里自定义的MyClassLoader.
//            testClass = classLoader.loadClass("com.itlucky.ConsumerApplication");
            //这里执行的结果是AppClassLoader, 是当前项目下就有的，双亲委派模型。也证实了这里的MyClassLoader的父类就是AppClassLoader。
            testClass = classLoader.loadClass("main.com.itlucky.classloader.FatherSonClassTest");
            Object obj = testClass.newInstance();
            System.out.println(obj.getClass().getClassLoader());
        } catch (ClassNotFoundException r){
            r.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }


}

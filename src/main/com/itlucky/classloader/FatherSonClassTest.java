package main.com.itlucky.classloader;

/**
 * 类到了初始化阶段，用户定义的Java程序代码才真正开始执行。
 * Java程序对类的使用方式可分为两种:主动使用与被动使用。一般来说只有当对类的首次主动
 * 使用的时候才会导致类的初始化，所以主动使用又叫做类加载过程中"初始化”开始的时机。
 * 那啥是主动使用呢?类的主动使用包括以下六种:
 * 1、创建类的实例，也就是new的方式
 * 2、访问某 个类或接口的静态变量，或者对该静态变量赋值(被final修饰除外 ，会放入常量池)
 * 3、调用类的静态方法
 * 4、反射(如Class.forName( "java.lang String" ))
 * 5、初始化某个类的子类，则其父类也会被初始化
 * 6、Java虚拟机启动时被标明为启动类的类， 还有就是Main方法的类会首先被初始化
 * 最后注意一-点对于静态字段， 只有直接定义这个字段的类才会被初始化(执行静态代码块)。
 */
public class FatherSonClassTest {

    public String test = "fatherSonTest";

    static class Father{
        Father(){
            System.out.println("father 构造函数");
        }
        static String fatherName = "小头爸爸";
        final static String test = "final static test";
        static {
            System.out.println("father 静态代码块");
        }
    }

    static class Son extends Father{
        Son(){
            super();
            System.out.println("son 构造函数");
        }
        static String sonName = "大头儿子";
        static {
            System.out.println("son 静态代码块");
        }
    }

    /**
     * final修饰的可以理解为是常量。
     * 执行结果分析：（结合类的初始化过程和规则即可理解）
     */
    public static void main(String[] args) {
//        System.out.println(Son.fatherName);  //1
        System.out.println(Son.sonName); //2
        System.out.println(Son.test);  //3
        new Son();  //4
    }
}

package cn.com.state;

public class TestDeadLock {
    public static void main(String[] args) {
        //测试死锁 过多的同步可能造成相互不释放资源，从而相互等待
        //一般发生于同步中持有多个对象的锁

        //避免： 不要再同一个代码块中，同时持有多个对象的锁
        Makeup m1 = new Makeup(1,"小红");
        Makeup m2 = new Makeup(0,"小亮");
        m1.start();
        m2.start();
    }
}
class Lipstick {

}
class Mirror {

}
class Makeup extends Thread {
    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    //选择
    int choice;
    String name;
    public Makeup(int choice,String name) {
        this.choice = choice;
        this.name = name;
    }
    @Override
    public void run() {
        makeup();
    }
    //相互持有对方的锁  造成死锁
    private void makeup() {
        if (choice ==0) {
            synchronized(lipstick) { //获得口红的锁
                System.out.println(this.name+"涂口红");
                //1秒后想拥有镜子的锁
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                synchronized(mirror) {
//                    System.out.println(this.name+"照镜子");
//                }
            }
            synchronized(mirror) {
                System.out.println(this.name+"照镜子");
            }
        }else {
            synchronized(mirror) { //获得镜子的锁
                System.out.println(this.name+"照镜子");
                //两秒后想拥有口红的锁
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                synchronized(lipstick) {
//                    System.out.println(this.name+"涂口红");
//                }
            }
            synchronized(lipstick) {
                System.out.println(this.name+"涂口红");
            }
        }
    }
}
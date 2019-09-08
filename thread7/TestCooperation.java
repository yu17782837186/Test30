package cn.com.state;

public class TestCooperation {
    //协作模型：生产者消费者实现方式一：管城法
    //借助缓冲区 缓冲区使用容器
    public static void main(String[] args) {
//        Container container = new Container();
//        new Producter(container).start();
//        new Consumer(container).start();
        Container container = new Container();
        new Producter(container).start();
        new Consumer(container).start();
    }
}
class Producter extends Thread{
    Container container;

    public Producter(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("生产"+i+"个商品");
            container.push(new Stor(i));
        }
    }
}
class Consumer extends Thread{
    Container container;

    public Consumer(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("消费"+container.pop().id+"个商品");
        }
    }
}
class Container {
    Stor[] stors = new Stor[5];
    int count;
    public synchronized void push(Stor stor) {
        if (count == stors.length) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        stors[count++] = stor;
        this.notifyAll();
    }
    public synchronized Stor pop() {
        if (count == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Stor stor = stors[--count];
        this.notifyAll();
        return stor;
    }
}
class Stor {
    int id;

    public Stor(int id) {
        this.id = id;
    }
}
////生产者
//class Producter extends Thread {
//    Container container;
//
//    public Producter(Container container) {
//        this.container = container;
//    }
//
//    @Override
//    public void run() {
//        //开始生产
//        for (int i = 0; i < 100; i++) {
//            System.out.println("生产-->"+i+"个馒头");
//            container.push(new Steamedbun(i));
//        }
//    }
//}
////消费者
//class Consumer extends Thread {
//    Container container;
//
//    public Consumer(Container container) {
//        this.container = container;
//    }
//
//    @Override
//    public void run() {
//        //开始消费
//        for (int i = 0; i < 100; i++) {
//            System.out.println("消费-->"+container.pop().id+"个馒头");
//        }
//    }
//}
////缓冲区
//class Container {
//    Steamedbun[] buns = new Steamedbun[10];
//    int count = 0;
//
//    //存储 生产
//    public synchronized void push(Steamedbun bun) {
//        //何时能生产 容器存在空间
//        //不能生产
//        if (count == buns.length) {
//            try {
//                this.wait(); //线程阻塞 消费者通知生产
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        //存在空间，可以生产
//        buns[count++] = bun;
//        this.notifyAll(); //存在数据，可以通知消费了
//    }
//
//    //获取 消费
//    public synchronized Steamedbun pop() {
//        //何时能消费 容器中是否存在数据
//        //没有数据 只有等待
//        if (count == 0) {
//            try {
//                this.wait();//线程阻塞  生产者通知消费 解除阻塞
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        //存在数据可以消费
//        count--;
//        Steamedbun bun = buns[count];
//        this.notifyAll(); //存在空间，可以唤醒对方生产了
//        return bun;
//    }
//}
////馒头
//class Steamedbun {
//    int id;
//
//    public Steamedbun(int id) {
//        this.id = id;
//    }
//}
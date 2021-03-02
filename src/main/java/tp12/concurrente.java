package tp12;

import java.util.concurrent.TimeUnit;

public class concurrente {

    public static void main(String[] args) {
        Baignoire baignoire = new Baignoire(500, 10);
        Robinet robinet = new Robinet(baignoire, 50);

        Thread threadBaignoire = new Thread(baignoire);
        Thread threadRobinet = new Thread(robinet);

        threadRobinet.start();
        threadBaignoire.start();


//        try {
//            TimeUnit.SECONDS.sleep(60);
//        } catch (InterruptedException e) {
//            System.out.println(e.toString());
//        }
//
//        robinet.setOn(false);
//        baignoire.setOn(false);
    }

}

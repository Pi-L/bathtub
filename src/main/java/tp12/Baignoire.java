package tp12;

import java.util.concurrent.TimeUnit;

public class Baignoire implements Runnable{

    private final int volumeMax;
    private int volumeFuite;
    private int volume;
    private boolean isOn=false;

    public Baignoire(int volumeMax, int volumeFuite) {
        super();

        this.volumeMax = volumeMax;
        this.volumeFuite = volumeFuite;
    }

    @Override
    public void run() {
        isOn = true;

        while (isOn) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }

            fuite();
        }
    }

    public void addVolume(int volume) {

        if(volume + this.volume > volumeMax) {
            this.volume = volumeMax;
            System.out.println("Attention, la baignore va deborder!");
        }
        else this.volume += volume;

        afficher();
    }

    public void removeVolume(int volume) {
        if(this.volume - volume <= 0) {
            this.volume = 0;

            if(volumeFuite > 0) volumeFuite--;
        }
        else this.volume -= volume;

        afficher();
    }

    public void fuite() {
        synchronized(this) {

            if(hasNoLeak()) {
                notify();

                isOn = false;
                Thread.currentThread().interrupt();
            }

            removeVolume(volumeFuite);
        }
    }

    public void afficher() {
        System.out.println(Thread.currentThread().getName()+ " - Volume actuel : "+volume+" - Volume Max : "+volumeMax+ " - Volume fuite : "+volumeFuite);
    }

    public Baignoire setOn(boolean on) {
        isOn = on;
        return this;
    }

    public boolean hasNoLeak() {
        return volumeFuite == 0;
    }

    public int getVolumeMax() {
        return volumeMax;
    }

    public int getVolume() {
        return volume;
    }
}

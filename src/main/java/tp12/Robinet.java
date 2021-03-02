package tp12;

public class Robinet implements Runnable {
    private Baignoire baignoire;
    private final int volumeDebite;
    private boolean isOn=false;

    public Robinet(Baignoire baignoire, int volumeDebite) {
        this.baignoire = baignoire;
        this.volumeDebite = volumeDebite;
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
            debite();
        }
    }

    public void debite() {
        synchronized (baignoire) {

            if (baignoire.getVolume() + volumeDebite < baignoire.getVolumeMax()) {
                baignoire.addVolume(volumeDebite);
                return;
            }

            if (baignoire.getVolume() <= baignoire.getVolumeMax()) {

                baignoire.addVolume(baignoire.getVolumeMax() - baignoire.getVolume());

                if (baignoire.hasNoLeak()) {
                    isOn = false;
                    return;
                }

                try {
                    baignoire.wait();

                } catch (InterruptedException e) {
                    isOn = false;
                    Thread.currentThread().interrupt();
                    System.out.println(e.toString());
                }
            }
        }
    }
}

//kerolos - 4ti - 17
class E_scooter_charging extends Thread {
    private int id;
    private int rounds;

    public E_scooter_charging(int id, int rounds) {
        this.id = id;
        this.rounds = rounds;
    }

    public void run() {
        for (int i = 0; i < rounds; i++) {
            Main.pool.acquire();

            Main.mutex.acquire();
            Main.busy++;
            if (Main.busy > Main.maxBusy) {
                Main.maxBusy = Main.busy;
            }
            Main.uses[id - 1]++;
            Main.total++;
            Main.mutex.release();

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }

            Main.mutex.acquire();
            Main.busy--;
            Main.mutex.release();

            Main.pool.release();
        }
    }
}

public class Main 
{
    static MySemaphore mutex = new MySemaphore(1);
    static MySemaphore pool = new MySemaphore(2);
    static int[] uses = new int[6];
    static int total = 0;
    static int busy = 0;
    static int maxBusy = 0;

    public static void main(String[] args){
    E_scooter_charging r1 = new E_scooter_charging(1, 3);
    E_scooter_charging r2 = new E_scooter_charging(2, 2);
    E_scooter_charging r3 = new E_scooter_charging(3, 4);
    E_scooter_charging r4 = new E_scooter_charging(4, 3);
    E_scooter_charging r5 = new E_scooter_charging(5, 2);
    E_scooter_charging r6 = new E_scooter_charging(6, 5);

    r1.start();
    r2.start();
    r3.start();
    r4.start();
    r5.start();
    r6.start();

    try {
        r1.join();
        r2.join();
        r3.join();
        r4.join();
        r5.join();
        r6.join();
    } catch (InterruptedException e) {
        System.out.println(e);
        return;
    }

    System.out.println("Total uses: " + total);
    System.out.println("Rider 1: " + uses[0] + " uses");
    System.out.println("Rider 2: " + uses[1] + " uses");
    System.out.println("Rider 3: " + uses[2] + " uses");
    System.out.println("Rider 4: " + uses[3] + " uses");
    System.out.println("Rider 5: " + uses[4] + " uses");
    System.out.println("Rider 6: " + uses[5] + " uses");
    System.out.println("Most in use at the same time: " + maxBusy);
    }
}
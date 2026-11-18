class MySemaphore {
private int permits;
public MySemaphore(int permits) {
this.permits = permits;
}
public synchronized void acquire() {
while (permits == 0) {
try {
wait();
} catch (InterruptedException e) {
}
}
permits--;
}
public synchronized void release() {
permits++;
notifyAll();
}
}
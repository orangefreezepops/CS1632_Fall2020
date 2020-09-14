class Interleaving implements Runnable {
    public static String script = "";
    public static int i = 0;
    public void run() {
        synchronized(this) {
            script += Thread.currentThread().getName() + " ";
            i++;
        }
        synchronized(this) {
            script += Thread.currentThread().getName() + " ";
            i++;
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Interleaving m = new Interleaving();
        Thread t = new Thread(m);
        t.start();
        m.run();
        t.join();
        System.out.println(script);
        assert i == 4;
    }
}

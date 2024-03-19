import java.util.Locale;

public class DownloadExample {

    public static void main(String[] args) throws InterruptedException {
//        sequentialDownload();
//        concurrentDownload2();
        concurrentDownload3();
    }

    // lista plików do pobrania
    static String[] toDownload = {
            "https://home.agh.edu.pl/~pszwed/wyklad-c/01-jezyk-c-intro.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/02-jezyk-c-podstawy-skladni.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/03-jezyk-c-instrukcje.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/04-jezyk-c-funkcje.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/05-jezyk-c-deklaracje-typy.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/06-jezyk-c-wskazniki.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/07-jezyk-c-operatory.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/08-jezyk-c-lancuchy-znakow.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/09-jezyk-c-struktura-programow.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/10-jezyk-c-dynamiczna-alokacja-pamieci.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/11-jezyk-c-biblioteka-we-wy.pdf",
            "https://home.agh.edu.pl/~pszwed/wyklad-c/preprocesor-make-funkcje-biblioteczne.pdf",
    };

    static void sequentialDownload() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            new Downloader(url).run();  // uwaga tu jest run()
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }

    static void concurrentDownload() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            // uruchom Downloader jako wątek... czyli wywołaj start()
            new Thread(new Downloader(url)).start();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }
    static void concurrentDownload2() {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            // uruchom Downloader jako wątek... czyli wywołaj start()
            new Thread(new Downloader(url)).start();
        }
        while(Downloader.count.get()!=toDownload.length){
            // wait...
            Thread.yield();
        }
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }

    static void concurrentDownload3() throws InterruptedException {
        double t1 = System.nanoTime() / 1e6;
        for (String url : toDownload) {
            // uruchom Downloader jako wątek... czyli wywołaj start()
            new Thread(new Downloader(url)).start();
        }
        Downloader.sem.acquire(toDownload.length);
        double t2 = System.nanoTime() / 1e6;
        System.out.printf(Locale.US, "t2-t1=%f\n", t2 - t1);
    }
}
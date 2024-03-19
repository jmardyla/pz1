import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

class Downloader implements Runnable{
    private final String url;
    static AtomicInteger count = new AtomicInteger(0);
    static Semaphore sem = new Semaphore(0);

    Downloader(String url){
        this.url = url;
    }

    public void run(){
        String[] urlParsed = url.split("/");
        String fileName = urlParsed[urlParsed.length-1];

        try(InputStream in = new URL(url).openStream(); FileOutputStream out = new FileOutputStream(fileName) ){
            for(;;){
                // czytaj znak z in
                // jeśli <0 break
                //zapisz znak do out
                int buff = in.read();
                if (buff<0) break;
                out.write(buff);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        count.getAndIncrement();
        sem.release();
        System.out.println("Done:"+fileName);
    }

}
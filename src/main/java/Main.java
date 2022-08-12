import model.Filme;
import service.Crawler;

import java.io.IOException;

public class Main {

    public Main() throws IOException {
    }
    public static void main(String[] args) throws IOException {
        Crawler crawler = new Crawler();
        crawler.findElements();
    }
    public static void log(String message){
        System.out.println(message);
    }
}

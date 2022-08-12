package service;

import model.Filme;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Crawler {
    static String url = "https://www.imdb.com/chart/bottom";
    public void findElements() throws IOException {
        Document doc = Jsoup.connect(url).get();

        Element table = doc.getElementsByClass("chart full-width").first();
        Element tbody = table.getElementsByTag("tbody").first();
        List<Element> elements = tbody.getElementsByTag("tr");
        List<Filme> filmes = new ArrayList<Filme>();

        elements.forEach(e -> {
            List<Element> attributes = e.getElementsByTag("td");
            Element filmNameElement = attributes.get(1);
            Double nota = Double.parseDouble(attributes.get(2).text());
            String path = filmNameElement.getElementsByTag("a").first().attr("href");
            Filme filme = new Filme();
            filme.setMoreInfoPath(path);
            filme.setNota(nota);
            filmes.add(filme);
        });
        System.out.println("Filmes encontrados.");
        System.out.println("Aguarde alguns instantes...");
        System.out.println("--------------------------------------------------------------");


        List<Filme> filmesFiltrados = filmes.stream().sorted(Comparator.comparing(e-> e.getNota())).limit(10).collect(Collectors.toList());

        filmesFiltrados.forEach(e->{
            try {
                extractOriginalName(e.getMoreInfoPath(),e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                extractDirector(e.getMoreInfoPath(),e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                extractAtores(e.getMoreInfoPath(),e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            try {
                extractComentarios(e.getMoreInfoPath(),e);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        montarResultado(filmesFiltrados);
    }
    private static void montarResultado(List<Filme> filmesFiltrados) {

        for (int i = filmesFiltrados.size() - 1; i >= 0; i--) {
            System.out.println("Nome Original: " + filmesFiltrados.get(i).getNomesOriginais().get(filmesFiltrados.get(i).getNomesOriginais().size()-1).toString().substring(16));
            filmesFiltrados.get(i).getNomesOriginais().stream().limit(filmesFiltrados.get(i).getNomesOriginais().size()-1).map(s -> s + ", ").forEach(System.out::print);

            System.out.println("Nota: " + filmesFiltrados.get(i).getNota());

            System.out.println("Diretores: " + filmesFiltrados.get(i).getDiretores().get(filmesFiltrados.get(i).getDiretores().size()-1));
            filmesFiltrados.get(i).getDiretores().stream().limit(filmesFiltrados.get(i).getDiretores().size()-1).map(s -> s + ", ").forEach(System.out::print);

            System.out.println("\nElenco Principal: " +  filmesFiltrados.get(i).getAtores().get(filmesFiltrados.get(i).getAtores().size()-1));
            filmesFiltrados.get(i).getAtores().stream().limit(filmesFiltrados.get(i).getAtores().size()-1).map(s -> s + ", ").forEach(System.out::print);

            System.out.println("\nComentarios: " + filmesFiltrados.get(i).getComentarios().get(0).toString());

            System.out.println("--------------------------------------------------------------");
        }
    }

    public static void extractOriginalName(String path, Filme filme) throws IOException {
        Document doc = Jsoup.connect("https://www.imdb.com/"+ path).get();
        List<Element> elements = new ArrayList<Element>();
        elements = doc.getElementsByClass("sc-dae4a1bc-0 gwBsXc");
        elements.forEach(e->{
                    filme.getNomesOriginais().add(e.text());
                }
        );
    }
    public static void extractDirector(String path, Filme filme) throws IOException {
        Document doc = Jsoup.connect("https://www.imdb.com/"+ path).get();
        List<Element> elements = new ArrayList<Element>();
        elements = doc.select("div:nth-child(1) > div > ul > li:nth-child(1) > div > ul > li > a");
        elements.forEach(e->{
                    filme.getDiretores().add(e.text());
                }
        );
    }
    public static void extractAtores(String path, Filme filme) throws IOException {
        Document doc = Jsoup.connect("https://www.imdb.com/"+ path).get();
        List<Element> elements = new ArrayList<Element>();
        elements = doc.select("div:nth-child(1) > div > ul > li:nth-child(3) > div > ul > li > a");
        elements.forEach(e->{
                    filme.getAtores().add(e.text());
                }
        );
    }
    public static void extractComentarios(String path, Filme filme) throws IOException {
        path = path.substring(0, 17);
        Document doc = Jsoup.connect("https://www.imdb.com/"+ path +"reviews?sort=userRating&dir=desc&ratingFilter=0").get();
        List<Element> elements = new ArrayList<Element>();
        List<Element> comments = new ArrayList<Element>();
        elements = doc.getElementsByClass("rating-other-user-rating");
        comments = doc.getElementsByClass("text show-more__control");
        elements.forEach(e->{
                    filme.getNotasComentarios().add(e.text());
                }
        );
        comments.forEach(e->{

            filme.getComentarios().add(e.text());
        });
    }
}

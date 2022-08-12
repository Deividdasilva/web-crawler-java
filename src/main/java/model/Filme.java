package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Filme {
    private Double nota;
    private String moreInfoPath;
    private List<String> diretores = new ArrayList<String>();
    private List<String> atores = new ArrayList<String>();
    private List<String> comentarios = new ArrayList<String>();
    private List<String> notasComentarios = new ArrayList<>();
    private List<String> nomesOriginais = new ArrayList<>();

    public Filme(String nome, Double nota) {
        this.nota = nota;
    }

    public Filme() {

    }

    public List<String> getDiretores() {
        return diretores;
    }

    public void setDiretores(List<String> diretores) {
        this.diretores = diretores;
    }
    public String getMoreInfoPath() {
        return moreInfoPath;
    }

    public void setMoreInfoPath(String moreInfoPath) {
        this.moreInfoPath = moreInfoPath;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public List<String> getAtores() {
        return atores;
    }

    public void setAtores(List<String> atores) {
        this.atores = atores;
    }

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    public List<String> getNotasComentarios() {
        return notasComentarios;
    }

    public void setNotaComentario(List<String> notaComentario) {
        this.notasComentarios = notaComentario;
    }

    public List<String> getNomesOriginais() {
        return nomesOriginais;
    }

    public void setNomeOriginal(List<String> nomeOriginal) {
        this.nomesOriginais = nomeOriginal;
    }
}

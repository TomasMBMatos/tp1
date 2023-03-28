package pack2Festivais;

public class Espetaculo extends Evento{
    private String[] artistas;
    private int maxArtistas = 10;
    private int nArtistas;
    private int numBilhetes;
    private String localidade;

    public Espetaculo(String nome, String localidade, int numBilhetes) {
        super(nome);
        this.localidade = localidade;
        this.numBilhetes = numBilhetes;
        artistas = new String[maxArtistas];
    }

    public int numActuacoes(String artista) {
        for(String art : artistas) {
            if(art != null) {
                if(art.equalsIgnoreCase(artista)) return 1;
            }
        }
        return 0;
    }

    public boolean addArtista(String artista) {
        if(nArtistas == maxArtistas) return false;
        for(String art: artistas) {
            if(art == null || art.equals(artista)) return false;


        }
        return false;
    }

    public int getNumBilhetes() {
        return this.numBilhetes;
    }

    public String[] getArtistas() {
        return this.artistas;
    }

    public String toString() {
        return super.toString() + localidade;
    }
}

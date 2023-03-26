package pack2Festivais;

public class Espetaculo extends Evento{
    private String[] artistas;
    private int nArtistas;
    private int numBilhetes;
    private String localidade;

    public Espetaculo(String nome, String localidade, int numBilhetes) {
        // TODO
        super(nome);
        this.localidade = localidade;
        this.numBilhetes = numBilhetes;
    }

    public int numActuacoes(String artista) {
        // TODO
        return 0;
    }

    public boolean addArtista(String artista) {
        // TODO
        return false;
    }

    public int getNumBilhetes() {
        return this.numBilhetes;
    }

    public String[] getArtistas() {
        return this.artistas;
    }

    public String toString() {
        // TODO
        return null;
    }
}

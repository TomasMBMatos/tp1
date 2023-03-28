package pack2Festivais;

public abstract class Evento {
    protected String nome;

    public Evento(String nome) {
        this.nome = nome;
    }

    public abstract int getNumBilhetes();

    public abstract String[] getArtistas();

    public abstract int numActuacoes(String artista);

    public String toString() {
        return String.format("%s com %d bilhetes e com os artistas %s", nome, getNumBilhetes(), getArtistas());
    }
}

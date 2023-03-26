package pack2Festivais;

public abstract class Evento {
    private String nome;

    public Evento(String nome) {
        this.nome = nome;
    }

    public int getNumBilhetes() {
        // TODO
        return 0;
    }

    public String[] getArtistas() {
        // TODO
        return null;
    }

    public int numActuacoes(String artista) {
        // TODO
        return 0;
    }

    public String toString() {
        // TODO
        return null;
    }
}

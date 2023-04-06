package pack2Festivais;

import java.util.Arrays;

public abstract class Evento {
    protected String nome;

    public Evento(String nome) {
        if(nome == null || nome.isBlank()) throw new IllegalArgumentException("O nome não pode ser vazio");
        this.nome = nome;
    }

    public abstract int getNumBilhetes();

    public abstract String[] getArtistas();

    public abstract int numActuacoes(String artista);

    public String toString() {
        return String.format("%s com %d bilhetes e com os artistas %s", nome, getNumBilhetes(), Arrays.toString(getArtistas()));
    }
}

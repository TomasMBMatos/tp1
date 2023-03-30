package pack2Festivais;

public class Espetaculo extends Evento{
    private String[] artistas;
    private int maxArtistas = 10;
    private int nArtistas;
    private int numBilhetes;
    private String localidade;

    public Espetaculo(String nome, String localidade, int numBilhetes) {
        super(nome);
        if( localidade == null || localidade.isBlank() ) throw new IllegalArgumentException("O nome da localidade não pode ser vazio");
        this.localidade = localidade;
        if( numBilhetes <= 0 ) throw new IllegalArgumentException("O número de bilhetes tem que ser superior a zero");
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
        for(int i=0; i<artistas.length;i++) {
            if(artista == null || artistas[i].equals(artista)) return false;
            if(artistas[i] == null) {
                artistas[i] = artista;
                nArtistas++;
                return true;
            }
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

    public static void main(String[] args) {
        Espetaculo e1 = new Espetaculo("NosAlive", "Lisboa", 20000);

        // teste addArtista
        e1.addArtista("Eminem");

        System.out.println(e1);

        // teste numAtuacoes
        System.out.println("Num atuacoes do artista" + e1.numActuacoes("Eminem"));

        // teste construtor
        System.out.println("Nome vazio");
        try {
            Espetaculo e2 = new Espetaculo(" ", "Lisboa", 20000);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println();
        }

        System.out.println("Localidade vazia");
        try {
            Espetaculo e2 = new Espetaculo("Rock in Rio", " ", 20000);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.out.println();
        }
    }
}

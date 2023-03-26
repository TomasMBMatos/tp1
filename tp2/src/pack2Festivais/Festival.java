package pack2Festivais;

public class Festival extends Evento{
    private Evento[] eventos;
    private int numEventos;

    public Festival(String nome) {
        super(nome);
    }

    @Override
    public int getNumBilhetes() {
        int cnt = 0;
        for (Evento evento : eventos) {
            if (evento != null)
                cnt += evento.getNumBilhetes();
        }
        return cnt;
    }

    @Override
    public int numActuacoes(String artista) {
        // TODO
        return 0;
    }

    @Override
    public String toString() {
        return "Festival " + super.toString();
    }

    @Override
    public String[] getArtistas() {
        // TODO
        return null;
    }

    private int getDeepFestival() {
        // TODO
        return 0;
    }

    public boolean addEvento(Evento evento) {
        // TODO
        return false;
    }
}

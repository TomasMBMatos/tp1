package pack2Festivais;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Festival extends Evento {
    private Evento[] eventos;
    private int maxEventos = 20;
    private int numEventos;

    public Festival(String nome) {
        super(nome);
        this.eventos = new Evento[maxEventos];
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
        int cnt = 0;
        for(Evento evento : eventos) {
            if(evento == null) {
                return 0;
            }
            if(evento instanceof Espetaculo) {
                for(String art : evento.getArtistas()) {
                    if(art.equalsIgnoreCase(artista)) {
                        cnt++;
                    }
                }
            }
            else if(evento instanceof Festival) {
                cnt += evento.numActuacoes(artista);
            }
        }
        return cnt;
    }

    @Override
    public String toString() {
        return "Festival " + super.toString();
    }

    @Override
    public String[] getArtistas() {
        List<String> artistas = new ArrayList<>();
        for(Evento evento: eventos) {
            if(evento instanceof Espetaculo) {
                for(String artista: evento.getArtistas()) {
                    if(evento.getArtistas() != null && !artistas.contains(artista)) artistas.add(artista);
                }
            }
            else if(evento instanceof Festival) {
                artistas.addAll(Arrays.asList(evento.getArtistas()));
            }
        }
        return artistas.toArray(String[]::new);
    }

    private int getDeepFestival() {
        int ret = 0;
        for(Evento evento: eventos) {
            int profundidade = 1;
            if(evento instanceof Festival) {
                profundidade += ((Festival) evento).getDeepFestival();
            }
            if(profundidade > ret) {
                ret = profundidade;
            }
        }
        return ret;
    }

    public boolean addEvento(Evento evento) {
        if(numEventos == maxEventos) return false;
        for(String artista : evento.getArtistas()) {
            if(evento.numActuacoes(artista) > numActuacoes(artista) + 2) return false;
        }
        for(int i=0;i<eventos.length;i++) {
            if(eventos[i] == null) {
                eventos[i] = evento;
                numEventos++;
                break;
            }
        }
        return true;
    }

    public boolean delEvento(String nomeEvento) {
        for(int i=0;i<eventos.length;i++) {
            if(eventos[i] == null) continue;
            if(eventos[i].nome.equals(nomeEvento)) {
                eventos[i] = null;
                numEventos--;
                return true;
            } else if (eventos[i] instanceof Festival) {
                if(((Festival) eventos[i]).delEvento(nomeEvento)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Festival f1 = new Festival("NosAlive");

        Espetaculo c1 = new Espetaculo("Imagine Dragons", "Lisboa", 20000);
        Espetaculo c2 = new Espetaculo("Eminem", "Lisboa", 30000);
        // teste addArtista
        c1.addArtista("Imagine Dragons");
        c2.addArtista("Eminem");

        f1.addEvento(c1);
        f1.addEvento(c2);

        System.out.println("NosAlive com eventos: " + f1);

        f1.delEvento("Eminem");

        System.out.println(f1);

        Festival f2 = new Festival("Rock in Rio");
        f1.addEvento(f2);

        System.out.println(f1);

        // teste getDeepFestival
        System.out.println("Profundidade de f1, valor esperado 2, valor obtido " + f1.getDeepFestival());

        // teste construtor
        System.out.println("Nome null");
        try {
            Festival f3 = new Festival(null);
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}

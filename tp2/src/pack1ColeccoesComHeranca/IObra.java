package pack1ColeccoesComHeranca;

public interface IObra {
    //Título
    String getTitulo();
    //Número de páginas
    int getNumPaginas();
    //Preço
    float getPreco();
    //String para depois dar print
    String toString();
    //Método para ver o output
    void print(String prefix);
    /*
     *Variável booleana em que quando dá True se fôr igual
     *caso contrário passa a False.
     */
    boolean equals(Object l);
}

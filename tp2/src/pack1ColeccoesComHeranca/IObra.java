package pack1ColeccoesComHeranca;

public interface IObra {
    //T�tulo
    String getTitulo();
    //N�mero de p�ginas
    int getNumPaginas();
    //Pre�o
    float getPreco();
    //String para depois dar print
    String toString();
    //M�todo para ver o output
    void print(String prefix);
    /*
     *Vari�vel booleana em que quando d� True se f�r igual
     *caso contr�rio passa a False.
     */
    boolean equals(Object l);
}

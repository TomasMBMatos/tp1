package pack1ColeccoesComHeranca;

public interface IObra {
    //T�tulo
    String getTitulo();
    //N�mero de p�ginas
    int getNumPaginas();
    //Pre�o
    float getPreco();
    //M�todo para ver o output
    void print(String prefix);

}

package pack1ColeccoesComHeranca;

public interface IObra {
    //Título
    String getTitulo();
    //Número de páginas
    int getNumPaginas();
    //Preço
    float getPreco();
    //Método para ver o output
    void print(String prefix);

}

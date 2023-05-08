import java.util.ArrayList;

public class Banco {

    ArrayList<Cliente> clientes = new ArrayList<>();
    ArrayList<Integer> numContas = new ArrayList<>();

    public void addCliente(Cliente cliente) {

    }

    private boolean existeConta(long numConta) {
        for(Cliente cliente: clientes) {
            if(cliente.getConta().getNumConta() == numConta)
                return true;
        }
        return false;
    }

    public Cliente getCliente(long numConta) {
        for(Cliente cliente: clientes) {
            if(cliente.getConta().getNumConta() == numConta) {
                return cliente;
            }
        }
        return null;
    }

    public void removerCliente(Cliente cliente) {
        clientes.remove(cliente);
    }
}

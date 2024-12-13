import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Blockchain {
    private LinkedList<Bloco> cadeia;
    private int dificuldade;
    private Map<String, Double> saldos;

    public Blockchain(int dificuldade) {
        this.dificuldade = dificuldade;
        cadeia = new LinkedList<>();
        saldos = new HashMap<>();
        
        Bloco blocoGenese = new Bloco("0", new LinkedList<>(), dificuldade);
        cadeia.add(blocoGenese);

        saldos.put("aabbccddeeff00112233445566778899aabbccddeeff0011", 1000.0);
        saldos.put("112233445566778899aabbccddeeff001122334455667788", 500.0);
    }

    public void adicionarBloco(List<Transacao> transacoes) {
        if (transacoes.isEmpty()) {
            throw new IllegalArgumentException("Nao eh possivel adicionar um bloco vazio.");
        }

        for (Transacao transacao : transacoes) {
            if (!validarEndereco(transacao.getRemetente()) || !validarEndereco(transacao.getDestinatario())) {
                throw new IllegalArgumentException("Endereco invalido em uma das transacoes.");
            }

            if (saldos.getOrDefault(transacao.getRemetente(), 0.0) < transacao.getQuantidade() + transacao.getTaxa()) {
                throw new IllegalArgumentException("Saldo insuficiente para a transacao.");
            }
            
            if (transacao.getQuantidade() <= 0 || transacao.getTaxa() < 0) {
                throw new IllegalArgumentException("Quantidade ou taxa invalida.");
            }
        }

        Bloco bloco = new Bloco(cadeia.getLast().getHash(), transacoes, dificuldade);
        cadeia.add(bloco);

        atualizarSaldos(transacoes);
    }

    private void atualizarSaldos(List<Transacao> transacoes) {
        for (Transacao transacao : transacoes) {

            double saldoRemetente = saldos.getOrDefault(transacao.getRemetente(), 0.0);
            saldos.put(transacao.getRemetente(), saldoRemetente - transacao.getQuantidade() - transacao.getTaxa());

            double saldoDestinatario = saldos.getOrDefault(transacao.getDestinatario(), 0.0);
            saldos.put(transacao.getDestinatario(), saldoDestinatario + transacao.getQuantidade());
        }
    }

    public Map<String, Double> getSaldos() {
        return saldos;
    }

    public boolean validarBlockchain() {
        for (int i = 1; i < cadeia.size(); i++) {
            Bloco blocoAtual = cadeia.get(i);
            Bloco blocoAnterior = cadeia.get(i - 1);

            if (!blocoAtual.getHash().equals(blocoAtual.calcularHash())) {
                return false;
            }

            if (!blocoAtual.getHashAnterior().equals(blocoAnterior.getHash())) {
                return false;
            }
        }
        return true;
    }

    private boolean validarEndereco(String endereco) {
        return endereco != null && endereco.matches("^[0-9a-fA-F]{48}$");
    }

    public void resolverFork(LinkedList<Bloco> novaCadeia) {
        if (novaCadeia.size() > this.cadeia.size()) {
            this.cadeia = novaCadeia;
            System.out.println("A cadeia mais longa foi adotada!");
        }
    }

    public boolean validarNos(List<No> nos) {
        for (No no : nos) {
            if (!no.getBlockchain().validarBlockchain()) {
                System.out.println("Cadeia do no " + no.getId() + " nao eh valida.");
                return false;
            }
        }
        return true;
    }

    public LinkedList<Bloco> getCadeia() {
        return cadeia;
    }
}

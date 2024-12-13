import java.util.List;
import java.util.ArrayList;

public class No {
    private String id;
    private Blockchain blockchain;
    private List<Transacao> poolTransacoes;

    public No(String id, int dificuldade) {
        this.id = id;
        this.blockchain = new Blockchain(dificuldade);
        this.poolTransacoes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public void receberTransacao(Transacao transacao) {
        poolTransacoes.add(transacao);
        System.out.println("No " + id + " recebeu uma nova transacao: \n" + transacao);
    }

    public void minerarBloco() {
        if (poolTransacoes.isEmpty()) {
            System.out.println("No " + id + " nao possui transacoes para minerar.");
            return;
        }
    
        double recompensaTotal = 0;
        for (Transacao transacao : poolTransacoes) {
            recompensaTotal += transacao.getTaxa();
        }
    
        double recompensaFixa = 50.0;
        recompensaTotal += recompensaFixa;
    
        blockchain.adicionarBloco(poolTransacoes);
        System.out.println("No " + id + " minerou um novo bloco! Recompensa total: " + recompensaTotal);
        poolTransacoes.clear();
    
        Bloco blocoMinerado = blockchain.getCadeia().getLast();
        System.out.println("Bloco: " + blocoMinerado.getHash());
        System.out.println("Hash Anterior: " + blocoMinerado.getHashAnterior());
        System.out.println("Timestamp: " + blocoMinerado.getTimestamp());
        System.out.println("Nonce: " + blocoMinerado.getNonce());
    }      

    public void propagarBloco(List<No> nos) {
        for (No no : nos) {
            if (!no.getId().equals(this.id)) {
                no.sincronizarBlockchain(this.blockchain);
                System.out.println("No " + id + " propagou o bloco para o " + no.getId());
            }
        }
    }

    public void sincronizarBlockchain(Blockchain blockchain) {
        this.blockchain.resolverFork(blockchain.getCadeia());
    }
}

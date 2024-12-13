import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Bloco {
    private String hashAnterior;
    private String hash;
    private long timestamp;
    private List<Transacao> transacoes;
    private int nonce;
    private int dificuldade;

    public Bloco(String hashAnterior, List<Transacao> transacoes, int dificuldade) {
        this.hashAnterior = hashAnterior;
        this.transacoes = transacoes;
        this.timestamp = System.currentTimeMillis();
        this.dificuldade = dificuldade;
        this.nonce = 0;
        this.hash = realizarProofOfWork();
    }

    private String realizarProofOfWork() {
        String hashCalculado;
        do {
            hashCalculado = calcularHash();
            nonce++;
        } while (!hashCalculado.startsWith("0".repeat(dificuldade)));

        return hashCalculado;
    }
    
    public String calcularHash() {
        String dados = hashAnterior + transacoes.toString() + nonce + dificuldade;
        return calcularSHA256(dados);
    }
    
    private String calcularSHA256(String dados) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(dados.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHash() {
        return hash;
    }

    public String getHashAnterior() {
        return hashAnterior;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public int getNonce() {
        return nonce;
    }

    public int getDificuldade() {
        return dificuldade;
    }
}

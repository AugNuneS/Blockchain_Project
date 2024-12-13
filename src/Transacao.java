public class Transacao {
    private String remetente;
    private String destinatario;
    private double quantidade;
    private double taxa;

    private static final String ENDERECO_REGEX = "^[0-9a-fA-F]{48}$";

    public Transacao(String remetente, String destinatario, double quantidade, double taxa) {
        if (!validarEndereco(remetente) || !validarEndereco(destinatario)) {
            throw new IllegalArgumentException("Endereco invalido! Os endereços devem ter 48 caracteres hexadecimais.");
        }
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.quantidade = quantidade;
        this.taxa = taxa;
    }

    private boolean validarEndereco(String endereco) {
        return endereco != null && endereco.matches(ENDERECO_REGEX);
    }

    public String getRemetente() {
        return remetente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public double getTaxa() {
        return taxa;
    }

    public static double calcularRecompensaMinerador(Transacao[] transacoes) {
        double recompensa = 0;
        for (Transacao t : transacoes) {
            recompensa += t.getTaxa();
        }
        return recompensa;
    }

    @Override
    public String toString() {
        return "Remetente: " + remetente + ", Destinatario: " + destinatario + ", Quantidade: " + quantidade + ", Taxa: " + taxa;
    }
}

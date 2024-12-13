import java.util.List;
import java.util.Map;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        No no1 = new No("01", 3);
        No no2 = new No("02", 3);
        No no3 = new No("03", 3);

        List<No> nos = Arrays.asList(no1, no2, no3);

        boolean ehValida = no1.getBlockchain().validarBlockchain();
        System.out.println("A blockchain eh valida? " + (ehValida ? "Sim" : "Nao"));

        Transacao t1 = criarTransacao(
                "aabbccddeeff00112233445566778899aabbccddeeff0011",
                "112233445566778899aabbccddeeff001122334455667788",
                50.0,
                1.0);

        Transacao t2 = criarTransacao(
                "112233445566778899aabbccddeeff001122334455667788",
                "aabbccddeeff00112233445566778899aabbccddeeff0011",
                20.0,
                0.5);

        no1.receberTransacao(t1);
        no1.receberTransacao(t2);

        propagarTransacoes(nos, t1, t2);

        exibirSaldos(no1, "antes da mineracao");

        no1.minerarBloco();
        no1.propagarBloco(nos);
        exibirSaldos(no1, "apos a mineracao");

        no2.minerarBloco();
        no2.propagarBloco(nos);
        exibirSaldos(no2, "apos a mineracao");
    }

    private static Transacao criarTransacao(String remetente, String destinatario, double quantidade, double taxa) {
        return new Transacao(remetente, destinatario, quantidade, taxa);
    }

    private static void propagarTransacoes(List<No> nos, Transacao... transacoes) {
        for (Transacao transacao : transacoes) {
            for (No no : nos) {
                if (!no.getId().equals(transacao.getRemetente())) {
                    no.receberTransacao(transacao);
                }
            }
        }
    }

    private static void exibirSaldos(No no, String contexto) {
        Blockchain blockchain = no.getBlockchain();
        Map<String, Double> saldos = blockchain.getSaldos();

        System.out.println("Saldos no " + no.getId() + " " + contexto + ":");
        for (Map.Entry<String, Double> entry : saldos.entrySet()) {
            System.out.println("Endereco: " + entry.getKey() + " | Saldo: " + entry.getValue());
        }
    }
}

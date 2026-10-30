import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String caminho = args.length > 0 ? args[0] : "missoes.txt";
        ArrayList<Missao> missoes = new ArrayList<>();
        Scanner scanner = null;
        int importados = 0;
        int ignorados = 0;
        int numeroLinha = 0;

        try {
            scanner = new Scanner(new File(caminho));

            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                numeroLinha++;

                try {
                    missoes.add(criarMissao(linha));
                    importados++;
                } catch (NumberFormatException e) {
                    ignorados++;
                    System.out.println("Linha " + numeroLinha + ": conteudo numerico invalido (" + e.getMessage() + ")");
                } catch (RegistroMissaoInvalidoException e) {
                    ignorados++;
                    System.out.println("Linha " + numeroLinha + ": registro rejeitado - " + e.getMessage());
                }
            }

            exibirMissoes(missoes);
        } catch (FileNotFoundException e) {
            System.out.println("O arquivo '" + caminho + "' nao pode ser localizado.");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            System.out.println();
            System.out.println("Processamento encerrado.");
            System.out.println("Registros importados: " + importados);
            System.out.println("Registros ignorados: " + ignorados);
        }
    }

    private static Missao criarMissao(String linha) throws RegistroMissaoInvalidoException {
        String[] campos = linha.split(";", -1);

        if (campos.length != 4) {
            throw new RegistroMissaoInvalidoException("a linha deve possuir exatamente 4 campos, mas possui " + campos.length);
        }

        String codigo = campos[0].trim();
        String destino = campos[1].trim();

        if (codigo.isEmpty()) {
            throw new RegistroMissaoInvalidoException("o codigo da missao e obrigatorio");
        }

        if (destino.isEmpty()) {
            throw new RegistroMissaoInvalidoException("o destino da missao e obrigatorio");
        }

        int tripulantes = Integer.parseInt(campos[2].trim());
        int combustivel = Integer.parseInt(campos[3].trim());

        if (tripulantes < 1 || tripulantes > 10) {
            throw new RegistroMissaoInvalidoException("tripulantes deve estar entre 1 e 10, mas foi " + tripulantes);
        }

        if (combustivel < 0 || combustivel > 100) {
            throw new RegistroMissaoInvalidoException("combustivel deve estar entre 0 e 100, mas foi " + combustivel);
        }

        return new Missao(codigo, destino, tripulantes, combustivel);
    }

    private static void exibirMissoes(ArrayList<Missao> missoes) {
        System.out.println();
        System.out.println("Missoes importadas:");

        if (missoes.isEmpty()) {
            System.out.println("Nenhuma missao importada.");
            return;
        }

        for (Missao missao : missoes) {
            missao.exibirResumo();
        }
    }
}
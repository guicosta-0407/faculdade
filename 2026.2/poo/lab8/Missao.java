public class Missao {

    private final String codigo;
    private final String destino;
    private final int tripulantes;
    private final int combustivel;

    public Missao(String codigo, String destino, int tripulantes, int combustivel) {
        this.codigo = codigo;
        this.destino = destino;
        this.tripulantes = tripulantes;
        this.combustivel = combustivel;
    }

    public void exibirResumo() {
        System.out.println("Missao " + codigo + " | Destino: " + destino
                + " | Tripulantes: " + tripulantes + " | Combustivel: " + combustivel + "%");
    }
}
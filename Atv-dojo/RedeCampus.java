import java.util.*;

public class RedeCampus {
    private final Map<String, List<String>> adjacencias = new LinkedHashMap<>();

    // TODO 1: Cadastrar um local[cite: 12]
    public void adicionarLocal(String local) {
        adjacencias.putIfAbsent(local, new ArrayList<>());
    }

    // TODO 2: Adicionar um corredor (aresta não direcionada)[cite: 12]
    public void adicionarAresta(String a, String b) {
        adicionarLocal(a);
        adicionarLocal(b);
        adjacencias.get(a).add(b);
        adjacencias.get(b).add(a);
    }

    public List<String> vizinhos(String local) {
        return adjacencias.getOrDefault(local, new ArrayList<>());
    }

    public void imprimir() {
        for (Map.Entry<String, List<String>> e : adjacencias.entrySet()) {
            System.out.println(e.getKey() + "->" + e.getValue());
        }
    }

    public List<String> menorCaminho(String origem, String destino) {
        // TODO 3: Casos especiais[cite: 13]
        if (!adjacencias.containsKey(origem) || !adjacencias.containsKey(destino)) {
            return new ArrayList<>();
        }
        if (origem.equals(destino)) {
            return List.of(origem);
        }

        Queue<String> fila = new ArrayDeque<>();
        Set<String> visitados = new HashSet<>();
        Map<String, String> anterior = new HashMap<>();

        // TODO 4: Preparar a busca[cite: 13]
        fila.add(origem);
        visitados.add(origem);

        // TODO 5: Loop BFS[cite: 13, 14]
        while (!fila.isEmpty()) {
            String atual = fila.poll();

            for (String vizinho : vizinhos(atual)) {
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    anterior.put(vizinho, atual);
                    fila.add(vizinho);

                    if (vizinho.equals(destino)) {
                        return reconstruirCaminho(anterior, origem, destino);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    // TODO 6: Reconstruir o caminho[cite: 14]
    private List<String> reconstruirCaminho(Map<String, String> anterior, String origem, String destino) {
        List<String> caminho = new ArrayList<>();
        String atual = destino;

        while (atual != null) {
            caminho.add(atual);
            if (atual.equals(origem)) break;
            atual = anterior.get(atual);
        }

        Collections.reverse(caminho);
        return caminho;
    }

    public static void main(String[] args) {
        RedeCampus rede = new RedeCampus();

        // Parte A - Grafo do Campus[cite: 15, 17]
        rede.adicionarAresta("Entrada", "Biblioteca");
        rede.adicionarAresta("Entrada", "Cantina");
        rede.adicionarAresta("Biblioteca", "Laboratório");
        rede.adicionarAresta("Biblioteca", "Bloco A");
        rede.adicionarAresta("Cantina", "Bloco A");
        rede.adicionarAresta("Laboratório", "Bloco A");
        rede.adicionarAresta("Laboratório", "Auditório");
        rede.adicionarAresta("Bloco A", "Auditório");

        System.out.println("=== Lista de adjacência ===");
        rede.imprimir();

        System.out.println("\n=== Testes ===");
        testar(rede, "Entrada", "Auditório");
        testar(rede, "Biblioteca", "Biblioteca");
        testar(rede, "Pátio", "Auditório");
    }

    private static void testar(RedeCampus rede, String origem, String destino) {
        List<String> caminho = rede.menorCaminho(origem, destino);
        if (caminho.isEmpty()) {
            System.out.println(origem + " -> " + destino + ": sem caminho");
        } else {
            System.out.println(origem + " -> " + destino + ": " +
                    String.join(" -> ", caminho) +
                    " (" + (caminho.size() - 1) + " ligações)");
        }
    }
}
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeSet;

public class App {

      private static void medirDesempenho(String nome, Runnable tarefa) {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage before = memoryBean.getHeapMemoryUsage();

        long start = System.nanoTime();
        tarefa.run();
        long end = System.nanoTime();

        MemoryUsage after = memoryBean.getHeapMemoryUsage();
        long tempo = (end - start) / 1_000_000; // em ms
        long memoriaUsada = after.getUsed() - before.getUsed();

        System.out.printf("%s -> Tempo: %d ms | Memória usada: %d KB%n",
                nome, tempo, memoriaUsada / 1024);
    }
public static void main(String[] args) {
        int n = 1000000;
        Random random = new Random();

        // ---------- Gerarando dados ----------
        int[] dados = new int[n];
        for (int i = 0; i < n; i++) {
            dados[i] = 100000 + random.nextInt(1000000); 
        }

        // ---------- Gerarando valores para busca ----------
        int[] buscas = new int[n];
        for (int i = 0; i < n; i++) {
            buscas[i] = 100000 + random.nextInt(1000000);
        }

                // ---------- TreeSet (árvore balanceada) ----------
        TreeSet<Integer> arvore = new TreeSet<>();
        
        medirDesempenho("TreeSet (Árvore)", () -> {
            for (int x : dados) arvore.add(x);
            for (int x : buscas) {
                arvore.contains(x);
            }
        });
        
        // ---------- Busca Binária ----------
        int[] vetorOrdenado = Arrays.copyOf(dados, n);
        
        medirDesempenho("Busca Binária", () -> {
            Arrays.sort(vetorOrdenado);
            for (int x : buscas) {
                Arrays.binarySearch(vetorOrdenado, x);
            }
        });

        // ---------- HashMap ----------
        HashMap<Integer, Boolean> hash = new HashMap<>();
        
        medirDesempenho("HashMap", () -> {
            for (int x : dados) hash.put(x, true);
            for (int x : buscas) {
                hash.containsKey(x);
            }
        });

        


    }

  
}

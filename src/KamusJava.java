import java.io.File;
import java.util.Scanner;

public class KamusJava {

    static class Node {
        String data;
        String meaning;
        Node left;
        Node right;
        public Node (String data, String meaning) {
            this.data = data;
            this.meaning = meaning;
            left = null;
            right = null;
        }
    }

    public void promptEnterKey(){
        System.out.println("\nTekan \"Enter\" untuk melanjutkan");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void main(String[] args) throws Exception{
        KamusJava kamusJava = new KamusJava();
        Scanner input = new Scanner(System.in);

        File file = new File("src/kosakata.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            String wordMeanLine = sc.nextLine();
            String[] wordMeanSplit = wordMeanLine.split(" = ");
            String word = wordMeanSplit[0].strip().toLowerCase();
            String mean = wordMeanSplit[1].strip();
            kamusJava.insert(word, mean);
        }

        boolean menu = true;
        while (menu) {
            System.out.println("PROGRAM KAMUS");
            System.out.println("1. Tampilkan Kosakata A-Z\n2. Tampilkan Kosakata Z-A\n" +
                    "3. Cari Makna Kosakata\n4. Keluar");
            System.out.print("Pilih menu (1-4): ");
            String pilihan = input.nextLine(); int pilihanInt = Integer.parseInt(pilihan);
            switch (pilihanInt) {
                case 1 -> {
                    System.out.println("\nDAFTAR KAMUS (A-Z):");
                    kamusJava.display(root); kamusJava.promptEnterKey();
                }
                case 2 -> {
                    System.out.println("\nDAFTAR KAMUS (Z-A):");
                    kamusJava.displaydesc(root); kamusJava.promptEnterKey();
                }
                case 3 -> {
                    System.out.print("Masukkan Kamus yang ingin dicari: ");
                    String cari = input.nextLine().toLowerCase().strip();
                    boolean check = kamusJava.find(cari);
                    if (check){
                        kamusJava.promptEnterKey();
                    } else {
                        System.out.println("\nKata \"" + cari + "\" tidak ditemukan");
                        kamusJava.promptEnterKey();
                    }
                }
                case 4 -> {
                    System.out.println("Program Keluar...");
                    input.close(); sc.close();
                    menu = false;
                }
            }
        }
    }

    public static Node root;

    public KamusJava() {
        root = null;
    }

    public boolean find(String id) {
        Node current = root;
        while (current != null) {
            if (current.data.equals(id)) {
                String mean = current.meaning.replace("|", "\n");
                System.out.println("\n" + current.data.toUpperCase() + ":\n" + mean);
                return true;
            } else if (id.compareTo(current.data) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return false;
    }

    public void insert(String id, String meaning) {
        Node newNode = new Node(id, meaning);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;
        while (true) {
            parent = current;
            if (id.compareTo(current.data) < 0) {
                current = current.left;
                if (current == null) {
                    parent.left = newNode;
                    return;
                }
            } else {
                current = current.right;
                if (current == null) {
                    parent.right = newNode;
                    return;
                }
            }
        }
    }

    public void display(Node root) {
        if (root != null) {
            display(root.left);
            System.out.print("- " + root.data + "\n");
            display(root.right);
        }
    }

    public void displaydesc(Node root) {
        if (root != null) {
            displaydesc(root.right);
            System.out.print("- " + root.data + "\n");
            displaydesc(root.left);
        }
    }
}
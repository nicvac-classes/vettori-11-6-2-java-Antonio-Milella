import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;

public class EsercizioBiblioteca {
    private static Scanner in = new Scanner(System.in);
    private static Scanner reader;
    private static FileWriter writer;

    public static void main(String[] args) {
        int c;
        int nv, np, i;
        String e;
        
        nv = 0;
        np = 0;
        String[] v = new String[100];
        String[] p = new String[100];
        
        nv = leggiVettore(v, nv, "Libri.txt");
        np = leggiVettore(p, np, "Prestiti.txt");
        do {
            c = leggiComando();
            if (c == 1) {
                System.out.println("Nuovo libro da inserire in biblioteca:");
                e = in.nextLine();
                nv = inserisciInVettore(v, nv, e, 0);
            } else {
                if (c == 2) {
                    if (nv > 0) {
                        visualizzaVettore(v, nv);
                    } else {
                        System.out.println("Non ci sono libri in biblioteca");
                    }
                } else {
                    if (c == 3) {
                        if (np > 0) {
                            visualizzaVettore(p, np);
                        } else {
                            System.out.println("Non ci sono libri in prestito");
                        }
                    } else {
                        if (c == 4) {
                            visualizzaVettore(v, nv);
                            System.out.println("Titolo del libro (o una sua parola) da prendere in prestito: ");
                            e = in.nextLine();
                            i = ricercaNelVettore(v, nv, e);
                            if (i >= 0) {
                                System.out.println("Libro scelto: " + (char) 10 + i + ":" + v[i]);
                                np = inserisciInVettore(p, np, v[i], 0);
                                nv = eliminaDaVettore(v, nv, i);
                            } else {
                                System.out.println("Libro " + e + " non trovato.");
                            }
                        } else {
                            if (c == 5) {
                                if (np > 0) {
                                    visualizzaVettore(p, np);
                                    do {
                                        System.out.println("Indice del libro da restituire: ");
                                        i = Integer.parseInt(in.next());
                                    }
                                    while (i >= np);
                                    System.out.println("Libro restituito: " + (char) 10 + i + ":" + p[i]);
                                    nv = inserisciInVettore(v, nv, p[i], 0);
                                    np = eliminaDaVettore(p, np, i);
                                } else {
                                    System.out.println("Non ci sono libri in prestito");
                                }
                            }
                        }
                    }
                }
            }
        }
        while (c != 6);
        salvaVettore(v, nv, "Libri.txt");
        salvaVettore(p, np, "Prestiti.txt");
    }
    
    public static int eliminaDaVettore(String[] v, int n, int ie) {
        int i, n2;
        
        n2 = n - 1;
        i = ie;
        while (i <= n - 2) {
            v[i] = v[i + 1];
            i = i + 1;
        }
        
        return n2;
    }
    
    public static int inserisciInVettore(String[] v, int n, String e, int ie) {
        int i, n2;
        
        n2 = n + 1;
        i = n2 - 1;
        while (i >= ie + 1) {
            v[i] = v[i - 1];
            i = i - 1;
        }
        v[ie] = e;
        
        return n2;
    }
    
    public static int leggiComando() {
        int c;
        
        do {
            System.out.println("1) Nuovo libro in biblioteca" + (char) 10 + "2) Visualizza libri in biblioteca" + (char) 10 + "3) Visualizza libri in prestito" + (char) 10 + "4) Prendi in prestito un libro" + (char) 10 + "5) Restituisci un libro" + (char) 10 + "6) Esci dal programma");
            c = Integer.parseInt(in.next());
        }
        while (c < 1 || 6 < c);
        
        return c;
    }
    
    public static int leggiVettore(String[] v, int n, String nomeFile) {
        int i;
        
        reader = new Scanner(new File(nomeFile));
        n = reader.nextInt();
        for (i = 0; i <= n - 1; i++) {
            v[i] = reader.nextLine();
        }
        reader.close();
        
        return n;
    }
    
    public static int ricercaNelVettore(String[] v, int n, String valore) {
        int i, iTrovato;
        
        i = 0;
        iTrovato = (int) (-1);
        while (i < n && iTrovato == -1) {
            
            // Controllo se valore è una sottostringa di V[i]
            if (sottostringa(valore, v[i]) != -1) {
                iTrovato = i;
            }
            i = i + 1;
        }
        
        return iTrovato;
    }
    
    public static void salvaVettore(String[] v, int n, String nomeFile) {
        writer = new FileWriter(nomeFile);
        int i;
        
        writer.write(N + "\n");
        for (i = 0; i <= n - 1; i++) {
            writer.write(V[i] + "\n");
        }
        writer.close();
    }
    
    public static int sottostringa(String sottoStr, String stringa) {
        int i, j, iCurr, iTrovato, lungStr, lungSSt;
        String primoCarattere;
        boolean continua;
        
        iTrovato = (int) (-1);
        lungStr = stringa.length();
        lungSSt = sottoStr.length();
        primoCarattere = sottoStr.charAt(0);
        iCurr = 0;
        while (iCurr < lungStr && iTrovato == -1) {
            i = iCurr;
            continua = true;
            while (i < lungStr && continua) {
                if (!primoCarattere.equals(stringa.charAt(i))) {
                    i = i + 1;
                } else {
                    continua = false;
                }
            }
            iCurr = i;
            if (i < lungStr) {
                iTrovato = iCurr;
                i = i + 1;
                j = 1;
                continua = true;
                while (i < lungStr && j < lungSSt && continua) {
                    if (stringa.charAt(i).equals(sottoStr.charAt(j))) {
                        i = i + 1;
                        j = j + 1;
                    } else {
                        continua = false;
                    }
                }
                if (j < lungSSt) {
                    iTrovato = (int) (-1);
                }
            } else {
                iTrovato = (int) (-1);
            }
            iCurr = iCurr + 1;
        }
        
        return iTrovato;
    }
    
    public static void visualizzaVettore(String[] v, int n) {
        int i;
        
        i = 0;
        while (i < n) {
            System.out.println(Integer.toString(i) + ": " + v[i]);
            i = i + 1;
        }
    }
}

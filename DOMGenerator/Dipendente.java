package DOMGenerator;

public class Dipendente {
    private String cognome;
    private String nome;
    private String dipartimento;
    private int stipendio;

    public Dipendente(String cognome, String nome, String dipartimento, int stipendio) {
        this.cognome = cognome;
        this.nome = nome;
        this.dipartimento = dipartimento;
        this.stipendio = stipendio;
    }

    public String getCognome() {
        return cognome;
    }

    public String getNome() {
        return nome;
    }

    public String getDipartimento() {
        return dipartimento;
    }

    public int getStipendio() {
        return stipendio;
    }
}

package it.sal.disco.unimib.avemanager.ui.model;

public class CheckInResult {
    public final String nome;
    public final String messaggio;

    public CheckInResult(String nome, String messaggio) {
        this.nome = nome;
        this.messaggio = messaggio;
    }

    public String getNomeUtente() {
        return nome;
    }

    public String getNomeEvento() {
        return messaggio;
    }
}

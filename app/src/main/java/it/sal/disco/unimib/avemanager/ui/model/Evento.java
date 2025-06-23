package it.sal.disco.unimib.avemanager.ui.model;

public class Evento {
    private String id;
    private String name;
    private String description;

    public Evento() {
        // costruttore vuoto per serializzazione/deserializzazione
    }

    public Evento(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Getter e setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // toString utile per debug
    @Override
    public String toString() {
        return "Organization{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

package it.sal.disco.unimib.avemanager.data.model.request;

public class OrganizationDTO {
    private String ORG_ID;
    private String ORG_NAME = "";
    private boolean ORG_ACTIVE;

    // Costruttore vuoto (necessario per librerie di serializzazione come Gson o Jackson)
    public OrganizationDTO() {}

    // Costruttore con parametri
    public OrganizationDTO(String ORG_ID, String ORG_NAME) {
        this.ORG_ID = ORG_ID;
        this.ORG_NAME = ORG_NAME;
    }

    // Getter e Setter
    public String getORG_ID() {
        return ORG_ID;
    }

    public void setORG_ID(String ORG_ID) {
        this.ORG_ID = ORG_ID;
    }

    public String getORG_NAME() {
        return ORG_NAME;
    }

    public void setORG_NAME(String ORG_NAME) {
        this.ORG_NAME = ORG_NAME;
    }

    // Override toString (opzionale, utile per debug)
    @Override
    public String toString() {
        return "OrganizationDto{" +
                "ORG_ID='" + ORG_ID + '\'' +
                ", ORG_NAME='" + ORG_NAME + '\'' +
                '}';
    }

    public boolean isORG_ACTIVE() {
        return ORG_ACTIVE;
    }

    public void setORG_ACTIVE(boolean ORG_ACTIVE) {
        this.ORG_ACTIVE = ORG_ACTIVE;
    }
}
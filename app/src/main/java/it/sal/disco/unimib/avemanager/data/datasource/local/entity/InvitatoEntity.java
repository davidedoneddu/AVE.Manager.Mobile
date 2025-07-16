package it.sal.disco.unimib.avemanager.data.datasource.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "invitati")
public class InvitatoEntity {

    @ColumnInfo(name = "needs_sync")
    public boolean needsSync = true;

    @ColumnInfo(name = "sync_action")
    public String syncAction;

    @PrimaryKey
    @ColumnInfo(name = "inv_id")
    private int invId;

    @ColumnInfo(name = "inv_codice")
    private String invCodice;

    @ColumnInfo(name = "inv_cognome")
    private String invCognome;

    @ColumnInfo(name = "inv_nome")
    private String invNome;

    @ColumnInfo(name = "inv_eve_id")
    private Integer invEveId;

    @ColumnInfo(name = "inv_cortesia")
    private String invCortesia;

    @ColumnInfo(name = "inv_titolo")
    private String invTitolo;

    @ColumnInfo(name = "inv_vot_id")
    private Integer invVotId;

    @ColumnInfo(name = "inv_societa")
    private String invSocieta;

    @ColumnInfo(name = "inv_rag_soc")
    private String invRagSoc;

    @ColumnInfo(name = "inv_indirizzo")
    private String invIndirizzo;

    @ColumnInfo(name = "inv_cap")
    private String invCap;

    @ColumnInfo(name = "inv_citta")
    private String invCitta;

    @ColumnInfo(name = "inv_provincia")
    private String invProvincia;

    @ColumnInfo(name = "inv_grp_codice")
    private String invGrpCodice;

    @ColumnInfo(name = "inv_direzione")
    private String invDirezione;

    @ColumnInfo(name = "inv_invitato")
    private Boolean invInvitato;

    @ColumnInfo(name = "inv_confermato")
    private Boolean invConfermato;

    @ColumnInfo(name = "inv_presente")
    private Boolean invPresente;

    @ColumnInfo(name = "inv_user_modifica")
    private String invUserModifica;

    @ColumnInfo(name = "inv_non_partecipa")
    private Boolean invNonPartecipa;

    @ColumnInfo(name = "inv_note")
    private String invNote;

    @ColumnInfo(name = "inv_dt_checkin")
    private String invDtCheckin;

    @ColumnInfo(name = "inv_email")
    private String invEmail;

    @ColumnInfo(name = "inv_web_code")
    private String invWebCode;

    @ColumnInfo(name = "inv_web_invitato")
    private Boolean invWebInvitato;

    @ColumnInfo(name = "inv_web_riscontro")
    private Boolean invWebRiscontro;

    @ColumnInfo(name = "inv_web_dtins_ris")
    private String invWebDtinsRis;

    @ColumnInfo(name = "inv_web_dtupd_ris")
    private String invWebDtupdRis;

    @ColumnInfo(name = "inv_email_originale")
    private String invEmailOriginale;

    @ColumnInfo(name = "inv_badge_spedito")
    private Boolean invBadgeSpedito;

    @ColumnInfo(name = "inv_save_the_date")
    private Boolean invSaveTheDate;

    @ColumnInfo(name = "inv_dt_badge_stampato")
    private String invDtBadgeStampato;

    @ColumnInfo(name = "inv_dt_giorni_passati")
    private Integer invDtGiorniPassati;

    @ColumnInfo(name = "inv_dt_save_the_date")
    private String invDtSaveTheDate;

    @ColumnInfo(name = "inv_no_assegnaposto")
    private Boolean invNoAssegnaposto;

    @ColumnInfo(name = "inv_accompagnatore")
    private Boolean invAccompagnatore;

    @ColumnInfo(name = "inv_busta")
    private Boolean invBusta;

    @ColumnInfo(name = "inv_carica")
    private String invCarica;

    @ColumnInfo(name = "inv_dt_checkout")
    private String invDtCheckout;

    @ColumnInfo(name = "inv_ex_posto_occupato")
    private String invExPostoOccupato;

    @ColumnInfo(name = "inv_cena")
    private Boolean invCena;

    @ColumnInfo(name = "inv_sesso")
    private String invSesso;

    @ColumnInfo(name = "inv_rag_soc_badge1")
    private String invRagSocBadge1;

    @ColumnInfo(name = "inv_rag_soc_badge2")
    private String invRagSocBadge2;

    @ColumnInfo(name = "inv_votante")
    private Boolean invVotante;

    @ColumnInfo(name = "inv_puo_essere_sostituito")
    private Boolean invPuoEssereSostituito;

    @ColumnInfo(name = "inv_puo_invitare")
    private Boolean invPuoInvitare;

    @ColumnInfo(name = "inv_download_doc")
    private Boolean invDownloadDoc;

    @ColumnInfo(name = "inv_voti_spettanti")
    private Integer invVotiSpettanti;

    @ColumnInfo(name = "inv_sostituisce_inv_id")
    private Integer invSostituisceInvId;

    @ColumnInfo(name = "inv_modello_email")
    private String invModelloEmail;

    @ColumnInfo(name = "inv_email_biglietto")
    private Boolean invEmailBiglietto;

    @ColumnInfo(name = "inv_barcode")
    private String invBarcode;

    @ColumnInfo(name = "inv_associazione_gestione")
    private String invAssociazioneGestione;

    @ColumnInfo(name = "inv_coptato")
    private Boolean invCoptato;

    @ColumnInfo(name = "inv_amministratore_sistema")
    private Boolean invAmministratoreSistema;

    @ColumnInfo(name = "inv_scrutinatore")
    private Boolean invScrutinatore;

    @ColumnInfo(name = "inv_vedi_quorum")
    private Boolean invVediQuorum;

    @ColumnInfo(name = "inv_vedi_votazione_runtime")
    private Boolean invVediVotazioneRuntime;

    @ColumnInfo(name = "inv_videocall")
    private Boolean invVideocall;

    @ColumnInfo(name = "inv_web_code_orig")
    private String invWebCodeOrig;

    @ColumnInfo(name = "inv_puo_fare_scelta_a")
    private Boolean invPuoFareSceltaA;

    @ColumnInfo(name = "inv_scelta_a")
    private Boolean invSceltaA;

    @ColumnInfo(name = "inv_info_scelta_a")
    private String invInfoSceltaA;

    @ColumnInfo(name = "inv_puo_fare_scelta_b")
    private Boolean invPuoFareSceltaB;

    @ColumnInfo(name = "inv_scelta_b")
    private Boolean invSceltaB;

    @ColumnInfo(name = "inv_info_scelta_b")
    private String invInfoSceltaB;


    public InvitatoEntity() {

    }

    public int getInvId() { return invId; }
    public void setInvId(int invId) { this.invId = invId; }

    public String getInvCodice() { return invCodice; }
    public void setInvCodice(String invCodice) { this.invCodice = invCodice; }

    public String getInvCognome() { return invCognome; }
    public void setInvCognome(String invCognome) { this.invCognome = invCognome; }

    public String getInvNome() { return invNome; }
    public void setInvNome(String invNome) { this.invNome = invNome; }

    public Integer getInvEveId() { return invEveId; }
    public void setInvEveId(Integer invEveId) { this.invEveId = invEveId; }

    public String getInvCortesia() { return invCortesia; }
    public void setInvCortesia(String invCortesia) { this.invCortesia = invCortesia; }

    public String getInvTitolo() { return invTitolo; }
    public void setInvTitolo(String invTitolo) { this.invTitolo = invTitolo; }

    public Integer getInvVotId() { return invVotId; }
    public void setInvVotId(Integer invVotId) { this.invVotId = invVotId; }

    public String getInvSocieta() { return invSocieta; }
    public void setInvSocieta(String invSocieta) { this.invSocieta = invSocieta; }

    public String getInvRagSoc() { return invRagSoc; }
    public void setInvRagSoc(String invRagSoc) { this.invRagSoc = invRagSoc; }

    public String getInvIndirizzo() { return invIndirizzo; }
    public void setInvIndirizzo(String invIndirizzo) { this.invIndirizzo = invIndirizzo; }

    public String getInvCap() { return invCap; }
    public void setInvCap(String invCap) { this.invCap = invCap; }

    public String getInvCitta() { return invCitta; }
    public void setInvCitta(String invCitta) { this.invCitta = invCitta; }

    public String getInvProvincia() { return invProvincia; }
    public void setInvProvincia(String invProvincia) { this.invProvincia = invProvincia; }

    public String getInvGrpCodice() { return invGrpCodice; }
    public void setInvGrpCodice(String invGrpCodice) { this.invGrpCodice = invGrpCodice; }

    public String getInvDirezione() { return invDirezione; }
    public void setInvDirezione(String invDirezione) { this.invDirezione = invDirezione; }

    public Boolean getInvInvitato() { return invInvitato; }
    public void setInvInvitato(Boolean invInvitato) { this.invInvitato = invInvitato; }

    public Boolean getInvConfermato() { return invConfermato; }
    public void setInvConfermato(Boolean invConfermato) { this.invConfermato = invConfermato; }

    public Boolean getInvPresente() { return invPresente; }
    public void setInvPresente(Boolean invPresente) { this.invPresente = invPresente; }

    public String getInvUserModifica() { return invUserModifica; }
    public void setInvUserModifica(String invUserModifica) { this.invUserModifica = invUserModifica; }

    public Boolean getInvNonPartecipa() { return invNonPartecipa; }
    public void setInvNonPartecipa(Boolean invNonPartecipa) { this.invNonPartecipa = invNonPartecipa; }

    public String getInvNote() { return invNote; }
    public void setInvNote(String invNote) { this.invNote = invNote; }

    public String getInvDtCheckin() { return invDtCheckin; }
    public void setInvDtCheckin(String invDtCheckin) { this.invDtCheckin = invDtCheckin; }

    public String getInvEmail() { return invEmail; }
    public void setInvEmail(String invEmail) { this.invEmail = invEmail; }

    public String getInvWebCode() { return invWebCode; }
    public void setInvWebCode(String invWebCode) { this.invWebCode = invWebCode; }

    public Boolean getInvWebInvitato() { return invWebInvitato; }
    public void setInvWebInvitato(Boolean invWebInvitato) { this.invWebInvitato = invWebInvitato; }

    public Boolean getInvWebRiscontro() { return invWebRiscontro; }
    public void setInvWebRiscontro(Boolean invWebRiscontro) { this.invWebRiscontro = invWebRiscontro; }

    public String getInvWebDtinsRis() { return invWebDtinsRis; }
    public void setInvWebDtinsRis(String invWebDtinsRis) { this.invWebDtinsRis = invWebDtinsRis; }

    public String getInvWebDtupdRis() { return invWebDtupdRis; }
    public void setInvWebDtupdRis(String invWebDtupdRis) { this.invWebDtupdRis = invWebDtupdRis; }

    public String getInvEmailOriginale() { return invEmailOriginale; }
    public void setInvEmailOriginale(String invEmailOriginale) { this.invEmailOriginale = invEmailOriginale; }

    public Boolean getInvBadgeSpedito() { return invBadgeSpedito; }
    public void setInvBadgeSpedito(Boolean invBadgeSpedito) { this.invBadgeSpedito = invBadgeSpedito; }

    public Boolean getInvSaveTheDate() { return invSaveTheDate; }
    public void setInvSaveTheDate(Boolean invSaveTheDate) { this.invSaveTheDate = invSaveTheDate; }

    public String getInvDtBadgeStampato() { return invDtBadgeStampato; }
    public void setInvDtBadgeStampato(String invDtBadgeStampato) { this.invDtBadgeStampato = invDtBadgeStampato; }

    public Integer getInvDtGiorniPassati() { return invDtGiorniPassati; }
    public void setInvDtGiorniPassati(Integer invDtGiorniPassati) { this.invDtGiorniPassati = invDtGiorniPassati; }

    public String getInvDtSaveTheDate() { return invDtSaveTheDate; }
    public void setInvDtSaveTheDate(String invDtSaveTheDate) { this.invDtSaveTheDate = invDtSaveTheDate; }

    public Boolean getInvNoAssegnaposto() { return invNoAssegnaposto; }
    public void setInvNoAssegnaposto(Boolean invNoAssegnaposto) { this.invNoAssegnaposto = invNoAssegnaposto; }

    public Boolean getInvAccompagnatore() { return invAccompagnatore; }
    public void setInvAccompagnatore(Boolean invAccompagnatore) { this.invAccompagnatore = invAccompagnatore; }

    public Boolean getInvBusta() { return invBusta; }
    public void setInvBusta(Boolean invBusta) { this.invBusta = invBusta; }

    public String getInvCarica() { return invCarica; }
    public void setInvCarica(String invCarica) { this.invCarica = invCarica; }

    public String getInvDtCheckout() { return invDtCheckout; }
    public void setInvDtCheckout(String invDtCheckout) { this.invDtCheckout = invDtCheckout; }

    public String getInvExPostoOccupato() { return invExPostoOccupato; }
    public void setInvExPostoOccupato(String invExPostoOccupato) { this.invExPostoOccupato = invExPostoOccupato; }

    public Boolean getInvCena() { return invCena; }
    public void setInvCena(Boolean invCena) { this.invCena = invCena; }

    public String getInvSesso() { return invSesso; }
    public void setInvSesso(String invSesso) { this.invSesso = invSesso; }

    public String getInvRagSocBadge1() { return invRagSocBadge1; }
    public void setInvRagSocBadge1(String invRagSocBadge1) { this.invRagSocBadge1 = invRagSocBadge1; }

    public String getInvRagSocBadge2() { return invRagSocBadge2; }
    public void setInvRagSocBadge2(String invRagSocBadge2) { this.invRagSocBadge2 = invRagSocBadge2; }

    public Boolean getInvVotante() { return invVotante; }
    public void setInvVotante(Boolean invVotante) { this.invVotante = invVotante; }

    public Boolean getInvPuoEssereSostituito() { return invPuoEssereSostituito; }
    public void setInvPuoEssereSostituito(Boolean invPuoEssereSostituito) { this.invPuoEssereSostituito = invPuoEssereSostituito; }

    public Boolean getInvPuoInvitare() { return invPuoInvitare; }
    public void setInvPuoInvitare(Boolean invPuoInvitare) { this.invPuoInvitare = invPuoInvitare; }

    public Boolean getInvDownloadDoc() { return invDownloadDoc; }
    public void setInvDownloadDoc(Boolean invDownloadDoc) { this.invDownloadDoc = invDownloadDoc; }

    public Integer getInvVotiSpettanti() { return invVotiSpettanti; }
    public void setInvVotiSpettanti(Integer invVotiSpettanti) { this.invVotiSpettanti = invVotiSpettanti; }

    public Integer getInvSostituisceInvId() { return invSostituisceInvId; }
    public void setInvSostituisceInvId(Integer invSostituisceInvId) { this.invSostituisceInvId = invSostituisceInvId; }

    public String getInvModelloEmail() { return invModelloEmail; }
    public void setInvModelloEmail(String invModelloEmail) { this.invModelloEmail = invModelloEmail; }

    public Boolean getInvEmailBiglietto() { return invEmailBiglietto; }
    public void setInvEmailBiglietto(Boolean invEmailBiglietto) { this.invEmailBiglietto = invEmailBiglietto; }

    public String getInvBarcode() { return invBarcode; }
    public void setInvBarcode(String invBarcode) { this.invBarcode = invBarcode; }

    public String getInvAssociazioneGestione() { return invAssociazioneGestione; }
    public void setInvAssociazioneGestione(String invAssociazioneGestione) { this.invAssociazioneGestione = invAssociazioneGestione; }

    public Boolean getInvCoptato() { return invCoptato; }
    public void setInvCoptato(Boolean invCoptato) { this.invCoptato = invCoptato; }

    public Boolean getInvAmministratoreSistema() { return invAmministratoreSistema; }
    public void setInvAmministratoreSistema(Boolean invAmministratoreSistema) { this.invAmministratoreSistema = invAmministratoreSistema; }

    public Boolean getInvScrutinatore() { return invScrutinatore; }
    public void setInvScrutinatore(Boolean invScrutinatore) { this.invScrutinatore = invScrutinatore; }

    public Boolean getInvVediQuorum() { return invVediQuorum; }
    public void setInvVediQuorum(Boolean invVediQuorum) { this.invVediQuorum = invVediQuorum; }

    public Boolean getInvVediVotazioneRuntime() { return invVediVotazioneRuntime; }
    public void setInvVediVotazioneRuntime(Boolean invVediVotazioneRuntime) { this.invVediVotazioneRuntime = invVediVotazioneRuntime; }

    public Boolean getInvVideocall() { return invVideocall; }
    public void setInvVideocall(Boolean invVideocall) { this.invVideocall = invVideocall; }

    public String getInvWebCodeOrig() { return invWebCodeOrig; }
    public void setInvWebCodeOrig(String invWebCodeOrig) { this.invWebCodeOrig = invWebCodeOrig; }

    public Boolean getInvPuoFareSceltaA() { return invPuoFareSceltaA; }
    public void setInvPuoFareSceltaA(Boolean invPuoFareSceltaA) { this.invPuoFareSceltaA = invPuoFareSceltaA; }

    public Boolean getInvSceltaA() { return invSceltaA; }
    public void setInvSceltaA(Boolean invSceltaA) { this.invSceltaA = invSceltaA; }

    public String getInvInfoSceltaA() { return invInfoSceltaA; }
    public void setInvInfoSceltaA(String invInfoSceltaA) { this.invInfoSceltaA = invInfoSceltaA; }

    public Boolean getInvPuoFareSceltaB() { return invPuoFareSceltaB; }
    public void setInvPuoFareSceltaB(Boolean invPuoFareSceltaB) { this.invPuoFareSceltaB = invPuoFareSceltaB; }

    public Boolean getInvSceltaB() { return invSceltaB; }
    public void setInvSceltaB(Boolean invSceltaB) { this.invSceltaB = invSceltaB; }

    public String getInvInfoSceltaB() { return invInfoSceltaB; }
    public void setInvInfoSceltaB(String invInfoSceltaB) { this.invInfoSceltaB = invInfoSceltaB; }


}

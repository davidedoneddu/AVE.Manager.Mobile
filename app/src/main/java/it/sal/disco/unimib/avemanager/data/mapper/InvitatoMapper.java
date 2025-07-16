package it.sal.disco.unimib.avemanager.data.mapper;


import it.sal.disco.unimib.avemanager.data.model.request.InvitatoDTO;
import it.sal.disco.unimib.avemanager.ui.model.Invitato;

public class InvitatoMapper implements Mapper<InvitatoDTO, Invitato>{

    @Override
    public Invitato toModel(InvitatoDTO dto) {
        if (dto == null) return null;
        Invitato model = new Invitato();

        model.setInvId(dto.getINV_ID());
        model.setInvCodice(dto.getINV_CODICE());
        model.setInvCognome(dto.getINV_COGNOME());
        model.setInvNome(dto.getINV_NOME());
        model.setInvEveId(dto.getINV_EVE_ID());
        model.setInvCortesia(dto.getINV_CORTESIA());
        model.setInvTitolo(dto.getINV_TITOLO());
        model.setInvVotId(dto.getINV_VOT_ID());
        model.setInvSocieta(dto.getINV_SOCIETA());
        model.setInvRagSoc(dto.getINV_RAGSOC());
        model.setInvIndirizzo(dto.getINV_INDIRIZZO());
        model.setInvCap(dto.getINV_CAP());
        model.setInvCitta(dto.getINV_CITTA());
        model.setInvProvincia(dto.getINV_PROVINCIA());
        model.setInvGrpCodice(dto.getINV_GRP_CODICE());
        model.setInvDirezione(dto.getINV_DIREZIONE());
        model.setInvInvitato(dto.getINV_INVITATO());
        model.setInvConfermato(dto.getINV_CONFERMATO());
        model.setInvPresente(dto.getINV_PRESENTE());
        model.setInvUserModifica(dto.getINV_USER_MODIFICA());
        model.setInvNonPartecipa(dto.getINV_NON_PARTECIPA());
        model.setInvNote(dto.getINV_NOTE());
        model.setInvDtCheckin(dto.getINV_DT_CHECKIN());
        model.setInvEmail(dto.getINV_EMAIL());
        model.setInvWebCode(dto.getINV_WEB_CODE());
        model.setInvWebInvitato(dto.getINV_WEB_INVITATO());
        model.setInvWebRiscontro(dto.getINV_WEB_RISCONTRO());
        model.setInvWebDtinsRis(dto.getINV_WEB_DTINS_RIS());
        model.setInvWebDtupdRis(dto.getINV_WEB_DTUPD_RIS());
        model.setInvEmailOriginale(dto.getINV_EMAIL_ORIGINALE());
        model.setInvBadgeSpedito(dto.getINV_BADGE_SPEDITO());
        model.setInvSaveTheDate(dto.getINV_SAVETHEDATE());
        model.setInvDtBadgeStampato(dto.getINV_DT_BADGE_STAMPATO());
        model.setInvDtGiorniPassati(dto.getINV_DT_GIORNI_PASSATI());
        model.setInvDtSaveTheDate(dto.getINV_DT_SAVETHEDATE());
        model.setInvNoAssegnaposto(dto.getINV_NO_ASSEGNAPOSTO());
        model.setInvAccompagnatore(dto.getINV_ACCOMPAGNATORE());
        model.setInvBusta(dto.getINV_BUSTA());
        model.setInvCarica(dto.getINV_CARICA());
        model.setInvDtCheckout(dto.getINV_DT_CHECKOUT());
        model.setInvExPostoOccupato(dto.getINV_EX_POSTO_OCCUPATO());
        model.setInvCena(dto.getINV_CENA());
        model.setInvSesso(dto.getINV_SESSO());
        model.setInvRagSocBadge1(dto.getINV_RAGSOC_BADGE1());
        model.setInvRagSocBadge2(dto.getINV_RAGSOC_BADGE2());
        model.setInvVotante(dto.getINV_VOTANTE());
        model.setInvPuoEssereSostituito(dto.getINV_PUO_ESSERE_SOSTITUITO());
        model.setInvPuoInvitare(dto.getINV_PUO_INVITARE());
        model.setInvDownloadDoc(dto.getINV_DOWNLOAD_DOC());
        model.setInvVotiSpettanti(dto.getINV_VOTI_SPETTANTI());
        model.setInvSostituisceInvId(dto.getINV_SOSTITUISCE_INV_ID());
        model.setInvModelloEmail(dto.getINV_MODELLO_EMAIL());
        model.setInvEmailBiglietto(dto.getINV_EMAIL_BIGLIETTO());
        model.setInvBarcode(dto.getINV_BARCODE());
        model.setInvAssociazioneGestione(dto.getINV_ASSOCIAZIONE_GESTIONE());
        model.setInvCoptato(dto.getINV_COPTATO());
        model.setInvAmministratoreSistema(dto.getINV_AMMINISTRATORE_SISTEMA());
        model.setInvScrutinatore(dto.getINV_SCRUTINATORE());
        model.setInvVediQuorum(dto.getINV_VEDI_QUORUM());
        model.setInvVediVotazioneRuntime(dto.getINV_VEDI_VOTAZIONE_RUNTIME());
        model.setInvVideocall(dto.getINV_VIDEOCALL());
        model.setInvWebCodeOrig(dto.getINV_WEB_CODEORIG());
        model.setInvPuoFareSceltaA(dto.getINV_PUO_FARE_SCELTA_A());
        model.setInvSceltaA(dto.getINV_SCELTA_A());
        model.setInvInfoSceltaA(dto.getINV_INFO_SCELTA_A());
        model.setInvPuoFareSceltaB(dto.getINV_PUO_FARE_SCELTA_B());
        model.setInvSceltaB(dto.getINV_SCELTA_B());
        model.setInvInfoSceltaB(dto.getINV_INFO_SCELTA_B());

        return model;
    }

    @Override
    public InvitatoDTO toDTO(Invitato model) {
        if (model == null) return null;
        InvitatoDTO dto = new InvitatoDTO();

        dto.setINV_ID(model.getInvId());
        dto.setINV_CODICE(model.getInvCodice());
        dto.setINV_COGNOME(model.getInvCognome());
        dto.setINV_NOME(model.getInvNome());
        dto.setINV_EVE_ID(model.getInvEveId());
        dto.setINV_CORTESIA(model.getInvCortesia());
        dto.setINV_TITOLO(model.getInvTitolo());
        dto.setINV_VOT_ID(model.getInvVotId());
        dto.setINV_SOCIETA(model.getInvSocieta());
        dto.setINV_RAGSOC(model.getInvRagSoc());
        dto.setINV_INDIRIZZO(model.getInvIndirizzo());
        dto.setINV_CAP(model.getInvCap());
        dto.setINV_CITTA(model.getInvCitta());
        dto.setINV_PROVINCIA(model.getInvProvincia());
        dto.setINV_GRP_CODICE(model.getInvGrpCodice());
        dto.setINV_DIREZIONE(model.getInvDirezione());
        dto.setINV_INVITATO(model.getInvInvitato());
        dto.setINV_CONFERMATO(model.getInvConfermato());
        dto.setINV_PRESENTE(model.getInvPresente());
        dto.setINV_USER_MODIFICA(model.getInvUserModifica());
        dto.setINV_NON_PARTECIPA(model.getInvNonPartecipa());
        dto.setINV_NOTE(model.getInvNote());
        dto.setINV_DT_CHECKIN(model.getInvDtCheckin());
        dto.setINV_EMAIL(model.getInvEmail());
        dto.setINV_WEB_CODE(model.getInvWebCode());
        dto.setINV_WEB_INVITATO(model.getInvWebInvitato());
        dto.setINV_WEB_RISCONTRO(model.getInvWebRiscontro());
        dto.setINV_WEB_DTINS_RIS(model.getInvWebDtinsRis());
        dto.setINV_WEB_DTUPD_RIS(model.getInvWebDtupdRis());
        dto.setINV_EMAIL_ORIGINALE(model.getInvEmailOriginale());
        dto.setINV_BADGE_SPEDITO(model.getInvBadgeSpedito());
        dto.setINV_SAVETHEDATE(model.getInvSaveTheDate());
        dto.setINV_DT_BADGE_STAMPATO(model.getInvDtBadgeStampato());
        dto.setINV_DT_GIORNI_PASSATI(model.getInvDtGiorniPassati());
        dto.setINV_DT_SAVETHEDATE(model.getInvDtSaveTheDate());
        dto.setINV_NO_ASSEGNAPOSTO(model.getInvNoAssegnaposto());
        dto.setINV_ACCOMPAGNATORE(model.getInvAccompagnatore());
        dto.setINV_BUSTA(model.getInvBusta());
        dto.setINV_CARICA(model.getInvCarica());
        dto.setINV_DT_CHECKOUT(model.getInvDtCheckout());
        dto.setINV_EX_POSTO_OCCUPATO(model.getInvExPostoOccupato());
        dto.setINV_CENA(model.getInvCena());
        dto.setINV_SESSO(model.getInvSesso());
        dto.setINV_RAGSOC_BADGE1(model.getInvRagSocBadge1());
        dto.setINV_RAGSOC_BADGE2(model.getInvRagSocBadge2());
        dto.setINV_VOTANTE(model.getInvVotante());
        dto.setINV_PUO_ESSERE_SOSTITUITO(model.getInvPuoEssereSostituito());
        dto.setINV_PUO_INVITARE(model.getInvPuoInvitare());
        dto.setINV_DOWNLOAD_DOC(model.getInvDownloadDoc());
        dto.setINV_VOTI_SPETTANTI(model.getInvVotiSpettanti());
        dto.setINV_SOSTITUISCE_INV_ID(model.getInvSostituisceInvId());
        dto.setINV_MODELLO_EMAIL(model.getInvModelloEmail());
        dto.setINV_EMAIL_BIGLIETTO(model.getInvEmailBiglietto());
        dto.setINV_BARCODE(model.getInvBarcode());
        dto.setINV_ASSOCIAZIONE_GESTIONE(model.getInvAssociazioneGestione());
        dto.setINV_COPTATO(model.getInvCoptato());
        dto.setINV_AMMINISTRATORE_SISTEMA(model.getInvAmministratoreSistema());
        dto.setINV_SCRUTINATORE(model.getInvScrutinatore());
        dto.setINV_VEDI_QUORUM(model.getInvVediQuorum());
        dto.setINV_VEDI_VOTAZIONE_RUNTIME(model.getInvVediVotazioneRuntime());
        dto.setINV_VIDEOCALL(model.getInvVideocall());
        dto.setINV_WEB_CODEORIG(model.getInvWebCodeOrig());
        dto.setINV_PUO_FARE_SCELTA_A(model.getInvPuoFareSceltaA());
        dto.setINV_SCELTA_A(model.getInvSceltaA());
        dto.setINV_INFO_SCELTA_A(model.getInvInfoSceltaA());
        dto.setINV_PUO_FARE_SCELTA_B(model.getInvPuoFareSceltaB());
        dto.setINV_SCELTA_B(model.getInvSceltaB());
        dto.setINV_INFO_SCELTA_B(model.getInvInfoSceltaB());

        return dto;
    }
}

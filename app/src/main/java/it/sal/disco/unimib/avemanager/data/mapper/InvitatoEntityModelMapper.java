package it.sal.disco.unimib.avemanager.data.mapper;

import java.util.ArrayList;
import java.util.List;

import it.sal.disco.unimib.avemanager.data.datasource.local.entity.InvitatoEntity;
import it.sal.disco.unimib.avemanager.ui.model.Invitato;

public class InvitatoEntityModelMapper {

    public static Invitato toModel(InvitatoEntity entity) {
        if (entity == null) return null;
        Invitato model = new Invitato();

        model.setInvId(entity.getInvId());
        model.setInvCodice(entity.getInvCodice());
        model.setInvCognome(entity.getInvCognome());
        model.setInvNome(entity.getInvNome());
        model.setInvEveId(entity.getInvEveId());
        model.setInvCortesia(entity.getInvCortesia());
        model.setInvTitolo(entity.getInvTitolo());
        model.setInvVotId(entity.getInvVotId());
        model.setInvSocieta(entity.getInvSocieta());
        model.setInvRagSoc(entity.getInvRagSoc());
        model.setInvIndirizzo(entity.getInvIndirizzo());
        model.setInvCap(entity.getInvCap());
        model.setInvCitta(entity.getInvCitta());
        model.setInvProvincia(entity.getInvProvincia());
        model.setInvGrpCodice(entity.getInvGrpCodice());
        model.setInvDirezione(entity.getInvDirezione());
        model.setInvInvitato(entity.getInvInvitato());
        model.setInvConfermato(entity.getInvConfermato());
        model.setInvPresente(entity.getInvPresente());
        model.setInvUserModifica(entity.getInvUserModifica());
        model.setInvNonPartecipa(entity.getInvNonPartecipa());
        model.setInvNote(entity.getInvNote());
        model.setInvDtCheckin(entity.getInvDtCheckin());
        model.setInvEmail(entity.getInvEmail());
        model.setInvWebCode(entity.getInvWebCode());
        model.setInvWebInvitato(entity.getInvWebInvitato());
        model.setInvWebRiscontro(entity.getInvWebRiscontro());
        model.setInvWebDtinsRis(entity.getInvWebDtinsRis());
        model.setInvWebDtupdRis(entity.getInvWebDtupdRis());
        model.setInvEmailOriginale(entity.getInvEmailOriginale());
        model.setInvBadgeSpedito(entity.getInvBadgeSpedito());
        model.setInvSaveTheDate(entity.getInvSaveTheDate());
        model.setInvDtBadgeStampato(entity.getInvDtBadgeStampato());
        model.setInvDtGiorniPassati(entity.getInvDtGiorniPassati());
        model.setInvDtSaveTheDate(entity.getInvDtSaveTheDate());
        model.setInvNoAssegnaposto(entity.getInvNoAssegnaposto());
        model.setInvAccompagnatore(entity.getInvAccompagnatore());
        model.setInvBusta(entity.getInvBusta());
        model.setInvCarica(entity.getInvCarica());
        model.setInvDtCheckout(entity.getInvDtCheckout());
        model.setInvExPostoOccupato(entity.getInvExPostoOccupato());
        model.setInvCena(entity.getInvCena());
        model.setInvSesso(entity.getInvSesso());
        model.setInvRagSocBadge1(entity.getInvRagSocBadge1());
        model.setInvRagSocBadge2(entity.getInvRagSocBadge2());
        model.setInvVotante(entity.getInvVotante());
        model.setInvPuoEssereSostituito(entity.getInvPuoEssereSostituito());
        model.setInvPuoInvitare(entity.getInvPuoInvitare());
        model.setInvDownloadDoc(entity.getInvDownloadDoc());
        model.setInvVotiSpettanti(entity.getInvVotiSpettanti());
        model.setInvSostituisceInvId(entity.getInvSostituisceInvId());
        model.setInvModelloEmail(entity.getInvModelloEmail());
        model.setInvEmailBiglietto(entity.getInvEmailBiglietto());
        model.setInvBarcode(entity.getInvBarcode());
        model.setInvAssociazioneGestione(entity.getInvAssociazioneGestione());
        model.setInvCoptato(entity.getInvCoptato());
        model.setInvAmministratoreSistema(entity.getInvAmministratoreSistema());
        model.setInvScrutinatore(entity.getInvScrutinatore());
        model.setInvVediQuorum(entity.getInvVediQuorum());
        model.setInvVediVotazioneRuntime(entity.getInvVediVotazioneRuntime());
        model.setInvVideocall(entity.getInvVideocall());
        model.setInvWebCodeOrig(entity.getInvWebCodeOrig());
        model.setInvPuoFareSceltaA(entity.getInvPuoFareSceltaA());
        model.setInvSceltaA(entity.getInvSceltaA());
        model.setInvInfoSceltaA(entity.getInvInfoSceltaA());
        model.setInvPuoFareSceltaB(entity.getInvPuoFareSceltaB());
        model.setInvSceltaB(entity.getInvSceltaB());
        model.setInvInfoSceltaB(entity.getInvInfoSceltaB());

        return model;
    }

    public static InvitatoEntity toEntity(Invitato model) {
        if (model == null) return null;
        InvitatoEntity entity = new InvitatoEntity();

        entity.setInvId(model.getInvId());
        entity.setInvCodice(model.getInvCodice());
        entity.setInvCognome(model.getInvCognome());
        entity.setInvNome(model.getInvNome());
        entity.setInvEveId(model.getInvEveId());
        entity.setInvCortesia(model.getInvCortesia());
        entity.setInvTitolo(model.getInvTitolo());
        entity.setInvVotId(model.getInvVotId());
        entity.setInvSocieta(model.getInvSocieta());
        entity.setInvRagSoc(model.getInvRagSoc());
        entity.setInvIndirizzo(model.getInvIndirizzo());
        entity.setInvCap(model.getInvCap());
        entity.setInvCitta(model.getInvCitta());
        entity.setInvProvincia(model.getInvProvincia());
        entity.setInvGrpCodice(model.getInvGrpCodice());
        entity.setInvDirezione(model.getInvDirezione());
        entity.setInvInvitato(model.getInvInvitato());
        entity.setInvConfermato(model.getInvConfermato());
        entity.setInvPresente(model.getInvPresente());
        entity.setInvUserModifica(model.getInvUserModifica());
        entity.setInvNonPartecipa(model.getInvNonPartecipa());
        entity.setInvNote(model.getInvNote());
        entity.setInvDtCheckin(model.getInvDtCheckin());
        entity.setInvEmail(model.getInvEmail());
        entity.setInvWebCode(model.getInvWebCode());
        entity.setInvWebInvitato(model.getInvWebInvitato());
        entity.setInvWebRiscontro(model.getInvWebRiscontro());
        entity.setInvWebDtinsRis(model.getInvWebDtinsRis());
        entity.setInvWebDtupdRis(model.getInvWebDtupdRis());
        entity.setInvEmailOriginale(model.getInvEmailOriginale());
        entity.setInvBadgeSpedito(model.getInvBadgeSpedito());
        entity.setInvSaveTheDate(model.getInvSaveTheDate());
        entity.setInvDtBadgeStampato(model.getInvDtBadgeStampato());
        entity.setInvDtGiorniPassati(model.getInvDtGiorniPassati());
        entity.setInvDtSaveTheDate(model.getInvDtSaveTheDate());
        entity.setInvNoAssegnaposto(model.getInvNoAssegnaposto());
        entity.setInvAccompagnatore(model.getInvAccompagnatore());
        entity.setInvBusta(model.getInvBusta());
        entity.setInvCarica(model.getInvCarica());
        entity.setInvDtCheckout(model.getInvDtCheckout());
        entity.setInvExPostoOccupato(model.getInvExPostoOccupato());
        entity.setInvCena(model.getInvCena());
        entity.setInvSesso(model.getInvSesso());
        entity.setInvRagSocBadge1(model.getInvRagSocBadge1());
        entity.setInvRagSocBadge2(model.getInvRagSocBadge2());
        entity.setInvVotante(model.getInvVotante());
        entity.setInvPuoEssereSostituito(model.getInvPuoEssereSostituito());
        entity.setInvPuoInvitare(model.getInvPuoInvitare());
        entity.setInvDownloadDoc(model.getInvDownloadDoc());
        entity.setInvVotiSpettanti(model.getInvVotiSpettanti());
        entity.setInvSostituisceInvId(model.getInvSostituisceInvId());
        entity.setInvModelloEmail(model.getInvModelloEmail());
        entity.setInvEmailBiglietto(model.getInvEmailBiglietto());
        entity.setInvBarcode(model.getInvBarcode());
        entity.setInvAssociazioneGestione(model.getInvAssociazioneGestione());
        entity.setInvCoptato(model.getInvCoptato());
        entity.setInvAmministratoreSistema(model.getInvAmministratoreSistema());
        entity.setInvScrutinatore(model.getInvScrutinatore());
        entity.setInvVediQuorum(model.getInvVediQuorum());
        entity.setInvVediVotazioneRuntime(model.getInvVediVotazioneRuntime());
        entity.setInvVideocall(model.getInvVideocall());
        entity.setInvWebCodeOrig(model.getInvWebCodeOrig());
        entity.setInvPuoFareSceltaA(model.getInvPuoFareSceltaA());
        entity.setInvSceltaA(model.getInvSceltaA());
        entity.setInvInfoSceltaA(model.getInvInfoSceltaA());
        entity.setInvPuoFareSceltaB(model.getInvPuoFareSceltaB());
        entity.setInvSceltaB(model.getInvSceltaB());
        entity.setInvInfoSceltaB(model.getInvInfoSceltaB());

        return entity;
    }

    // Mapper per lista Entity -> lista Model
    public static List<Invitato> toModelList(List<InvitatoEntity> entities) {
        List<Invitato> models = new ArrayList<>();
        if (entities != null) {
            for (InvitatoEntity entity : entities) {
                models.add(toModel(entity));
            }
        }
        return models;
    }

    // Mapper per lista Model -> lista Entity
    public static List<InvitatoEntity> toEntityList(List<Invitato> models) {
        List<InvitatoEntity> entities = new ArrayList<>();
        if (models != null) {
            for (Invitato model : models) {
                entities.add(toEntity(model));
            }
        }
        return entities;
    }
}

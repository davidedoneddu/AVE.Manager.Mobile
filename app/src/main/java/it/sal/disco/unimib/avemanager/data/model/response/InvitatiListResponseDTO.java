package it.sal.disco.unimib.avemanager.data.model.response;

import java.util.List;

import it.sal.disco.unimib.avemanager.data.model.request.InvitatoDTO;

public class InvitatiListResponseDTO extends BaseResponseDTO {

    private List<InvitatoDTO> Invitati;

    public List<InvitatoDTO> getInvitati() {
        return Invitati;
    }

    public void setInvitati(List<InvitatoDTO> invitati) {
        Invitati = invitati;
    }
}

package it.sal.disco.unimib.avemanager.util;

import it.sal.disco.unimib.avemanager.ui.model.Invitato;

public interface OnInvitatoActionListener {
    void onEditClick(Invitato invitato);
    void onDeleteClick(Invitato invitato);
    void onSendEmailClick(Invitato invitato);
    void onInfoClick(Invitato invitato);
}

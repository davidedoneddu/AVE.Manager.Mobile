package it.sal.disco.unimib.avemanager.data.model.response;


import java.util.List;

import it.sal.disco.unimib.avemanager.data.model.request.EventDTO;

public class EventListResponseDTO extends BaseResponseDTO{

    private List<EventDTO> EventiList;

    public List<EventDTO> getEventiList() {
        return EventiList;
    }

    public void setEventiList(List<EventDTO> eventiList) {
        EventiList = eventiList;
    }
}

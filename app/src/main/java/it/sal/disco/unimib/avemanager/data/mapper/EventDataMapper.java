package it.sal.disco.unimib.avemanager.data.mapper;

import it.sal.disco.unimib.avemanager.data.model.response.EventDataResponseDTO;
import it.sal.disco.unimib.avemanager.ui.model.EventData;

public class EventDataMapper implements Mapper<EventDataResponseDTO, EventData> {
    @Override
    public EventData toModel(EventDataResponseDTO eventDataResponseDTO) {
        EventData model = new EventData();
        model.setEventId(eventDataResponseDTO.getEventId());
        model.setEventName(eventDataResponseDTO.getEventName());
        model.setEventDataQuorum(eventDataResponseDTO.getEventDataQuorum());
        model.setEventInvPresenti(eventDataResponseDTO.getEventInvPresenti());
        model.setEventInvTotali(eventDataResponseDTO.getEventInvTotali());
        model.setEventIsQuorumOk(eventDataResponseDTO.isEventIsQuorumOk());
        model.setEventPercQuorum(eventDataResponseDTO.getEventPercQuorum());
        model.setEventVotiInvPresenti(eventDataResponseDTO.getEventVotiInvPresenti());
        model.setEventVotiInvTotali(eventDataResponseDTO.getEventVotiInvTotali());
        model.setEventVotiPresenti(eventDataResponseDTO.getEventVotiPresenti());
        model.setEventVotiTotali(eventDataResponseDTO.getEventVotiTotali());

        return model;
    }

    @Override
    public EventDataResponseDTO toDTO(EventData eventData) {
        return new EventDataResponseDTO();
    }
}

package it.sal.disco.unimib.avemanager.data.mapper;

import it.sal.disco.unimib.avemanager.data.model.request.EventDTO;
import it.sal.disco.unimib.avemanager.ui.model.Evento;

public class EventoMapper implements Mapper<EventDTO, Evento> {

    @Override
    public Evento toModel(EventDTO dto) {
        if (dto == null) return null;
        Evento evento = new Evento();
        evento.setId(dto.getEventId());
        evento.setName(dto.getEventName());
        evento.setDescription("Privato");
        return evento;
    }

    @Override
    public EventDTO toDTO(Evento model) {
        if (model == null) return null;
        EventDTO dto = new EventDTO();
        dto.setEventId(model.getId());
        dto.setEventName(model.getName());
        return dto;
    }
}
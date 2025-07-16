package it.sal.disco.unimib.avemanager.data.mapper;

import java.util.ArrayList;
import java.util.List;

public interface Mapper<DTO, Model> {

    Model toModel(DTO dto);

    DTO toDTO(Model model);

    default List<Model> toModelList(List<DTO> dtoList) {
        if (dtoList == null) return new ArrayList<>();
        List<Model> list = new ArrayList<>();
        for (DTO dto : dtoList) {
            list.add(toModel(dto));
        }
        return list;
    }

    default List<DTO> toDTOList(List<Model> modelList) {
        if (modelList == null) return new ArrayList<>();
        List<DTO> list = new ArrayList<>();
        for (Model model : modelList) {
            list.add(toDTO(model));
        }
        return list;
    }
}


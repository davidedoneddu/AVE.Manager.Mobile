package it.sal.disco.unimib.avemanager.data.mapper;

import it.sal.disco.unimib.avemanager.data.model.request.OrganizationDTO;
import it.sal.disco.unimib.avemanager.ui.model.Organization;

public class OrganizationMapper implements Mapper<OrganizationDTO, Organization> {

    @Override
    public Organization toModel(OrganizationDTO dto) {
        if (dto == null) return null;
        return new Organization(
                dto.getORG_ID(),
                dto.getORG_NAME(),
                "/Images/"+dto.getORG_ID()+".png"
        );
    }

    @Override
    public OrganizationDTO toDTO(Organization model) {
        if (model == null) return null;
        OrganizationDTO dto = new OrganizationDTO();
        dto.setORG_ID(model.getId());
        dto.setORG_NAME(model.getName());
        return dto;
    }
}
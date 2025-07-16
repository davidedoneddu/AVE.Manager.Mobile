package it.sal.disco.unimib.avemanager.data.model.response;

import java.util.List;

import it.sal.disco.unimib.avemanager.data.model.request.OrganizationDTO;

public class OrganizationListResponseDTO extends BaseResponseDTO{

    private List<OrganizationDTO> organizationDTOList;

    public List<OrganizationDTO> getOrganizationDTOList() {
        return organizationDTOList;
    }

    public void setOrganizationDTOList(List<OrganizationDTO> organizationDTOList) {
        this.organizationDTOList = organizationDTOList;
    }
}

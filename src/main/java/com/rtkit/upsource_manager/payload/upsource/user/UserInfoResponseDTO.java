
package com.rtkit.upsource_manager.payload.upsource.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceResponse;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)

public class UserInfoResponseDTO extends ABaseUpsourceResponse {

    @JsonProperty("infos")
    private List<Info> infos = null;

    /**
     * No args constructor for use in serialization
     */
    public UserInfoResponseDTO() {
    }

    /**
     * @param infos
     */
    public UserInfoResponseDTO(List<Info> infos) {
        super();
        this.infos = infos;
    }

    @JsonProperty("infos")
    public List<Info> getInfos() {
        return infos;
    }

    @JsonProperty("infos")
    public void setInfos(List<Info> infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "UserInfoResponseDTO{" +
                "infos=" + infos +
                '}';
    }
}

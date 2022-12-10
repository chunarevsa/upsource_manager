package com.rtkit.upsource_manager.payload.api.response.userInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.Generated;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "infos"
})
@Generated("jsonschema2pojo")
public class Result {

    @JsonProperty("infos")
    private List<Info> infos = null;

    /**
     * No args constructor for use in serialization
     */
    public Result() {
    }

    /**
     * @param infos
     */
    public Result(List<Info> infos) {
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

}

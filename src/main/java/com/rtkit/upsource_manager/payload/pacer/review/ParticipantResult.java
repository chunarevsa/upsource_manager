package com.rtkit.upsource_manager.payload.pacer.review;

import java.util.List;

public class ParticipantResult {
    public List<Participant> infos;

    public List<Participant> getInfos() {
        return infos;
    }

    public void setInfos(List<Participant> infos) {
        this.infos = infos;
    }

    @Override
    public String toString() {
        return "ParticipantResult{" +
                "infos=" + infos +
                '}';
    }
}

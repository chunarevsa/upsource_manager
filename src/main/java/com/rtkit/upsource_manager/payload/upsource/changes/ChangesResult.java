package com.rtkit.upsource_manager.payload.upsource.changes;

// TODO: удалить?
public class ChangesResult {
    public RevisionsDiffDTO revisionsDiffDTO;
    public String annotation;
    public int resultKind;

    public RevisionsDiffDTO getDiff() {
        return revisionsDiffDTO;
    }

    public String getAnnotation() {
        return annotation;
    }

    public int getResultKind() {
        return resultKind;
    }
}

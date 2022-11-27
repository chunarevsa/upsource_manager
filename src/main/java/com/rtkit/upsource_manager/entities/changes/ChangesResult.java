package com.rtkit.upsource_manager.entities.changes;

public class ChangesResult {
	public Diff diff;
	public String annotation;
	public int resultKind;

	public Diff getDiff() {
		return diff;
	}

	public String getAnnotation() {
		return annotation;
	}

	public int getResultKind() {
		return resultKind;
	}
}

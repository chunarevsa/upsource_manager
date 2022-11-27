package com.rtkit.upsource_manager.entities.revision;

public class RevisionResult {
	public AllRevisions allRevisions;
	public NewRevisions newRevisions;
	public boolean hasMissingRevisions;
	public boolean canSquash;
	public boolean canTrackBranch;
	public String branchHint;

	public AllRevisions getAllRevisions() {
		return allRevisions;
	}

	public NewRevisions getNewRevisions() {
		return newRevisions;
	}

	public boolean isHasMissingRevisions() {
		return hasMissingRevisions;
	}

	public boolean isCanSquash() {
		return canSquash;
	}

	public boolean isCanTrackBranch() {
		return canTrackBranch;
	}

	public String getBranchHint() {
		return branchHint;
	}
}

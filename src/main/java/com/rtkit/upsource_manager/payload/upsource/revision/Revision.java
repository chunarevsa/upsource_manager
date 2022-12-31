package com.rtkit.upsource_manager.payload.upsource.revision;

import java.util.List;

public class Revision {
    public String projectId;
    public String revisionId;
    public Object revisionDate;
    public Object effectiveRevisionDate;
    public String revisionCommitMessage;
    public int state;
    public String vcsRevisionId;
    public String shortRevisionId;
    public String authorId;
    public int reachability;
    public List<String> parentRevisionIds;
    public List<String> branchHeadLabel;

    public String getProjectId() {
        return projectId;
    }

    public String getRevisionId() {
        return revisionId;
    }

    public Object getRevisionDate() {
        return revisionDate;
    }

    public Object getEffectiveRevisionDate() {
        return effectiveRevisionDate;
    }

    public String getRevisionCommitMessage() {
        return revisionCommitMessage;
    }

    public int getState() {
        return state;
    }

    public String getVcsRevisionId() {
        return vcsRevisionId;
    }

    public String getShortRevisionId() {
        return shortRevisionId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public int getReachability() {
        return reachability;
    }

    public List<String> getParentRevisionIds() {
        return parentRevisionIds;
    }

    public List<String> getBranchHeadLabel() {
        return branchHeadLabel;
    }


    @Override
    public String toString() {
        return "revisionId='" + revisionId + '\'';
    }
}

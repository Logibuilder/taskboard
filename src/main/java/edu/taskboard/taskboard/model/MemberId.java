package edu.taskboard.taskboard.model;

import java.io.Serializable;
import java.util.Objects;

// Clé composite pour Member
public class MemberId implements Serializable {
    private Long  member;
    private Long  project;

    public MemberId() {}

    public MemberId(Long  member, Long  project) {
        this.member = member;
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberId)) return false;
        MemberId that = (MemberId) o;
        return member == that.member && project == that.project;
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, project);
    }
}

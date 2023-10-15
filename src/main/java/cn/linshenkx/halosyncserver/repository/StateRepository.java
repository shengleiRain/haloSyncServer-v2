package cn.linshenkx.halosyncserver.repository;

public interface StateRepository {

    String getCommit();

    void setCommit(String commit);
}

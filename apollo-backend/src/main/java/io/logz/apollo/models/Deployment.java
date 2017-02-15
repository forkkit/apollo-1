package io.logz.apollo.models;

import java.util.Date;

/**
 * Created by roiravhon on 1/5/17.
 */
public class Deployment {

    public enum DeploymentStatus {
        PENDING,
        PENDING_CANCELLATION,
        STARTED,
        CANCELING,
        DONE,
        CANCELED
    }

    private int id;
    private int environmentId;
    private int serviceId;
    private int deployableVersionId;
    private String userEmail;
    private DeploymentStatus status;
    private String sourceVersion;
    private Date startedAt;
    private Date lastUpdate;

    public Deployment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(int environmentId) {
        this.environmentId = environmentId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getDeployableVersionId() {
        return deployableVersionId;
    }

    public void setDeployableVersionId(int deployableVersionId) {
        this.deployableVersionId = deployableVersionId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public DeploymentStatus getStatus() {
        return status;
    }

    public void setStatus(DeploymentStatus status) {
        this.status = status;
    }

    public String getSourceVersion() {
        return sourceVersion;
    }

    public void setSourceVersion(String sourceVersion) {
        this.sourceVersion = sourceVersion;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

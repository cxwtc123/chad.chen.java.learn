package com.cxw.azure.client;

/**
 * Define an Azure Instance
 */
public class Instance {
    public static enum Status {
        STARTED
    }

    private String privateIp;
    private String publicIp;
    private String publicPort;
    private Status status;
    private String name;

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getPublicPort() {
        return publicPort;
    }

    public void setPublicPort(String publicPort) {
        this.publicPort = publicPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Instance instance = (Instance) o;

        if (name != null ? !name.equals(instance.name) : instance.name != null) return false;
        if (privateIp != null ? !privateIp.equals(instance.privateIp) : instance.privateIp != null) return false;
        if (publicIp != null ? !publicIp.equals(instance.publicIp) : instance.publicIp != null) return false;
        if (publicPort != null ? !publicPort.equals(instance.publicPort) : instance.publicPort != null) return false;
        if (status != instance.status) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = privateIp != null ? privateIp.hashCode() : 0;
        result = 31 * result + (publicIp != null ? publicIp.hashCode() : 0);
        result = 31 * result + (publicPort != null ? publicPort.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Instance{");
        sb.append("privateIp='").append(privateIp).append('\'');
        sb.append(", publicIp='").append(publicIp).append('\'');
        sb.append(", publicPort='").append(publicPort).append('\'');
        sb.append(", status=").append(status);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

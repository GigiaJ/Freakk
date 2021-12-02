package main;

public enum VersionEnum {
    VERSION("1.0.1");

    private final String version;

    private VersionEnum(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }
}

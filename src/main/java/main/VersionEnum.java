package main;

public enum VersionEnum {
    VERSION("2.6.1");

    private final String version;

    private VersionEnum(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }
}

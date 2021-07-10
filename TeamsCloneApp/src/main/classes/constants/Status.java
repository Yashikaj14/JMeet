package constants;

public enum Status {
    ONLINE,
    AWAY,
    OFFLINE,
    IN_CALL,
    BUSY;

    @Override
    public String toString() {
        return this.name();
    }
}

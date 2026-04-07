package domain;

public enum OperationType {
    DEPOSIT,
    WITHDRAWAL;

    @Override
    public String toString() {
        return name();
    }
}
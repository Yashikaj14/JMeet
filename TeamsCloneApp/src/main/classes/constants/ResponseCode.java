package constants;

import java.io.Serializable;

public enum ResponseCode implements Serializable {
    SUCCESS,
    FAILURE;

    @Override
    public String toString() {
        return this.name();
    }
}

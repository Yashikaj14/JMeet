package request;

import constants.RequestCode;

import java.io.Serializable;
import java.net.InetAddress;

public class LoginRequest extends Request implements Serializable {
    private String username;
    private String password;
    private InetAddress ipAddress;

    public LoginRequest(String username, String password, InetAddress ipAddress) {
        this.username = username;
        this.password = password;
        this.ipAddress = ipAddress;
        this.requestCode = RequestCode.LOGIN_REQUEST;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }
}

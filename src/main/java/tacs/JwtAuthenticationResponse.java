package tacs;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {
	
	private static final long serialVersionUID = 1250166508152483573L;

    private final String token;
    private final String userId;
    private final boolean esAdmin;

    public JwtAuthenticationResponse(String token, String unId, boolean unBool) {
        this.token = token;
        userId = unId;
        esAdmin = unBool;
    }

    public String getToken() {
        return this.token;
    }
    
    public String getId() {
    	return this.userId;
    }
    
    public boolean isAdmin() {
    	return this.esAdmin;
    }

}

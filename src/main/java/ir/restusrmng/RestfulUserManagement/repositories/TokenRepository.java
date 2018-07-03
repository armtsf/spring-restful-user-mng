package ir.restusrmng.RestfulUserManagement.repositories;

public interface TokenRepository {

    public void save(String token, String id);
    public String find(String token);

}

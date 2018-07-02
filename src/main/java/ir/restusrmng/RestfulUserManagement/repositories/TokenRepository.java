package ir.restusrmng.RestfulUserManagement.repositories;

public interface TokenRepository {

    public void save(String id, String token);
    public String find(String id);

}

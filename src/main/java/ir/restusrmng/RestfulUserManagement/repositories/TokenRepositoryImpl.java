package ir.restusrmng.RestfulUserManagement.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private static final String KEY = "TOKEN";

    @Autowired
    @Qualifier("userTokenRedisTemplate")
    private RedisTemplate<String, String> redisTemplate;

    private HashOperations hashOperations;

    @PostConstruct
    void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(String id, String token) {
        hashOperations.put(KEY, id, token);
    }

    @Override
    public String find(String id) {
        return (String) hashOperations.get(KEY, id);
    }
}

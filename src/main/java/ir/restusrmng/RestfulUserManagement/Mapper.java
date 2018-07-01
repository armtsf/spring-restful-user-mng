package ir.restusrmng.RestfulUserManagement;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public ModelMapper Mapper() {
        return new ModelMapper();
    }
}

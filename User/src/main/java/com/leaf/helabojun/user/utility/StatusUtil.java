package com.leaf.helabojun.user.utility;


import com.leaf.helabojun.user.entity.Status;
import com.leaf.helabojun.user.exception.DataNotFoundException;
import com.leaf.helabojun.user.repository.StatusRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class StatusUtil {

    private StatusRepository statusRepository;

    public Status getStatus(String code) {
        return statusRepository.findByCode(code)
                .orElseThrow(() -> new DataNotFoundException(""));
    }
}

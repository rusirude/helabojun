package com.leaf.helabojun.user.repository.specification;

import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.entity.Section;
import com.leaf.helabojun.user.entity.Section_;
import com.leaf.helabojun.user.entity.Status_;
import com.leaf.helabojun.user.enums.StatusEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SectionSpecification {
    public Specification<Section> generateSearchCriteria(SearchRequestDTO searchRequestDTO) {
        return (Specification<Section>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!Optional.ofNullable(searchRequestDTO.getKey1()).orElse("").isEmpty())
                predicates.add(criteriaBuilder.equal(root.get(Section_.DESCRIPTION), searchRequestDTO.getKey1()));

            if (!Optional.ofNullable(searchRequestDTO.getKey2()).orElse("").isEmpty())
                predicates.add(criteriaBuilder.equal(root.get(Section_.STATUS).get(Status_.DESCRIPTION), searchRequestDTO.getKey2()));

            predicates.add(criteriaBuilder.notEqual(root.get(Section_.STATUS).get(Status_.CODE), StatusEnum.DELETE.getCode()));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


}

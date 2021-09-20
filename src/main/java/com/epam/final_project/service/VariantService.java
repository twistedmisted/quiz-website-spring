package com.epam.final_project.service;

import com.epam.final_project.entity.VariantEntity;
import com.epam.final_project.repository.VariantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class VariantService {

    private final VariantRepository variantRepository;

    public VariantService(VariantRepository variantRepository) {
        this.variantRepository = variantRepository;
    }

    public VariantEntity save(VariantEntity variant) {
        return variantRepository.save(variant);
    }

}

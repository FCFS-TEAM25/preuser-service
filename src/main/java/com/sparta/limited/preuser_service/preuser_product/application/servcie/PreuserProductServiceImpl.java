package com.sparta.limited.preuser_service.preuser_product.application.servcie;

import com.sparta.limited.preuser_service.preuser_product.application.dto.request.PreuserProductCreateRequest;
import com.sparta.limited.preuser_service.preuser_product.application.dto.response.PreuserProductCreateResponse;
import com.sparta.limited.preuser_service.preuser_product.application.dto.response.PreuserProductGetResponse;
import com.sparta.limited.preuser_service.preuser_product.application.mapper.PreuserProductMapper;
import com.sparta.limited.preuser_service.preuser_product.domain.model.PreuserProduct;
import com.sparta.limited.preuser_service.preuser_product.domain.repository.PreuserProductRepository;
import com.sparta.limited.preuser_service.preuser_product.infrastructure.client.ProductClient;
import com.sparta.limited.preuser_service.preuser_product.infrastructure.dto.response.ProductReadResponse;
import com.sparta.limited.preuser_service.preuser_product.infrastructure.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PreuserProductServiceImpl implements PreuserProductService {

    private final PreuserProductRepository preuserProductRepository;
    private final ProductClient productClient;

    @Override
    @Transactional
    public PreuserProductCreateResponse createPreuserProduct(PreuserProductCreateRequest request) {

        UUID productId = request.getProductId();

        preuserProductRepository.findByProductId(productId);

        ProductReadResponse product = productClient.getProduct(productId);

        PreuserProduct preuserProduct = ProductMapper.toEntity(product, request.getQuantity());

        preuserProductRepository.save(preuserProduct);
        return PreuserProductMapper.toPreuserProductCreateResponse(preuserProduct);
    }

    @Override
    public PreuserProductGetResponse getPreuserProduct(UUID preuserProductId) {

        PreuserProduct preuserProduct = preuserProductRepository.findById(preuserProductId);


        return PreuserProductMapper.toPreuserProductGetResponse(preuserProduct);
    }
}

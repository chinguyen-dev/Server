package com.yody.Server.api.admin;

import com.yody.Server.dto.product.DataProductReqDTO;
import com.yody.Server.dto.product.ProductResAdminDTO;
import com.yody.Server.dto.product.ShowPageDTO;
import com.yody.Server.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/admin/products")
@Slf4j
public class AdminProductAPI {
    public final IProductService productService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ShowPageDTO getProducts(@RequestParam(defaultValue = "0") int page) {
        return this.productService.showPage(page);
    }

    @GetMapping("/{id}")
    public ProductResAdminDTO getProductById(@PathVariable Long id) {
        return this.productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResAdminDTO addProduct(@RequestBody DataProductReqDTO dataProductReqDTO) {
        return this.productService.addProduct(dataProductReqDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ProductResAdminDTO remove(@PathVariable Long id) {
       return this.productService.removeById(id);
    }
}
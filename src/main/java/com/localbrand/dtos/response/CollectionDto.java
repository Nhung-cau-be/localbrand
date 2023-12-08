package com.localbrand.dtos.response;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CollectionDto {
    private String id;
    @NotBlank(message = "Vui lòng nhập tên bộ sưu tập")
    private String name;
    @NotBlank(message = "Vui lòng đường dẫn hình ảnh")
    private String imageLink;
}

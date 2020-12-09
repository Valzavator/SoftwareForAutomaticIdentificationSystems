package com.gmail.maxsvynarchuk.presentation.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BarcodeDto {
    private String code;
    private String imageName;
}

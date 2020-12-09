package com.gmail.maxsvynarchuk.presentation.controller;

import com.gmail.maxsvynarchuk.facade.ProductFacade;
import com.gmail.maxsvynarchuk.presentation.dto.ProductDto;
import com.gmail.maxsvynarchuk.presentation.dto.BarcodeDto;
import com.gmail.maxsvynarchuk.presentation.util.ControllerUtil;
import com.gmail.maxsvynarchuk.presentation.util.constant.Views;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

import static com.gmail.maxsvynarchuk.presentation.util.constant.Resources.RESOURCES_PATH;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class BarcodeController {
    private final ProductFacade productFacade;

    @GetMapping
    public String getBarcodePage(Model model) {
        return Views.BARCODE_VIEW;
    }

    @PostMapping("/add")
    public String addProduct(
            ProductDto productDto,
            RedirectAttributes redirectAttributes) {
        BarcodeDto dto = productFacade.createNewProduct(productDto);
        redirectAttributes.addFlashAttribute("barcodeDto", dto);
        return ControllerUtil.redirectTo("/");
    }

    @GetMapping(value = "/download")
    public ResponseEntity<InputStreamResource> downloadBarcode(@RequestParam String filename) throws IOException {
        File file = new File(RESOURCES_PATH, filename);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=" + filename)
                .contentType(MediaType.IMAGE_PNG).contentLength(file.length())
                .body(resource);
    }

    @PostMapping(value = "/decode")
    public String decodeBarcode(@RequestParam MultipartFile barcode,
                                RedirectAttributes redirectAttributes) throws IOException {
        if (!barcode.getContentType().contains("image")) {
            redirectAttributes.addFlashAttribute("invalidBarcodeImage", true);
            return ControllerUtil.redirectTo("/");
        }
        ProductDto dto = productFacade.recognizeBarcode(barcode);
        if (dto != null) {
            redirectAttributes.addFlashAttribute("productDto", dto);
        } else {
            redirectAttributes.addFlashAttribute("notFoundProduct", true);
        }
        return ControllerUtil.redirectTo("/");
    }

}

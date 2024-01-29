package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.client.ImgurClient;
import com.vemser.dbc.searchorganic.dto.produto.Imagem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImgurService {
    @Value("${imgur.client-id}")
    private String clientId;
    private final ObjectMapper objectMapper;

    private final ImgurClient imgurClient;


    public Imagem uploadImage(MultipartFile image) throws Exception {
        byte[] imageBytes = image.getBytes();
        String authorization = "Client-ID " + clientId;

        String response = imgurClient.uploadImage(authorization, imageBytes);

        JsonNode jsonNode = objectMapper.readTree(response);
        String imageUrl = jsonNode.path("data").path("link").asText();
        Imagem imagem = new Imagem();
        imagem.setUrlImagem(imageUrl);

        return imagem;

    }
}
package com.vemser.dbc.searchorganic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vemser.dbc.searchorganic.client.ImgurClient;
import com.vemser.dbc.searchorganic.dto.produto.Imagem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ImgurService - Test")
class ImgurServiceTest {
    @InjectMocks
    private ImgurService imgurService;

    @Mock
    private ImgurClient imgurClient;

    @Mock
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Deve realizar upimagem")
    public void deveUploadImg() throws Exception {
        byte[] imageBytes = "Imagem Test".getBytes();
        String clientId = "idClientImgur";
        ReflectionTestUtils.setField(imgurService, "clientId", clientId);
        MultipartFile mockImage = mock(MultipartFile.class);

        String urlImagem = "http://url/imagem.jpg";

        String authorization = "Client-ID " + clientId;
        String imgurResponse = "{ \"data\": { \"link\": \"http://url/imagem.jpg\" } }";
        JsonNode mockJsonNode = mock(JsonNode.class);
        JsonNode mockDataNode = mock(JsonNode.class);

        when(imgurClient.uploadImage(eq(authorization), eq(imageBytes))).thenReturn(imgurResponse);
        when(objectMapper.readTree(eq(imgurResponse))).thenReturn(mockJsonNode);

        when(mockImage.getBytes()).thenReturn(imageBytes);
        when(mockJsonNode.path("data")).thenReturn(mockDataNode);
        when(mockDataNode.path("link")).thenReturn(mock(JsonNode.class));
        when(mockDataNode.path("link").asText()).thenReturn(urlImagem);

        Imagem resultado = imgurService.uploadImage(mockImage);

        assertEquals(urlImagem, resultado.getUrlImagem());
        verify(imgurClient).uploadImage(eq(authorization), eq(imageBytes));
        verify(objectMapper).readTree(eq(imgurResponse));

    }

}
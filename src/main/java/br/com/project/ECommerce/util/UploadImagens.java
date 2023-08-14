package br.com.project.ECommerce.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.logging.Logger;

public class UploadImagens {

    private static final Logger LOGGER = Logger.getLogger(UploadImagens.class.getName());

    private static final String CAMINHO_ARQUIVO = "";

    public static void uploadProductImagens(MultipartFile imagem){
        LOGGER.info("Fazendo upload da imagem");

        try {
            if (!imagem.isEmpty()) {
                String nomeArquivo = imagem.getOriginalFilename();

                String caminho = CAMINHO_ARQUIVO + "\\product-images";

                File dir = new File(caminho);

                File serverFile = new File(dir.getAbsolutePath() + File.pathSeparator + nomeArquivo);

                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(imagem.getBytes());
                stream.close();

                LOGGER.info("Imagem Armazenada com Sucesso");
            }
        } catch (Exception e){
            LOGGER.info("Erro ao fazer upload da imagem - " + e.getMessage());
        }
    }
}

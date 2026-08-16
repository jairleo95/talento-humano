/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the editor.
 */
package com.app.controller.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author ALFA 3
 */
public class FileUploadValidator {

    public static final long MAX_IMAGE_SIZE_BYTES = 5 * 1024 * 1024;
    public static final long MAX_DOCUMENT_SIZE_BYTES = 20 * 1024 * 1024;

    private static final Set<String> ALLOWED_IMAGE_TYPES = new HashSet<>(Arrays.asList(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    ));
    private static final Set<String> ALLOWED_IMAGE_EXTENSIONS = new HashSet<>(Arrays.asList(
            "jpg", "jpeg", "png", "gif", "webp"
    ));
    private static final Set<String> ALLOWED_DOCUMENT_EXTENSIONS = new HashSet<>(Arrays.asList(
            "pdf", "doc", "docx", "xls", "xlsx", "jpg", "jpeg", "png"
    ));

    private FileUploadValidator() {
    }

    public static void validateImage(FileItem item) {
        if (item == null || item.getSize() == 0) {
            throw new IllegalArgumentException("No se recibio ningun archivo de imagen");
        }
        if (item.getSize() > MAX_IMAGE_SIZE_BYTES) {
            throw new IllegalArgumentException("El archivo excede el tamano maximo permitido de 5 MB");
        }
        if (!ALLOWED_IMAGE_TYPES.contains(item.getContentType())) {
            throw new IllegalArgumentException("Tipo de imagen no permitido: " + item.getContentType());
        }
        String extension = getExtension(item.getName());
        if (!ALLOWED_IMAGE_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Extension de imagen no permitida: " + extension);
        }
    }

    public static void validateDocument(FileItem item) {
        if (item == null || item.getSize() == 0) {
            throw new IllegalArgumentException("No se recibio ningun archivo");
        }
        if (item.getSize() > MAX_DOCUMENT_SIZE_BYTES) {
            throw new IllegalArgumentException("El archivo excede el tamano maximo permitido de 20 MB");
        }
        String extension = getExtension(item.getName());
        if (!ALLOWED_DOCUMENT_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("Extension de archivo no permitida: " + extension);
        }
    }

    public static String getExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fileName.length() - 1) {
            return "";
        }
        return fileName.substring(dotIndex + 1).toLowerCase();
    }

    public static String sanitizeFileName(String fileName) {
        if (fileName == null) {
            return "archivo";
        }
        String baseName = new java.io.File(fileName).getName();
        return baseName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

}

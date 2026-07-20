package com.parking.backend.service;

public class EmailService {
    public boolean sendQrEmail(String to, String qrValue, String spaceCode) {
        if (to == null || to.isBlank()) {
            System.out.println("Correo no enviado: no hay destinatario.");
            return false;
        }

        System.out.println("[EMAIL MOCK] Para: " + to);
        System.out.println("[EMAIL MOCK] Asunto: Tu QR de acceso al estacionamiento UTP");
        System.out.println("[EMAIL MOCK] Contenido: QR=" + qrValue + " | Espacio=" + spaceCode);
        return true;
    }
}

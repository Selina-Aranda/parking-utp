import { Html5Qrcode } from "html5-qrcode";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import styles from "../styles/ScanQR.module.css";

export default function ScanQR() {
  const navigate = useNavigate();
  const [error, setError] = useState("");

  useEffect(() => {
    let scanned = false;
    const scanner = new Html5Qrcode("reader");

    scanner
      .start(
        { facingMode: "environment" },
        {
          fps: 10,
          qrbox: {
            width: 250,
            height: 250,
          },
        },
        async (decodedText) => {
          if (scanned) return;
          scanned = true;

          console.log("QR escaneado:", decodedText);

          try {
            const response = await fetch("http://localhost:8080/api/alumnos/salida", {
              method: "POST",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify({ qrValue: decodedText }),
            });

            const data = await response.json();

            if (!response.ok) {
              throw new Error(data.message || "No se pudo procesar la salida.");
            }

            navigate("/exit/success", {
              state: {
                studentName: data.studentName,
                studentCode: data.studentCode,
                vehicleType: data.vehicleType,
                spaceCode: data.spaceCode,
                qrValue: data.qrValue,
                entryTime: data.entryTime,
                exitTime: data.exitTime,
                career: data.career,
              },
            });
          } catch (err) {
            console.error(err);
            setError(err instanceof Error ? err.message : "Error inesperado al validar el QR.");
            scanned = false;
          }
        },
        () => {},
      )
      .catch((err) => {
        console.error(err);
        setError("No se pudo inicializar la cámara.");
      });

    return () => {
      scanner.stop().catch(() => {});
    };
  }, [navigate]);

  return (
    <div className={styles.page}>
      <h1 className={styles.title}>Escanear QR</h1>
      <div className={styles.scannerArea}>
        <div className={styles.readerBox}>
          <div id="reader"></div>
          <div className={styles.scanLine}></div>
        </div>
      </div>
      <p className={styles.note}>Aproxima tu código QR del celular</p>
      {error && <p className={styles.note}>{error}</p>}
      <button className={styles.linkButton} type="button" onClick={() => navigate("/register")}>
        ¿No tienes QR? Regístrate aquí
      </button>
    </div>
  );
}

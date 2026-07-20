import { useLocation, useNavigate } from "react-router-dom";
import Button from "../components/ui/Button";
import QRCode from "../components/ui/QRCode";
import styles from "../styles/RegisterSuccess.module.css";

export default function RegisterSuccess() {
  const navigate = useNavigate();
  const location = useLocation();
  const qrValue = location.state?.qrValue ?? "UTP-QR-1234";
  const spaceCode = location.state?.spaceCode ?? "B-12";

  return (
    <div className={styles.page}>
      <div className={styles.card}>
        <span className={styles.check}>✓</span>
        <h1 className={styles.title}>Registro Exitoso</h1>
        <QRCode value={qrValue} spaceCode={spaceCode} />
        <div className={styles.buttonsRow}>
          <Button label="✉ Enviar QR al correo" onClick={() => navigate("/access/free")} variant="primary" fullWidth />
        </div>
        <p className={styles.note}>Usa este código QR para entrar y salir del estacionamiento</p>
      </div>
    </div>
  );
}

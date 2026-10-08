import { useLocation, useNavigate } from "react-router-dom";
import styles from "../styles/ExitSuccess.module.css";
import { useEffect, useMemo} from "react";

function formatDateTime(value?: string) {
  if (!value) return "—";
  const date = new Date(value);
  if (Number.isNaN(date.getTime())) return value;
  return date.toLocaleString("es-PE", {
    dateStyle: "medium",
    timeStyle: "short",
  });
}

export default function ExitSuccess() {
  const navigate = useNavigate();
  const location = useLocation();
<<<<<<< HEAD
=======
  const setClock = useState(() => new Date());
>>>>>>> b0c12c24e57cfdd8f162a2b2f667c49a3538434c

  const payload = useMemo(
    () => ({
      studentName: location.state?.studentName ?? "—",
      studentCode: location.state?.studentCode ?? "—",
      vehicleType: location.state?.vehicleType ?? "—",
      spaceCode: location.state?.spaceCode ?? "—",
      qrValue: location.state?.qrValue ?? "—",
      entryTime: location.state?.entryTime ?? "",
      exitTime: location.state?.exitTime ?? "",
      career: location.state?.career ?? "—",
    }),
    [location.state],
  );

  useEffect(() => {
    const redirectTimer = window.setTimeout(() => {
      navigate("/");
    }, 8000);

    return () => {
      window.clearTimeout(redirectTimer);
    };
  }, [navigate]);

  return (
    <div className={styles.page}>
      <div className={styles.card}>
        <span className={styles.check}>✓</span>
        <h1 className={styles.title}>Salida Registrada</h1>
        <p className={styles.subtitle}>El QR pertenece a {payload.studentName} y su salida quedó validada correctamente.</p>
        <div className={styles.grid}>
          <div className={styles.field}>
            <span className={styles.fieldLabel}>NOMBRE</span>
            <span>{payload.studentName}</span>
          </div>
          <div className={styles.field}>
            <span className={styles.fieldLabel}>CÓDIGO</span>
            <span>{payload.studentCode}</span>
          </div>
          <div className={styles.field}>
            <span className={styles.fieldLabel}>VEHÍCULO</span>
            <span>{payload.vehicleType}</span>
          </div>
          <div className={styles.field}>
            <span className={styles.fieldLabel}>CARRERA</span>
            <span>{payload.career}</span>
          </div>
          <div className={styles.field}>
            <span className={styles.fieldLabel}>ESPACIO</span>
            <span>{payload.spaceCode}</span>
          </div>
          <div className={styles.field}>
            <span className={styles.fieldLabel}>HORA DE SALIDA</span>
            <span>{formatDateTime(payload.exitTime)}</span>
          </div>
          <div className={styles.field}>
            <span className={styles.fieldLabel}>INGRESO REGISTRADO</span>
            <span>{formatDateTime(payload.entryTime)}</span>
          </div>
          <div className={styles.field}>
            <span className={styles.fieldLabel}>QR ESCANEADO</span>
            <span>{payload.qrValue}</span>
          </div>
        </div>
        <p className={styles.note}>Registro verificado. Gracias por utilizar Parking UTP.</p>
        <p className={styles.small}>Redirigiendo al inicio en unos segundos...</p>
      </div>
    </div>
  );
}

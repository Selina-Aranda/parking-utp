import { useState } from "react";
import { useNavigate } from "react-router-dom";
import Input from "../components/ui/Input";
import VehicleTypeSelector from "../components/ui/VehicleTypeSelector";
import Button from "../components/ui/Button";
import styles from "../styles/Register.module.css";
import { Bike, Zap, Motorbike } from "lucide-react";

const vehicleOptions = [
  { label: "Bicicleta", icon: Bike },
  { label: "Scooter Eléctrico", icon: Zap },
  { label: "Bicicleta Eléctrica", icon: Bike },
  { label: "Motocicleta Eléctrica", icon: Motorbike },
];

function isValidStudentCode(code: string) {
  const trimmed = code.trim().toUpperCase();
  if (!/^U\d{7,8}$/.test(trimmed)) {
    return false;
  }
  return true;
}

function mapVehicleTypeToBackend(label: string) {
  switch (label) {
    case "Bicicleta":
      return 1;
    case "Scooter Eléctrico":
      return 2;
    case "Bicicleta Eléctrica":
      return 3;
    case "Motocicleta Eléctrica":
      return 4;
    default:
      return 5;
  }
}

export default function Register() {
  const navigate = useNavigate();
  const [studentCode, setStudentCode] = useState("");
  const [selectedType, setSelectedType] = useState("");
  const [otherType, setOtherType] = useState("");
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const hasVehicleType = selectedType || otherType.trim();
  const canSubmit = studentCode.trim().length > 0 && hasVehicleType;

  async function handleSubmit() {
    if (!canSubmit) {
      setError("Completa el código estudiantil y el tipo de vehículo.");
      return;
    }

    if (!isValidStudentCode(studentCode)) {
      setError("El código debe empezar con U y tener 8 dígitos aprox. Ejemplo: U22302061");
      return;
    }

    setError("");
    setLoading(true);

    try {
      const response = await fetch("http://localhost:8080/api/alumnos/registro", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          codigo: studentCode.trim().toUpperCase(),
          tipoVehiculo: mapVehicleTypeToBackend(selectedType || otherType.trim()),
          otroTipo: otherType.trim() || null,
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || "No se pudo registrar el vehículo.");
      }

      navigate("/register/success", { state: { qrValue: data.qrValue, spaceCode: data.spaceCode } });
    } catch (err) {
      setError(err instanceof Error ? err.message : "Ocurrió un error inesperado.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className={styles.page}>
      <h1 className={styles.title}>Registro de Vehículo</h1>
      <form
        className={styles.form}
        onSubmit={(event) => {
          event.preventDefault();
          void handleSubmit();
        }}
      >
        <Input label="CÓDIGO DEL ESTUDIANTE" placeholder="U15331486" value={studentCode} onChange={setStudentCode} />
        <div className={styles.section}>
          <span className={styles.sectionLabel}>TIPO DE VEHÍCULO</span>
          <VehicleTypeSelector options={vehicleOptions} selected={selectedType} onSelect={setSelectedType} />
        </div>
        <Input label="OTRO" placeholder="Especificar otro tipo" value={otherType} onChange={setOtherType} />
        {error && <p className={styles.note}>{error}</p>}
        <Button
          label={loading ? "PROCESANDO..." : "REGISTRAR VEHÍCULO"}
          onClick={() => {
            void handleSubmit();
          }}
          variant={canSubmit ? "primary" : "disabled"}
          fullWidth
        />
        <p className={styles.note}>Recibirás tu código QR por correo</p>
      </form>
    </div>
  );
}

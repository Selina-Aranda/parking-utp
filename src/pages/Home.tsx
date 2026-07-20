import { useNavigate } from 'react-router-dom'
import OccupancyBar from '../components/ui/OccupancyBar'
import StatCard from '../components/ui/StatCard'
import Button from '../components/ui/Button'
import styles from '../styles/Home.module.css'
import { Bike, QrCode } from 'lucide-react';
import { useEffect, useState } from "react";
import { getDashboardData, type DashboardData } from "../services/dashboardService";

export default function Home() {
  const navigate = useNavigate()
  const [dashboard, setDashboard] = useState<DashboardData>({
    total: 0,
    occupied: 0,
    available: 0,
    percentage: 0,
  });

  useEffect(() => {

      const loadData = async () => {
          const data = await getDashboardData();
          setDashboard(data);
      };

      loadData();

      const interval = setInterval(loadData, 3000);

      return () => clearInterval(interval);

  }, []);

  return (
    <div className={styles.page}>
      <h1 className={styles.title}>Estacionamiento de Micromovilidad</h1>
      <div className={styles.availableCard}>
        <span className={styles.availableNumber}>{dashboard.available}</span>
        <span className={styles.availableLabel}>ESPACIOS DISPONIBLES</span>
      </div>
      <OccupancyBar occupied={dashboard.occupied} total={dashboard.total} />
      <div className={styles.statsRow}>
        <StatCard value={dashboard.occupied.toString()} label="OCUPADOS" />
        <StatCard value={dashboard.total.toString()} label="CAPACIDAD TOTAL" />
      </div>
      <div className={styles.banner}>
        <span>
          {dashboard.available > 0 ? '✓ Espacio disponible' : 'X No hay espacio disponible'}
        </span>
      </div>
      <div className={styles.actionsRow}>
        <div className={styles.actionCard}>
         <Button icon={<Bike size={20} />} label="ENTRADA" onClick={() => navigate('/register')} variant="primary" fullWidth />
          <p className={styles.actionText}>Registrarse</p>
        </div>
        <div className={styles.actionCard}>
          <Button icon={<QrCode size={20} />} label="SALIDA" onClick={() => navigate('/scan')} variant="secondary" fullWidth />
          <p className={styles.actionText}>Escanear código QR</p>
        </div>
      </div>
    </div>
  )
}

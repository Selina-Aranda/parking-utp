import { useState } from 'react'
import Input from '../../components/ui/Input'
import Button from '../../components/ui/Button'
import StatCard from '../../components/ui/StatCard'
import VehicleTable from '../../components/ui/VehicleTable'
import type { VehicleRow } from '../../components/ui/VehicleTable'
import styles from '../../styles/Dashboard.module.css'

const rows: VehicleRow[] = [
  { codigo: 'U15331486', nombre: 'Mateo R.', vehiculo: 'Scooter Eléctrico', hora: '08:15 am', tiempo: '2h 30m' },
  { codigo: 'U15331487', nombre: 'Ana G.', vehiculo: 'Bicicleta', hora: '07:45 am', tiempo: '3h 00m' },
  { codigo: 'U15331488', nombre: 'Carlos M.', vehiculo: 'Motocicleta Eléctrica', hora: '09:00 am', tiempo: '1h 45m' },
  { codigo: 'U15331489', nombre: 'Sofía L.', vehiculo: 'Bicicleta Eléctrica', hora: '08:30 am', tiempo: '2h 15m' },
  { codigo: 'U15331490', nombre: 'Diego P.', vehiculo: 'Scooter Eléctrico', hora: '10:00 am', tiempo: '45m' },
]

export default function Dashboard() {
  const [search, setSearch] = useState('')

  return (
    <div className={styles.page}>
      <h1 className={styles.title}>Monitoreo de Estacionamiento</h1>
      <div className={styles.topRow}>
        <div className={styles.statsRow}>
          <StatCard value="5" label="DENTRO" />
          <StatCard value="45" label="TOTAL" />
          <StatCard value="40" label="LIBRES" />
        </div>
      </div>
      <div className={styles.controlsRow}>
        <Input label="Buscar" placeholder="Buscar código o nombre" value={search} onChange={setSearch} />
        <Button label="Reporte" onClick={() => {}} variant="secondary" />
      </div>
      <VehicleTable data={rows} />
    </div>
  )
}

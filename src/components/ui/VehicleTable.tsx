import styles from '../../styles/components/VehicleTable.module.css'

export interface VehicleRow {
  codigo: string
  nombre: string
  vehiculo: string
  hora: string
  tiempo: string
}

interface VehicleTableProps {
  data: VehicleRow[]
}

export default function VehicleTable({ data }: VehicleTableProps) {
  return (
    <div className={styles.tableWrapper}>
      <table className={styles.table}>
        <thead>
          <tr>
            <th>CÓDIGO</th>
            <th>NOMBRE</th>
            <th>VEHÍCULO</th>
            <th>HORA</th>
            <th>TIEMPO</th>
          </tr>
        </thead>
        <tbody>
          {data.map((row) => (
            <tr key={row.codigo}>
              <td>{row.codigo}</td>
              <td>{row.nombre}</td>
              <td>{row.vehiculo}</td>
              <td>{row.hora}</td>
              <td>{row.tiempo}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  )
}

import styles from '../../styles/components/OccupancyBar.module.css'

interface OccupancyBarProps {
  occupied: number
  total: number
}

export default function OccupancyBar({ occupied, total }: OccupancyBarProps) {
  const percent = total > 0 ? Math.round((occupied / total) * 100) : 0

  return (
    <div className={styles.container}>
      <div className={styles.status}>
        <span>{percent}% OCUPADO</span>
      </div>
      <div className={styles.barBackground}>
        <div className={styles.barFill} style={{ width: `${percent}%` }} />
      </div>
      <div className={styles.summary}>
        <span>{occupied} ocupados de {total}</span>
      </div>
    </div>
  )
}

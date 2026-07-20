import styles from '../../styles/components/StatCard.module.css'

interface StatCardProps {
  value: string | number
  label: string
}

export default function StatCard({ value, label }: StatCardProps) {
  return (
    <div className={styles.card}>
      <div className={styles.value}>{value}</div>
      <div className={styles.label}>{label}</div>
    </div>
  )
}

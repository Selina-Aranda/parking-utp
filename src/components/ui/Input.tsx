import styles from '../../styles/components/Input.module.css'

interface InputProps {
  label?: string
  placeholder?: string
  value: string
  onChange: (value: string) => void
}

export default function Input({ label, placeholder, value, onChange }: InputProps) {
  return (
    <label className={styles.wrapper}>
      {label && <span className={styles.label}>{label}</span>}
      <input
        className={styles.input}
        placeholder={placeholder}
        value={value}
        onChange={(event) => onChange(event.target.value)}
      />
    </label>
  )
}

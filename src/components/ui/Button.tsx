import styles from '../../styles/components/Button.module.css'
import { type ReactNode } from 'react'

interface ButtonProps {
  label: string
  icon?:ReactNode
  onClick: () => void
  variant: 'primary' | 'secondary' | 'disabled'
  fullWidth?: boolean
}

export default function Button({ label, icon, onClick, variant, fullWidth }: ButtonProps) {
  const className = [styles.button, styles[variant], fullWidth ? styles.fullWidth : '']
    .filter(Boolean)
    .join(' ')

  return (
    <button
      className={className}
      onClick={variant === 'disabled' ? undefined : onClick}
      disabled={variant === 'disabled'}
      type="button"
    >
      {icon}
      {label}
    </button>
  )
}

import type { LucideIcon } from 'lucide-react'
import styles from '../../styles/components/Button.module.css'

interface VehicleOption {
  label: string
  icon: LucideIcon
}

interface VehicleTypeSelectorProps {
  options: VehicleOption[]
  selected: string
  onSelect: (value: string) => void
}

export default function VehicleTypeSelector({ options, selected, onSelect }: VehicleTypeSelectorProps) {
  return (
    <div className={styles.selectorGrid}>
      {options.map((option) => {
        const Icon = option.icon
        const isSelected = selected === option.label
        const itemClass = [styles.typeButton, isSelected ? styles.selected : '']
          .filter(Boolean)
          .join(' ')

        return (
          <button
            key={option.label}
            type="button"
            className={itemClass}
            onClick={() => onSelect(option.label)}
          >
            <Icon size={36}/>
            <span>{option.label}</span>
          </button>
        )
      })}
    </div>
  )
}

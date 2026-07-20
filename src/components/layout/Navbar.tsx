import styles from '../../styles/components/Navbar.module.css'
import { CircleParking } from 'lucide-react';

export default function Navbar() {
  return (
    <header className={styles.navbar}>
      <div className={styles.brand}>
        <CircleParking />
        <span className={styles.title}>Parking UTP</span>
      </div>
    </header>
  )
}

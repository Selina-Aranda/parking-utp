import { QRCodeCanvas } from 'qrcode.react'
import styles from '../../styles/components/QRCode.module.css'

interface QRCodeProps {
  value: string
  spaceCode: string
}

export default function QRCode({ value, spaceCode }: QRCodeProps) {
  return (
    <div className={styles.qrWrapper}>
      <QRCodeCanvas value={value} size={180} bgColor="#ffffff" fgColor="#000000" />
      <p className={styles.spaceText}>Espacio: {spaceCode}</p>
    </div>
  )
}

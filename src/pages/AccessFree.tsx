import { useNavigate } from 'react-router-dom'
import { useEffect } from 'react'
import styles from '../styles/AccessFree.module.css'

export default function AccessFree(){
     const navigate = useNavigate()

    useEffect(() => {
        const timer = setTimeout(() => {
            navigate("/");
        }, 5000);

        return () => clearTimeout(timer);
    }, [navigate]);

    return(
        <div className={styles.page}>
        <div className={styles.card}>
        <span className={styles.check}>✓</span>
        <h1 className={styles.title}>Accesso liberado</h1>
        <p className={styles.note}>Registro verificado. Gracias por utilizar Parking UTP.</p>
        <p className={styles.small}>Redirigiendo al inicio...</p>
      </div>
    </div>
    )
}
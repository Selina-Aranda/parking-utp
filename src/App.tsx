import { BrowserRouter, Routes, Route } from 'react-router-dom'
import Navbar from './components/layout/Navbar'
import Home from './pages/Home'
import Register from './pages/Register'
import RegisterSuccess from './pages/RegisterSuccess'
import ScanQR from './pages/ScanQR'
import ExitSuccess from './pages/ExitSuccess'
import Dashboard from './pages/admin/Dashboard'
import AccessFree from './pages/AccessFree'

function App() {
  return (
    <BrowserRouter>
      <Navbar />
      <main className="appMain">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route path="/register/success" element={<RegisterSuccess />} />
          <Route path="/access/free" element={<AccessFree />} />
          <Route path="/scan" element={<ScanQR />} />
          <Route path="/exit/success" element={<ExitSuccess />} />
          <Route path="/admin" element={<Dashboard />} />
        </Routes>
      </main>
    </BrowserRouter>
  )
}

export default App

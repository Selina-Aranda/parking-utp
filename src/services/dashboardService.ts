export interface DashboardData {
  total: number;
  occupied: number;
  available: number;
  percentage: number;
}

export async function getDashboardData(): Promise<DashboardData> {
  const response = await fetch("http://localhost:8080/api/dashboard");

  if (!response.ok) {
    throw new Error("No se pudo obtener la información");
  }

  return response.json();
}
const baseURL = 'http://localhost:8081'

const defaultHeaders = {
  'Content-Type': 'application/json',
}

async function handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    const text = await response.text()
    throw new Error(text || response.statusText)
  }
  return response.json()
}

export const axiosInstance = {
  baseURL,
  async get<T>(url: string): Promise<T> {
    const response = await fetch(`${baseURL}${url}`, {
      method: 'GET',
      headers: defaultHeaders,
    })
    return handleResponse<T>(response)
  },
  async post<T>(url: string, data: unknown): Promise<T> {
    const response = await fetch(`${baseURL}${url}`, {
      method: 'POST',
      headers: defaultHeaders,
      body: JSON.stringify(data),
    })
    return handleResponse<T>(response)
  },
}

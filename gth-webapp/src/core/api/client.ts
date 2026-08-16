const API_BASE = '';

const TOKEN_KEY = 'gth_token';

let onUnauthorized: () => void = () => {};

export function configureApiClient(logout: () => void) {
  onUnauthorized = logout;
}

export function saveToken(token: string) {
  localStorage.setItem(TOKEN_KEY, token);
}

export function clearToken() {
  localStorage.removeItem(TOKEN_KEY);
}

export function loadToken(): string | null {
  return localStorage.getItem(TOKEN_KEY);
}

function getAuthHeader(): Record<string, string> {
  const token = loadToken();
  if (token) {
    return { 'Content-Type': 'application/json', Authorization: `Bearer ${token}` };
  }
  return { 'Content-Type': 'application/json' };
}

async function handleResponse(response: Response) {
  if (response.status === 401) {
    let message = 'Usuario o contraseña incorrectos';
    try {
      const body = await response.json();
      message = body?.error || body?.message || message;
    } catch {
      // Ignored
    }
    if (!response.url.includes('/auth/login')) {
      onUnauthorized();
    }
    throw new Error(message);
  }
  if (response.status === 204) {
    return null;
  }
  let body: any = null;
  try {
    body = await response.json();
  } catch {
    body = null;
  }
  if (!response.ok) {
    const message = body?.error || body?.message || `Error en el servicio (${response.status}). Verifique la conexión con el servidor.`;
    throw new Error(message);
  }
  return body;
}

export async function apiGet<T = unknown>(path: string): Promise<T> {
  const response = await fetch(`${API_BASE}${path}`, { headers: getAuthHeader() });
  return handleResponse(response) as Promise<T>;
}

export async function apiPost<T = unknown>(path: string, body?: unknown): Promise<T> {
  const response = await fetch(`${API_BASE}${path}`, {
    method: 'POST',
    headers: getAuthHeader(),
    body: body ? JSON.stringify(body) : undefined,
  });
  return handleResponse(response) as Promise<T>;
}

export async function apiPut<T = unknown>(path: string, body?: unknown): Promise<T> {
  const response = await fetch(`${API_BASE}${path}`, {
    method: 'PUT',
    headers: getAuthHeader(),
    body: body ? JSON.stringify(body) : undefined,
  });
  return handleResponse(response) as Promise<T>;
}

export async function apiPatch<T = unknown>(path: string, body?: unknown): Promise<T> {
  const response = await fetch(`${API_BASE}${path}`, {
    method: 'PATCH',
    headers: getAuthHeader(),
    body: body ? JSON.stringify(body) : undefined,
  });
  return handleResponse(response) as Promise<T>;
}

export async function apiDelete<T = unknown>(path: string): Promise<T> {
  const response = await fetch(`${API_BASE}${path}`, {
    method: 'DELETE',
    headers: getAuthHeader(),
  });
  return handleResponse(response) as Promise<T>;
}

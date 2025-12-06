const API_BASE_URL =
  process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8000';

type HttpMethod = 'GET' | 'POST';

export class ApiError extends Error {
  status: number;
  payload?: unknown;

  constructor(message: string, status: number, payload?: unknown) {
    super(message);
    this.status = status;
    this.payload = payload;
  }
}

async function request<T>(
  path: string,
  options: RequestInit,
  method: HttpMethod
): Promise<T> {
  const url = `${API_BASE_URL}${path}`;
  const response = await fetch(url, {
    ...options,
    method,
    headers: {
      ...(options.headers || {}),
    },
  });

  const contentType = response.headers.get('content-type');
  const payload =
    contentType && contentType.includes('application/json')
      ? await response.json()
      : await response.text();

  if (!response.ok) {
    throw new ApiError(
      `Request to ${path} failed with status ${response.status}`,
      response.status,
      payload
    );
  }

  return payload as T;
}

export function getJson<T>(path: string, init?: RequestInit) {
  return request<T>(path, init ?? {}, 'GET');
}

export function postJson<T>(path: string, body: unknown, init?: RequestInit) {
  return request<T>(
    path,
    {
      ...init,
      body: JSON.stringify(body),
      headers: {
        'Content-Type': 'application/json',
        ...(init?.headers || {}),
      },
    },
    'POST'
  );
}

export function postForm<T>(
  path: string,
  formData: FormData,
  init?: RequestInit
) {
  return request<T>(
    path,
    {
      ...init,
      body: formData,
    },
    'POST'
  );
}

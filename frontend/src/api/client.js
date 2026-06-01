const API_BASE = '/api';

let _accessToken = sessionStorage.getItem('accessToken') || null;

export const Auth = {
  getToken: () => _accessToken,
  getUser: () => {
    try {
      return JSON.parse(localStorage.getItem('user') || 'null');
    } catch {
      return null;
    }
  },
  setSession(accessToken, user) {
    _accessToken = accessToken;
    if (accessToken) sessionStorage.setItem('accessToken', accessToken);
    localStorage.setItem('user', JSON.stringify(user));
  },
  clear() {
    _accessToken = null;
    sessionStorage.removeItem('accessToken');
    localStorage.removeItem('user');
  },
  isLoggedIn: () => !!_accessToken || !!localStorage.getItem('user'),
  isAdmin: () => Auth.getUser()?.role === 'ADMIN',
  isInstruktor: () => Auth.getUser()?.role === 'INSTRUKTOR',
  isKandidat: () => {
    const r = Auth.getUser()?.role;
    return r === 'USER' || r === 'ROLE_USER' || (!Auth.isAdmin() && !Auth.isInstruktor() && !!r);
  },
};

export function normalizeLoginResponse(res) {
  const user = {
    id: res.id,
    firstName: res.firstName || '',
    lastName: res.lastName || '',
    email: res.email || '',
    role: res.role || 'USER',
  };
  return {
    accessToken: res.accessToken || res.token,
    user,
    role: user.role,
  };
}

async function refreshAccessToken() {
  try {
    const res = await fetch(`${API_BASE}/auth/refresh`, {
      method: 'POST',
      credentials: 'include',
    });
    if (res.ok) {
      const data = await res.json();
      _accessToken = data.accessToken;
      sessionStorage.setItem('accessToken', data.accessToken);
      return true;
    }
  } catch (e) {
    console.warn('Refresh failed:', e.message);
  }
  return false;
}

export async function initAuth() {
  if (!localStorage.getItem('user')) return;
  if (!_accessToken) await refreshAccessToken();
  else refreshAccessToken().catch(() => {});
}

async function apiFetch(path, options = {}, retry = true) {
  const headers = { 'Content-Type': 'application/json', ...options.headers };
  if (_accessToken) headers.Authorization = `Bearer ${_accessToken}`;

  const res = await fetch(API_BASE + path, {
    ...options,
    headers,
    credentials: 'include',
  });

  if (res.status === 401 && retry) {
    const refreshed = await refreshAccessToken();
    if (refreshed) return apiFetch(path, options, false);
    Auth.clear();
    window.location.href = '/login';
    throw new Error('Sesioni ka skaduar.');
  }

  if (res.status === 403) {
    let errorMsg = 'Nuk keni leje për këtë veprim.';
    try {
      const errData = await res.json();
      errorMsg = errData.message || errorMsg;
    } catch {
      /* ignore */
    }
    throw new Error(errorMsg);
  }

  if (!res.ok) {
    const err = await res.json().catch(() => ({ message: 'Gabim i panjohur' }));
    throw new Error(err.message || `HTTP ${res.status}`);
  }

  if (res.status === 204) return null;
  return res.json();
}

const makeResource = (endpoint) => ({
  getAll: (params = '') => apiFetch(`${endpoint}${params}`),
  getById: (id) => apiFetch(`${endpoint}/${id}`),
  create: (data) => apiFetch(endpoint, { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => apiFetch(`${endpoint}/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (id) => apiFetch(`${endpoint}/${id}`, { method: 'DELETE' }),
});

export const API = {
  auth: {
    login: (data) => apiFetch('/auth/login', { method: 'POST', body: JSON.stringify(data) }),
    register: (data) => apiFetch('/auth/register', { method: 'POST', body: JSON.stringify(data) }),
    logout: () => apiFetch('/auth/logout', { method: 'POST' }),
    me: () => apiFetch('/auth/me'),
  },
  kandidatet: makeResource('/kandidate'),
  kategorite: makeResource('/kategorite-patentes'),
  regjistrime: makeResource('/regjistrime'),
  instruktoret: makeResource('/instruktore'),
  automjetet: makeResource('/automjete'),
  oreteTeoria: makeResource('/oret-teorise'),
  oretPraktike: makeResource('/oret-praktikes'),
  provimet: makeResource('/provimet'),
  pagesat: makeResource('/pagesa'),
  oraret: makeResource('/oraret'),
};

export function unwrapList(res) {
  if (!res) return [];
  if (Array.isArray(res)) return res;
  return res.content || [];
}

initAuth();

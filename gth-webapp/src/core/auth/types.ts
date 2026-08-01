export interface MeResponse {
  id: string;
  username: string;
  email: string;
  enabled: boolean;
  createdAt: string;
  roles: string[];
  privileges: PrivilegeInfo[];
}

export interface PrivilegeInfo {
  code: string;
  description: string;
  linkUrl: string;
  icon: string;
  moduleName: string;
  sortOrder: number;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  tokenType: string;
  expiresIn: number;
  user: MeResponse;
}

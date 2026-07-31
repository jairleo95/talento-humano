export interface UserResponse {
  id: string;
  username: string;
  email: string;
  enabled: boolean;
  createdAt: string;
  roleIds: string[];
}

export interface RoleResponse {
  id: string;
  name: string;
  description: string;
}

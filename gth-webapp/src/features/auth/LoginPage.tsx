import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import { Message } from 'primereact/message';
import { useAuth } from '../../core/auth/useAuth';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';

const loginSchema = z.object({
  username: z.string().min(1, 'Usuario requerido'),
  password: z.string().min(1, 'Contraseña requerida'),
});

type LoginForm = z.infer<typeof loginSchema>;

export function LoginPage() {
  const { login } = useAuth();
  const navigate = useNavigate();
  const [error, setError] = useState<string | null>(null);

  const { control, handleSubmit, formState: { errors, isSubmitting } } = useForm<LoginForm>({
    resolver: zodResolver(loginSchema),
    defaultValues: { username: '', password: '' },
  });

  const onSubmit = async (data: LoginForm) => {
    setError(null);
    try {
      await login(data);
      navigate('/requirements');
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Error de autenticación');
    }
  };

  return (
    <div className="login-wrapper">
      <div className="flex flex-1 align-items-center justify-content-center">
        <div className="login-card">
          <div className="login-card-header">
            <div className="login-logo">
              Talento<span>Humano</span>
            </div>
            <div className="login-subtitle">Sistema de Gestión de Personal</div>
          </div>

          <div className="login-card-body">
            {error && <Message severity="error" text={error} className="mb-3 w-full" />}

            <form onSubmit={handleSubmit(onSubmit)} className="flex flex-column gap-3">
              <div className="flex flex-column gap-1">
                <label htmlFor="username" className="text-sm font-semibold" style={{ color: '#374151' }}>Usuario</label>
                <Controller name="username" control={control}
                  render={({ field }) => (<InputText id="username" {...field} className={`w-full ${errors.username ? 'p-invalid' : ''}`} autoFocus placeholder="Ingrese su usuario" />)}
                />
                {errors.username && <small className="p-error">{errors.username.message}</small>}
              </div>

              <div className="flex flex-column gap-1">
                <label htmlFor="password" className="text-sm font-semibold" style={{ color: '#374151' }}>Contraseña</label>
                <Controller name="password" control={control}
                  render={({ field }) => (
                    <Password id="password" {...field} feedback={false} toggleMask
                      className={`w-full ${errors.password ? 'p-invalid' : ''}`}
                      inputClassName="w-full" inputStyle={{ width: '100%' }}
                      placeholder="Ingrese su contraseña" />
                  )}
                />
                {errors.password && <small className="p-error">{errors.password.message}</small>}
              </div>

              <Button type="submit" label="Ingresar" icon="pi pi-sign-in" loading={isSubmitting} className="mt-2 w-full" />
            </form>
          </div>
        </div>
      </div>

      <div className="login-footer">
        Talento Humano &copy; {new Date().getFullYear()}
      </div>
    </div>
  );
}

export const getEnvMode = () => import.meta.env.MODE

export const getEnvName = () => import.meta.env.VITE_MSF_ENV_NAME || ''

export const isLocal = () => getEnvMode() === 'loc'

export const isDevelop = () => getEnvMode() === 'dev'

export const isStaging = () => getEnvMode() === 'stg'

export const isProduction = () => getEnvMode() === 'prd'

export const isNonProduction = () => !isProduction()

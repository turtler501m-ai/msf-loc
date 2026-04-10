import process from 'node:process'
import { fileURLToPath, URL } from 'node:url'

import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import Components from 'unplugin-vue-components/vite'

// https://vite.dev/config/
export default defineConfig(({ command, mode }) => {
  // 💡 loadEnv를 이용해 현재 모드에 맞는 환경 변수를 불러옵니다.
  // 첫 번째 인자: 현재 모드 ('development' 또는 'production')
  // 두 번째 인자: .env 파일이 위치한 경로 (보통 process.cwd() 사용)
  // 세 번째 인자: (옵션) 환경변수 접두사 설정
  const env = loadEnv(mode, process.cwd(), '')
  console.log('>>> Loaded Environment Variables:', env.VITE_MSF_API_URL)

  return {
    plugins: [vue(), vueDevTools(), Components({ dirs: ['src/components', 'src/libs/ui'] })],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url)),
      },
    },
    css: {
      preprocessorOptions: {
        scss: {
          // 모든 컴포넌트에서 공통 변수·믹스인을 import 없이 즉시 사용 가능하도록 자동 주입
          additionalData: `@use '@/assets/styles/abstracts' as *;`,
        },
      },
    },
    server: {
      port: 7080,
      host: '0.0.0.0',
      // proxy: {
      //   '/api': {
      //     target: env.MSF_BASE_URL,
      //     changeOrigin: true,
      //     rewrite: (path) => path.replace(/^\/api/, '')
      //   },
      // },
    },
    build: {
      minify: 'terser',
      chunkSizeWarningLimit: 3000,
      terserOptions: {
        compress: {
          drop_console: mode !== 'logging',
          drop_debugger: mode !== 'logging',
        },
      },
    },
  }
})

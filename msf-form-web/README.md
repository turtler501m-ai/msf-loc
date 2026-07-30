# MSF Form Web

`msf-form-web` 프론트엔드 프로젝트 실행 가이드입니다.

## 개요

- 빌드 도구: `Vite`
- 컨테이너 빌드: `Node 24 Alpine`
- 런타임 서버: `nginx 1.29.7 Alpine`
- 필수 빌드 모드 인자: `BUILD_MODE`

## 로컬 실행 가이드

의존성 설치:

```sh
npm install
```

개발 서버 실행:

```sh
npm run dev
```

개발 서버 포트:

- 로컬 Vite dev server: `7080`

접속 주소:

- [http://localhost:7080](http://localhost:7080)

추가 실행 명령:

```sh
npm run dev:loc
npm run dev:dev
npm run dev:stg
npm run dev:prd
```

프로덕션 빌드:

```sh
npm run build
```

린트 실행:

```sh
npm run lint
```

## Docker 빌드 및 실행 가이드

빌드 모드 지정:

```sh
docker build -t msf-form-web --build-arg BUILD_MODE=stg .
```

사용 가능한 빌드 모드 파일:

- `.env.loc`
- `.env.dev`
- `.env.stg`
- `.env.prd`

`BUILD_MODE`를 지정하지 않으면 Docker 빌드가 실패합니다.

## Docker 실행

컨테이너 실행:

```sh
docker run --rm -p 7080:80 msf-form-web
```

포트 정보:

- 컨테이너 내부 `nginx` 포트: `80`
- 호스트 노출 포트 예시: `7080`

접속 예시:

- [http://localhost:7080](http://localhost:7080)

## 관련 파일

- `Dockerfile`
- `nginx.conf`
- `.dockerignore`

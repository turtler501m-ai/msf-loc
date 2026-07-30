# syntax=docker/dockerfile:1.7

ARG BUILDER_IMAGE=node:24-alpine
ARG RUNTIME_IMAGE=nginx:1.29.7-alpine
ARG BUILD_MODE

FROM ${BUILDER_IMAGE} AS builder

ARG BUILD_MODE
ARG NPM_CACHE_ID=msf-form-web

WORKDIR /app

COPY package.json package-lock.json ./

RUN --mount=type=cache,id=npm-${NPM_CACHE_ID},target=/root/.npm,sharing=locked \
    npm ci --no-audit --no-fund

COPY . .

RUN test -n "${BUILD_MODE}" || (echo "BUILD_MODE is required. Use --build-arg BUILD_MODE=loc|dev|stg|prd" && exit 1)
RUN npm run build -- --mode ${BUILD_MODE}

FROM ${RUNTIME_IMAGE} AS runtime

WORKDIR /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=builder /app/dist ./

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=3s --start-period=10s --retries=3 \
    CMD wget -q -O /dev/null http://127.0.0.1/ || exit 1

CMD ["nginx", "-g", "daemon off;"]

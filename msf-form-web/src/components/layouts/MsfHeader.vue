<template>
  <div class="header-wrap">
    <header class="header-inner">
      <div class="header-logo-wrap">
        <h1 class="logo"><img src="@/assets/images/logo.svg" alt="kt mobile" /></h1>
        <span v-if="envName" class="env-badge" :class="`env-badge--${envMode}`">{{ envName }}</span>
      </div>
      <div class="side-wrap">
        <div class="user-info">
          <router-link to="/setting">
            <p class="name"><span class="avatar"></span>{{ msfUserStore.userInfo?.userName }}</p>
          </router-link>
          <ul v-if="displayCode || displayName" class="infos">
            <li v-if="displayCode">
              {{ displayCode }}
            </li>
            <li v-if="displayName">
              {{ displayName }}
            </li>
          </ul>
        </div>
      </div>
    </header>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useMsfUserStore } from '@/stores/msf_user'
import { getEnvMode, getEnvName } from '@/libs/utils/env.utils'

const msfUserStore = useMsfUserStore()
const envName = getEnvName()
const envMode = getEnvMode()

// 조직 정보
const org = computed(() => msfUserStore.userInfo?.organization)
// 출력 내용
const displayCode = computed(() => org.value?.shopCode || org.value?.agentCode)
const displayName = computed(() => org.value?.shopName || org.value?.agentName)
</script>

<style lang="scss" scoped>
.header-wrap {
  width: 100%;
  height: var(--layout-header-height);
  background-color: var(--color-background);
  border-bottom: var(--border-width-base) solid var(--color-gray-75);
  flex-shrink: 0;
  padding-inline: var(--spacing-x6);
  // 헤더높이(50px) + 디바이스 상단 노치 영역(safe-area) 처리
  @include safe-area(top, 50px, height);
  @include safe-area(top, 0px, padding);
  @include safe-area(left, 24px, padding);
  @include safe-area(right, 24px, padding);

  .header-inner {
    position: relative;
    max-width: var(--layout-max-width);
    width: 100%;
    height: 100%;
    margin: 0 auto;
    @include flex($v: center, $h: space-between);
    .title {
      @include position($p: absolute, $l: 50%, $t: 50%);
      transform: translate(-50%, -50%);
      font-size: var(--font-size-20);
      font-weight: var(--font-weight-bold);
      text-align: center;
    }
    .header-logo-wrap {
      flex-shrink: 0;
      flex-grow: 0;
      @include flex($v: center) {
        gap: rem(8px);
      }
    }
    h1.logo {
      margin: 0;
      padding: 0;
      height: auto;
      @include flex($v: center);
    }
    .side-wrap {
      @include flex($v: center) {
        gap: var(--spacing-x6);
      }
      .user-info {
        @include flex($v: center) {
          gap: var(--spacing-x4);
        }
        .name {
          @include flex($v: center) {
            gap: rem(4px);
          }
          font-size: var(--font-size-14);
          font-weight: var(--font-weight-bold);
          .avatar {
            display: inline-block;
            width: rem(24px);
            height: rem(24px);
            background-image: url('@/assets/images/userAvatar.svg');
            background-position: left center;
            background-size: 100%;
            background-repeat: no-repeat;
          }
        }
        ul.infos {
          padding-inline: var(--spacing-indent);
          @include flex($v: center);
          column-gap: var(--spacing-x2);
          font-size: var(--font-size-14);
          color: var(--color-gray-500);
          & > :not(:last-child) {
            @include separator;
          }
        }
      }
      .side-btns {
        @include flex($v: center) {
          gap: var(--spacing-x2);
        }
        button {
          width: rem(40px);
          height: rem(40px);
          border-radius: rem(8px);
          background: var(--color-gray-100);
          border-color: var(--color-gray-100);
          :deep(.msf-icon) {
            --icon-size: #{rem(24px)};
            color: var(--color-gray-500);
          }
        }
      }
    }
  }
}

</style>

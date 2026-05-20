<template>
  <!-- MsfBaseLayout -->
  <div class="layout-root">
    <!-- MsfHeader -->
    <div class="header-wrap">
      <header class="header-inner">
        <h1 class="logo"><img src="@/assets/images/logo.svg" alt="kt mobile" /></h1>
        <div class="side-wrap">
          <div class="user-info">
            <p class="name"><span class="avatar"></span>홍길동</p>
            <ul class="infos">
              <li>SPT8050</li>
              <li>IT전략팀</li>
            </ul>
          </div>
        </div>
      </header>
    </div>
    <!-- // MsfHeader -->
    <!-- 컨텐츠 -->
    <main class="container-layout">
      <MsfCustomScroll
        :ref="
          (el) => {
            mainScrollRef = el
          }
        "
        class="main-layout-scroll"
        :use-lock="isLayoutLocked"
      >
        <MsfContainer class="main-content">
          <component :is="currentContent" v-if="currentContent" />
          <div v-else>
            <h3>해당 화면({{ screenId }})을 찾을 수 없습니다.</h3>
          </div>
        </MsfContainer>
      </MsfCustomScroll>
    </main>
    <!-- // 컨텐츠 -->
    <!-- MsfBottomNav -->
    <aside class="bottom-container">
      <h2 class="ut-blind">하단 메뉴</h2>
      <ul class="bottom-list">
        <MsfBottomNavItem v-for="menu in menusSample" :key="menu.id" :item="menu" />
        <MsfButton iconOnly="logout" class="logout-btn">로그아웃</MsfButton>
      </ul>
    </aside>
    <!-- // MsfBottomNav -->
  </div>
  <!-- // MsfBaseLayout -->
</template>

<script setup>
import { computed, defineAsyncComponent } from 'vue'
import { mainScrollRef, isLayoutLocked } from '@/hooks/useGlobalScroll'
const props = defineProps({
  screenId: String,
})

// 폴더 안에 있는 모든 vue 파일을 탐색
const components = import.meta.glob('./pages/pub/*.vue')

const currentContent = computed(() => {
  // 파일 경로 패턴: ./pub/화면ID.vue
  const path = `./pages/pub/${props.screenId}.vue`

  if (components[path]) {
    return defineAsyncComponent(components[path])
  }
  return null
})

// 퍼블리싱용 메뉴 샘플
const menusSample = [
  {
    id: '00',
    name: '홈',
    url: '/',
    iconName: 'home',
  },
  {
    id: '01',
    name: '신규/변경',
    url: '/form/newchange',
    iconName: 'formNewChg',
  },
  {
    id: '02',
    name: '서비스 변경',
    url: '/form/servicechange',
    iconName: 'formSvcChg',
  },
  {
    id: '03',
    name: '명의변경',
    url: '/form/ownerchange',
    iconName: 'formOwnChg',
  },
  {
    id: '04',
    name: '서비스 해지',
    url: '/form/termination',
    iconName: 'formSvcCncl',
  },
  {
    id: '05',
    name: '부가기능',
    url: '/extra',
    iconName: 'tempStorage',
    children: [
      { id: '0501', name: '임시저장', url: '/extra/tempsave/TempSavePage' },
      { id: '0502', name: '접수완료 신청서', url: '/extra/receipt/ReceiptPage' },
      {
        id: '0503',
        name: '간편 신청서',
        url: '/extra/simplerequest/SimpleRequestPage',
      },
      { id: '0504', name: '로그인 설정', url: '/extra/mobileapp/MobileAppPage' },
    ],
  },
]
</script>

<style scoped lang="scss">
/* MsfHeader 스타일 */
.header-wrap {
  width: 100%;
  height: var(--layout-header-height);
  background-color: var(--color-background);
  border-bottom: var(--border-width-base) solid var(--color-gray-75);
  flex-shrink: 0;
  padding-inline: var(--spacing-x6);
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
    h1.logo {
      flex-shrink: 0;
      flex-grow: 0;
      margin: 0;
      padding: 0;
      height: auto;
      font-size: inherit;
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
/* MsfBaseLayout 스타일 (레이아웃) */
.layout-root {
  display: flex;
  flex-direction: column;
  height: 100vh;
  height: 100dvh;
  overflow: hidden;
}
.container-layout {
  flex: 1;
  min-height: 0;
  width: 100%;
  max-width: var(--layout-max-width);
  margin: 0 auto;
  overflow: hidden;
  @include flex() {
    gap: rem(8px);
  }
  & > .step-nav {
    flex-shrink: 0;
    flex-grow: 0;
    flex-basis: rem(94px);
  }
  & > .main-content {
    flex: 1;
  }
}
/* MsfBottomNav 스타일 (하단 네비게이션) */
.bottom-container {
  width: 100%;
  height: rem(64px);
  background-color: var(--color-primary-base);
  padding-inline: rem(24px);
}
.bottom-list {
  @include flex($v: center) {
    gap: rem(16px);
  }
  width: 100%;
  max-width: var(--layout-max-width);
  margin: 0 auto;
  height: 100%;
  text-align: center;
}
// 로그아웃 버튼 스타일 지정
.logout-btn {
  background: var(--color-gray-750);
  border-color: var(--color-gray-750);
  border-radius: var(--border-radius-m);
  color: var(--color-gray-150);
  width: rem(44px);
  height: rem(44px);
  padding: 0;
  :deep(i) {
    --icon-size: #{rem(24px)};
  }
  &:hover {
    background: var(--color-gray-750);
    border-color: var(--color-gray-750);
  }
}
</style>

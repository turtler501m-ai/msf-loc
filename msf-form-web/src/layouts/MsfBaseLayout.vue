<template>
  <div class="layout-root">
    <MsfHeader />
    <main class="container-layout">
      <MsfContainer class="main-content">
        <RouterView />
      </MsfContainer>
    </main>
    <MsfBottomNav />
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
import { MsfHeader, MsfContainer, MsfBottomNav } from '@/components/layouts'
</script>

<style scoped lang="scss">
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
</style>

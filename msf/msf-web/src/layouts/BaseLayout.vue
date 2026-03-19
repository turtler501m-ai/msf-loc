<template>
  <div class="layout-root">
    <MsfHeader />
    <main class="container">
      <aside class="side-nav"><MsfSideNav /></aside>
      <MsfContainer class="main-content"><RouterView /></MsfContainer>
    </main>
  </div>
</template>

<script setup>
import { RouterView } from 'vue-router'
import { MsfHeader, MsfSideNav, MsfContainer } from '@/components/layouts'
</script>

<style scoped lang="scss">
.layout-root {
  display: flex;
  flex-direction: column;
  height: 100vh;
}
.container {
  width: 100%;
  max-width: var(--layout-max-width);
  margin: 0 auto;
  flex: 1;
  @include flex() {
    gap: rem(40px);
  }
  & > .lnb {
    flex-shrink: 0;
    flex-grow: 0;
    flex-basis: rem(200px);
    display: flex;
    flex-direction: column;
    gap: 20px;
    padding: 20px;
  }
  & > .main-content {
    flex: 1;
  }
}
</style>
